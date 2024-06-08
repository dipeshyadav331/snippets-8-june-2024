class SparseTable{
    	private int n;
    	private int[] data;
    	private int[][] sparseTable;
    	
    	public SparseTable(int[] data){
    		this.data = data;
    		this.n = data.length;
    		sparseTable = new int[n][32];
    		for(int i = 0 ; i < n ; i++){
    			sparseTable[i][0] = data[i];	
    		}
    		build(sparseTable);
    	}
    	
    	private void build(int[][] sparseTable){
    		for(int k = 1 ; k < Math.ceil(Math.log(n) / Math.log(2)) ; k++){
	    		for(int i = 0 ; i + (1 << k) - 1 < n ; i++){
	    			sparseTable[i][k] = Math.min(sparseTable[i][k-1] , sparseTable[i+(1<<(k-1))][k-1]);
	    		}
	    	}
    	}
    	
    	public int query(int l , int r){
	    	int len = r - l + 1;
	    	int k = 0;
	    	while((1 << (k+1)) <= len) k++;
	    	return Math.min(sparseTable[l][k] , sparseTable[r-(1<<k)+1][k]);
	    }
    }