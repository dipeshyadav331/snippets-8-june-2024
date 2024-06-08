class IntPair implements Comparable<IntPair> {
	    int first;
	    int second;
	 
	    IntPair(int first, int second) {
	        this.first = first;
	        this.second = second;
	    }
	 
	    @Override
	    public boolean equals(Object o) {
	        if (!(o instanceof IntPair)) {
	            return false;
	        }
	        IntPair that = (IntPair)o;
	        return first == that.first && second == that.second;
	    }
	 
	    @Override
	    public int hashCode() {
	        return first * 31 + second;
	    }
	 
	    @Override
	    public int compareTo(IntPair o) {
	        return first == o.first ? Integer.compare(second, o.second) : Integer.compare(first, o.first);
	    }
	 
	    @Override
	    public String toString() {
	        return String.format("[%d, %d]", first, second);
	    }
	}