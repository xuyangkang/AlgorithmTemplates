;; Automaticly download and install packages
;; TODO: The priority of elpa is still magic...
(require 'package)
(require 'cl)

;; Set up packages
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

;; Let c++-mode handle .h
(add-to-list 'auto-mode-alist '("\\.h\\'" . c++-mode))

;; C++ sport mode
(add-to-list 'load-path "~/AlgorithmTemplates/")
(require 'c++-sport-mode)

;; YASnippet for code template
(require 'yasnippet)
(setq yas-snippet-dirs (append yas-snippet-dirs
			       '("~/AlgorithmTemplates/snippets")))
(yas-reload-all)
(add-hook 'prog-mode-hook #'yas-minor-mode)

;; irony for C++ indexing
(require 'irony)
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
(require 'company)
(eval-after-load 'company
  '(add-to-list
    'company-backends '(company-irony-c-headers company-irony)))
(add-hook 'after-init-hook 'global-company-mode)

;; flycheck
(require 'flycheck)
(add-hook 'after-init-hook 'global-flycheck-mode)
(setq-default flycheck-clang-language-standard "c++11"
              flycheck-gcc-language-standard "c++11")
