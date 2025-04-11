
import java.util.*;

public class Practice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Value of n: ");
        int n = sc.nextInt();
        int[] a = new int[n+1];
        
        for(int i=1;i<n;i++){
            int val = sc.nextInt();
            int idx = sc.nextInt();
            a[idx] = val;
        }
        System.out.println(a);
        sc.close();
    }
}
