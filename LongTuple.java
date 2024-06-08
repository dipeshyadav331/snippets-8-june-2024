class LongTuple implements Comparable<LongTuple> {
    long a, b, c;
 
    LongTuple(long a, long b, long c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
 
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LongTuple)) {
            return false;
        }
        LongTuple that = (LongTuple)o;
        return a == that.a && b == that.b && c == that.c;
    }
 
    @Override
    public int hashCode() {
        return (Long.hashCode(a) * 31 + Long.hashCode(b)) * 31 + Long.hashCode(c);
    }
 
    @Override
    public int compareTo(LongTuple o) {
        return a == o.a ? b == o.b ? Long.compare(c, o.c) : Long.compare(b, o.b) : Long.compare(a, o.a);
    }
 
    @Override
    public String toString() {
        return String.format("[%d, %d, %d]", a, b, c);
    }
}