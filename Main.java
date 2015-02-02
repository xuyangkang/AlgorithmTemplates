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

  private static final String INPUT_FILE = "";

  private void solve(QuickScanner in, PrintWriter out) {
    out.println("Hello world!");
  }

  private class BinaryIndexedTree {
    int length;
    int logLength;
    int[] c;

    BinaryIndexedTree(int n) {
      logLength = Integer.SIZE - Integer.numberOfLeadingZeros(n);
      length = 1 << logLength;
      c = new int[length + 1];
    }

    void add(int x, int num) {
      for (; x <= length; x += Integer.lowestOneBit(x)) {
        c[x] += num;
      }
    }

    void insert(int x) {
      add(x, 1);
    }

    void remove(int x) {
      add(x, -1);
    }

    // [1, x]
    int sum(int x) {
      int sum = 0;
      for (; x > 0; x -= Integer.lowestOneBit(x)) {
        sum += c[x];
      }
      return sum;
    }

    // [l, r]
    int sum(int l, int r) {
      return sum(r) - sum(l - 1);
    }

    int rank(int x) {
      return sum(x - 1) + 1;
    }

    int select(int k) {
      int ans = 0;
      for (int i = logLength; i >= 0; i--) {
        ans += (1 << i);
        if (ans <= length && c[ans] < k) {
          k -= c[ans];
        } else {
          ans -= (1 << i);
        }
      }
      return ans + 1;
    }
  }

  private class Rmq<T extends Comparable<T>> {
    List<T> data;
    int[] tree;
    int length;
    Comparator<T> comparator;

    Rmq(T[] data, Comparator<T> comparator) {
      this.data = new ArrayList<T>();
      for (T e : data) {
        this.data.add(e);
      }
      this.comparator = comparator;
      buildTree();
    }

    Rmq(T[] data) {
      this(data, null);
    }

    Rmq(Iterable<T> data, Comparator<T> comparator) {
      this.data = new ArrayList<T>();
      for (T e : data) {
        this.data.add(e);
      }
      this.comparator = comparator;
      buildTree();
    }

    Rmq(Iterable<T> data) {
      this(data, null);
    }

    void set(int i, T value) {
      data.set(i, value);
      updateWorker(i);
    }

    T get(int i) {
      return data.get(i);
    }

    // [s, t)
    int query(int s, int t) {
      int ret = -1;
      T val = null;
      while (0 < s && s + Integer.lowestOneBit(s) <= t) {
        int i = (length + s) / Integer.lowestOneBit(s);
        if (ret == -1 || compareWorker(data.get(tree[i]), val) < 0) {
          ret = tree[i];
          val = data.get(ret);
        }
        s += Integer.lowestOneBit(s);
      }

      while (s < t) {
        int i = (length + t) / Integer.lowestOneBit(t) - 1;
        if (ret == -1 || compareWorker(data.get(tree[i]), val) < 0) {
          ret = tree[i];
          val = data.get(ret);
        }
        t -= Integer.lowestOneBit(t);
      }
      return ret;
    }

    private void buildTree() {
      length = Integer.highestOneBit(data.size()) * 2;
      tree = new int[length * 2];
      Arrays.fill(tree, -1);
      for (int i = 0; i < data.size(); i++) {
        tree[length + i] = i;
      }
      for (int i = 0; i < data.size(); i++) {
        updateWorker(i);
      }
    }

    private int compareWorker(T o1, T o2) {
      if (comparator != null) {
        return comparator.compare(o1, o2);
      } else {
        return o1.compareTo(o2);
      }
    }

    private void updateWorker(int i) {
      T tmp2 = data.get(i);
      for (int j = length + i; j > 0; j /= 2) {
        if (tree[j] == -1) {
          tree[j] = i;
        } else {
          T tmp1 = data.get(tree[j]);
          int cmp = compareWorker(tmp2, tmp1);
          if (cmp < 0 || (cmp == 0 && i < tree[j])) {
            tree[j] = i;
          }
        }
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

  public Main() {}

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

  private class QuickScanner {
    private BufferedReader bufferedReader = null;
    private StringTokenizer stringTokenizer = null;
    private String nextHolder = null;

    QuickScanner(Reader reader) {
      bufferedReader = new BufferedReader(reader);
    }

    boolean hasNext() {
      if (nextHolder == null) {
        nextHolder = next();
      }
      return nextHolder != null;
    }

    String next() {
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
    Reader reader = null;
    PrintWriter out = new PrintWriter(System.out);
    try {
      if (INPUT_FILE.isEmpty()) {
        reader = new InputStreamReader(System.in);
      } else {
        reader = new FileReader(INPUT_FILE);
      }
      QuickScanner in = new QuickScanner(reader);
      solve(in, out);
      reader.close();
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
