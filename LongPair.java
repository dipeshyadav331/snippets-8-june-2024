class LongPair implements Comparable<LongPair> {
    long first;
    long second;
 
    LongPair(long first, long second) {
        this.first = first;
        this.second = second;
    }
 
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LongPair)) {
            return false;
        }
        LongPair that = (LongPair)o;
        return first == that.first && second == that.second;
    }
 
    @Override
    public int hashCode() {
        return Long.hashCode(first) * 31 + Long.hashCode(second);
    }
 
    @Override
    public int compareTo(LongPair o) {
        return first == o.first ? Long.compare(second, o.second) : Long.compare(first, o.first);
    }
 
    @Override
    public String toString() {
        return String.format("[%d, %d]", first, second);
    }
}