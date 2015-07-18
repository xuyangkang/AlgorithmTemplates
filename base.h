/* Copyright 2015 Xuyang Kang */

#ifndef BASE_H_
#define BASE_H_
#define __SWEET_BASE_H_

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

using int8 = int8_t;
using int16 = int16_t;
using int32 = int32_t;
using int64 = int64_t;
using uint8 = uint8_t;
using uint16 = uint16_t;
using uint32 = uint32_t;
using uint64 = uint64_t;
using byte = uint8_t;

using std::cin;
using std::cout;
using std::cerr;
using std::endl;

using std::vector;
using std::string;
using std::set;
using std::map;
using std::unordered_set;
using std::unordered_map;

using std::min;
using std::max;
using std::sort;

const double PI = acos(0.0) * 2.0;
const double EPS = 1e-10;
const int MOD = 1000000007;
const char WS[] = " \n";

#ifdef LOCAL_DEBUG
#define debug(x) cerr << #x << " = " << x << endl
#else
#define debug(x) {}
#endif

struct StaticRunner {
  StaticRunner() {
    std::ios_base::sync_with_stdio(0);
  }
} static_runner;

#endif  // BASE_H_
