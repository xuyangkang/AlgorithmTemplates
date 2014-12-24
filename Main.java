import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class Main implements Runnable {

  private void solve(QuickScanner in, PrintWriter out) {
    // TODO(xuyang): Write code here.
    out.println("Hello World!");
  }

  private static final double EPS = 1E-8;
  private static final long MOD = 1000000007L;

  private static int signum(double val) {
    if (Math.abs(val) < EPS) {
      return 0;
    }
    return val > 0 ? 1 : -1;
  }

  private static int cmp(double x, double y) {
    return signum(x - y);
  }

  private static void debug(Object... objects) {
    System.err.println(Arrays.deepToString(objects));
  }

  private static void assertTrue(boolean cond) {
    if (!cond) {
      throw new AssertionError();
    }
  }

  /**
   * A util function to get current time. This is often used in some search or random algorithm for
   * limit program run time.
   * 
   * @return currentTime in MS.
   */
  private static long currentTime() {
    return System.currentTimeMillis();
  }

  private class QuickScanner {
    private BufferedReader bufferedReader = null;
    private StringTokenizer stringTokenizer = null;
    private String nextHolder = null;

    QuickScanner() {
      bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    QuickScanner(String fileName) {
      try {
        bufferedReader = new BufferedReader(new FileReader(fileName));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }

    boolean hasNext() {
      // Try to get next and put it into nextHolder.
      if (nextHolder == null) {
        nextHolder = next();
      }
      return nextHolder != null;
    }

    String next() {
      // If called hasNext before, should return string in nextHolder.
      if (nextHolder != null) {
        String next = nextHolder;
        nextHolder = null;
        return next;
      }

      try {
        while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
          String newLine = bufferedReader.readLine();
          if (newLine != null) {
            stringTokenizer = new StringTokenizer(newLine);
          } else {
            return null;
          }
        }
        return stringTokenizer.nextToken();
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }

    int nextInt() {
      return Integer.parseInt(next());
    }

    long nextLong() {
      return Long.parseLong(next());
    }

    double nextDouble() {
      return Double.parseDouble(next());
    }

    BigInteger nextBigInteger() {
      return new BigInteger(next());
    }

    BigDecimal nextBigDecimal() {
      return new BigDecimal(next());
    }
  }

  private static class MathUtils {

    private static final BigInteger BIG_ZERO = new BigInteger("0");
    private static final BigInteger BIG_ONE = new BigInteger("1");
    private static final BigInteger BIG_TWO = new BigInteger("2");
    private static final SecureRandom RANDOM = new SecureRandom();

    static boolean isProbablePrime(long x) {
      return BigInteger.valueOf(x).isProbablePrime(30);
    }

    static long multiplyMod(long a, long b, long c) {
      a %= c;
      b %= c;
      long ret = 0;
      while (b > 0) {
        if ((b & 1) == 1) {
          ret += a;
          if (ret >= c) {
            ret -= c;
          }
        }
        a <<= 1;
        if (a >= c) {
          a -= c;
        }
        b >>= 1;
      }
      return ret;
    }


    static long modPow(long x, long n, long mod) {
      long ret = 1;
      while (n > 0) {
        if ((n & 1) == 1) {
          ret = multiplyMod(ret, x, mod);
        }
        x = multiplyMod(x, x, mod);
        n >>= 1;
      }
      return ret;
    }

    static long reverseRoot(long x, long prime) {
      assertTrue(isProbablePrime(prime));
      return modPow(x, prime - 2, prime);
    }

    static BigInteger rho(BigInteger N) {
      BigInteger divisor = null;
      BigInteger c = new BigInteger(N.bitLength(), RANDOM);
      BigInteger x = new BigInteger(N.bitLength(), RANDOM);
      BigInteger xx = x;

      // check divisibility by 2
      if (N.mod(BIG_TWO).compareTo(BIG_ZERO) == 0) {
        return BIG_TWO;
      }

      do {
        x = x.multiply(x).mod(N).add(c).mod(N);
        xx = xx.multiply(xx).mod(N).add(c).mod(N);
        xx = xx.multiply(xx).mod(N).add(c).mod(N);
        divisor = x.subtract(xx).gcd(N);
      } while ((divisor.compareTo(BIG_ONE)) == 0);

      return divisor;
    }

    static void factorWorker(BigInteger N, List<BigInteger> result) {
      if (N.compareTo(BIG_ONE) == 0) {
        return;
      }
      if (N.isProbablePrime(30)) {
        result.add(N);
        return;
      }
      BigInteger divisor = rho(N);
      factorWorker(divisor, result);
      factorWorker(N.divide(divisor), result);
    }

    static List<BigInteger> factor(BigInteger N) {
      List<BigInteger> result = new ArrayList<BigInteger>();
      factorWorker(N, result);
      Collections.sort(result);
      return result;
    }
  }

  /**
   * A class to handle range coloring problem.
   *
   */
  private class Intervals {
    private TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();

    Intervals() {
      this(-1);
    }

    /**
     * Color range (-inf, inf) to default color.
     * 
     * @param defaultColor the default color in int.
     */
    Intervals(int defaultColor) {
      map.put(Integer.MIN_VALUE, defaultColor);
      map.put(Integer.MAX_VALUE, defaultColor);
    }


    /**
     * Color range [left, right) to color. The cost is O(logN).
     * 
     * @param left the left border of the range, inclusive.
     * @param right the right border of the range, exclusive.
     * @param color the new color of the range.
     */
    void paintColor(int left, int right, int color) {
      int prevColor = getColor(right);
      map.subMap(left, right).clear();
      map.put(left, color);
      map.put(right, prevColor);
    }


    /**
     * Return current color by index.
     * 
     * @param index the index to query.
     * @return the color of position index.
     */
    int getColor(int index) {
      return map.floorEntry(index).getValue();
    }

    /**
     * Return the map for some future operation.
     * 
     * @return the map itself.
     */
    TreeMap<Integer, Integer> getMap() {
      return map;
    }
  }

  /**
   * A class to solve Range Minimum/Maximum Query problem.
   *
   */
  private class RMQ {

    private int[] data;
    private int[][] table;
    private final Comparator<Integer> comparator;

    /**
     * Build Sparse Table with comparator.
     * 
     * @param data the origin data.
     * @param comparator how to compare the data.
     */
    RMQ(int[] data, Comparator<Integer> comparator) {
      int n = data.length;
      int m = log2(n) + 1;
      this.data = data;
      this.comparator = comparator;
      table = new int[m][n];
      for (int i = 0; i < n; i++) {
        table[0][i] = i;
      }
      for (int i = 1, k = 1; i < m; i++, k <<= 1) {
        for (int j = 0; j + k < n; j++) {
          if (comparator.compare(data[table[i - 1][j]], data[table[i - 1][j + k]]) <= 0) {
            table[i][j] = table[i - 1][j];
          } else {
            table[i][j] = table[i - 1][j + k];
          }
        }
      }
    }

    /**
     * Query and return the index of the "smallest" number defined by comparator in range[from, to)
     * 
     * @param from the left border of the range, inclusive.
     * @param to the right border of the range, exclusive.
     * @return the index of the "smallest" number.
     */
    int query(int from, int to) {
      int k = log2(to - from);
      if (comparator.compare(data[table[k][from]], data[table[k][to - (1 << k)]]) <= 0) {
        return table[k][from];
      } else {
        return table[k][to - (1 << k)];
      }
    }

    private int log2(int b) {
      return 31 - Integer.numberOfLeadingZeros(b);
    }
  }

  // Tedious code.
  public Main() {}

  @Override
  public void run() {
    QuickScanner in = new QuickScanner();
    PrintWriter out = new PrintWriter(System.out);
    try {
      solve(in, out);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.flush();
    }
  }

  public static void main(String[] args) {
    new Main().run();
  }

}
