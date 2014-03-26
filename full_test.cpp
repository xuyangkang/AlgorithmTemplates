#include "basic_temp.hpp"
#include "float_utility.hpp"

#include <cassert>
#include <cstdio>

void float_test() {
  assert(!ls(1, 0));  assert(!ls(0, 0));  assert(ls(0, 1));
  assert(gr(1, 0));  assert(!gr(0, 0));  assert(!gr(0, 1));
  assert(!eq(1, 0));  assert(eq(0, 0));  assert(!eq(0, 1));
  assert(ge(1, 0));  assert(ge(0, 0));  assert(!ge(0, 1));
  assert(!le(1, 0));  assert(le(0, 0));  assert(le(0, 1));
  assert(ne(1, 0));  assert(!ne(0, 0));  assert(ne(0, 1));
}

int main() {
  float_test();
  puts("All test passed!");
  return 0;
}
