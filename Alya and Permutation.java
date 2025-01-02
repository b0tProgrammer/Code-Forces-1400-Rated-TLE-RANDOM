import java.util.*;
import java.io.*;

import static java.lang.Math.*;

public class Main {

    private static final int mod = (int) 1e9 + 7; //mod
    private static final int iif = 998244353; // int infinity!

    public static void main(String[] args) {
        try {
            FastReader in = new FastReader();
            FastWriter out = new FastWriter();

            Comparator<Pair> com = Comparator.comparingInt(i -> i.v1);

            int testcases = 1;

            testcases = in.nextInt();

            while(testcases-- > 0) {
                int n = in.nextInt();
                /*
                    In this question, we have to find the permutation such a way that.
                    The value of K is maximized
                    we can do 2 operations!
                    1) if i odd then do k&pi
                    2) if i even then do k|pi
                    so, after doing these operations, we have to maximize the value of K
                    This depends on the last 5 values of array
                 */

                /*
                    If the n is odd then the max k value is n itself because we have to do the and operation for odd idx.
                    The idx is odd if the size is odd so the maximum for and is n itself

                    else if n is even then the maximum k value is x|(x-1) here x is the maximum power of 2 for that n

                    so, arranging them in that way is correct!
                 */

                int[] ans = new int[n];

                if(odd(n)) {
                    ans = getAns(n-1);
                    out.println(n);
                    for(int i = 0; i < n-1; i++) {
                        out.print(ans[i]+" ");
                    }
                    out.println(n);
                } else {
                    ans = getAns(n);
                    int v = get(n);
                    out.println(v|(v-1));
                    for(int i = 0; i < n; i++) {
                        out.print(ans[i]+" ");
                    }
                    out.println();
                }


            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private static int[] getAns(int n) {
        int[] ans = new int[n];
        if(n == 4) {
            return new int[]{2,1,3,4};
        }
        else if(n == 6) {
            return new int[]{1,2,4,6,5,3};
        }
        int get = get(n);
        Set<Integer> set = new HashSet<>();
        for(int i = 1; i <= n; i++) set.add(i);
        ans[n-1] = get;
        ans[n-2] = get-1;
        ans[n-3] = get-2;
        ans[n-4] = 1;
        ans[n-5] = 3;
        for(int i = 5; i >= 1; i--) {
            set.remove(ans[n-i]);
        }
        int i = 0;
        for(int v : set) {
            ans[i++] = v;
        }
        return ans;
    }
    private static int get(int n) {
        int v = 1;
        while((v<<1) <= n) {
            v <<= 1;
        }
        return v;
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

        public int[] nextIntArray(int siz) {
            int[] nums = new int[siz];
            for(int i = 0; i < siz; i++) {
                nums[i] = Integer.parseInt(next());
            }
            return nums;
        }

        public Pair[] nextPairArray(int siz) {
            Pair[] nums = new Pair[siz];
            for(int i = 0; i < siz; i++) {
                int v1 = Integer.parseInt(next());
                int v2 = Integer.parseInt(next());
                nums[i] = new Pair(v1,v2);
            }
            return nums;
        }

        public long[] nextLongArray(int siz) {
            long[] nums = new long[siz];
            for(int i = 0; i < siz; i++) {
                nums[i] = Long.parseLong(next());
            }
            return nums;
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
            if (odd(e)) res = (res * b) % mod;
            b = (b * b) % mod;
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

    private static long fact(int num) {
        long v = 1;
        for(int i = 1; i <= num; i++) {
            v = (v*i)%mod;
        }
        return v;
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
class Pair{
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