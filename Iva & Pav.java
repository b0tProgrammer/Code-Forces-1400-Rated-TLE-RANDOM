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

//            Comparator<Pair> com = Comparator.comparingInt(i -> i.v1);

            int testcases = 1;

            testcases = in.nextInt();

            while(testcases-- > 0) {

                int n = in.nextInt();

                int[] nums = new int[n+1]; // considering 1 based indexing!

                int[][] prefix = new int[n+1][31]; // array that counts the prefix sums of the current set bit!

                for(int i = 1; i <= n; i++) {
                    nums[i] = in.nextInt(); // then taking numbers ad input!
                }

                for(int i = 1; i <= n; i++) {
                    for(int j = 0; j < 31; j++) {
                        if((nums[i]&(1<<j)) != 0) prefix[i][j] = prefix[i-1][j]+1; // why prefix ?
                        else prefix[i][j] = prefix[i-1][j]; // this is used to speed up the evaluation in order to check whether the number of set bits is equal to the number of indices between them or not!
                    }
                }

                int q = in.nextInt(); // to find a valid r we will use the binary search!

                for(int i = 0; i < q; i++) {
                    int l = in.nextInt();
                    int k = in.nextInt();

                    if(nums[l] < k) out.print(-1+" "); // the a & b <= min(a,b); so if the first element is less then the current element or (K) then answer is invalid!
                    else {
                        // we will do the binary search for finding the r;
                        int low = l; // the worst case of r
                        int high = n; // the best case of r
                        int ans = l; // assuming that answer is at current location only!

                        while(low <= high) {
                            int mid = (low+high)/2;
                            int num = 0;
                            for(int j = 0; j < 31; j++) {
                                if((prefix[mid][j] - prefix[l-1][j]) == (mid-l)+1) num += (1<<j); // if the bit is set in that column we will add that corresponding decimal value to the answer!
                            }
                            if(num >= k) {
                                ans = max(ans,mid); // if the number is greater than k, we can say that we have found a valid r, we have to increment to the next possible r
                                // so update the low!
                                low = mid+1;
                            } else high = mid-1; // else decrement the high

                        }
                        out.print(ans+" ");
                    }
                }
                out.print("\n");
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

    private static boolean check(int mid, Pair[] ranges) {

        int siz = ranges.length;
        int currL = 0;
        int currR = mid;
        for (Pair range : ranges) {
            if (range.v1 > currR || range.v2 < currL) return false;
            currL = max(currL, range.v1);
            currL = max(0, currL - mid);
            currR = min(currR, range.v2);
            currR += mid;
        }
        return true;
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
