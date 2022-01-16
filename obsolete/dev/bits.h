/* Copyright 2015 Xuyang Kang */

#ifndef BITS_H_
#define BITS_H_

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
  return i - static_cast<uint32>(i >> 1);
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
  uint32 j = static_cast<uint32>(i);
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

struct StaticRunner {
  StaticRunner() {
    for (int i = 0; i < 256; i++) {
      bits::bits_set_table[i] = (i & 1) + bits::bits_set_table[i / 2];
    }
  }
} static_runner;

}  // namespace bits

#endif  // BITS_H_
