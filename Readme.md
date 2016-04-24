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
3. See snippets, type keys like __base to insert customized template
4. F5 to compile, F6 to debug in GDB
