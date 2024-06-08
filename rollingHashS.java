class RollingHash{
	private static final long BASE = new Random().nextInt(1000) + Character.MAX_VALUE + 1;
	private static final long MASK30 = (1L << 30) - 1;
	private static final long MASK31 = (1L << 31) - 1;
	private static final long MOD = (1L << 61) - 1;
	private static final long MASK61 = MOD;
	private long[] hash;
	private String string;
 
	public RollingHash(final String str) {
		string = str;
		hash = new long[str.length() + 1];
		roll();
	}
 
	private void roll() {
		final int len = string.length();
		for (int i = 1; i <= len; ++i) {
			hash[i] = multiply(hash[i - 1], BASE) + string.charAt(i - 1) - ' ' + 1;
			if (MOD <= hash[i]) {
				hash[i] -= MOD;
			}
		}
	}
 
	private static long multiply(final long a, final long b) {
		final long au = a >> 31;
		final long ad = a & MASK31;
		final long bu = b >> 31;
		final long bd = b & MASK31;
		final long mid = ad * bu + au * bd;
		final long midu = mid >> 30;
		final long midd = mid & MASK30;
		return mod(au * bu * 2 + midu + (midd << 31) + ad * bd);
	}
 
	private static long mod(final long x) {
		final long xu = x >> 61;
		final long xd = x & MASK61;
		long ans = xu + xd;
		if (MOD <= ans) {
			ans -= MOD;
		}
		return ans;
	}
 
	public long getHash(final int l, final int r) {
		return (hash[r] - multiply(hash[l], modBasePow(r - l)) + MOD) % MOD;
	}
 
	private static long modBasePow(long b) {
		long ans = 1;
		long a = BASE;
		while (b > 0) {
			if ((b & 1) == 1) {
				ans = multiply(ans, a);
			}
			a = multiply(a, a);
			b >>= 1;
		}
		return ans;
	}
 
	public int length() {
		return string.length();
	}
}
 