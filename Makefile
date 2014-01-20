install:
	mv ~/.emacs ~/.emacs_bak
	cp ./emacs_config.txt ~/.emacs
	cp ./ ~/ -rf

uninstall:
	mv ~/.emacs_bak ~/.emacs

test:
	g++ -o test full_test.cpp -std=c++11 -O3

clean:
	rm -rf *~
	rm test
