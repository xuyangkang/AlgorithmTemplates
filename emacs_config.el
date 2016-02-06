;; Automaticly download and install packages
;; TODO: The priority of elpa is still magic...
(require 'package)
(require 'cl)

(setq package-archives '(("gnu" . "https://elpa.gnu.org/packages/")
                         ("marmalade" . "https://marmalade-repo.org/packages/")
                         ("melpa" . "https://melpa.org/packages/")))
(package-initialize)

(defconst my-packages '(better-defaults
                        flycheck
                        irony
                        company
                        company-irony
                        company-irony-c-headers
                        go-mode
                        yasnippet))

(defun my-packages-installed-p ()
  (loop for p in my-packages
        when (not (package-installed-p p)) do (return nil)
        finally (return t)))

(unless (my-packages-installed-p)
  (message "%s" "Emacs Prelude is now refreshing its package database...")
  (package-refresh-contents)
  (message "%s" " done.")
  (dolist (p my-packages)
    (when (not (package-installed-p p))
      (package-install p))))

;; use space instead of tab
(setq-default indent-tabs-mode nil)

;; do not create backup files
(setq make-backup-files nil)

;; hide start up screen
(setq inhibit-startup-screen t)

;; unset visible-bell
(setq visible-bell nil)

;; faster window switch
(global-set-key (kbd "M-[") 'previous-multiframe-window)
(global-set-key (kbd "M-]") 'next-multiframe-window)

;; remove trailing whitespace when saving
(add-hook 'before-save-hook 'delete-trailing-whitespace)

;; F5 for compile, F6 for debug and run
(defun compile-buffer()
  (interactive)
  (delete-other-windows)
  (save-buffer)
  (compile (concat "g++ -std=c++11 -g -Werror -o " buffer-file-name ".out " buffer-file-name)))

(defun debug-buffer()
  (interactive)
  (compile-buffer)
  (delete-other-windows)
  (split-window-vertically)
  (next-multiframe-window)
  (gud-gdb (concat "gdb --fullname " buffer-file-name ".out")))

(define-minor-mode cc-sport-mode
  "Sport mode for C++."
  :lighter " cc-sport"
  :keymap (let ((map (make-sparse-keymap)))
            (define-key map [f5] 'compile-buffer)
            (define-key map [f6] 'debug-buffer)
            map))
(add-hook 'c++-mode-hook 'cc-sport-mode)

;; YASnippet for code template
(require 'yasnippet)
(setq yas-snippet-dirs (append yas-snippet-dirs
			       '("~/AlgorithmTemplates/snippets")))
(yas-reload-all)
(add-hook 'prog-mode-hook #'yas-minor-mode)

;; in each header file we define a macro __SWEET__TEMPLATE_NAME__
;; dump all the macros defined to a temp file
;; so we're able to grep the file to check if the lib is imported before.
(defun dump-macros ()
  (save-buffer)
  (shell-command (concat "g++ -E -dM -std=c++11 " buffer-file-name " | grep __SWEET > /tmp/macros.txt" )))

(defun check-lib-imported (name)
  (shell-command (concat "grep -q " (upcase name) " /tmp/macros.txt")))

(defconst template-dependency
  '(("sweet_bits" "sweet_base")
    ("sweet_base")))

(defun import-single-template (name)
  (dump-macros)
  (unless (eq (check-lib-imported name) 0)
    (yas-expand-snippet (yas-lookup-snippet name))))

(defun import-worker (templates)
  (when templates
    (progn
      (import-worker (cdr templates))
      (let ((name (car templates)))
        (import-worker (cdr (assoc name template-dependency)))
        (import-single-template name)))))

(defun import-template (name)
  "Import a pre-defined template"
  (interactive "sWhich template do you want to import? ")
  (import-worker (assoc (concat "sweet_" name) template-dependency)))

;; irony for C++ indexing
(add-hook 'c++-mode-hook 'irony-mode)
(add-hook 'c-mode-hook 'irony-mode)
(add-hook 'objc-mode-hook 'irony-mode)
(defun my-irony-mode-hook ()
  (define-key irony-mode-map [remap completion-at-point]
    'irony-completion-at-point-async)
  (define-key irony-mode-map [remap complete-symbol]
    'irony-completion-at-point-async))
(add-hook 'irony-mode-hook 'my-irony-mode-hook)
(add-hook 'irony-mode-hook 'irony-cdb-autosetup-compile-options)

;; company
(eval-after-load 'company
  '(add-to-list
    'company-backends '(company-irony-c-headers company-irony)))
(add-hook 'after-init-hook 'global-company-mode)

;; flycheck
(add-hook 'after-init-hook 'global-flycheck-mode)
(add-hook 'c++-mode-hook (lambda () (setq flycheck-gcc-language-standard "c++11")))
