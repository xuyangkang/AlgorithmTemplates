test:
	g++ -o test full_test.cpp -std=c++11 -O3

install:
	cp ../AlgoTemplates/ ~/ -rf
	echo "Please patch emacs_config.txt to your .emacs"

uninstall:
	rm -rf ~/AlgoTemplates/

clean:
	rm -rf *~
	rm -rf test
