class Art {
    private static int MOD = 998_244_353;

    static void setMod(final int MOD) {
        Art.MOD = MOD;
    }

    static final int multiply(int multiplicand, int multiplier) {
        multiplicand %= MOD;
        multiplier %= MOD;
        return (int) ((long) multiplicand * multiplier % MOD);
    }

    static final long multiply(long multiplicand, long multiplier) {
        multiplicand %= MOD;
        multiplier %= MOD;
        return multiplicand * multiplier % MOD;
    }

    static final int add(int augend, int addend) {
        augend %= MOD;
        addend %= MOD;
        return (augend + addend) % MOD;
    }

    static final long add(long augend, long addend) {
        augend %= MOD;
        addend %= MOD;
        return (augend + addend) % MOD;
    }

    static final int subtract(int minuend, int subtrahend) {
        minuend %= MOD;
        subtrahend %= MOD;
        int result = minuend - subtrahend;
        if (result < 0) {
            result += MOD;
        }
        return result;
    }

    static final long subtract(long minuend, long subtrahend) {
        minuend %= MOD;
        subtrahend %= MOD;
        long result = minuend - subtrahend;
        if (result < 0) {
            result += MOD;
        }
        return result;
    }

    static final int binExp(int base, int exp) {
        base %= MOD;
        int result = 1;
        while (exp > 0) {
            if ((exp & 1) > 0) {
                result = (int) ((long) result * base % MOD);
            }
            base = (int) ((long) base * base % MOD);
            exp >>= 1;
        }
        return result;
    }

    static final long binExp(long base, long exp) {
        base %= MOD;
        long result = 1;
        while (exp > 0) {
            if ((exp & 1) > 0) {
                result *= base;
                result %= MOD;
            }
            base *= base;
            base %= MOD;
            exp >>= 1;
        }
        return result;
    }

    // Works only when num and MOD are relatively prime
    // If Mod is a non prime number than binExp(num , phi(num) - 1);
    static final int inverse(int num) {
        return binExp(num, MOD - 2);
    }

    // Works only when num and MOD are relatively prime
    static final long inverse(long num) {
        return binExp(num, MOD - 2);
    }

    static final int[] getFactorialInverse(int[] fac) {
        int[] facInv = new int[fac.length];
        facInv[0] = facInv[1] = 1;
        facInv[facInv.length - 1] = inverse(fac[fac.length - 1]);
        for (int i = facInv.length - 2; i > 1; i--) {
            facInv[i] = multiply(facInv[i + 1], i + 1);
        }
        return facInv;
    }
}