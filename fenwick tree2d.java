class FenwickTree{
		// no build method
	    private int _n;
	    private long[][] data;
	
	    public FenwickTree(int n){
	        this._n = n;
	        data = new long[n][n];
	    }
		
	    public void set(int p, long x , int row){
	        add(p, x - get(p , row) , row);
	    }
	
	    public void add(int p, long x , int row){
	        assert(0<=p && p<_n);
	        p++;
	        while(p<=_n){
	            data[row][p-1] += x;
	            p += p&-p;
	        }
	    }
	    
	    public long sum(int l, int r , int row){
	        assert(0<=l && l<=r && r<=_n);
	        return sum(r , row)-sum(l , row);
	    }
	
	    public long get(int p , int row){
	        return sum(p, p+1 , row);
	    }
	
	    private long sum(int r , int row){
	        long s = 0;
	        while(r>0){
	            s += data[row][r-1];
	            r -= r&-r;
	        }
	        return s;
	    }
	    
	    public String toString(){
	    	return Arrays.deepToString(data);
	    }
	}