import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Main implements Runnable {

  private static final String inputFilePath = "";

  private void solve(QuickScanner in, PrintWriter out) {
    out.println("Hello World!");
  }

  private static void debug(Object... objects) {
    System.err.println(Arrays.deepToString(objects));
  }

  private static void checkCondition(boolean cond) {
    if (!cond) {
      throw new AssertionError();
    }
  }

  /**
   * A util function to get current time. This is often used in some search or random algorithm to
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

  // Tedious code.
  public Main() {}

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
