// https://atcoder.jp/contests/abc335/submissions/49121960 
class PrimeUtils {
    private static final int[] bases = {15591, 2018, 166, 7429, 8064, 16045, 10503, 4399, 1949, 1295, 2776, 3620, 560, 3128, 5212, 2657, 2300, 2021, 4652, 1471, 9336, 4018, 2398, 20462, 10277, 8028, 2213, 6219, 620, 3763, 4852, 5012, 3185, 1333, 6227, 5298, 1074, 2391, 5113, 7061, 803, 1269, 3875, 422, 751, 580, 4729, 10239, 746, 2951, 556, 2206, 3778, 481, 1522, 3476, 481, 2487, 3266, 5633, 488, 3373, 6441, 3344, 17, 15105, 1490, 4154, 2036, 1882, 1813, 467, 3307, 14042, 6371, 658, 1005, 903, 737, 1887, 7447, 1888, 2848, 1784, 7559, 3400, 951, 13969, 4304, 177, 41, 19875, 3110, 13221, 8726, 571, 7043, 6943, 1199, 352, 6435, 165, 1169, 3315, 978, 233, 3003, 2562, 2994, 10587, 10030, 2377, 1902, 5354, 4447, 1555, 263, 27027, 2283, 305, 669, 1912, 601, 6186, 429, 1930, 14873, 1784, 1661, 524, 3577, 236, 2360, 6146, 2850, 55637, 1753, 4178, 8466, 222, 2579, 2743, 2031, 2226, 2276, 374, 2132, 813, 23788, 1610, 4422, 5159, 1725, 3597, 3366, 14336, 579, 165, 1375, 10018, 12616, 9816, 1371, 536, 1867, 10864, 857, 2206, 5788, 434, 8085, 17618, 727, 3639, 1595, 4944, 2129, 2029, 8195, 8344, 6232, 9183, 8126, 1870, 3296, 7455, 8947, 25017, 541, 19115, 368, 566, 5674, 411, 522, 1027, 8215, 2050, 6544, 10049, 614, 774, 2333, 3007, 35201, 4706, 1152, 1785, 1028, 1540, 3743, 493, 4474, 2521, 26845, 8354, 864, 18915, 5465, 2447, 42, 4511, 1660, 166, 1249, 6259, 2553, 304, 272, 7286, 73, 6554, 899, 2816, 5197, 13330, 7054, 2818, 3199, 811, 922, 350, 7514, 4452, 3449, 2663, 4708, 418, 1621, 1171, 3471, 88, 11345, 412, 1559, 194};
    private static final SieveOfEratosthenes sieve = new SieveOfEratosthenes();

    public static int getMinPrimeFactor(int n) {
        return sieve.minPrimeFactor(n);
    }

    public static int getNthPrime(int n) {
        return sieve.nthPrime(n);
    }

