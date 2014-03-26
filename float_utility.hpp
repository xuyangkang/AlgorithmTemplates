#ifndef __FLOAT__UTILITI__
#define __FLOAT__UTILITI__
// float utilities
const double EPS = 1E-8;
inline bool eq(double a, double b) { return a - b < EPS && b - a < EPS; }
inline bool ne(double a, double b) { return a + EPS < b || b + EPS < a; }
inline bool ls(double a, double b) { return a + EPS < b; }
inline bool gr(double a, double b) { return b + EPS < a; }
inline bool le(double a, double b) { return a - EPS < b; }
inline bool ge(double a, double b) { return b - EPS < a; }
#endif
