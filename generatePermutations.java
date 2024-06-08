static class Permutation implements Iterator<int[]>, Iterable<int[]> {
        private int[] a;
 
        Permutation(int n) {
            this.a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = i;
            }
        }
 
        Permutation(int[] a) {
            this.a = a;
        }
 
        @Override
        public boolean hasNext() {
            return a != null;
        }
 
        @Override
        public int[] next() {
            int[] ret = a.clone();
            if (!nextPermutation(a)) {
                a = null;
            }
            return ret;
        }
 
        @Override
        public Iterator<int[]> iterator() {
            return this;
        }
 
        static boolean nextPermutation(int[] a) {
            if (a.length <= 1) {
                return false;
            }
            int n = a.length;
            int p = n - 2;
            while (p >= 1 && a[p] >= a[p + 1]) {
                p--;
            }
            int q = n - 1;
            while (q > p && a[q] <= a[p]) {
                q--;
            }
            if (q == 0) {
                return false;
            }
            int temp = a[p];
            a[p] = a[q];
            a[q] = temp;
            int l = p + 1;
            int r = n - 1;
            while (l < r) {
                temp = a[l];
                a[l] = a[r];
                a[r] = temp;
                l++;
                r--;
            }
            return true;
        }
    }