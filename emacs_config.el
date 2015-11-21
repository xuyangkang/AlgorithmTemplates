(require 'package)
(require 'cl)

;; Automaticly download and install packages
(package-initialize)

(setq package-archives
      '(("gnu" . "http://elpa.gnu.org/packages/")
        ("marmalade" . "https://marmalade-repo.org/packages/")))

(defconst my-packages '(better-defaults
                        projectile
                        flycheck
                        go-mode
                        clojure-mode
                        cider))

(defun my-packages-installed-p ()
  (loop for p in my-packages
        when (not (package-installed-p p)) do (return nil)
        finally (return t)))

(unless (my-packages-installed-p)
  (message "%s" "Emacs Prelude is now refreshing its package database...")
  (package-refresh-contents)
  (message "%s" " done.")
  ;; install the missing packages
  (dolist (p my-packages)
    (when (not (package-installed-p p))
      (package-install p))))

;; use space instead of tab
(setq-default indent-tabs-mode nil)

;; do not create backup files
(setq make-backup-files nil)

;; hide start up screen
(setq inhibit-startup-screen t)

;; faster window switch
(global-set-key (kbd "M-[") 'previous-multiframe-window)
(global-set-key (kbd "M-]") 'next-multiframe-window)

(defconst algorithm-template-root
  (expand-file-name "~/AlgorithmTemplates/")
  "The position of algorithm templates")

;; in each header file we define a macro __SWEET__TEMPLATE_NAME__
;; dump all the macros defined to a temp file
;; so we're able to grep the file to check if the lib is imported before.
(defun dump-macros ()
  (save-buffer)
  (shell-command (concat "g++ -E -dM -std=c++11 " buffer-file-name " | grep __SWEET > macros.txt" )))
(defun check-lib-imported (name)
  (shell-command (concat "grep -q " (upcase name) " ./macros.txt")))

;; copy-paste a template into cursor
;; if the template is already here, do nothing
(defun import-worker (name)
  (dump-macros)
  (unless (eq (check-lib-imported name) 0)
    (goto-char (point-max))
    (insert-file-contents (concat algorithm-template-root name ".h"))
    (goto-char (point-max))))

(defun import-base()
  (interactive)
  (import-worker "base"))

(defun import-bits()
  (interactive)
  (import-base)
  (import-worker "bits"))

;; compile single cpp file
(defun compile-buffer()
  (interactive)
  (save-buffer)
  (compile (concat "g++ -std=c++11 -g -Werror -o " buffer-file-name ".out " buffer-file-name)))

;; debug single cpp file
(defun debug-buffer()
  (interactive)
  (compile-buffer)
  (gud-gdb (concat "gdb --fullname " buffer-file-name ".out")))

;; F5 for compile and F6 for debug and run
(defun my-hook ()
  (define-key c++-mode-map [f5] 'compile-buffer)
  (define-key c++-mode-map [f6] 'debug-buffer))

(add-hook 'c++-mode-hook 'my-hook)
(add-hook 'after-init-hook 'global-flycheck-mode)
(add-hook 'before-save-hook 'delete-trailing-whitespace)
(add-hook 'c++-mode-hook (lambda () (setq flycheck-gcc-language-standard "c++11")))
