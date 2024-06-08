static class TreeAncestor {
        private final int n;
        private final int h;
        private final int root;
        private final List<List<Integer>> edges;
        private final List<List<Integer>> buckets;
        private final int[] bucketId;
        private final int[] preorder;
        private final int[] postorder;
        private final int[][] table;
        private final int[][] ancestor;
        private final int[] depth;
 
        public TreeAncestor(List<List<Integer>> edges) {
            this(edges, 0);
        }
 
        public TreeAncestor(List<List<Integer>> edges, int root) {
            this.n = edges.size();
            this.h = 32 - Integer.numberOfLeadingZeros(n);
            this.root = root;
            this.edges = edges;
            this.buckets = new ArrayList<>();
            this.bucketId = new int[n];
            this.preorder = new int[n];
            this.postorder = new int[n];
            this.table = new int[h + 1][];
            this.ancestor = new int[h][n];
            this.depth = new int[n];
            build();
        }
 
        private void build() {
            int[] ei = new int[n];
            int t = 0;
            Arrays.fill(bucketId, -1);
            Arrays.fill(ancestor[0], -1);
            ancestor[0][root] = root;
            preorder[root] = t;
            table[0] = new int[n * 2];
            table[0][t++] = root;
            int cur = root;
            while (true) {
                if (ei[cur] < edges.get(cur).size()) {
                    int next = edges.get(cur).get(ei[cur]++);
                    if (ancestor[0][next] == -1) {
                        ancestor[0][next] = cur;
                        depth[next] = depth[cur] + 1;
                        preorder[next] = t;
                        table[0][t++] = next;
                        cur = next;
                    }
                    continue;
                }
                postorder[cur] = t;
                if (postorder[cur] - preorder[cur] == 1) {
                    bucketId[cur] = buckets.size();
                    buckets.add(new ArrayList<>());
                }
                buckets.get(bucketId[cur]).add(cur);
                if (cur == root) {
                    break;
                }
                int next = ancestor[0][cur];
                int old = bucketId[next] == -1 ? 0 : buckets.get(bucketId[next]).size();
                int size = buckets.get(bucketId[cur]).size();
                if (old < size) {
                    bucketId[next] = bucketId[cur];
                }
                table[0][t++] = next;
                cur = next;
            }
            for (List<Integer> bucket : buckets) {
                int ladder = bucket.size();
                int v = bucket.get(bucket.size() - 1);
                for (int i = 0; i < ladder && ancestor[0][v] != v; i++) {
                    v = ancestor[0][v];
                    bucket.add(v);
                }
            }
            for (int i = 1; i <= h; i++) {
                table[i] = new int[n * 2 + 1 - (1 << i)];
                for (int j = 0; j < table[i].length; j++) {
                    int k1 = table[i - 1][j];
                    int k2 = table[i - 1][j + (1 << (i - 1))];
                    table[i][j] = depth[k1] < depth[k2] ? k1 : k2;
                }
            }
            for (int i = 0; i < h - 1; i++) {
                for (int j = 0; j < n; j++) {
                    ancestor[i + 1][j] = ancestor[i][ancestor[i][j]];
                }
            }
        }
 
        public int[] dfs(int r) {
            int[] ei = new int[n];
            int[] ord = new int[n];
            int[] parent = new int[n];
            Arrays.fill(parent, -1);
            parent[r] = -2;
            int j = 0;
            ord[j++] = r;
            while (0 <= r) {
                if (ei[r] < edges.get(r).size()) {
                    int next = edges.get(r).get(ei[r]++);
                    if (parent[next] == -1) {
                        parent[next] = r;
                        ord[j++] = next;
                        r = next;
                    }
                    continue;
                }
                r = parent[r];
            }
            return ord;
        }
 
        public int[] diameter() {
            int u = farthest(0);
            int v = farthest(u);
            return new int[] {u, v};
        }
 
        public int farthest(int v) {
            int u = 0;
            int d = 0;
            for (int i = 0; i < n; i++) {
                int dist = dist(v, i);
                if (d < dist) {
                    d = dist;
                    u = i;
                }
            }
            return u;
        }
 
        public int depth(int u) {
            return depth[u];
        }
 
        public int depth(int r, int u) {
            return dist(r, u);
        }
 
        public int parent(int u) {
            return u == root ? -1 : ancestor[0][u];
        }
 
        public int parent(int r, int u) {
            if (u == r) {
                return -1;
            }
            int lca = lca(r, u);
            if (u == lca) {
                return la(r, depth[u] + 1);
            } else {
                return ancestor[0][u];
            }
        }
 
        public int dist(int u, int v) {
            int lca = lca(u, v);
            return depth[u] + depth[v] - depth[lca] * 2;
        }
 
        public int la(int u, int d) {
            if (d < 0 || depth[u] < d) {
                throw new IllegalArgumentException();
            }
            if (depth[u] == d) {
                return u;
            }
            int i = 31 - Integer.numberOfLeadingZeros(depth[u] - d);
            u = ancestor[i][u];
            List<Integer> bucket = buckets.get(bucketId[u]);
            int k = depth[bucket.get(0)];
            return bucket.get(k - d);
        }
 
        public int la(int r, int u, int d) {
            int lca = lca(r, u);
            if (d <= depth[r] - depth[lca]) {
                return la(r, depth[r] - d);
            } else {
                return la(u, d - depth[r] + depth[lca] * 2);
            }
        }
 
        public int lca(int u, int v) {
            int l = Math.min(preorder[u], preorder[v]);
            int r = Math.max(preorder[u], preorder[v]) + 1;
            int i = 31 - Integer.numberOfLeadingZeros(r - l);
            int k1 = table[i][l];
            int k2 = table[i][r - (1 << i)];
            return depth[k1] < depth[k2] ? k1 : k2;
        }
 
        public int lca(int r, int u, int v) {
            int lca1 = lca(r, u);
            int lca2 = lca(r, v);
            int lca3 = lca(u, v);
            if (depth[lca3] > depth[lca1]) {
                return lca3;
            } else if (depth[lca1] > depth[lca2]) {
                return lca1;
            } else {
                return lca2;
            }
        }
 
        public int[] path(int u, int v) {
            int lca = lca(u, v);
            int n = depth[u] + depth[v] - depth[lca] * 2;
            int[] path = new int[n + 1];
            path[0] = u;
            for (int i = 0; u != lca; i++) {
                u = ancestor[0][u];
                path[i + 1] = u;
            }
            for (int i = n; v != lca; i--) {
                path[i] = v;
                v = ancestor[0][v];
            }
            return path;
        }
 
        public int subtreeSize(int u) {
            return (postorder[u] - preorder[u] + 1) / 2;
        }
}