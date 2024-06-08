import java.io.*;
import java.util.*;
import java.lang.*;
public class Main implements Runnable{
    // static StringBuilder ans = new StringBuilder();
  static PrintWriter out = new PrintWriter(System.out);
  static FastReader in = new FastReader();
  public static void main(String[] args) throws IOException{
    new Thread(null, new Main(), "whatever", 1<<27).start();
    /*
    
    try 26 then 27 then 28
    
    */
  } 
  
  public void run() {
        // Do whatever you want in this function instead of main
        Scanner scn = new Scanner(System.in); 
    	int ntc = in.nextInt();
        // int ntc = 1;
	    for(int tno = 1 ; tno <= ntc ; tno++) solve();
	    // out.print(ans);
	    out.flush();
}
  public static void solve(){
        int n = inp() , flag = 0 , temp = 0 , slag = 0;
        





  }


      //  ***  ***  ***  ***  ***  ***  ***  ***  ***  ***  ***  *** 
	public static int[] ia(int n){
		int[] arr = new int[n];
		for(int i = 0 ; i < n ; i++){
			arr[i] = in.nextInt();
		}
		return arr;
	}
	public static int inp(){return in.nextInt();}
	public static String inps(){return in.next();}
	public static int[] radixSort2(int[] a){
		int n = a.length;
		int[] c0 = new int[0x101];
		int[] c1 = new int[0x101];
		int[] c2 = new int[0x101];
		int[] c3 = new int[0x101];
		for(int v : a) {
			c0[(v&0xff)+1]++;
			c1[(v>>>8&0xff)+1]++;
			c2[(v>>>16&0xff)+1]++;
			c3[(v>>>24^0x80)+1]++;
		}
		for(int i = 0;i < 0xff;i++) {
			c0[i+1] += c0[i];
			c1[i+1] += c1[i];
			c2[i+1] += c2[i];
			c3[i+1] += c3[i];
		}
		int[] t = new int[n];
		for(int v : a)t[c0[v&0xff]++] = v;
		for(int v : t)a[c1[v>>>8&0xff]++] = v;
		for(int v : a)t[c2[v>>>16&0xff]++] = v;
		for(int v : t)a[c3[v>>>24^0x80]++] = v;
		return a;
	}
   static long mod = 1000000007;
   public static long pow(long a, long b ) {
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
 public static void no(){out.println("No");}
 public static void yes(){out.println("Yes");}
  static long gcd(long a, long b) {
        while (b != 0) {
          long t = a;
          a = b;  
          b = t % b;
        } 
        return a;
  }    
  static class FastReader {
      BufferedReader br;
      StringTokenizer st;
      public FastReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
      }
      String next() {
        while (st == null || !st.hasMoreTokens()) {
          try {
            st = new StringTokenizer(br.readLine());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        return st.nextToken();
      }
      int nextInt() {
        return Integer.parseInt(next());
      }
      long nextLong() {
        return Long.parseLong(next());
      }
      double nextDouble() {
        return Double.parseDouble(next());
      }
      String nextLine() {
        String str = "";
        try {
          str = br.readLine().trim();
        } catch (Exception e) {
          e.printStackTrace();
        }
        return str;
      }
    }
}

/*
integer to string -> String str1 = Integer.toString(12213) or String.valueOf(c);
string to integer -> int i=Integer.parseInt("200");  
*/