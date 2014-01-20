AlgoTemplates
===

I'll backup and share some of my codes in competitive programming here.

A guy forked my old template before I pruged them. See: [here] (https://github.com/wangxiaodiu/ACM)

Design
===

I use Emacs and C++.

+ Why C++? Because Java is very tricky. Everything is reference except the basic types(int, char...). Sometimes it causes problems which is very hard to find. I know Java has rich resources like BigInteger class, isProbablePrime(), regex... But it's not used very often in programming contests. So it's OK to use C++.

+ Why Emacs? It works very well with GNU toolkits like Make, GDB. You can "deep" configure it with Emacs lisp. 

+ The most important thing: I love Emacs! // VIM MUST DIE!!

Program is consists of algorithm and data structure. So the design will look like that:

    class DataStructure {
    };
    
    class ProbSolver {
      DataStructure ds;
      ProbSolver(DataStructure);
      void solve();
    };
