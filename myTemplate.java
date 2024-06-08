import java.io.*;
import java.util.*;
import java.lang.*;
public class Main{
    // static StringBuilder ans = new StringBuilder();
  static PrintWriter out = new PrintWriter(System.out);
  static FastReader in = new FastReader();
  public static void main(String[] args) throws IOException{
    // Scanner scn = new Scanner(System.in); 
    int ntc = in.nextInt();
    // int ntc = 1;
    for(int tno = 1 ; tno <= ntc ; tno++) solve();
    // out.print(ans);
    out.flush();
  } 
  public static void solve(){
        int n = inp() , flag = 0 , temp = 0 , slag = 0;
        





  }
   
  /*
        ***  ***  ***  ***  ***  ***  ***  ***  ***  ***  ***  *** 
             
  */
	static final Random random = new Random();

	static void ruffleSort(int[] a) {
		int n=a.length;//shuffle, then sort 
		for (int i=0; i<n; i++) {
			int oi=random.nextInt(n), temp=a[oi];
			a[oi]=a[i]; a[i]=temp;
		}
		Arrays.sort(a);
	}
	public static int[] ia(int n){
		int[] arr = new int[n];
		for(int i = 0 ; i < n ; i++){
			arr[i] = in.nextInt();
		}
		return arr;
	}
	public static int inp(){
		return in.nextInt();
	}
 
	public static void run(int tno){
		out.println("Running for case " + tno);
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

 public static void no(){
  out.println("No");
 }
 public static void yes(){
  out.println("Yes");
 }
                            
  public static void sort(int[] arr){
      ArrayList<Integer> ls = new ArrayList<Integer>();
      for(int x: arr) ls.add(x);
      Collections.sort(ls);
      for(int i=0; i < arr.length; i++) arr[i] = ls.get(i);
  }
  public static void reverseSort(int[] arr){
      ArrayList<Integer> ls = new ArrayList<Integer>();
      for(int x: arr) ls.add(x);
      Collections.sort(ls);
      Collections.reverse(ls);
      for(int i=0; i < arr.length; i++) arr[i] = ls.get(i);
  }
  static long gcd(long a, long b) {
        while (b != 0) {
          long t = a;
          a = b;  
          b = t % b;
        } 
        return a;
  }    

  public static void printArray(int[] arr){
    // out.print("arr -> ");
    for(int i = 0 ; i < arr.length ; i++){
      out.print(arr[i] + " ");
    }
    out.println();
  }


  public static String reverse(String str){
    StringBuilder sb = new StringBuilder(str);
    return sb.reverse().toString();
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