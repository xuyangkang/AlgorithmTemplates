README
===

This is my code template and Emacs config for ACM/ICPC, TopCoder, CodeForces and other sport programming contests.

It is written in C++11 and integrates with emacs.

Tested with Emacs 24.

C++ Google style guide is strongly recommended.

Warm welcome for contributing and challenging!

## Install
1. Put or merge emacs_config.el to your ~/.emacs
2. In .emacs, Change dirs like "~/AlgorithmTemplates/snippets" to your location
3. M-x irony-install-server (you may need clang library to make irony server)

## Usage
1. M-[ M-] to move between frames
2. Type C++ keywords like for, while, if, etc + tab to insert code template
3. See snippets, type keys like __base to insert custom template
4. F5 to compile, F6 to debug in GDB

## Development Guide

For example we're creating a custom template, called algo1. Then ideally we'll have 2 files, algo1.h and algo1.cpp.

In algo1.h, the file should be guarded with #ifndef, #define and #endif, and NO #include in it.

In algo1.cpp, we include base.h and algo1.h first, then in main we run testcases for algo1.

We'll create another script, to transform *.h to Emacs snippets.
