class BinaryTrie {
    private static final int BIT_LEN = 63;
    private final Node root = new Node();

    public void clear() {
        root.count = 0;
        root.left = null;
        root.right = null;
    }

    public int size() {
        return root.count;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public long get(int i) {
        if (i < 0 || size() <= i) {
            throw new IndexOutOfBoundsException();
        }
        Node node = root;
        long val = 0;
        for (int j = 0; j < BIT_LEN; j++) {
            val *= 2;
            int lc = node.left == null ? 0 : node.left.count;
            if (i < lc) {
                node = node.left;
            } else {
                val++;
                i -= lc;
                node = node.right;
            }
        }
        return val;
    }

    public void add(long x) {
        add(x, 1);
    }

    public void add(long x, int count) {
        if (x < 0 || count < 0) {
            throw new IllegalArgumentException();
        }
        Node node = root;
        node.count += count;
        node.sum += x * count;
        for (int i = BIT_LEN - 1; i >= 0; i--) {
            if ((x >> i & 1) == 0) {
                if (node.left == null) {
                    node.left = new Node();
                }
                node.left.count += count;
                node.left.sum += x * count;
                node = node.left;
            } else {
                if (node.right == null) {
                    node.right = new Node();
                }
                node.right.count += count;
                node.right.sum += x * count;
                node = node.right;
            }
        }
    }

    public boolean remove(long x) {
        return remove(x, 1);
    }

    public boolean remove(long x, int count) {
        if (x < 0 || count < 0) {
            throw new IllegalArgumentException();
        }
        count = Math.min(count, count(x));
        if (count == 0) {
            return false;
        }
        Node node = root;
        node.count -= count;
        node.sum -= x * count;
        for (int i = BIT_LEN - 1; i >= 0; i--) {
            if ((x >> i & 1) == 0) {
                node.left.count -= count;
                node.left.sum -= x * count;
                if (node.left.count == 0) {
                    node.left = null;
                    return true;
                }
                node = node.left;
            } else {
                node.right.count -= count;
                node.right.sum -= x * count;
                if (node.right.count == 0) {
                    node.right = null;
                    return true;
                }
                node = node.right;
            }
        }
        return true;
    }

    public int count(long x) {
        Node node = root;
        for (int i = BIT_LEN - 1; i >= 0; i--) {
            if ((x >> i & 1) == 0) {
                if (node.left == null) {
                    return 0;
                }
                node = node.left;
            } else {
                if (node.right == null) {
                    return 0;
                }
                node = node.right;
            }
        }
        return node.count;
    }

    public boolean contains(long x) {
        return count(x) >= 1;
    }

    public OptionalLong min() {
        if (isEmpty()) {
            return OptionalLong.empty();
        }
        Node node = root;
        long val = 0;
        for (int j = 0; j < BIT_LEN; j++) {
            val *= 2;
            if (node.left != null) {
                node = node.left;
            } else {
                node = node.right;
                val++;
            }
        }
        return OptionalLong.of(val);
    }

    public OptionalLong max() {
        if (isEmpty()) {
            return OptionalLong.empty();
        }
        Node node = root;
        long val = 0;
        for (int j = 0; j < BIT_LEN; j++) {
            val *= 2;
            if (node.right == null) {
                node = node.left;
            } else {
                node = node.right;
                val++;
            }
        }
        return OptionalLong.of(val);
    }

    public int lowerBound(long x) {
        if (isEmpty()) {
            return 0;
        }
        Node node = root;
        int i = 0;
        for (int j = BIT_LEN - 1; j >= 0; j--) {
            if ((x >> j & 1) == 0) {
                node = node.left;
            } else {
                i += node.left == null ? 0 : node.left.count;
                node = node.right;
            }
            if (node == null) {
                return i;
            }
        }
        return i;
    }

    public int upperBound(long x) {
        return lowerBound(x + 1);
    }

    public long sum(int l, int r) {
        if (0 < l) {
            return sum(0, r) - sum(0, l);
        }
        Node node = root;
        long acc = 0;
        long val = 0;
        for (int j = 0; j < BIT_LEN; j++) {
            val *= 2;
            int lc = node.left == null ? 0 : node.left.count;
            if (r < lc) {
                node = node.left;
            } else {
                acc += node.left == null ? 0 : node.left.sum;
                r -= lc;
                val++;
                node = node.right;
            }
            if (node == null) {
                return acc;
            }
        }
        acc += Math.min(r, node.count) * val;
        return acc;
    }

    public OptionalLong lower(long x) {
        int index = lowerBound(x) - 1;
        if (index < 0) {
            return OptionalLong.empty();
        }
        return OptionalLong.of(get(index));
    }

    public OptionalLong higher(long x) {
        int index = upperBound(x);
        if (size() <= index) {
            return OptionalLong.empty();
        }
        return OptionalLong.of(get(index));
    }

    public OptionalLong floor(long x) {
        int index = upperBound(x) - 1;
        if (index < 0) {
            return OptionalLong.empty();
        }
        return OptionalLong.of(get(index));
    }

    public OptionalLong ceil(long x) {
        int index = lowerBound(x);
        if (size() <= index) {
            return OptionalLong.empty();
        }
        return OptionalLong.of(get(index));
    }

    public List<Long> toList() {
        List<Long> list = new ArrayList<>();
        root.collect(0, 0, list);
        return Collections.unmodifiableList(list);
    }

    @Override
    public String toString() {
        return toList().toString();
    }

    private static class Node {
        private int count;
        private long sum;
        private Node left;
        private Node right;

        private void collect(int depth, long val, List<Long> list) {
            if (left != null) {
                left.collect(depth + 1, val * 2, list);
            }
            if (right != null) {
                right.collect(depth + 1, val * 2 + 1, list);
            }
            if (depth == BIT_LEN) {
                for (int i = 0; i < count; i++) {
                    list.add(val);
                }
            }
        }
    }
}