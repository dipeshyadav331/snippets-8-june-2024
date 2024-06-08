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
 