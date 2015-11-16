(defun compile-buffer() 
  (interactive)
  (save-buffer)
  (compile (concat "g++ -std=c++11 -g -Werror -o " buffer-file-name ".out " buffer-file-name)))

(defun debug-buffer() 
  (interactive)
  (compile-buffer)
  (gud-gdb (concat "gdb --fullname " buffer-file-name ".out")))

(global-set-key (kbd "M-[") 'previous-multiframe-window)
(global-set-key (kbd "M-]") 'next-multiframe-window)
