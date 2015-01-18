import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;


public class Main implements Runnable {

  private static final String inputFilePath = "";

  private void solve(QuickScanner in, PrintWriter out) {
    out.println("Hello World!");
  }

  private class SegmentTree {
    class TreeNode {
      int l;
      int r;
      TreeNode lc;
      TreeNode rc;

      // Add data field below.

      // Implement these according to problems. May add args.
      TreeNode pushUp() {
        throw new AssertionError("Push up is not implemented.");
      }

      TreeNode pushDown() {
        if (lc == null) {
          this.split();
        }
        throw new AssertionError("Push down is not implemented.");
      }

      TreeNode update() {
        throw new AssertionError("Update is not implemented.");
      }

      TreeNode() {
        this(0, 0, null, null);
      }

      TreeNode(int l, int r) {
        this(l, r, null, null);
      }

      TreeNode(int l, int r, TreeNode lc, TreeNode rc) {
        this.l = l;
        this.r = r;
        this.lc = lc;
        this.rc = rc;
      }

      int getMid() {
        return (l + r) >> 1;
      }

      boolean isLeaf() {
        return l == r;
      }

      TreeNode split() {
        if (l != r) {
          int mid = getMid();
          lc = new TreeNode(l, mid);
          rc = new TreeNode(mid + 1, r);
        }
        return this;
      }

      TreeNode expand() {
        split();
        if (!isLeaf()) {
          lc.expand();
          rc.expand();
        }
        return this;
      }
    }

    TreeNode root;

    SegmentTree(int l, int r) {
      this(l, r, false);
    }

    SegmentTree(int l, int r, boolean dynamicExpand) {
      root = new TreeNode(l, r);
      if (!dynamicExpand) {
        root.expand();
      }
    }

    void updateKey(int key) {
      updateKeyWorker(root, key);
    }

    private void updateKeyWorker(TreeNode now, int key) {
      // now.update();
      // now.pushDown();
      if (!now.isLeaf()) {
        if (key <= now.getMid()) {
          updateKeyWorker(now.lc, key);
        } else {
          updateKeyWorker(now.rc, key);
        }
        // now.pushUp();
      }
    }

    void updateRange(int l, int r) {
      updateRangeWorker(root, l, r);
    }

    private void updateRangeWorker(TreeNode now, int l, int r) {
      if (r < now.l || l > now.r) {
        return;
      }
      if (l <= now.l && now.r <= r) {
        // now.update();
      } else {
        // now.pushDown();
        updateRangeWorker(now.lc, l, r);
        updateRangeWorker(now.rc, l, r);
        // now.pushUp();
      }
    }

    void queryKey(int key) {
      queryKeyWorker(root, key);
    }

    private void queryKeyWorker(TreeNode now, int key) {
      if (!now.isLeaf()) {
        // now.pushDown();
        if (key <= now.getMid()) {
          queryKeyWorker(now.lc, key);
        } else {
          queryKeyWorker(now.lc, key);
        }
        // now.pushUp();
      }
    }

    void queryRange(int l, int r) {
      queryRangeWorker(root, l, r);
    }

    private void queryRangeWorker(TreeNode now, int l, int r) {
      if (r < now.l || l > now.r) {
        return;
      }
      if (l <= now.r && now.r <= r) {
        return;
      } else {
        // now.pushDown();
        queryRangeWorker(now.lc, l, r);
        queryRangeWorker(now.rc, l, r);
        // now.pushUp();
      }
    }
  }

  private static class MathUtils {

    private MathUtils() {
      throw new AssertionError();
    }

    private static final BigInteger BIG_ZERO = new BigInteger("0");
    private static final BigInteger BIG_ONE = new BigInteger("1");
    private static final BigInteger BIG_TWO = new BigInteger("2");
    private static final SecureRandom RANDOM = new SecureRandom();
    private static int PRIME_CHECK_TIMES = 30;

    static boolean isProbablePrime(long x) {
      return BigInteger.valueOf(x).isProbablePrime(PRIME_CHECK_TIMES);
    }

