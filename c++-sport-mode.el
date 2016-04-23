;;; c++-sport-mode.el --- A minor mode for sport programming in C++

;;; Commentary:
;;

;;; code:

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

(define-minor-mode c++-sport-mode
  "Sport mode in C++"
  :lighter " c++-sport"
  :keymap (let ((map (make-sparse-keymap)))
            (define-key map [f5] 'compile-buffer)
            (define-key map [f6] 'debug-buffer)
            map))
(add-hook 'c++-mode-hook 'cc-sport-mode)

(provide 'c++-sport-mode)
;;; c++-sport-mode ends here
