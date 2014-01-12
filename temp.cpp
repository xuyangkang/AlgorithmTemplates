#include <cctype>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iostream>
#include <map>
#include <set>
#include <string>
#include <unordered_map>
#include <unordered_set>
#include <utility>
#include <vector>

using namespace std;

// type define
typedef char int8;
typedef unsigned char uint8;
typedef short int16;
typedef unsigned short uint16;
typedef int int32;
typedef unsigned int uint32;
typedef long long int64;
typedef unsigned long long uint64;

// float utilities
const double EPS = 1E-8;
inline bool eq(double a, double b) { return a - b < EPS && b - a < EPS; }
inline bool ne(double a, double b) { return a + EPS < b || b + EPS < a; }
inline bool ls(double a, double b) { return a + EPS < b; }
inline bool gr(double a, double b) { return b + EPS < a; }
inline bool le(double a, double b) { return a - EPS < b; }
inline bool ge(double a, double b) { return b - EPS < a; }

void float_utilities_test() {
  for (int i = -1; i <= 1; i++) {
    cerr << eq(i, 0) << endl;
    cerr << ne(i, 0) << endl;
    cerr << ls(i, 0) << endl;
    cerr << gr(i, 0) << endl;
    cerr << le(i, 0) << endl;
    cerr << ge(i, 0) << endl;
  }
}

int main(int argc, char **argv) {
  float_utilities_test();
  return 0;
}
