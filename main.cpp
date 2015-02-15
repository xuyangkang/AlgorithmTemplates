#include <algorithm>
#include <bitset>
#include <cassert>
#include <cctype>
#include <climits>
#include <cmath>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <ctime>
#include <deque>
#include <iomanip>
#include <iostream>
#include <fstream>
#include <functional>
#include <map>
#include <numeric>
#include <queue>
#include <set>
#include <sstream>
#include <stack>
#include <string>
#include <unordered_map>
#include <unordered_set>
#include <utility>
#include <vector>

using namespace std;

using int8 = char;
using int16= short;
using int32 = int;
using int64 = long long;
using uint8 = unsigned char;
using uint16 = unsigned short;
using uint32 = unsigned int;
using uint64 = unsigned long long;
using byte = unsigned char;

const double PI = acos(0.0) * 2.0;
const double EPS = 1e-10;
const int MOD = 1E9 + 7;

#ifdef LOCAL_DEBUG
#define debug(x) cerr << #x << " = " << x << endl
#else
#define debug(x) ;
#endif

namespace bits {
// TODO(xuyang): Grab more codes from:
// http://graphics.stanford.edu/~seander/bithacks.html
inline int LowestOneBit(int i) {
  return i & -i;
}

inline int HighestOneBit(int i) {
  i |= (i >> 1);
  i |= (i >> 2);
  i |= (i >> 4);
  i |= (i >> 8);
  i |= (i >> 16);
  return i - ((uint32) i >> 1);
}

int bits_set_table[256];
inline int BitCount(int i) {
  unsigned char * p = (unsigned char *) &i;
  return bits_set_table[p[0]] + bits_set_table[p[1]] + bits_set_table[p[2]]
      + bits_set_table[p[3]];
}

int NumberOfLeadingZeros(int i) {
  if (i == 0) {
    return 32;
  }
  uint32 j = (uint32) i;
  int n = 1;
  if ((j >> 16) == 0)
    n += 16, j <<= 16;
  if ((j >> 24) == 0)
    n += 8, j <<= 8;
  if ((j >> 28) == 0)
    n += 4, j <<= 4;
  if ((j >> 30) == 0)
    n += 2, j <<= 2;
  n -= j >> 31;
  return n;
}
}  // namespace bits

struct StaticRunner {
  StaticRunner() {
    ios_base::sync_with_stdio(0);
    for (int i = 0; i < 256; i++) {
      bits::bits_set_table[i] = (i & 1) + bits::bits_set_table[i / 2];
    }
  }
} static_runner;

int main() {
  cout << "Hello World!" << endl;
  return 0;
}