    static BigInteger rho(BigInteger N) {
      BigInteger divisor = null;
      BigInteger c = new BigInteger(N.bitLength(), RANDOM);
      BigInteger x = new BigInteger(N.bitLength(), RANDOM);
      BigInteger xx = x;

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

  private class LessComparator<T extends Comparable<T>> implements Comparator<T> {
    @Override
    public int compare(T a, T b) {
      return a.compareTo(b);
    }
  }

  private class GreaterComparator<T extends Comparable<T>> implements Comparator<T> {
    @Override
    public int compare(T a, T b) {
      return b.compareTo(a);
    }
  }

  /**
   * A class to answer Range Minimum/Maximum Query problem.
   *
   */
  private class RMQ<T extends Comparable<T>> {

    private final T[] data;
    private final int length;
    private final int logLength;
    private final int[][] table;
    private final Comparator<T> comparator;

    /**
     * Build Sparse Table with comparator.
     * 
     * @param data the origin data.
     * @param comparator how to compare the data.
     */
    RMQ(T[] data, Comparator<T> comparator) {
      this.data = data;
      this.comparator = comparator;
      length = data.length;
      logLength = log2(length) + 1;
      table = new int[logLength][length];
      for (int i = 0; i < length; i++) {
        table[0][i] = i;
      }
      for (int i = 1, k = 1; i < logLength; i++, k <<= 1) {
        int[] now = table[i - 1];
        int[] next = table[i];
        for (int j = 0; j + k < length; j++) {
          int tmp1 = now[j];
          int tmp2 = now[j + k];
          if (comparator.compare(data[tmp1], data[tmp2]) <= 0) {
            next[j] = tmp1;
          } else {
            next[j] = tmp2;
          }
        }
      }
    }

    /**
     * Query and return the index of the "smallest" number defined by comparator in range[from, to]
     * 
     * @param from the left border of the range, inclusive.
     * @param to the right border of the range, inclusive.
     * @return the index of the "smallest" number.
     */
    int query(int from, int to) {
      to++;
      int k = log2(to - from);
      int tmp1 = table[k][from];
      int tmp2 = table[k][to - (1 << k)];
      if (comparator.compare(data[tmp1], data[tmp2]) <= 0) {
        return tmp1;
      } else {
        return tmp2;
      }
    }

    T get(int index) {
      return data[index];
    }

    private int log2(int b) {
      return 31 - Integer.numberOfLeadingZeros(b);
    }
  }

  // Tedious code.
  public Main() {}

  private class QuickScanner {
    private BufferedReader bufferedReader = null;
    private StringTokenizer stringTokenizer = null;
    private String nextHolder = null;

    QuickScanner(Reader reader) {
      bufferedReader = new BufferedReader(reader);
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

    int nextInt(int radix) {
      return Integer.parseInt(next(), radix);
    }

    long nextLong() {
      return Long.parseLong(next());
    }

    long nextLong(int radix) {
      return Long.parseLong(next(), radix);
    }

    double nextDouble() {
      return Double.parseDouble(next());
    }

    BigInteger nextBigInteger() {
      return new BigInteger(next());
    }

    BigInteger nextBigInteger(int radix) {
      return new BigInteger(next(), radix);
    }

    BigDecimal nextBigDecimal() {
      return new BigDecimal(next());
    }
  }

  private static long currentTimeInMiiliSeconds() {
    return System.currentTimeMillis();
  }

  private static void debug(Object... objects) {
    System.err.println(Arrays.deepToString(objects));
  }

  private static void checkCondition(boolean cond) {
    if (!cond) {
      throw new AssertionError();
    }
  }

  private static void checkCondition(boolean cond, String errorMessage) {
    if (!cond) {
      throw new AssertionError(errorMessage);
    }
  }

  @Override
  public void run() {
    PrintWriter out = new PrintWriter(System.out);
    try {
      Reader reader = null;
      if (inputFilePath.isEmpty()) {
        reader = new InputStreamReader(System.in);
      } else {
        reader = new FileReader(inputFilePath);
      }
      QuickScanner in = new QuickScanner(reader);
      solve(in, out);
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // To make sure PrintWriter will always flush to stdout.
      out.flush();
    }
  }

  public static void main(String[] args) {
    new Main().run();
  }
}
