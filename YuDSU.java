class UnionFind {
    private final int n;
    private int m;
    private final int[] parent;
    private final int[] next;
    private final int[] size;
    private final long[] diff;
    private final boolean[] conflict;

    public UnionFind(int n) {
        this.n = n;
        this.m = n;
        this.parent = new int[n];
        this.next = new int[n];
        this.size = new int[n];
        this.diff = new long[n];
        this.conflict = new boolean[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            next[i] = i;
            size[i] = 1;
        }
    }

    public int root(int n) {
        while (n != parent[n]) {
            diff[n] += diff[parent[n]];
            n = parent[n] = parent[parent[n]];
        }
        return n;
    }

    public long potential(int n) {
        long ret = 0;
        while (n != parent[n]) {
            diff[n] += diff[parent[n]];
            ret += diff[n];
            n = parent[n] = parent[parent[n]];
        }
        return ret;
    }

    public boolean unite(int x, int y) {
        return unite(x, y, 1);
    }

    public boolean unite(int x, int y, long w) {
        w += potential(x) - potential(y);
        x = root(x);
        y = root(y);
        if (x != y) {
            int temp = next[x];
            next[x] = next[y];
            next[y] = temp;
            if (size[x] > size[y]) {
                parent[y] = x;
                size[x] += size[y];
                diff[y] = w;
                conflict[x] |= conflict[y];
            } else {
                parent[x] = y;
                size[y] += size[x];
                diff[x] = -w;
                conflict[y] |= conflict[x];
            }
            m--;
            return true;
        } else if (w != 0) {
            conflict[x] = true;
        }
        return false;
    }

    public boolean conflict(int n) {
        return conflict[root(n)];
    }

    public long diff(int x, int y) {
        return potential(y) - potential(x);
    }

    public int size() {
        return m;
    }

    public boolean same(int x, int y) {
        return root(x) == root(y);
    }

    public int getSize(int n) {
        return size[root(n)];
    }

    public int[] group(int n) {
        n = root(n);
        int[] group = new int[size[n]];
        for (int i = 0; i < group.length; i++) {
            n = group[i] = next[n];
        }
        return group;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < n; i++) {
            if (i == root(i)) {
                joiner.add(Arrays.toString(group(i)));
            }
        }
        return joiner.toString();
    }
}