    public static boolean isPrime(int n) {
        if (n == 2 || n == 3 || n == 5 || n == 7) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0 || n % 5 == 0 || n % 7 == 0) {
            return false;
        }
        if (n < 121) {
            return n > 1;
        }
        long h = n;
        h = ((h >> 16) ^ h) * 0x45D9F3B;
        h = ((h >> 16) ^ h) * 0x45D9F3B;
        h = ((h >> 16) ^ h) & 0xFF;
        int a = bases[(int)h];
        return millerRabin(n, a, new BigMontgomery(n));
    }

    private static boolean millerRabin(long n, long a, BigMontgomery bm) {
        long d = n - 1;
        int s = 0;
        while ((d & 1) == 0) {
            s++;
            d >>= 1;
        }
        long x = bm.pow(a, d);
        if (x == 1) {
            return true;
        }
        for (int r = 0; r < s; r++) {
            if (x == n - 1) {
                return true;
            }
            x = bm.mul(x, x);
        }
        return false;
    }

    public static boolean isPrime(long n) {
        if (n <= Integer.MAX_VALUE) {
            return isPrime((int)n);
        }
        BigMontgomery bm = new BigMontgomery(n);
        for (long a : new long[] {2, 325, 9375, 28178, 450775, 9780504, 1795265022}) {
            if (!millerRabin(n, a, bm)) {
                return false;
            }
        }
        return true;
    }

    public static List<Integer> getPrimesLessThan(int n) {
        List<Integer> primes = new ArrayList<>();
        for (int prime : sieve) {
            if (prime >= n) {
                break;
            }
            primes.add(prime);
        }
        return primes;
    }

    public static List<Integer> getNPrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            primes.add(sieve.nthPrime(i));
        }
        return primes;
    }

    // O(N + Q * logN)
    public static Map<Integer, Integer> factorize0(int n) {
        Map<Integer, Integer> primeFactors = new LinkedHashMap<>();
        while (n > 1) {
            int primeFactor = getMinPrimeFactor(n);
            int count = 0;
            while (getMinPrimeFactor(n) == primeFactor) {
                count++;
                n /= primeFactor;
            }
            primeFactors.put(primeFactor, count);
        }
        return primeFactors;
    }

    // O(√N + Q * √N / logN)
    public static Map<Integer, Integer> factorize1(int n) {
        Map<Integer, Integer> primeFactors = new LinkedHashMap<>();
        int c = n;
        for (int prime : sieve) {
            int count = 0;
            while (c % prime == 0) {
                count++;
                c /= prime;
            }
            if (count > 0) {
                primeFactors.put(prime, count);
            }
            if (prime * prime >= n) {
                break;
            }
        }
        if (c > 1) {
            primeFactors.put(c, 1);
        }
        return primeFactors;
    }

    // O(Q * N^{1/4})
    public static Map<Long, Integer> factorize2(long n) {
        Map<Long, Integer> count = new LinkedHashMap<>();
        for (long p = 2; p * p * p * p < n && p < 10000; p++) {
            int e = 0;
            while (n % p == 0) {
                n /= p;
                e++;
            }
            if (e > 0) {
                count.put(p, e);
            }
        }
        long[] factors = factorize(n);
        Arrays.sort(factors);
        for (long f : factors) {
            count.merge(f, 1, Integer::sum);
        }
        return count;
    }

    private static long gcd(long a, long b) {
        return 0 < b ? gcd(b, a % b) : a;
    }

    private static long pollardRho(long n) {
        if (n % 2 == 0) {
            return 2;
        }
        if (PrimeUtils.isPrime(n)) {
            return n;
        }
        BigMontgomery bm = new BigMontgomery(n);
        int m = 128;
        for (int c = 1; ; c++) {
            long x = -1;
            long y = 2;
            long ys = -1;
            long q = 1;
            long g = 1;
            for (int r = 1; g == 1; r <<= 1) {
                x = y;
                for (int i = 0; i < r; i++) {
                    y = bm.mod(bm.mul(y, y) + c);
                }
                for (int k = 0; k < r && g == 1; k += m) {
                    ys = y;
                    for (int i = 0; i < m && i < r - k; i++) {
                        y = bm.mod(bm.mul(y, y) + c);
                        q = bm.mul(q, Math.abs(x - y));
                    }
                    g = gcd(q, n);
                }
            }
            if (g == n) {
                do {
                    ys = bm.mod(bm.mul(ys, ys) + c);
                    g = gcd(Math.abs(x - ys), n);
                } while (g == 1);
            }
            if (g != n) {
                return g;
            }
        }
    }

    private static long[] factorize(long n) {
        if (n == 1) {
            return new long[] {};
        }
        long p = pollardRho(n);
        if (p == n) {
            return new long[] {n};
        }
        long[] l = factorize(p);
        long[] r = factorize(n / p);
        long[] a = Arrays.copyOf(l, l.length + r.length);
        System.arraycopy(r, 0, a, l.length, r.length);
        return a;
    }

    public static List<Long> divisors(long n) {
        List<Long> divisors = new ArrayList<>();
        divisors.add(1L);
        Map<Long, Integer> primeFactors = factorize2(n);
        for (Map.Entry<Long, Integer> entry : primeFactors.entrySet()) {
            int exp = entry.getValue();
            int s = divisors.size();
            for (int i = 0; i < s; i++) {
                long v = 1;
                for (int j = 0; j < exp; j++) {
                    v *= entry.getKey();
                    divisors.add(divisors.get(i) * v);
                }
            }
        }
        Collections.sort(divisors);
        return divisors;
    }
}

class BigMontgomery {
    private final long n, r2, nInv;

    public BigMontgomery(long n) {
        long r2 = (1L << 62) % n;
        for (int i = 0; i < 66; i++) {
            r2 <<= 1;
            if (r2 >= n) {
                r2 -= n;
            }
        }
        long nInv = n;
        for (int i = 0; i < 5; ++i) {
            nInv *= 2 - n * nInv;
        }
        this.n = n;
        this.r2 = r2;
        this.nInv = nInv;
    }

