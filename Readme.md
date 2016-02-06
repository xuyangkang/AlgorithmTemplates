README
===

This is my code template for ACM/ICPC, TC, CF and other sport programming games.

It is written in C++11 and integrates with emacs.

Tested with Ubuntu 15.04 and Emacs 24.

C++ Google style guide is strongly recommended.

Warm welcome for contributing and challenging!

## Install
For ACM/ICPC on-site: Type emacs_minimum_config.el and save as ~/.emacs

For full:

1. Put or emacs_config.el to ~/.emacs
2. In .emacs, Change my snippet template dir "~/AlgorithmTemplates/snippets" to your
3. M-x irony-install-server (you may need clang library to build irony)

## Usage
1. F5 to compile, F6 to debug in GDB
2. M-[ M-] to move in frames
3. Type keyworks(for, while, if, etc) + tab to trigger template
4. M-x import-template, then type template name to "inline" template into current file
