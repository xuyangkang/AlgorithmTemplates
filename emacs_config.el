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

;; faster window switch
(defun back-window ()
  (interactive)
  (other-window -1))
(global-set-key [M-left] 'previous-multiframe-window)
(global-set-key [M-right] 'next-multiframe-window)
(defadvice split-window (after move-point-to-new-window activate)
  "Moves the point to the newly created window after splitting."
  (other-window 1))

;; get output file name.
(defun get-output-filename ()
  (concat buffer-file-name ".out"))

;; get compile command.
(defun get-compile-command()
  (concat "g++ -std=c++11 -g -o " (get-output-filename) " " buffer-file-name))

;; compile single cpp file
(defun compile-buffer() 
  (interactive)
  (save-buffer)
  (compile (get-compile-command)))

;; debug single cpp file
(defun debug-buffer() 
  (interactive)
  (compile-buffer)
  (gud-gdb (concat "gdb --fullname " (get-output-filename))))

(defun my-hook ()
  (define-key c++-mode-map [f5] 'compile-buffer)  
  (define-key c++-mode-map [f6] 'debug-buffer))

(add-hook 'c++-mode-hook 'my-hook)