    private static long high(long x, long y) {
        return Math.multiplyHigh(x, y) + (x >> 63 & y) + (y >> 63 & x);
    }

    private long mr(long x) {
        return high(-nInv * x, n) + (x == 0 ? 0 : 1);
    }

    private long mr2(long x, long y) {
        return high(x, y) + high(-nInv * x * y, n) + (x * y == 0 ? 0 : 1);
    }

    public long mod(long x) {
        return x < n ? x : x - n;
    }

    public long mul(long x, long y) {
        return mod(mr2(mr2(x, r2), y));
    }

    public long pow(long x, long y) {
        long z = mr2(x, r2);
        long r = 1;
        while (y > 0) {
            if ((y & 1) == 1) {
                r = mr2(r, z);
            }
            z = mr2(z, z);
            y >>= 1;
        }
        return mod(r);
    }
}

class SieveOfEratosthenes implements Iterable<Integer> {
    private final List<Integer> primes = new ArrayList<>();
    private int[] table;

    public SieveOfEratosthenes() {
        this(1000);
    }

    public SieveOfEratosthenes(int initialCapacity) {
        calcTable(initialCapacity);
    }

    private void calcTable(int n) {
        int oldCapacity = 0;
        int[] newTable = new int[n];
        if (table != null) {
            oldCapacity = table.length;
            System.arraycopy(table, 0, newTable, 0, oldCapacity);
        }
        table = newTable;
        for (int i = oldCapacity; i < n; i++) {
            table[i] = i;
        }
        for (int i = 2; i < n; i++) {
            if (i >= oldCapacity && table[i] == i) {
                primes.add(i);
            }
            for (int prime : primes) {
                if (prime > table[i] || (long)prime * i >= n) {
                    break;
                }
                table[prime * i] = prime;
            }
        }
    }

    private void grow(int minCapacity) {
        int oldCapacity = table.length;
        int newCapacity = (int)Math.min(Integer.MAX_VALUE, Math.max(minCapacity, (long)oldCapacity + (oldCapacity >> 1)));
        calcTable(newCapacity);
    }

    public int minPrimeFactor(int n) {
        if (table.length <= n) {
            grow(n + 1);
        }
        return table[n];
    }

    public int nthPrime(int n) {
        while (primes.size() <= n) {
            grow(table.length + 1);
        }
        return primes.get(n);
    }

    public int countLessThan(int n) {
        while (primes.get(primes.size() - 1) < n) {
            grow(table.length + 1);
        }
        int left = -1;
        int right = primes.size();
        while (right - left > 1) {
            int mid = (left + right) / 2;
            if (primes.get(mid) < n) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }

    public Map<Integer, Integer> factorize(int n) {
        Map<Integer, Integer> primeFactors = new LinkedHashMap<>();
        while (n > 1) {
            int primeFactor = minPrimeFactor(n);
            int exp = 0;
            while (n % primeFactor == 0) {
                n /= primeFactor;
                exp++;
            }
            primeFactors.put(primeFactor, exp);
        }
        return primeFactors;
    }

    public long sumOfDivisors(int n) {
        long prod = 1;
        while (n > 1) {
            int primeFactor = minPrimeFactor(n);
            int factor = 1;
            long sum = 1;
            while (n % primeFactor == 0) {
                n /= primeFactor;
                factor *= primeFactor;
                sum += factor;
            }
            prod *= sum;
        }
        return prod;
    }

    public List<Integer> divisors(int n) {
        List<Integer> divisors = new ArrayList<>();
        divisors.add(1);
        Map<Integer, Integer> primeFactors = factorize(n);
        for (Map.Entry<Integer, Integer> entry : primeFactors.entrySet()) {
            int exp = entry.getValue();
            int s = divisors.size();
            for (int i = 0; i < s; i++) {
                int v = 1;
                for (int j = 0; j < exp; j++) {
                    v *= entry.getKey();
                    divisors.add(divisors.get(i) * v);
                }
            }
        }
        Collections.sort(divisors);
        return divisors;
    }

    @Override
    public PrimitiveIterator.OfInt iterator() {
        return new PrimitiveIterator.OfInt() {
            private int i = 0;

            @Override
            public int nextInt() {
                return nthPrime(i++);
            }

            @Override
            public boolean hasNext() {
                return true;
            }
        };
    }
}
 