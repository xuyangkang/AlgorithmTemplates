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
  private static final int STACK_SIZE = 16 << 20;

  private void solve(QuickScanner in, PrintWriter out) {
    out.println("Hello World!");
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

    static long gcd(long a, long b) {
      return b == 0 ? a : gcd(b, a % b);
    }

    static long[] extendedGcd(long a, long b) {
      BigInteger bigA = BigInteger.valueOf(a);
      BigInteger bigB = BigInteger.valueOf(b);
      BigInteger[] res = extendedGcd(bigA, bigB);
      return new long[] {res[0].longValue(), res[1].longValue(), res[2].longValue()};
    }

    static BigInteger[] extendedGcd(BigInteger p, BigInteger q) {
      if (q.equals(BIG_ZERO))
        return new BigInteger[] {p, BIG_ONE, BIG_ZERO};

      BigInteger[] vals = extendedGcd(q, p.mod(q));
      BigInteger d = vals[0];
      BigInteger a = vals[2];
      BigInteger b = vals[1].subtract(p.divide(q).multiply(a));
      return new BigInteger[] {d, a, b};
    }

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
      if (N.isProbablePrime(PRIME_CHECK_TIMES)) {
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

  private static long currentTimeInNanoSeconds() {
    return System.nanoTime();
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
    new Thread(null, new Main(), "Main Thread.", STACK_SIZE).start();
  }
}
