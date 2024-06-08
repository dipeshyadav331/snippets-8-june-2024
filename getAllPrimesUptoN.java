  public static void getAllPrimesuptoN(int N , int[] isprime , List<Integer> primes){
    // fill the isprime array with 1
    for (int i = 2; i <= N ; i++) {
        if (isprime[i] == 1) {
            primes.add(i);
        }
        for (int p : primes) {
            if (i * p > N) {
                break;
            }
            isprime[i * p] = 0;
            if (i % p == 0) {
                break;
            }
        }
    }
  }