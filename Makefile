install:
	cp ../AlgoTemplates/ ~/ -rf

uninstall:
	rm -rf ~/AlgoTemplates/

test:
	g++ -o test full_test.cpp -std=c++11 -O3

clean:
	rm -rf *~
	rm -rf test
