CC = g++
CFLAGS = -g -std=c++11 -Wall

all: $(patsubst %.cpp, %.out, $(wildcard *.cpp))

%.out: %.cpp Makefile
	$(CC) $(CFLAGS) $< -o $@
clean:
	rm *.out
