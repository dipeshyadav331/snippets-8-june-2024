 long gcd(long a, long b) {
        while (b != 0) {
          long t = a;
          a = b;  
          b = t % b;
        } 
        return a;
  }  