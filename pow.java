long pow(long a, long b ) {
	    long res = 1;
	    while (b > 0) {
	        if ((b & 1) == 0) {
	            a  = (a*a) ;
	            b = b >> 1;
	        } else {
	            res = (res*a) ;
	            b--;
	        }
	    }
	    return res;
}