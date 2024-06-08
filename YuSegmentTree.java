// https://atcoder.jp/contests/abc339/submissions/49927939
class SegmentTree<T> {
    private boolean isBuilt;
    private final Monoid<T> monoid;
    private List<T> node;
    private int n;
    private int m;
    private int[] left;
    private int[] right;

    public SegmentTree(Monoid<T> monoid) {
        this.monoid = monoid;
    }

    public void build(int m) {
        build(m, i -> monoid.identity());
    }

    public void build(int m, T init) {
        build(m, i -> init);
    }

    public void build(List<T> data) {
        build(data.size(), data::get);
    }

    public void build(int m, IntFunction<T> initFunc) {
        this.m = m;
        this.n = Integer.highestOneBit(m * 2 - 1);
        this.node = new ArrayList<>(n * 2);
        this.left = new int[n * 2];
        this.right = new int[n * 2];
        for (int i = 0; i < n; i++) {
            node.add(null);
        }
        for (int i = 0; i < n; i++) {
            node.add(i < m ? initFunc.apply(i) : monoid.identity());
            left[i + n] = i;
            right[i + n] = i + 1;
        }
        for (int i = n - 1; i > 0; i--) {
            node.set(i, monoid.merge(node.get(i * 2), node.get(i * 2 + 1)));
            left[i] = left[i * 2];
            right[i] = right[i * 2 + 1];
        }
        this.isBuilt = true;
    }

    public void set(int i, T value) {
        checkBuilt();
        i += n;
        node.set(i, value);
        while (i > 1) {
            i >>= 1;
            node.set(i, monoid.merge(node.get(i * 2), node.get(i * 2 + 1)));
        }
    }

    public T get(int i) {
        checkBuilt();
        return node.get(i + n);
    }

    public T get(int l, int r) {
        checkBuilt();
        l += n;
        r += n;
        T xl = monoid.identity();
        T xr = monoid.identity();
        for (; l < r; l >>= 1, r >>= 1) {
            if (l % 2 == 1) {
                xl = monoid.merge(xl, node.get(l++));
            }
            if (r % 2 == 1) {
                xr = monoid.merge(node.get(--r), xr);
            }
        }
        return monoid.merge(xl, xr);
    }

    public T getAll() {
        checkBuilt();
        return node.get(1);
    }

    // check(get(0,index-1)) && !check(get(0,index)
    public int binarySearchLeft(Predicate<T> predicate) {
        return binarySearchLeft(0, n, predicate);
    }

    public int binarySearchLeft(int l, int r, Predicate<T> predicate) {
        checkBuilt();
        T current = monoid.identity();
        for (int i = l + n; i < n * 2 && l < r; ) {
            T t = monoid.merge(current, node.get(i));
            if (right[i] <= r && predicate.test(t)) {
                current = t;
                l = right[i];
                i++;
                if (i % 2 == 0) {
                    i >>= 1;
                }
            } else {
                i <<= 1;
            }
        }
        return l < r ? l + 1 : -1;
    }

    // !check(get(index-1,n)) && check(get(index,n)
    public int binarySearchRight(Predicate<T> predicate) {
        return binarySearchRight(0, n, predicate);
    }

    public int binarySearchRight(int l, int r, Predicate<T> predicate) {
        checkBuilt();
        T current = monoid.identity();
        for (int i = r + n - 1; i < n * 2 && l < r; ) {
            T t = monoid.merge(node.get(i), current);
            if (left[i] >= l && predicate.test(t)) {
                current = t;
                r = left[i];
                i--;
                if (i % 2 == 1) {
                    i >>= 1;
                }
            } else {
                i = i * 2 + 1;
            }
        }
        return l < r ? r : -1;
    }

    public List<T> toList() {
        return new ArrayList<>(node.subList(n, n + m));
    }

    private void checkBuilt() {
        if (!isBuilt) {
            throw new IllegalStateException("segment tree is not built");
        }
    }

    @Override
    public String toString() {
        return toList().toString();
    }
}

class Monoid<T> {
    public static final Monoid<Integer> SET_INT = new Monoid<>((Integer)null, (a, b) -> b);
    public static final Monoid<Integer> SUM_INT = new Monoid<>(0, Integer::sum);
    public static final Monoid<Integer> PROD_INT = new Monoid<>(1, (a, b) -> a * b);
    public static final Monoid<Integer> XOR_INT = new Monoid<>(0, (a, b) -> a ^ b);
    public static final Monoid<Integer> GCD = new Monoid<>(0, (a, b) -> {
        if (b == 0) {
            return a;
        }
        while (a % b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return b;
    });
    public static final Monoid<Integer> MIN_INT = new Monoid<>(Main.iinf, Math::min);
    public static final Monoid<Integer> MAX_INT = new Monoid<>(-Main.iinf, Math::max);
    public static final Monoid<Long> SET_LONG = new Monoid<>((Long)null, (a, b) -> b);
    public static final Monoid<Long> SUM_LONG = new Monoid<>(0L, Long::sum);
    public static final Monoid<Long> PROD_LONG = new Monoid<>(1L, (a, b) -> a * b);
    public static final Monoid<Long> XOR_LONG = new Monoid<>(0L, (a, b) -> a ^ b);
    public static final Monoid<Long> MIN_LONG = new Monoid<>(Main.inf, Math::min);
    public static final Monoid<Long> MAX_LONG = new Monoid<>(-Main.inf, Math::max);
    public static final Monoid<Long> SUM_MOD = new Monoid<>(0L, (a, b) -> (a + b) % Main.mod);
    public static final Monoid<Long> PROD_MOD = new Monoid<>(1L, (a, b) -> a * b % Main.mod);
    private T identity;
    private Supplier<T> identitySupplier;
    private final BinaryOperator<T> operation;

    public Monoid(BinaryOperator<T> operation) {
        this.operation = operation;
    }

    public Monoid(T identity, BinaryOperator<T> operation) {
        this(operation);
        this.identity = identity;
    }

    public Monoid(Supplier<T> identitySupplier, BinaryOperator<T> operation) {
        this(operation);
        this.identitySupplier = identitySupplier;
    }

    public T merge(T x, T y) {
        return operation.apply(x, y);
    }

    public T pow(T x, long n) {
        T p = x;
        T res = identity();
        while (n > 0) {
            if ((n & 1) == 1) {
                res = merge(res, p);
            }
            p = merge(p, p);
            n >>= 1;
        }
        return res;
    }

    public T identity() {
        return identitySupplier == null ? identity : identitySupplier.get();
    }

    public boolean equalsIdentity(T obj) {
        return Objects.equals(obj, identity());
    }
}