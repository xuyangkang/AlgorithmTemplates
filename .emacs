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

(custom-set-faces
 ;; custom-set-faces was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 )

;; highlight selection area
(transient-mark-mode t)

;; highlight current line
(global-hl-line-mode 1)

;; use space instead of tab
(setq-default indent-tabs-mode nil) 

;; do not create backup files
(setq make-backup-files nil) 

;; key binding for goto line
(global-set-key (kbd "M-g") 'goto-line)
