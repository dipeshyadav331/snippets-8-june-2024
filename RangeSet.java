class RangeSet {
    private long size;
    private final TreeSet<Range> set = new TreeSet<>();

    RangeSet() {
        set.add(new Range(Long.MIN_VALUE, Long.MIN_VALUE));
        set.add(new Range(Long.MAX_VALUE, Long.MAX_VALUE));
    }

    void clear() {
        set.clear();
        set.add(new Range(Long.MIN_VALUE, Long.MIN_VALUE));
        set.add(new Range(Long.MAX_VALUE, Long.MAX_VALUE));
    }

    private boolean isIntersect(Range a, Range b) {
        return Math.max(a.left, b.left) <= Math.min(a.right, b.right);
    }

    private long add0(Range range) {
        set.add(range);
        size += range.length();
        return range.length();
    }

    private long remove0(Range range) {
        set.remove(range);
        size -= range.length();
        return range.length();
    }

    boolean contains(long n) {
        return containsRangeClosed(n, n);
    }

    boolean containsRange(long l, long r) {
        return containsRangeClosed(l, r - 1);
    }

    boolean containsRangeClosed(long l, long r) {
        Range range = new Range(l + 1, l + 1);
        Range lower = Objects.requireNonNull(set.lower(range));
        return r <= lower.right;
    }

    long mex() {
        return minex(0);
    }

    long minex(long n) {
        Range range = new Range(n + 1, n + 1);
        Range lower = Objects.requireNonNull(set.lower(range));
        if (n <= lower.right) {
            return lower.right + 1;
        } else {
            return n;
        }
    }

    long maxex(long n) {
        Range range = new Range(n + 1, n + 1);
        Range lower = Objects.requireNonNull(set.lower(range));
        if (lower.right <= n - 1) {
            return n;
        } else {
            return lower.left - 1;
        }
    }

    Range included(long n) {
        Range lower = Objects.requireNonNull(set.lower(new Range(n + 1, n + 1)));
        return n <= lower.right ? lower : null;
    }

    boolean add(long n) {
        return addRangeClosed(n, n) == 1;
    }

    boolean remove(long n) {
        return removeRangeClosed(n, n) == 1;
    }

    void inverse(long n) {
        inverseRangeClosed(n, n);
    }

    long addRange(long l, long r) {
        return addRangeClosed(l, r - 1);
    }

    long addRangeClosed(long l, long r) {
        Range lower = Objects.requireNonNull(set.lower(new Range(l + 1, l + 1)));
        long added = 0;
        if (r <= lower.right) {
            return 0;
        }
        if (l - 1 <= lower.right) {
            added -= remove0(lower);
            l = lower.left;
        }
        for (Range range : new TreeSet<>(set.tailSet(new Range(l + 1, l + 1)))) {
            if (r + 1 < range.left) {
                added += add0(new Range(l, r));
                break;
            }
            added -= remove0(range);
            if (r <= range.right) {
                added += add0(new Range(l, range.right));
                break;
            }
        }
        return added;
    }

    void inverseRange(long l, long r) {
        inverseRangeClosed(l, r - 1);
    }

    void inverseRangeClosed(long l, long r) {
        Range lower = Objects.requireNonNull(set.lower(new Range(l + 1, l + 1)));
        if (l <= lower.right) {
            remove0(lower);
            if (lower.left < l) {
                add0(new Range(lower.left, l - 1));
            }
            if (r < lower.right) {
                add0(new Range(r + 1, lower.right));
                return;
            }
            l = lower.right + 1;
        }
        for (Range range : new TreeSet<>(set.tailSet(new Range(l + 1, l + 1)))) {
            if (r + 1 < range.left) {
                add0(new Range(l, r));
                break;
            }
            remove0(range);
            if (l < range.left) {
                add0(new Range(l, range.left - 1));
            }
            if (r < range.right) {
                add0(new Range(r + 1, range.right));
            }
            if (r <= range.right) {
                break;
            }
            l = range.right + 1;
        }
    }

    long removeRange(long l, long r) {
        return removeRangeClosed(l, r - 1);
    }

    long removeRangeClosed(long l, long r) {
        long removed = 0;
        Range lower = Objects.requireNonNull(set.lower(new Range(l + 1, l + 1)));
        if (l <= lower.right) {
            removed += remove0(lower);
            if (lower.left < l) {
                removed -= add0(new Range(lower.left, l - 1));
            }
            if (r < lower.right) {
                removed -= add0(new Range(r + 1, lower.right));
                return removed;
            }
        }
        for (Range range : new TreeSet<>(set.tailSet(new Range(l + 1, l + 1)))) {
            if (r < range.left) {
                break;
            }
            removed += remove0(range);
            if (r < range.right) {
                removed -= add0(new Range(r + 1, range.right));
                break;
            }
        }
        return removed;
    }

    long size() {
        return size;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Range range : set) {
            if (range.left != Long.MIN_VALUE && range.left != Long.MAX_VALUE) {
                joiner.add(range.toString());
            }
        }
        return joiner.toString();
    }

    private static class Range implements Comparable<Range> {
        private final long left;
        private final long right;

        private Range(long left, long right) {
            this.left = left;
            this.right = right;
        }

        long length() {
            return right - left + 1;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Range)) {
                return false;
            }
            Range that = (Range)o;
            return left == that.left && right == that.right;
        }

        @Override
        public int hashCode() {
            return Long.hashCode(left) * 31 + Long.hashCode(right);
        }

        @Override
        public int compareTo(Range o) {
            return left == o.left ? Long.compare(right, o.right) : Long.compare(left, o.left);
        }

        @Override
        public String toString() {
            return String.format("{%d:%d}", left, right);
        }
    }
}