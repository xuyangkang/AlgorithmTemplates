import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

  public static void main(String[] args) {
    new Main().solve();
  }

  private void solve() {
    System.out.println("Hello World~");
  }

  private void debug(Object... objects) {
    System.err.println(Arrays.deepToString(objects));
  }

  /*
   * A fast Scanner class.
   */
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

    public boolean hasNext() {
      if (nextHolder == null) {
        nextHolder = next();
      }
      return nextHolder != null;
    }

    public String next() {
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

    public int nextInt() {
      return Integer.parseInt(next());
    }

    public long nextLong() {
      return Long.parseLong(next());
    }

    public double nextDouble() {
      return Double.parseDouble(next());
    }

    public BigInteger nextBigInteger() {
      return new BigInteger(next());
    }

    public BigDecimal nextBigDecimal() {
      return new BigDecimal(next());
    }
  }

}
