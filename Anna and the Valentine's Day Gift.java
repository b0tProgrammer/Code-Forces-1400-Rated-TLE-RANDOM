import java.util.*;
import java.io.*;

import static java.lang.Math.*;

public class Main {

    private static final long lif = (long) 1e9 + 7; //long infinity
    private static final int iif = 998244353; // int infinity!

    public static void main(String[] args) {
        try {
            FastReader in = new FastReader();
            FastWriter out = new FastWriter();

            Comparator<Pair> com = Comparator.comparingInt(i -> i.v1);

            int testcases = 1;

            testcases = in.nextInt();


            while(testcases-- > 0) {

                /*
                    As both player's play optimally both of them will play to win!

                    as in this question the operation of anna is to reverse any number!

                    and the sasha chooses two numbers whose indices are unique! and concat them in either a+b or b+a

                    so, if you closely observe that if the trailing zeros are more for a number then anna chooses that number to reverse that inorder to decrease the overall number of digits in the final answer!

                    whereas sasha chooses to concat the non-zero trailing numbers with trailing zero number!

                    in this way after playing checking whether the total digits are greater than the minDigits required!

                 */

                int siz = in.nextInt();

                int minDigitsReq = in.nextInt()+1; // at last how many digits are required!

                List<Integer> zeros = new ArrayList<>(); // which will have the count of zeros for a digit!

                long tLen = 0; // total number of digits before any move is made by both the players!

                for(int i = 0; i < siz; i++) {

                    String s = in.next(); // taking input as a string easy to know the length!

                    int len = s.length()-1; // the last index of that number!

                    tLen += (len+1); // adding the total number of digits assuming that still no operation is done on digits!

                    int c = 0; // counts number of trailing zeros!

                    while(len > -1 && s.charAt(len) == '0') { // counting!

                        len--;

                        c++;

                    }

                    if(c > 0) zeros.add(c); // if there are any trailing zero's then add that count to the list!

                }

                zeros.sort(null); //  sort them accordingly!

                for(int i = zeros.size()-1; i >= 0; i-=2) { // i -= 2 ? because as anna performs an operation sasha immediately has to perform an operation so, she can choose the current maximum trailing zeros number and concat it to make a non-zero trailing number!

                    tLen -= zeros.get(i); // then subtracting those trailing zeros from the total number of digits as anna performs that operation!

                }

                if(tLen >= minDigitsReq) out.println("Sasha"); // checking whether the number of digits are greater than the max digits required!

                else out.println("Anna");
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    //<--------------------------------------Fast reader----------------------------------------->
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
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

        String nextLine() {
            String str = "";
            try {
                str = br.readLine().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }
    }

//<---------------------------------------Fast writer--------------------------------------------->

    static class FastWriter {
        private final BufferedWriter bw;

        public FastWriter() {
            this.bw = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        public void print(Object object) throws IOException {
            bw.append("" + object);
        }

        public void println() throws IOException {
            bw.append("\n");
        }

        public void println(Object object) throws IOException {
            print(object);
            bw.append("\n");
        }

        public void close() throws IOException {
            bw.close();
        }
    }

// <-------------------------------------------------Some helper methods---------------------------------------->

    private static char check(int i, int siz,String s) {
        for(char c = 'a'; c < 'z'; c++) {
            if(c != s.charAt(i-1)) {
                if(i == siz-1) return c;
                else if(c != s.charAt(i+1)) return c;
            }
        }
        return 'z';
    }

    private static long kadane(int l, int r, int[] nums) {
        long sum = 0;
        long mSum = 0;
        for(int i = l; i < r; i++) {
            if(sum < 0) sum = nums[i];
            else sum += nums[i];
            mSum = max(sum,mSum);
        }
        return mSum;
    }

    public static List<Integer> reversed(List<Integer> lis) {
        List<Integer> ans = new ArrayList<>();
        for(int i = lis.size()-1; i >= 0; i--) {
            ans.add(lis.get(i));
        }
        return ans;
    }

    private static int update(int ans, int i) {
        return (int)((ans*1L*i)%iif);
    }

    private static void getFactors(int num, Map<Integer, Integer> map) {

        for(int i = 2; i <= (int) Math.sqrt(num); i++) {
            while(num%i == 0) {
                map.put(i,map.getOrDefault(i,0)+1);
                num /= i;
            }
        }
        if(num > 1) map.put(num,map.getOrDefault(num,0)+1);
    }

    public static void print(boolean cond,FastWriter out) throws IOException {
        out.println(cond ? "Yes" : "No");
    }

    public static long fastPow(long b, long e) {
        long res = 1;
        while (e > 0) {
            if (odd(e)) res = (res * b) % lif;
            b = (b * b) % lif;
            e = e >> 1;
        }
        return res;
    }

    public static int charToInt(char c) {
        return (c - '0');
    }

    public static char intToChar(int n) {
        return (char) (n + 48);
    }

    public static int[] sieve(int upto) {
        int[] primes = new int[upto + 1];
        Arrays.fill(primes, 1);
        for (int i = 2; i * i <= upto; i++) {
            if (primes[i] == 1)
                for (int j = i * i; j <= upto; j += i) {
                    primes[j] = 0;
                }
        }
        return primes;
    }

    private static long gcd(long a, long b) {
        if (b != 0) return gcd(b, (a % b));
        return a;
    }

    private static int gcd(int a, int b) {
        if (b != 0) return gcd(b, (a % b));
        return a;
    }

    private static long getSum(int startNum, int commonDiff, long range) {
        long lastNum = startNum + (range - 1) * commonDiff;
        return range * (startNum + lastNum) / 2;
    }

    private static boolean odd(long siz) {
        return (siz & 1) == 1;
    }

    private static boolean powOf2(long siz) {
        return (siz & (siz - 1)) == 0;
    }

    private static List<Integer> primeFactors(int siz) {

        List<Integer> sizs = new ArrayList<>();
        int[] p;
        p = sieve(siz);
        for (int i = 2; i <= siz; i++) {
            while (siz % i == 0 && p[i] == 1) {
                sizs.add(i);
                siz /= i;
            }
        }
        return sizs;
    }
}

class Pair2 {
    long v1;
    long v2;

    public Pair2(long v1, long v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
}
class Pair {
    int v1,v2;
    public Pair(int v,int w) {v1 = v; v2 = w;}
}
class Node {
    int data;
    Node left;
    Node right;
    public Node(int data) {
        this.data = data;
    }
}
