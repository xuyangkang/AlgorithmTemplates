install:
	mv ~/.emacs ~/.emacs_bak
	cp ./emacs_config.txt ~/.emacs
	cp ./ ~/ -rf

uninstall:
	mv ~/.emacs_bak ~/.emacs
