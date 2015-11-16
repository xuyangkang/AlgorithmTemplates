(custom-set-variables
 ;; custom-set-variables was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 '(delete-selection-mode nil)
 '(inhibit-startup-screen t)
 '(mark-even-if-inactive t)
 '(scroll-bar-mode (quote right))
 '(transient-mark-mode 1))

(require 'package)
(require 'cl)

(package-initialize)

;; Automaticly download and install packages
(setq package-archives
      '(("gnu" . "http://elpa.gnu.org/packages/")
        ("marmalade" . "https://marmalade-repo.org/packages/")))

(defvar my-packages '(better-defaults
                      projectile
                      clojure-mode
                      cider))

(defvar packages-to-install
  (cl-remove-if (lambda (p) (package-installed-p p)) my-packages))

(unless (eq packages-to-install nil)
  (package-refresh-contents)
  (dolist (p packages-to-install)
    (package-install p)))

;; highlight current line
(global-hl-line-mode 1)

`;; use space instead of tab
(setq-default indent-tabs-mode nil) 

;; do not create backup files
(setq make-backup-files nil) 

;; key binding for goto line
(global-set-key (kbd "M-g") 'goto-line)

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
    (end-of-buffer)
    (insert-file-contents (concat algorithm-template-root name ".h"))
    (end-of-buffer)))

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
