README
===

This is my code template for ACM/ICPC, TC, CF and other sport programming games.

It is written in C++11 and integrates with emacs.

Tested with Ubuntu 15.04 and Emacs 24.

C++ Google style guide is strongly recommended.

Warm welcome for contributing and challenging!

##Usage:
For ACM/ICPC on-site game: Type emacs_minimum_config.el to ~/.emacs

For normal case:

1. Put emacs_config.el to ~/.emacs(or merge it into your config)
2. Change snippet dir "~/AlgorithmTemplates/snippets" to your location
3. M-X irony-install-server (you may need clang library to build irony)

After open a cpp file, a hook will triggers sport-cc-mode, F5 to compile, F6 to debug in GDB.

M-X import to "inline" template into current file
