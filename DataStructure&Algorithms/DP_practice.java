import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class DP_practice{

    //Fibonacci Numbers - VS Code -
    //Method 1 - Recursion
    public static int fibonacci01(int n){
        if(n==0 || n==1) return n;

        return fibonacci01(n-1)+fibonacci01(n-2);
    }

    //Method 2 - Memoization
    public static int fibonacci02(int[] dp,int n){
        if(n==0 || n==1){
            if(n==0) return dp[n] = 0;
            return dp[n] = 1;
        }

        if(dp[n]!=0) return dp[n];

        return dp[n] = fibonacci02(dp,n-1) + fibonacci02(dp, n-2);
    }
    public static int fibonacci02(int n){
        int[]dp = new int[n+1];
        Arrays.fill(dp,-1);
        return fibonacci02(dp,n);
    }

    //Method 3 - Tabulation
    public static int fibonacci03(int n){
        int[] dp = new int[n+1];
        for(int i=0;i<=n;i++){
            if(i==0) dp[i] = 0;
            else if(i==1) dp[i] = 1;
            else dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }

    //Method 4 - Final Optimized Solution
    public static int fibonacci04(int n){
        int a = 0;
        int b = 1;
        int c;

        for(int i=2;i<=n;i++){
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    //https://www.geeksforgeeks.org/problems/nth-fibonacci-number1335/1
    static int nthFibonacci(int n){
        long a = 0;
        long b = 1;
        long c;
        int mod = 1000000007;
        for(int i=2;i<=n;i++){
            c = (a%mod + b%mod)%mod;
            a = b;
            b = c;
        }
        return (int)(b%mod);
    }

    //No. of ways to reach a destination in a grid(single jump)
    //Method 1 - Memoization
    public static int mazePath01(int sr,int sc,int dr,int dc,int[][] dp){
        if(sr==dr && sc==dc) return dp[sr][sc] = 1;

        if(dp[sr][sc] != 0 ) return dp[sr][sc];

        if(sr+1<=dr)
            dp[sr][sc] += mazePath01(sr+1,sc,dr,dc,dp);
        if(sc+1<=dc)
            dp[sr][sc] += mazePath01(sr,sc+1,dr,dc,dp);
        if(sr+1<=dr && sc+1<=dc)
            dp[sr][sc] += mazePath01(sr+1,sc+1,dr,dc,dp);
        return dp[sr][sc];
    }
    public static int mazePath01(int sr,int sc,int dr,int dc){
        int[][] dp = new int[dr][dc];
        return mazePath01(sr,sc,dr,dc,dp);
    }

    //Method 2 - Tabulation
    public static int mazePath02(int sr,int sc,int dr,int dc){
        int[][] dp = new int[dr][dc];
        int[][] dir = {{0,1},{1,1},{1,0}};

        for(int i=dr;i>=sr;i--){
            for(int j=dc;j>=sc;j--){
                if(i==dr && j==dc){
                    dp[i][j] = 1;
                    continue;
                }
                for(int d=0;d<3;d++){
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];
                    if(r<=dr && c<=dc)
                        dp[i][j] += dp[r][c];
                }
            }
        }

        return dp[sr][sc];
    }

    //No. of ways to reach a destination in a grid(multiple jumps)
    //Method 1 - Memoization
    public static int mazePathMultiJumps01(int sr,int sc,int dr,int dc,int[][] dp){
        if(sr==dr && sc==dc){
            return dp[sr][sc] = 1;
        }

        if(dp[sr][sc]!=0) return dp[sr][sc];

        for(int k=1;sr+k<=dr;k++)
            dp[sr][sc] += mazePathMultiJumps01(sr+k,sc,dr,dc,dp);
        for(int k=1;sc+k<=dc;k++)
            dp[sr][sc] += mazePathMultiJumps01(sr,sc+k,dr,dc,dp);
        for(int k=1;sr+k<=dr && sc+k<=dc;k++)
            dp[sr][sc] += mazePathMultiJumps01(sr+k,sc+k,dr,dc,dp);
        return dp[sr][sc];
    }
    public static int mazePathMultiJumps01(int sr,int sc,int dr,int dc){
        int[][] dp = new int[dr][dc];
        return mazePathMultiJumps01(sr,sc,dr,dc,dp);
    }

    //Method 2 - Tabulation
    public static int mazePathMultiJumps02(int sr,int sc,int dr,int dc){
        int[][] dp = new int[dr][dc];

        for(int i=dr;i>=sr;i--){
            for(int j=dc;j>=sc;j--){
                if(i==dr && j==dc){
                    dp[i][j] = 1;
                    continue;
                }

                int count = 0;
                for(int k=1;j+k<=dc;k++)
                    count += dp[i][j+k];
                for(int k=1;i+k<=dr;k++)
                    count += dp[i+k][j];
                for(int k=1;i+k<=dr && j+k<=dc;k++)
                    count += dp[i+k][j+k];
                
                dp[i][j] = count;
            }
        }
        return dp[sr][sc];
    }

    //Dice throw to determine jump - No. of ways to reach a destination
    //Method 1 - Recursion
    public static int boardPathRecursion(int sp,int ep){
        if(sp==ep) return 1;

        int count = 0;
        for(int i=1;i<=6;i++){
            if(sp+i<=ep)
                count += boardPathRecursion(sp+i,ep);
        }
        return count;
    }

    //Method 2 - Memoization
    public static int boardPath01(int sp,int ep,int[] dp){
        if(sp==ep) return dp[sp] = 1;

        if(dp[sp]!=0) return dp[sp];

        int count = 0;
        for(int i=1;i<=6;i++){
            if(sp+i<=ep)
                count += boardPath01(sp+i,ep,dp);
        }
        return dp[sp] = count;
    }
    public static int boardPath01(int sp,int ep){
        int[] dp = new int[ep];
        return boardPath01(sp,ep,dp);
    }
    
    //Method 3 - Tabulation
    public static int boardPath02(int sp,int ep){
        int[] dp = new int[ep];

        for(int i=ep;i>=sp;i--){
            if(i==ep){
                dp[i] = 1;
                continue;
            }

            for(int j=1;j<=6;j++){
                if(i+j<=ep) dp[i] += dp[i+j];
            }
        }
        return dp[sp];
    }

    //Method 4 - Final Optimized Solution
    public static int boardPath03(int sp,int ep){
        LinkedList<Integer> list = new LinkedList<>();
        
        for(int i=ep;i>=sp;i--){
            if(list.size()<=1) list.add(1);

            else if(list.size()<=6) list.addFirst(list.getFirst()*2);
            else list.addFirst(list.getFirst()*2-list.removeLast());
        }

        return list.getFirst();
    }

    //Leetcode 70
    //Method 1 - Recursion
    public static int climbStairs01(int n){
        if(n<=1) return 1;
        return climbStairs01(n-1) + climbStairs01(n-2);
    }

    //Method 2 - Memoization
    public int climbStairs02(int i,int n,int[] dp){
        if(i==n-1||i==n) return dp[i] = 1;
        if(dp[i]!=0) return dp[i];
        return dp[i] = climbStairs02(i+1,n,dp) + climbStairs02(i+2,n,dp);
    }
    public int climbStairs02(int n) {
        int[] dp = new int[n+1];
        return climbStairs02(0,n,dp);
    }

    //Method 3 - Tabulation
    public int climbStairs03(int n) {
        if(n<=1) return 1;
        int[] dp = new int[n+1];

        for(int i=n;i>=0;i--){
            if(i==n || i==n-1){
                dp[i] = 1;
                continue;
            } 
            dp[i] = dp[i+1] + dp[i+2];
        }
        return dp[0];
    }

    //Method 4 - Final Optimized Solution
    public int climbStairs(int n) {
        if(n<=1) return 1;
        int a = 1;
        int b = 1;
        int c = a + b;
        for(int i=n-2;i>=0;i--){
            c = a+b;
            b = a;
            a = c;
        }
        return a;
    }

    //Leetcode 746
    //Method 1 - Memoization
    public int minCostClimbingStairs01(int i,int[] cost,int[] dp){
        if(i==cost.length-1||i==cost.length-2) return dp[i] = cost[i];

        if(dp[i]!=-1) return dp[i];
        int min = 0;
        min = Math.min(minCostClimbingStairs01(i+1,cost,dp),minCostClimbingStairs01(i+2,cost,dp));
        return dp[i] = min+cost[i];
    }
    public int minCostClimbingStairs01(int[] cost) {
        int[] dp = new int[cost.length];
        Arrays.fill(dp,-1);
        return Math.min(minCostClimbingStairs01(0,cost,dp),minCostClimbingStairs01(1,cost,dp));
    }

    //Method 2 - Tabulation
    public int minCostClimbingStairs02(int[] cost) {
        int[] dp = new int[cost.length];

        for(int i=cost.length-1;i>=0;i--){
            if(i==cost.length-1 || i==cost.length-2) {
                dp[i] = cost[i];
                continue;
            }

            dp[i] = cost[i] + Math.min(dp[i+1],dp[i+2]);
        }
        return Math.min(dp[0],dp[1]);
    }

    //Method 3 - Final Optimized Solution
    public int minCostClimbingStairs03(int[] cost){
        int a=cost[cost.length-2],b=cost[cost.length-1],c=0;
        for(int i=cost.length-3;i>=0;i--){
            c = cost[i] + Math.min(a,b);
            b = a;
            a = c;
        }
        return Math.min(a,b);
    }

    /*Mock Interview by Google Question
    Question : You are eating a candy bar that is made of pieces in a single row (e.g., a Toblerone). 
    You can bite off 1 or 2 pieces at a time. How many different ways can you eat a bar that is n pieces long? */
    //Method 1 - Memoization
    private int candyBar(int i,int n,int[] dp){
        if(i==n) return dp[i] = 1;
        if(i>n) return 0;

        if(dp[i]!=0) return dp[i];

        return dp[i] = candyBar(i+1,n,dp) + candyBar(i+2,n,dp);

    }
    public int candyBar01(int n){
        int[] dp = new int[n+1];

        return candyBar(0,n,dp);
    }

    //Method 2 - Tabulation
    public int candyBar(int 


    //https://www.geeksforgeeks.org/problems/geek-jump/1
    private int minimumEnergy(int idx,int n,int[] dp,int[] arr){
        if(idx==n-1) return dp[idx] = 0;
        
        if(dp[idx]!=-1) return dp[idx];
        int ans = (int)1e8;
        ans = minimumEnergy(idx+1,n,dp,arr) + Math.abs(arr[idx]-arr[idx+1]);
        if(idx+2<n) ans = Math.min(ans, minimumEnergy(idx+2,n,dp,arr) + Math.abs(arr[idx]-arr[idx+2]));
        
        return dp[idx] = ans;
    }
    public int minimumEnergy(int arr[],int N){
        int[] dp = new int[N];
        Arrays.fill(dp,-1);
        return minimumEnergy(0,N,dp,arr);
    }

    //https://www.geeksforgeeks.org/problems/geeks-training/1
    private int maximumPoints(int idx,int flag,int[][] dp,int[][] arr,int n){
        if(idx==n) return 0;
        
        if(dp[idx][flag]!=0) return dp[idx][flag];
        
        int ans = -(int)1e8;
        for(int i=0;i<3;i++){
            if(i!=flag) ans = Math.max(ans,maximumPoints(idx+1,i,dp,arr,n));
        }
        
        return dp[idx][flag] = ans + arr[idx][flag];
    }
    public int maximumPoints(int arr[][], int N) {
        int[][] dp = new int[N][3];
        
        int run = Math.max(maximumPoints(1,1,dp,arr,N),maximumPoints(1,2,dp,arr,N)) + arr[0][0];
        int fight = Math.max(maximumPoints(1,0,dp,arr,N),maximumPoints(1,2,dp,arr,N)) + arr[0][1];
        int learn = Math.max(maximumPoints(1,0,dp,arr,N),maximumPoints(1,1,dp,arr,N)) + arr[0][2];
        
        return Math.max(run,Math.max(fight,learn));
    }

    //Leetcode 1137
    public int tribonacci(int n) {
        if(n==0||n==1||n==2){
            if(n==0||n==1) return n;
            else return 1;
        }
        int a = 0;
        int b = 1;
        int c = 1;
        int d = 0;
        for(int i=3;i<=n;i++){
            d = a+b+c;
            a = b;
            b = c;
            c = d;
        }
        return c;
    }

    //Leetcode 62
    private int uniquePaths(int i,int j,int m,int n,int[][] dp){
        if(i==m-1 && j==n-1){
            return dp[i][j] = 1;
        }

        if(dp[i][j]!=0) return dp[i][j];

        int count = 0;
        if(i+1<m) count += uniquePaths(i+1,j,m,n,dp);
        if(j+1<n) count += uniquePaths(i,j+1,m,n,dp);

        return dp[i][j] = count;
    }
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        return uniquePaths(0,0,m,n,dp);
    }

    //Leetcode 63
    private int uniquePathsWithObstacles(int i,int j,int n,int m,int[][] dp,int[][] obstacleGrid){
        if(i==n-1 && j==m-1) return dp[i][j] = 1;

        if(dp[i][j]!=-1) return dp[i][j];

        int count = 0;
        if(i+1<n && obstacleGrid[i+1][j]==0) count += uniquePathsWithObstacles(i+1,j,n,m,dp,obstacleGrid);
        if(j+1<m && obstacleGrid[i][j+1]==0) count += uniquePathsWithObstacles(i,j+1,n,m,dp,obstacleGrid);

        return dp[i][j] = count;
    }
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int n = obstacleGrid.length;
        int m = obstacleGrid[0].length;

        int[][] dp = new int[n][m];
        for(int[] d:dp) Arrays.fill(d,-1);

        return (obstacleGrid[0][0]==0) ? uniquePathsWithObstacles(0,0,n,m,dp,obstacleGrid) : 0;
    }

    //Leetcode 64
    private int minPathSum(int i,int j,int n,int m,int[][] dp,int[][] grid){
        if(i==n-1 && j==m-1){
            return dp[i][j] = grid[i][j];
        }

        if(dp[i][j]!=-1) return dp[i][j];

        int min = (int)1e8;
        if(i+1<n) min = minPathSum(i+1,j,n,m,dp,grid);
        if(j+1<m) min = Math.min(min,minPathSum(i,j+1,n,m,dp,grid));

        return dp[i][j] = min + grid[i][j]; 
    }
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[][] dp = new int[n][m];
        for(int[] d:dp) Arrays.fill(d,-1);
        return minPathSum(0,0,n,m,dp,grid);
    }

    //Leetcode 931
    private int minFallingPathSum(int i,int j,int n,int[][] dir,Integer[][] dp,int[][] matrix){
        if(i==n-1) return dp[i][j] = matrix[i][j];

        if(dp[i][j]!=null) return dp[i][j];

        dp[i][j] = (int)1e9;
        for(int d=0;d<3;d++){
            int r = i + dir[d][0];
            int c = j + dir[d][1];
            if(r>=0 && c>=0 && r<n && c<n){
                dp[i][j] = Math.min(dp[i][j],minFallingPathSum(r,c,n,dir,dp,matrix));
            }
        }  

        return dp[i][j] = dp[i][j] + matrix[i][j];
    }
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        if(n==1) return matrix[0][0];
        Integer[][] dp = new Integer[n][n];
        int[][] dir = {{1,0},{1,-1},{1,1}};
        int ans = (int)1e9;
        for(int i=0;i<n;i++){
            for(int d=0;d<3;d++){
                int r = 0 + dir[d][0];
                int c = i + dir[d][1];
                if(r>=0 && c>=0 && r<n && c<n)
                    ans = Math.min(ans,minFallingPathSum(r,c,n,dir,dp,matrix)+matrix[0][i]);
            }
        }

        return ans;
    }

    //https://www.geeksforgeeks.org/problems/friends-pairing-problem5425/1
    //Method 1 - Memoization
    int mod = (int)1e9 + 7;
    public long countFriendsPairing01(int n,long[] dp){
        if(n<=1) return dp[n] = 1;
        
        if(dp[n]!=0) return dp[n];
        long single = countFriendsPairing01(n-1,dp);
        long pair = countFriendsPairing01(n-2,dp)*(n-1);
        
        return dp[n] = (single%mod + pair%mod)%mod;
    }
    public long countFriendsPairings01(int n) 
    { 
        long[] dp = new long[n+1];
        return countFriendsPairing01(n,dp);
    }

    //Method 2 - Tabulation
    public long countFriendsPairings02(int n) 
    { 
        long[] dp = new long[n+1];
        int mod = (int)1e9 + 7;
        for(int i=0;i<=n;i++){
            if(i<=1){
                dp[i] = 1;
                continue;
            }
            
            dp[i] = (dp[i-1]%mod + (dp[i-2]*(i-1))%mod)%mod;
        }
        return dp[n];
    }

    //Method 3 - Final Optimized Solution
    public long countFriendsPairings(int n) 
    { 
       int mod = (int)1e9 + 7;
       long a = 1;
       long b = 1;
       long c = 0;
       
       for(int i=0;i<=n;i++){
           if(i<=1) continue;
           
           c = (b%mod + (a*(i-1))%mod)%mod;
           a = b;
           b = c;
       }
       
       return b;
    }

    //https://www.geeksforgeeks.org/problems/gold-mine-problem2608/1
    //Method 1 - Memoization
    static int maxGold(int i,int j,int n,int m,int[][] dir,int[][] dp,int[][] M){
        if(j==m-1) return M[i][j];
        
        if(dp[i][j]!=0) return dp[i][j];
        
        for(int d=0;d<dir.length;d++){
            int x = i + dir[d][0];
            int y = j + dir[d][1];
            if(x>=0 && y>=0 && x<n && y<m){
                dp[i][j] = Math.max(dp[i][j],maxGold(x,y,n,m,dir,dp,M));
            }
        }
        
        return dp[i][j] = dp[i][j] + M[i][j];
    }
    static int maxGold01(int n, int m, int M[][])
    {
        int[][] dp = new int[n][m];
        int max = 0;
        int[][] dir = {{-1,1},{0,1},{1,1}};
        for(int i=0;i<n;i++){
            max = Math.max(max,maxGold(i,0,n,m,dir,dp,M));
        }
        return max;
    }

    //Method 2 - Tabulation
    static int maxGold(int n, int m, int M[][])
    {
        int[][] dp = new int[n][m];
        int[][] dir = {{-1,1},{0,1},{1,1}};
        int max = 0;
        for(int j=m-1;j>=0;j--){
            for(int i=0;i<n;i++){
                
                if(j==m-1) {
                    dp[i][j] = M[i][j];
                    continue;
                }
                
                for(int d=0;d<dir.length;d++){
                    int x = i + dir[d][0];
                    int y = j + dir[d][1];
                    
                    if(x>=0 && y>=0 && x<n && y<m){
                        dp[i][j] = Math.max(dp[i][j],dp[x][y]);
                    }
                }
                dp[i][j] += M[i][j];
                if(j==0) max = Math.max(max,dp[i][j]);
            }
        }
        return max;
    }

    //Leetcode 91
    //Method 1 - Memoization
    public int numDecodings01(int i,int[] dp,String s){
        if(i == s.length()) return dp[i] = 1;

        if(dp[i]!=-1) return dp[i];

        if(s.charAt(i)=='0') return dp[i] = 0;
        int count = 0;

        count += numDecodings01(i+1,dp,s);

        if(i<s.length()-1){
            int num = (s.charAt(i)-'0')*10 + (s.charAt(i+1)-'0');
            if(num<=26) count += numDecodings01(i+2,dp,s);
        }

        return dp[i] = count;
    }
    public int numDecodings01(String s) {
        int[] dp = new int[s.length()+1];
        Arrays.fill(dp,-1);
        return numDecodings01(0,dp,s); 
    }

    //Method 2 - Tabulation
    public int numDecodings02(String s) {
        int[] dp = new int[s.length()+1];

        for(int i=s.length();i>=0;i--){
            if(i==s.length()){
                dp[i] = 1;
                continue;
            }

            if(s.charAt(i)=='0'){
                dp[i] = 0;
            }else{
                int count = 0;
                count += dp[i+1];

                if(i<s.length()-1){
                    int num = (s.charAt(i)-'0')*10 + (s.charAt(i+1)-'0');
                    if(num<=26) count += dp[i+2];
                }
                dp[i] = count;
            }

        }

        return dp[0];
    }

    //Method 3 - Final Optimized Solution
    public int numDecodings(String s) {
        int a = 1,b=0;

        for(int i=s.length()-1;i>=0;i--){
            
            if(s.charAt(i)=='0'){
                b = a;
                a = 0;
                continue;
            }

            int count = 0;
            count += a;

            if(i<s.length()-1){
                int num = (s.charAt(i)-'0')*10 + (s.charAt(i+1)-'0');
                if(num<=26) count += b;
            }

            b = a;
            a = count;
        }

        return a;
    }

    //Leetcode 639
    //Method 1 - Memoization
    public long numDecodings(int i,int mod,long[] dp,String s){
        if(i==s.length()) return dp[i] = 1;

        if(dp[i]!=-1) return dp[i];

        if(s.charAt(i)=='0') return dp[i] = 0;

        long count = 0;
        if(s.charAt(i)=='*'){
            count =  (count + 9*numDecodings(i+1,mod,dp,s))%mod;
            if(i<s.length()-1){
                if(s.charAt(i+1)=='*'){
                    count =  (count + 15*numDecodings(i+2,mod,dp,s))%mod;
                }else{
                    int num = s.charAt(i+1)-'0';
                    if(num<=6)
                        count = (count + 2*numDecodings(i+2,mod,dp,s))%mod;
                    else count = (count + numDecodings(i+2,mod,dp,s))%mod;
                }
            }
            
        }else{
            count = (count + numDecodings(i+1,mod,dp,s))%mod;
            int num = s.charAt(i)-'0';
            if(i<s.length()-1){
                if(s.charAt(i+1)=='*'){
                    if(num==1){
                        count = (count + 9*numDecodings(i+2,mod,dp,s))%mod;
                    }else if(num==2){
                        count = (count + 6*numDecodings(i+2,mod,dp,s))%mod;
                    }
                }else{
                    num = num*10 + (s.charAt(i+1)-'0');
                    if(num<=26) count = (count + numDecodings(i+2,mod,dp,s))%mod;
                }
            }
        }

        return dp[i] = count;
    }
    public int numDecodingsII_01(String s) {
        long[] dp = new long[s.length()+1];
        Arrays.fill(dp,-1);
        int mod = (int)1e9 + 7;
        return (int)numDecodings(0,mod,dp,s);
    }

    //Method 2 - Tabulation
    public int numDecodingsII_02(String s) {
        int n = s.length();
        long[] dp = new long[n+1];
        int mod = (int)(Math.pow(10,9) + 7);
        for(int idx=n;idx>=0;idx--){
            if(idx==n){
                dp[idx] = 1;
                continue;
            }

            long count = 0;

            if(s.charAt(idx)=='*'){
                count = (count + 9*dp[idx+1])%mod;

                if(idx<n-1){
                    if(s.charAt(idx+1)=='*'){
                        count = (count + 15*dp[idx+2])%mod;
                    }else{
                        int num = s.charAt(idx+1)-'0';
                        if(num>=0 && num<=6)
                            count = (count + 2*dp[idx+2])%mod;
                        else
                            count = (count + dp[idx+2])%mod;
                    }
                }
            }
            else if(s.charAt(idx)!='0'){
                count  = (count + dp[idx+1])%mod;

                if(idx<n-1){
                    if(s.charAt(idx+1)=='*'){
                        if(s.charAt(idx)=='1')
                            count = (count + 9*dp[idx+2])%mod;
                        else if(s.charAt(idx)=='2')
                            count = (count + 6*dp[idx+2])%mod;
                    }else{
                        int num = (s.charAt(idx)-'0')*10 + (s.charAt(idx+1)-'0');
                        if(num<=26)
                            count  = (count + dp[idx+2])%mod;
                    }
                }
            }
            dp[idx] = count;
        }
        return (int)dp[0];
    }

    //Method 3 - Final Optimized Solution
    public int numDecodingsII_03(String s) {
        int n = s.length();
        long a = 1,b=0;
        int mod = (int)(Math.pow(10,9)+7);
        for(int i=n-1;i>=0;i--){
            long count = 0;
            if(s.charAt(i)=='*'){
                count = (count + 9*a)%mod;
                
                if(i<n-1){
                    if(s.charAt(i+1)=='*') 
                        count = (count + 15*b)%mod;
                    else if(s.charAt(i+1)>='0' && s.charAt(i+1)<='6')
                        count = (count + 2*b)%mod;
                    else 
                        count = (count + b)%mod;
                }
            }
            else if(s.charAt(i)!='0'){
                count = (count + a)%mod;
                if(i<n-1){
                    int num = s.charAt(i) - '0';
                    if(s.charAt(i+1)=='*'){
                        if(num==1)
                            count = (count + 9*b)%mod;
                        else if(num==2)
                            count  = (count + 6*b)%mod;
                    }
                    else{
                        char ch2 = s.charAt(i+1);
                        int num2 = num*10 + (ch2-'0');
                        if(num2<=26)
                            count = (count + b)%mod;
                    }
                }
            }
            b = a;
            a = count;
        }

        return (int)a;
    }

    //Count no. of ways to partition a set into k subsets.
    //https://www.geeksforgeeks.org/count-number-of-ways-to-partition-a-set-into-k-subsets/

    //Method 1 - Recursion
    public static int countPartitions01(int n,int k){
        if(n==0||k==0||k>n) return 0;
        if(k==1||n==k) return 1;
        return (countPartitions01(n-1, k-1) + k*countPartitions01(n-1, k));
    }

    //Method 2 - Memoization
    public int countPartitions02(int n,int k,int[][] dp){
        if(n==0||k==0||k>n) return dp[n][k] = 0;
        if(k==1||n==k) return dp[n][k] = 1;

        if(dp[n][k]!=-1) return dp[n][k];

        return dp[n][k] = countPartitions02(n-1,k-1,dp) + k*countPartitions02(n-1,k,dp);
 
    }
    public int countPartitions02(int n,int k){
        int[][] dp = new int[n+1][k+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return countPartitions02(n,k,dp);
    }

    //Method 3 - Tabulation
    public int countPartitions(int n,int k){
        int[][] dp = new int[n+1][k+1];
        
        for(int i=0;i<=n;i++){
            for(int j=0;j<=k;j++){
                if(i==0||j==0||j>i){
                    dp[i][j] = 0;
                    continue;
                }
                if(j==1||i==j){
                    dp[i][j] = 1;
                    continue;
                }

                dp[i][j] = dp[i-1][j-1] + j*dp[i-1][j];
            }
        }
        return dp[n][k];
    }

    //DP_String

    //To check if a substring in a string is palindrome or not if indices are given.
    
    //Method 1 - Recursion
    public static boolean checkPalindromeSubstring01(String str,int i1,int i2){
        if(i1>=i2) return true;
        boolean res = false;
        if(str.charAt(i1)==str.charAt(i2)){
            res = res || checkPalindromeSubstring01(str, i1+1, i2-1);
        }
        return res;
    }

    //Method 2 - Gap Strategy
    public static boolean checkPalindromeSubstring(String str,int i1,int i2){
        boolean[][] dp = new boolean[str.length()][str.length()];
        int n = i2-i1+1;
        
        for(int gap=0;gap<n;gap++){
            for(int i=i1,j=i1+gap;j<=i2;i++,j++){
                if(gap==0) dp[i][j] = true;
                else{
                    if(str.charAt(i)==str.charAt(j)){
                        dp[i][j] = dp[i+1][j-1];
                    }
                }
            }
        }
        return dp[i1][i2];
    }

    //Leetcode 647
    public int countSubstrings(String s) {
        int n = s.length();
        int count = 0;
        boolean[][] dp = new boolean[n][n];

        for(int gap=0;gap<n;gap++){
            for(int i=0,j=gap;j<n;i++,j++){
                if(gap==0){
                    dp[i][j] = true;
                    count++;
                }
                else if(gap==1 && (s.charAt(i)==s.charAt(j))){
                    dp[i][j] = true;
                    count++;
                }
                else{
                    if(s.charAt(i)==s.charAt(j) && dp[i+1][j-1]){
                        dp[i][j] = true;
                        count++;
                    }
                }
            }
        }
        return count;
    }

    //Leetcode 5
    public String longestPalindrome(String s) {
        int n = s.length();
        
        boolean[][] dp = new boolean[n][n];
        int maxLen = 0,si=0,ei=0;

        for(int gap=0;gap<n;gap++){
            for(int i=0,j=gap;j<n;i++,j++){
                if(gap==0){
                    dp[i][j] = true;
                }
                else if(gap==1 && (s.charAt(i)==s.charAt(j))){
                    dp[i][j] = true;
                }else{
                    if((s.charAt(i)==s.charAt(j)) && dp[i+1][j-1]) dp[i][j] = true;
                }

                if(dp[i][j] && ((j-i+1) > maxLen)){
                    maxLen = j-i+1;
                    si=i;
                    ei=j;
                }
            }
        }
        return s.substring(si,ei+1);
    }

    //Leetcode 516
    public int longestPalindromeSubseq(String s) {
        int n = s.length();

        int[][] dp = new int[n][n];

        for(int gap=0;gap<n;gap++){
            for(int i=0,j=gap;j<n;i++,j++){
                if(gap==0){
                    dp[i][j] = 1;
                }
                else if(gap==1){
                    if(s.charAt(i)==s.charAt(j)) dp[i][j] = 2;
                    else dp[i][j] = 1;
                }
                else{
                    if(s.charAt(i)==s.charAt(j)) dp[i][j] = dp[i+1][j-1] + 2;
                    else dp[i][j] = Math.max(dp[i+1][j],dp[i][j-1]);
                }
            }
        }

        return dp[0][n-1];
    }

    //Return the longest palindromic subsequence
    public String longestPalindromSubseqString(String s){
        int n = s.length();
        if(n==1) return s;
        String[][] dp = new String[n][n];

        for(int gap=0;gap<n;gap++){
            for(int i=0,j=gap;j<n;i++,j++){
                if(gap==0){
                    dp[i][j] = s.charAt(i)+"";
                    continue;
                }
                if(s.charAt(i)==s.charAt(j)){
                    dp[i][j] = s.charAt(i) + dp[i+1][j-1] + s.charAt(j);
                }
                else{
                    dp[i][j] = (dp[i+1][j].length() > dp[i][j-1].length()) ? dp[i+1][j]:dp[i][j-1];
                }
            }
        }
        return dp[0][n-1];
    }

    //https://www.geeksforgeeks.org/problems/count-palindromic-subsequences/1
    long countPS(String s)
    {
        int n = s.length();
        
        long[][] dp = new long[n][n];
        int mod = (int)1e9 + 7;
        
        for(int gap=0;gap<n;gap++){
            for(int i=0,j=gap;j<n;i++,j++){
                if(gap==0){
                    dp[i][j] = 1;
                    continue;
                }
                
                long x = dp[i+1][j-1]%mod;
                long y = dp[i][j-1]%mod;
                long z = dp[i+1][j]%mod;
                
                if(s.charAt(i)==s.charAt(j)){
                   dp[i][j] = ((1%mod+x)%mod + (y%mod + z%mod - x%mod)%mod)%mod;
                }else{
                    dp[i][j] = (y + z - x)%mod;
                }
                dp[i][j] = dp[i][j]<0  ? (dp[i][j]+mod):dp[i][j]%mod;
            }
        }
        return dp[0][n-1];
    }

    //Leetcode 730
    //Method 1 - Memoization
    public long countPalindromicSubsequences(int i,int j,int mod,long[][] dp,String s){
        if(j-i<=1){
            if(i>j) return dp[i][j] = 0;
            return dp[i][j] = j-i+1;
        }

        if(dp[i][j]>0) return dp[i][j];

        if(s.charAt(i)==s.charAt(j)){
            int left = i+1;
            int right = j-1;
            while(left<=right && s.charAt(i)!=s.charAt(left)) left++;
            while(left<=right && s.charAt(i)!=s.charAt(right)) right--;

            if(left<right){
                dp[i][j] = ((countPalindromicSubsequences(i+1,j-1,mod,dp,s)*2)%mod - countPalindromicSubsequences(left+1,right-1,mod,dp,s)%mod)%mod;
            }
            else if(left==right){
                dp[i][j] = ((countPalindromicSubsequences(i+1,j-1,mod,dp,s)*2)%mod + 1)%mod;
            }
            else 
                dp[i][j] = ((countPalindromicSubsequences(i+1,j-1,mod,dp,s)*2)%mod + 2)%mod;
        }
        else{
            dp[i][j] = countPalindromicSubsequences(i,j-1,mod,dp,s) + countPalindromicSubsequences(i+1,j,mod,dp,s) - countPalindromicSubsequences(i+1,j-1,mod,dp,s);
        }

        return dp[i][j] = (dp[i][j]<0) ? dp[i][j]+mod:dp[i][j]%mod;
    }
    public int countPalindromicSubsequences01(String s) {
        int n = s.length();
        if(n==1) return 1;
        long[][] dp = new long[n][n];
        int mod = (int)1e9 + 7;
        return (int)countPalindromicSubsequences(0,n-1,mod,dp,s);
    }

    //Method 2 - Gap Strategy
    public int countPalindromicSubsequences(String s) {
        int n = s.length();

        long[][] dp = new long[n][n];
        int mod = (int)1e9 + 7;

        for(int gap=0;gap<n;gap++){
            for(int i=0,j=gap;j<n;i++,j++){
                if(gap==0 || gap==1){
                    dp[i][j] = j-i+1;
                }else{
                    if(s.charAt(i)==s.charAt(j)){
                        int left = i+1;
                        int right = j-1;
                        while(left<=right && s.charAt(i)!=s.charAt(left)) left++;
                        while(left<=right && s.charAt(i)!=s.charAt(right)) right--;

                        if(left<right){
                            dp[i][j] = (dp[i+1][j-1]*2)%mod - dp[left+1][right-1];
                        }else if(left==right){
                            dp[i][j] = ((dp[i+1][j-1]*2)%mod +1)%mod; 
                        }
                        else{
                            dp[i][j] = ((dp[i+1][j-1]*2)%mod + 2)%mod;
                        }
                    }
                    else{
                        dp[i][j] = (dp[i][j-1]%mod + dp[i+1][j]%mod - dp[i+1][j-1]%mod)%mod;
                    }
                    dp[i][j] = (dp[i][j]<0) ? dp[i][j]+mod:dp[i][j]%mod;

                }

            }
        }

        return (int)dp[0][n-1];
    }

    //Leetcode 115
    //Method 1 - Memoization
    public int numDistinct01(int n,int m,String s,String t,int[][] dp){
        if(n<m) return dp[n][m] = 0;
        if(m==0) return dp[n][m] = 1;

        if(dp[n][m]!=-1) return dp[n][m];

        dp[n][m] = 0;
        if(s.charAt(n-1)==t.charAt(m-1))
            dp[n][m]  += numDistinct01(n-1,m-1,s,t,dp);
        
        dp[n][m] += numDistinct01(n-1,m,s,t,dp);

        return dp[n][m];

    }
    public int numDistinct01(String s, String t) {
        int n = s.length();
        int m = t.length();

        int[][] dp = new int[n+1][m+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return numDistinct01(n,m,s,t,dp);
    }

    //Method 2 - Tabulation
    public int numDistinct02(String s, String t) {
        int n = s.length();
        int m = t.length();

        int[][] dp = new int[n+1][m+1];

        for(int i=0;i<=n;i++){
            for(int j=0;j<=m;j++){
                if(j==0 || j>i){
                    dp[i][j] = (j==0)? 1:0;
                    continue;
                }

                dp[i][j] = dp[i-1][j];

                if(s.charAt(i-1)==t.charAt(j-1)) dp[i][j] += dp[i-1][j-1];
            }
        }

        return dp[n][m];
    }

    //Leetcode 1143
    //Method 1 - Memoization
    public int longestCommonSubsequence01(int n,int m,String text1,String text2,int[][] dp){
        if(n==0||m==0) return dp[n][m] = 0;

        if(dp[n][m] != -1) return dp[n][m];

        if(text1.charAt(n-1)==text2.charAt(m-1))
            return dp[n][m] = 1 + longestCommonSubsequence01(n-1,m-1,text1,text2,dp);
        
        return dp[n][m] = Math.max(longestCommonSubsequence01(n,m-1,text1,text2,dp),longestCommonSubsequence01(n-1,m,text1,text2,dp));
    }
    public int longestCommonSubsequence01(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();

        int[][] dp = new int[n+1][m+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return longestCommonSubsequence01(n,m,text1,text2,dp);
    }

    //Method 2 - Tabulation
    public int longestCommonSubsequence02(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();

        int[][] dp = new int[n+1][m+1];

        for(int i=0;i<=n;i++){
            for(int j=0;j<=m;j++){
                if(i==0||j==0){
                    dp[i][j] = 0;
                    continue;
                }

                if(text1.charAt(i-1)==text2.charAt(j-1))
                    dp[i][j] = 1 + dp[i-1][j-1];
                
                else dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
            }
        }

        return dp[n][m];
    }

    //Method 3 - Final Optimised Solution
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1.equals(text2)) return text1.length();
        if (text2.length() > text1.length()) return longestCommonSubsequence(text2, text1);

        int n = text1.length();
        int m = text2.length();
        int[] curr = new int[m+1];
        int[] prev = new int[m+1];
        int[] temp;
        for (int i=0;i<n;i++) {
            for (int j=0;j<m;j++) {
                if (text1.charAt(i)==text2.charAt(j)) curr[j+1] = prev[j] + 1; //dp[i][j] = dp[i-1][j-1] + 1;
                else curr[j+1] = Math.max(curr[j], prev[j+1]);
                                       //(dp(i,j-1),dp(i-1,j));
            }
            temp = curr;
            curr = prev;
            prev = temp;
        }
        return prev[m];
    }

    //Leetcode 1035
    //Method 1
    public int maxUncrossedLines01(int n,int m,int[] nums1,int[] nums2,int[][] dp){
        if(n==0 || m==0) return dp[n][m] = 0;

        if(dp[n][m] != -1) return dp[n][m];

        if(nums1[n-1]==nums2[m-1])
            return dp[n][m] = 1 + maxUncrossedLines01(n-1,m-1,nums1,nums2,dp);
        
        return dp[n][m] = Math.max(maxUncrossedLines01(n-1,m,nums1,nums2,dp),maxUncrossedLines01(n,m-1,nums1,nums2,dp));
    }
    public int maxUncrossedLines01(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;

        int[][] dp = new int[n+1][m+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return maxUncrossedLines01(n,m,nums1,nums2,dp);
    }

    //Method 2 - Final Optimised Solution
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        if(nums2.length > nums1.length) return maxUncrossedLines(nums2,nums1);

        int n1 = nums1.length;
        int n2 = nums2.length;

        int[] curr = new int[n2+1];
        int[] prev = new int[n2+1];
        int[] temp;
        for(int i=0;i<n1;i++){
            for(int j=0;j<n2;j++){
                if(nums1[i]==nums2[j]){
                    curr[j+1] = prev[j] + 1;
                }else{
                    curr[j+1] = Math.max(curr[j],prev[j+1]);
                }
            }
            temp = curr;
            curr = prev;
            prev = temp;
        }

        return prev[n2];
    }


    //Leetcode 1458
    //Method 1 - Memoization
    public int maxDotProduct01(int n,int m,int[] nums1,int[] nums2,int[][] dp){
        if(n==0||m==0) return dp[n][m] = -(int)1e8;

        if(dp[n][m]!= -(int)1e9) return dp[n][m];

        int val = nums1[n-1]*nums2[m-1];
        int max1 = Math.max(val,maxDotProduct01(n-1,m-1,nums1,nums2,dp)+val);
        int max2 = Math.max(maxDotProduct01(n-1,m,nums1,nums2,dp),maxDotProduct01(n,m-1,nums1,nums2,dp));

        return dp[n][m] = Math.max(max1,max2);
    }
    public int maxDotProduct01(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;

        int[][] dp = new int[n+1][m+1];
        for(int[] d:dp) Arrays.fill(d,-(int)1e9);
        return maxDotProduct01(n,m,nums1,nums2,dp);
    }

    //Method 2 - Tabulation
    public int maxDotProduct02(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;

        int[][] dp = new int[n+1][m+1];
        int val,max1,max2;
        for(int i=0;i<=n;i++){
            for(int j=0;j<=m;j++){
                if(i==0||j==0){
                    dp[i][j] = -(int)1e8;
                    continue;
                }

                val = nums1[i-1]*nums2[j-1];
                max1 = Math.max(val,dp[i-1][j-1]+val);
                max2 = Math.max(dp[i-1][j],dp[i][j-1]);

                dp[i][j] = Math.max(max1,max2);
            }
        }

        return dp[n][m];
    }

    //Method 3 - Final Optimised Solution
    public int maxDotProduct(int[] nums1, int[] nums2) {
        if(nums2.length > nums1.length) return maxDotProduct(nums2,nums1);

        int n1 = nums1.length;
        int n2 = nums2.length;
        int[] curr = new int[n2+1];
        Arrays.fill(curr,-(int)1e9);
        int[] prev = new int[n2+1];
        Arrays.fill(prev,-(int)1e9);
        int[] temp;
        int max1,max2,val;
        for(int i=0;i<n1;i++){
            for(int j=0;j<n2;j++){
                val = nums1[i]*nums2[j];
                max1 = Math.max(val,prev[j]+val);
                max2 = Math.max(curr[j],prev[j+1]);
                curr[j+1] = Math.max(max1,max2);
            }
            temp = curr;
            curr = prev;
            prev = temp;
        }

        return prev[n2];
    }

    //Leetcode 72
    //Method 1 - Memoization
    public int minDistance01(int n1,int n2,String word1,String word2,int[][] dp){
        if(n1==0||n2==0){
            return (n2==0) ? n1 : n2;
        }

        if(dp[n1][n2]!=-1) return dp[n1][n2];

        if(word1.charAt(n1-1)==word2.charAt(n2-1))
            return dp[n1][n2] = minDistance01(n1-1,n2-1,word1,word2,dp);
        
        int insert = minDistance01(n1,n2-1,word1,word2,dp);
        int delete = minDistance01(n1-1,n2,word1,word2,dp);
        int replace = minDistance01(n1-1,n2-1,word1,word2,dp);

        return dp[n1][n2] = 1 + Math.min(insert,Math.min(delete,replace));
    }
    public int minDistance01(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();

        int[][] dp = new int[n1+1][n2+1];
        for(int[] d:dp) Arrays.fill(d,-1);

        return minDistance01(n1,n2,word1,word2,dp);
    }

    //Method 2 - Tabulation
    public int minDistance(String word1, String word2) {
        int l1 = word1.length();
        int l2 = word2.length();

        int[][] dp = new int[l1+1][l2+1];
        for(int[] d:dp)
            Arrays.fill(d,-1);

        for(int i=0;i<=l1;i++){
            for(int j=0;j<=l2;j++){
                if(i==0||j==0){
                    dp[i][j] = (i==0)? j:i;
                    continue;
                }

                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                    continue;
                }

                int insert = dp[i][j-1];
                int delete = dp[i-1][j];
                int replace = dp[i-1][j-1];

                dp[i][j] = 1 + Math.min(insert,Math.min(delete,replace));
            }
        }

        return dp[l1][l2];
    }

    //Leetcode 72 - Follow up Question - Return min cost to convert word1 to word2 if different operations have diff costs which are given in a cost array.
    // cost[0] - insert, cost[1] - delete, cost[2] - replace
    public int minDistanceWithCost(int n1,int n2,String word1,String word2,int[] cost,int[][] dp){
        if(n1==0||n2==0){
            return (n2==0) ? n1 : n2;
        }

        if(dp[n1][n2]!=-1) return dp[n1][n2];

        if(word1.charAt(n1-1)==word2.charAt(n2-1))
            return dp[n1][n2] = minDistanceWithCost(n1-1,n2-1,word1,word2,cost,dp);
        
        int insert = minDistanceWithCost(n1,n2-1,word1,word2,cost,dp) + cost[0];
        int delete = minDistanceWithCost(n1-1,n2,word1,word2,cost,dp) + cost[1];
        int replace = minDistanceWithCost(n1-1,n2-1,word1,word2,cost,dp) + cost[2];

        return dp[n1][n2] = Math.min(insert,Math.min(delete,replace));
    }
    public int minDistanceWithCost(String word1, String word2, int[] cost) {
        int n1 = word1.length();
        int n2 = word2.length();

        int[][] dp = new int[n1+1][n2+1];
        for(int[] d:dp) Arrays.fill(d,-1);

        return minDistanceWithCost(n1,n2,word1,word2,cost,dp);
    }

    //Leetcode 44 - Wildcard Matching
    //Method 1 - Memoization
    public String removeExtraStars(String p){
        if(p.length()==0) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(p.charAt(0));

        int i = 1;
        while(i<p.length()){
            while(i<p.length() && p.charAt(i)=='*' && p.charAt(i-1)=='*')
                i++;

            if(i<p.length()) sb.append(p.charAt(i));
            i++;
        }

        return sb.toString();
    }
    public int isMatch(int n,int m,String s,String p,int[][] dp){
        if(n==0||m==0){
            if(n==0 && m==0) return dp[n][m] = 1;
            if(m==1 && p.charAt(m-1)=='*') return dp[n][m] = 1;
            return dp[n][m] = 0;
        }

        if(dp[n][m]!=-1) return dp[n][m];

        char ch1 = s.charAt(n-1);
        char ch2 = p.charAt(m-1);
        int val = 0;

        if(ch1 == ch2 || ch2 == '?'){
            val = isMatch(n-1,m-1,s,p,dp);
        }
        else if(p.charAt(m-1)=='*'){
            boolean res = false;
            res = res || (isMatch(n-1,m,s,p,dp)==1);
            res = res || (isMatch(n,m-1,s,p,dp)==1);
            
            if(res) val = 1;
        }
        return dp[n][m] = val;
    }
    public boolean isMatch01(String s, String p) {
        p = removeExtraStars(p);
        int n = s.length();
        int m = p.length();

        int[][] dp = new int[n+1][m+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return (isMatch(n,m,s,p,dp)==1);
    }

    //Method 2 - Tabulation
    public int isMatch(int N,int M,int[][] dp,String s,String p){
        for(int n=0;n<=N;n++){
            for(int m=0;m<=M;m++){

                if(n==0||m==0){
                    if(n==0 && m==0)
                        dp[n][m] = 1;
                    else if(m==1 && p.charAt(m-1)=='*')
                        dp[n][m] = 1;
                    else dp[n][m] = 0;
                    continue;
                }

                char ch1 = s.charAt(n-1);
                char ch2 = p.charAt(m-1);
                int val = 0;

                if(ch1==ch2 || ch2 == '?')
                    val = dp[n-1][m-1];
        
                else if(p.charAt(m-1)=='*'){
                    boolean res = false;
                    res = res || (dp[n-1][m]==1);
                    res = res || (dp[n][m-1]==1);
                    if(res)
                        val = 1;
                }
                dp[n][m] = val;
            }
        }
        
        return dp[N][M];
    }
    public boolean isMatch(String s, String p) {
        p = removeExtraStars(p);
        int n = s.length();
        int m = p.length();

        int[][] dp = new int[n+1][m+1];
        
        return (isMatch(n,m,dp,s,p)==1);
    }

    //Leetcode 300
    //Method 1 - 37ms
    public int lengthOfLIS01(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int maxLen = 1;
        dp[0] = 1;
        for(int i=1;i<n;i++){
            dp[i] = 1;
            for(int j=i-1;j>=0;j--){
                if(nums[i]>nums[j]) 
                    dp[i] = Math.max(dp[i],dp[j]+1);
                else if(dp[j]==1) break;
            }
            maxLen = Math.max(maxLen,dp[i]);
        }
        return maxLen;
    }

    //Method 2 - 2ms
    public int lengthOfLIS02(int[] nums) {
        int n = nums.length;
         // dp[i] -> last index in lis [0,]
        int[] dp = new int[n];
        int length = 1;
        dp[0] = nums[0];
        for(int i=1;i<n;i++) {
            if(nums[i] > dp[length - 1]) {
                dp[length++] = nums[i];
            } 
            else {
                int j = length-1;
                while (j >=0 && dp[j] >= nums[i]) {
                    j--;
                }
                dp[j+1] = nums[i];
            }
        }
        return length;
    }

    //Method 3 - Use binary search in method 2
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        
        int[] dp = new int[n];
        int len = 1;
        dp[0] = nums[0];

        for(int i=1;i<n;i++){
            if(dp[len-1] < nums[i]){
                dp[len++] = nums[i];
            }else{
                int si=0,ei=len-1;
                int mid;
                while(si<ei){
                    mid = (si+ei)/2;
                    if(dp[mid]>=nums[i]) ei = mid;
                    else si = mid+1;
                }
                dp[si] = nums[i];
            }
        }
        return len;
    }

    //https://www.geeksforgeeks.org/problems/minimum-number-of-deletions-to-make-a-sorted-sequence3248/1
    public int minDeletions(int nums[], int n) 
	{ 
	    if(n==1) return 0;
	    int[] dp = new int[n];
	    dp[0] = nums[0];
	    int length = 1;
	    
	    for(int i=1;i<n;i++){
	        if(dp[length-1]<nums[i]){
	            dp[length++] = nums[i];
	        }else{
	            int si=0,ei=length-1;
	            int mid;
	            while(si<=ei){
	                mid = (ei-si)/2 + si;
	                if(dp[mid]==nums[i]){
	                    si = mid;
	                    break;
	                }
	                else if(dp[mid]<nums[i]) si = mid+1;
	                else ei = mid-1;
	            }
	            dp[si] = nums[i];
	        }
	    }
	    return n - length;
	} 

    //https://www.geeksforgeeks.org/problems/maximum-sum-increasing-qsubsequence4749/1
    public int maxSumIS(int arr[], int n)  
	{  
	    if(n==1) return arr[0];
	    int[] dp = new int[n];
	    int maxSum = 0;
	    
	    for(int i=0;i<n;i++){
	        dp[i] = arr[i];
	        for(int j=i-1;j>=0;j--){
	            if(arr[i]>arr[j]) dp[i] = Math.max(dp[i],dp[j]+arr[i]);
	        }
	        maxSum = Math.max(maxSum,dp[i]);
	    }
	    return maxSum;
	}  

    //Maximum sum of strictly increasing subsequence of maximum length

    public int maxSumLIS(int[] nums,int n){
        if(n==1) return nums[0];
        int[] lenDP = new int[n];
        int[] sumDP = new int[n];
        int maxSum = 0;
        int maxLen = 0;
        
        lenDP[0] = 1;
        sumDP[0] = nums[0];
        maxSum = nums[0];
        maxLen = 1;

        for(int i=1;i<n;i++){
            sumDP[i] = nums[i];
            lenDP[i] = 1;
            for(int j=i-1;j>=0;j--){
                if(nums[i]>nums[j]){
                    if(lenDP[j]+1==lenDP[i]){
                        sumDP[i] = Math.max(sumDP[i],sumDP[j]+nums[i]);
                    }else if(lenDP[j]+1>lenDP[i]){
                        lenDP[i] = lenDP[j]+1;
                        sumDP[i] = sumDP[j]+nums[i];
                    }
                }
            }
            if(maxLen==lenDP[i]){
                maxSum = Math.max(maxSum,sumDP[i]);
            }else if(maxLen<lenDP[i]){
                maxLen = lenDP[i];
                maxSum = sumDP[i];
            }
        }
        return maxSum;
    }

    //https://www.geeksforgeeks.org/problems/longest-bitonic-subsequence0824/1
    //Longest Bitonic Subsequence Uphill /\
    public static int LIS(int[] nums,int[] dp,int n){
        int len = 1;
        for(int i=0;i<n;i++){
            dp[i] = 1;
            for(int j=i-1;j>=0;j--){
                if(nums[i]>nums[j]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }else if(dp[j]==1) break;
            }
            len = Math.max(len,dp[i]);
        }
        return len;
    }
    //LIS from right to left
    public static int LDS(int[] nums,int[] dp,int n){
        int len = 1;
        for(int i=n-1;i>=0;i--){
            dp[i] = 1;
            for(int j=i+1;j<n;j++){
                if(nums[i]>nums[j]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }else if(dp[j]==1) break;
            }
            len = Math.max(len,dp[i]);
        }
        return len;
    }
    public int LongestBitonicSequence(int n,int[] nums)
    {
        if(n==1) return 0;
        int[] lisDP = new int[n];
        int[] ldsDP = new int[n];
        int lis = LIS(nums,lisDP,n);
        int lds = LDS(nums,ldsDP,n);
        if(lis==1||lds==1) return 0;
        int maxLen = 0;
        for(int i=0;i<n;i++){
            if(lisDP[i]==1 || ldsDP[i]==1) continue;
            maxLen = Math.max(maxLen,lisDP[i]+ldsDP[i]-1);
        }
        return maxLen;
    }

    //Longest Bitonic Subsequence Downhill \/
    //LDS from left to right
    public void LDS_LR(int n,int[] nums,int[] dp){
        for(int i=0;i<n;i++){
            dp[i] = 1;
            for(int j=i-1;j>=0;j--){
                if(nums[i]<nums[j]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }else if(dp[j]==1) break;
            }
        }
    }
    //LDS from right to left
    public void LDS_RL(int n,int[] nums,int[] dp){
        for(int i=n-1;i>=0;i--){
            dp[i] = 1;
            for(int j=i+1;j<n;j++){
                if(nums[i]<nums[j]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }else if(dp[j]==1) break;
            }
        }
    }
    public int longestBitonicSubsequence(int n,int[] nums){
        if(n==1) return 0;
        int[] lisDP = new int[n];
        int[] ldsDP = new int[n];
        LDS_LR(n,nums,ldsDP);
        LDS_RL(n,nums,lisDP);
        int maxLen = 0;
        for(int i=0;i<n;i++){
            if(lisDP[i]==1 || ldsDP[i]==1) continue;
            maxLen = Math.max(maxLen,lisDP[i]+ldsDP[i]-1);
        }
        return maxLen;
    }

    //https://www.geeksforgeeks.org/problems/maximum-sum-bitonic-subsequence1857/1
    public static void MSIS(int n,int[] arr,int[] dp){
        for(int i=0;i<n;i++){
            dp[i] = arr[i];
            for(int j=i-1;j>=0;j--){
                if(arr[i]>arr[j]){
                    dp[i] = Math.max(dp[i],dp[j]+arr[i]);
                }else if(dp[j]==arr[j]) break;
            }
        }
    }
    public static void MSDS(int n,int[] arr,int[] dp){
        for(int i=n-1;i>=0;i--){
            dp[i] = arr[i];
            for(int j=i+1;j<n;j++){
                if(arr[i]>arr[j]){
                    dp[i] = Math.max(dp[i],dp[j]+arr[i]);
                }else if(dp[j]==arr[i]) break;
            }
        }
    }
    public static int maxSumBS(int arr[], int n)
    {
        if(n==1) return arr[0];
        int[] MSIS_DP = new int[n];
        int[] MSDS_DP = new int[n];
        MSIS(n,arr,MSIS_DP);
        MSDS(n,arr,MSDS_DP);
        int sum = 0;
        for(int i=0;i<n;i++){
            sum = Math.max(sum,MSIS_DP[i]+MSDS_DP[i]-arr[i]);
        }
        return sum;
    }

    //Leetcode 673
    //Method 1 - O(n^2)
    public int findNumberOfLIS01(int[] nums) {
        int n = nums.length;
        if(n==1) return 1;
        int[] len = new int[n];
        int[] count = new int[n];
        int maxCount = 0;
        int maxLen = 0;

        for(int i=0;i<n;i++){
            len[i] = 1;
            count[i] = 1;
            for(int j=i-1;j>=0;j--){
                if(nums[i]>nums[j]){
                    if(len[i]<len[j]+1){
                        len[i] = len[j]+1;
                        count[i] = count[j];
                    }else if(len[i]==len[j]+1){
                        count[i] += count[j];
                    }
                }else if(len[j]==1) break;
            }
            if(len[i]>maxLen){
                maxLen = len[i];
                maxCount = count[i];
            }else if(len[i]==maxLen){
                maxCount += count[i];
            }
        }

        return maxCount;  
    }

    //Method 2 - O(nlog(n))
    private int bSearchLength(List<int[]>[] dp,int right,int ele){
        int left = 0;
        while(left<right){
            int mid = (right-left)/2 + left;
            if(ele > dp[mid].get(dp[mid].size()-1)[0]) left = mid+1;
            else right = mid;
        }
        return left;
    }
    private int bSearchIdx(List<int[]> t,int ele){
        int left = 0,right = t.size()-1;
        while(left<right){
            int mid = (right-left)/2 + left;
            if(ele<=t.get(mid)[0]) left = mid+1;
            else right = mid;
        }
        return left;
    }
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        @SuppressWarnings("unchecked")
        List<int[]>[] dp = new ArrayList[n];
        for(int i=0;i<n;i++) dp[i] = new ArrayList<>();

        int len = 0;

        for(int ele:nums){
            int idx = bSearchLength(dp,len,ele);
            int count = 1;

            if(idx>0){
                List<int[]> t = dp[idx-1];
                int p = bSearchIdx(t,ele);
                count = t.get(t.size()-1)[1] - (p==0 ? 0 : t.get(p-1)[1]);
            }
            if(dp[idx].size()==0){
                dp[idx].add(new int[]{ele,count});
                len++;
            }else{
                List<int[]> t = dp[idx];
                int[] last = t.get(t.size()-1);
                if(last[0]==ele){
                    last[1] += count;
                }else{
                    t.add(new int[]{ele,last[1]+count});
                }
            }
        }

        return dp[len-1].get(dp[len-1].size()-1)[1];
    }

    //Follow Up Question Leetcode 673 - Print all longest increasing subsequences
    public static void allLIS(int idx,int len,int[] arr,ArrayList<ArrayList<Integer>> mapping,String ans){
        if(len==1){
            StringBuilder sb = new StringBuilder(ans+arr[idx]);
            System.out.println(sb.reverse());
            return;
        }

        for(Integer i:mapping.get(len-1)){
            if(i<idx && arr[i]<arr[idx]){
                allLIS(i,len-1,arr,mapping,ans+arr[idx]+" ");
            }
        }
    }
    public static void allLIS(int[] arr){
        int n = arr.length;
        int[] dp = new int[n];
        int len = LIS(arr,dp,n);

        ArrayList<ArrayList<Integer>> mapping = new ArrayList<>();
        for(int i=0;i<=len;i++){
            mapping.add(new ArrayList<>());
        }

        for(int i=0;i<n;i++){
            mapping.get(dp[i]).add(i);
        }

        for(Integer i:mapping.get(len)){
            allLIS(i, len, arr, mapping, "");
        }
    }

    //https://www.geeksforgeeks.org/dynamic-programming-building-bridges/
    //Method 1 - I'm not sure whether this will work
    public static class bridgePair{
        int x = 0;
        int idx = 0;
        bridgePair(int x,int idx){
            this.x = x;
            this.idx = idx;
        }
    }
    public static int buildingBridges(int i,int j,bridgePair[] arr1,bridgePair[] arr2,int[][] dp){
        if(i==0||j==0){
            return dp[i][j] = 0;
        }

        if(dp[i][j]!=-1) return dp[i][j];

        if(arr1[i-1].idx==arr2[j-1].idx){
            return dp[i][j] = 1 + buildingBridges(i-1,j-1,arr1,arr2,dp);
        }

        return dp[i][j] = Math.max(buildingBridges(i-1,j,arr1,arr2,dp),buildingBridges(i,j-1,arr1,arr2,dp));
    }
    public static int buildingBridges0(int[] nums1,int[] nums2){
        int n = nums1.length;
        bridgePair[] arr1 = new bridgePair[n];
        for(int i=0;i<n;i++) arr1[i] = new bridgePair(nums1[i], i);
        Arrays.sort(arr1, (a,b) -> {
            return a.x - b.x;
        });

        bridgePair[] arr2 = new bridgePair[n];
        for(int i=0;i<n;i++) arr2[i] = new bridgePair(nums2[i], i);
        Arrays.sort(arr2, (a,b) -> {
            return a.x - b.x;
        });

        int[][] dp = new int[n+1][n+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return buildingBridges(n,n,arr1,arr2,dp);
    }

    //Method 2 - USING LIS logic - Method 1
    static class NorthSouthBridgePair{
        int north = 0;
        int south = 0;
        NorthSouthBridgePair(int north,int south){
            this.north = north;
            this.south = south;
        }
    }public static int buildingBridges02(int[] nums1,int[] nums2){
        int n = nums1.length;
        NorthSouthBridgePair[] arr = new NorthSouthBridgePair[n];

        for(int i=0;i<n;i++){
            arr[i] = new NorthSouthBridgePair(nums1[i], nums2[i]);
        }
        Arrays.sort(arr, (a,b) -> {
            return a.north - b.north;
        });

        return buildingBridges02(n,arr);
    }
    public static int buildingBridges02(int n,NorthSouthBridgePair[] arr){
        int[] dp = new int[n];
        int length = 1;
        dp[0] = 1;
        for(int i=1;i<n;i++){
            dp[i] = 1;

            for(int j=i-1;j>=0;j--){
                if(arr[i].south>arr[j].south){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
                else if(dp[j]==1) break;
            }
            length = Math.max(length,dp[i]);
        }
        return length;
    }

    //Using LIS logic - Method 2 (Leetcode 300) using binary search
    private static int buildingBridges03(int n,NorthSouthBridgePair[] arr){
        int[] dp = new int[n];
        dp[0] = arr[0].south;
        int length = 1;

        for(int i=1;i<n;i++){
            if(dp[length-1]<arr[i].south){
                dp[length++] = arr[i].south;
            }else{
                /*
                    int idx = Arrays.binarySearch(dp, 0, length, arr[i].south);
                    if(idx<0) idx = -idx - 1;/
                    dp[idx] = arr[i].south;
                 */
                int ele = arr[i].south;
                int si=0,ei=length-1;
                int mid;
                while(si<=ei){
                    mid = (ei-si)/2 + si;
                    if(dp[mid]==ele){
                        si = mid;
                        break;
                    }
                    else if(dp[mid]<ele) si = mid+1;
                    else ei = mid-1; 
                }
                dp[si] = ele;
                
            }
        }
        return length;
    }
    public static int buildingBridges(int[] nums1,int[] nums2){
        int n = nums1.length;
        NorthSouthBridgePair[] arr = new NorthSouthBridgePair[n];

        for(int i=0;i<n;i++){
            arr[i] = new NorthSouthBridgePair(nums1[i], nums2[i]);
        }
        Arrays.sort(arr, (a,b) -> {
            return a.north - b.north;
        });

        return buildingBridges03(n,arr);
    }

    //Coin Change - No. of coins are infinite - Permutation
    // Method 1 - Memoization
    public static int coinChangePermutationInfi01(int[] arr,int tar,String ans,int[] dp){
        if(tar==0){
            return dp[tar] = 1;
        }

        if(dp[tar]!=-1) return dp[tar];

        int count = 0;
        for(int coin:arr){
            if(tar-coin>=0){
                count += coinChangePermutationInfi01(arr,tar-coin,ans+coin+" ",dp);
            }
        }
        return dp[tar] = count;
    }
    public static int coinChangePermutationInfi01(int[] arr,int tar){
        int[] dp = new int[tar+1];
        Arrays.fill(dp,-1);
        return coinChangePermutationInfi01(arr,tar,"",dp);
    }
    
    //Method 2 - Tabulation
    public static int coinChangePermutationInfi02(int[] arr,int tar){
        int[] dp = new int[tar+1];
        dp[0] = 1;

        for(int i=1;i<=tar;i++){
            for(int coin:arr){
                if(i-coin>=0)
                    dp[i] += dp[i-coin];
            }
        }
        return dp[tar];
    }

    //Coin Change - No. of coins are infinite - Combination
    //Method 1 - Memoization
    public static int coinChangeCombinationInfi01(int idx,int[] arr,int[][] dp,int tar,String ans){
        if(tar==0){
            System.out.println(ans);
            return dp[idx][tar] = 1;
        }

        if(dp[idx][tar]!=-1) return dp[idx][tar];

        int count = 0;
        for(int i=idx;i<arr.length;i++){
            if(tar-arr[i]>=0){
                count += coinChangeCombinationInfi01(i,arr,dp,tar,ans+arr[i]+" ");
            }
        }
        return dp[idx][tar] = count;
    }
    public static int coinChangeCombinationInfi01(int[] arr,int tar){
        int[][] dp = new int[arr.length][tar+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return coinChangeCombinationInfi01(0,arr,dp,tar,"");
    }

    //Method 2 - Tabulation
    public static int coinChangeCombinationInfi02(int[] arr,int tar){
        int n = arr.length;
        int[][] dp = new int[n][tar+1];

        for(int i=0;i<n;i++){
            for(int j=0;j<=tar;j++){
                if(j==0){
                    dp[i][j] = 1;
                    continue;
                }

                for(int k=i;k<n;k++){
                    if(j-arr[k]>=0){
                        dp[i][j] += dp[i][j-arr[k]];
                    }
                }
            }
        }

        return dp[n-1][tar];
    }

    //Method 3 - Tabulation - 1D DP
    //Actually not a recursion problem but derived from permutation infinte coin change
    public static int coinChangeCombinationInfi(int[] arr,int tar){
        int[] dp = new int[tar+1];
        dp[0] = 1;

        for(int ele:arr){
            for(int i=ele;i<=tar;i++)
                dp[i] += dp[i-ele];
        }

        return dp[tar];
    }

    //Leetcode 518 - Same as coin change combination infinite
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount+1];
        dp[0] = 1;

        for(int coin:coins){
            for(int i=coin;i<=amount;i++){
                dp[i] += dp[i-coin];
            }
        }
        return dp[amount];
    }

    //Leetcode 322
    //Method 1 - Memoization
    public int coinChange01(int[] dp,int[] coins,int amount){
        if(amount==0){
            return dp[amount] = 0;
        }

        if(dp[amount]!=(int)1e9) return dp[amount];

        int minCoins = (int)1e8;
        for(int coin:coins){
            if(amount-coin>=0)
                minCoins = Math.min(minCoins,coinChange01(dp,coins,amount-coin)+1);
        }

        return dp[amount] = minCoins;
    }
    public int coinChange01(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp,(int)1e9);
        int ans = coinChange01(dp,coins,amount);
        return (ans!=(int)1e8) ? ans:-1;
    }

    //Method 2 - Tabulation
    public int coinChange02(int[] dp,int[] coins,int amount){
        for(int tar=0;tar<=amount;tar++){
            if(tar==0){
                dp[tar]=0;
                continue;
            }

            for(int ele:coins){
                if(tar-ele>=0)
                    dp[tar] = Math.min(dp[tar],dp[tar-ele]+1);
            }
        }
        return (dp[amount]!=(int)1e9) ? dp[amount] : -1;
    }
    public int coinChange02(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp,(int)1e9);
        return coinChange02(dp,coins,amount);
    }

    //Method 3 - Final Optimised Solution
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp,(int)1e8);
        dp[0] = 0;

        for(int coin:coins){
            for(int i=coin;i<=amount;i++){
                dp[i] = Math.min(dp[i],1+dp[i-coin]);
            }
        }
        return (dp[amount]==(int)1e8) ? -1 : dp[amount];
    }

    //Leetcode 377
    //Same as permutation infinite
    public int combinationSum4(int[] dp,int[] nums,int target){
        if(target==0){
            return dp[target] = 1;
        }

        if(dp[target]!=-1) return dp[target];

        int count = 0;
        for(int ele:nums){
            if(target-ele>=0)
                count += combinationSum4(dp,nums,target-ele);
        }

        return dp[target] = count;
    } 
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target+1];
        Arrays.fill(dp,-1);
        return combinationSum4(dp,nums,target);
    }

    //https://www.geeksforgeeks.org/find-number-of-solutions-of-a-linear-equation-of-n-variables/
    public int countSol(int[] coeff,int n,int rhs){
        int[] dp = new int[rhs+1];
        dp[0] = 1;

        for(int ele:coeff){
            for(int i=ele;i<=rhs;i++){
                dp[i] += dp[i-ele];
            }
        }
        return dp[rhs];
    }

    //https://www.geeksforgeeks.org/problems/subset-sum-problem-1611555638/1
    //Method 1 - Memoization
    public static int isSubsetSum01(int i,int sum,int[] arr,int[][] dp){
        if(i==arr.length||sum==0){
            return dp[i][sum] = (sum==0) ? 1:0;
        }
        
        if(dp[i][sum]!=-1) return dp[i][sum];
        
        boolean res = false;
        if(sum-arr[i]>=0)
        res = res || (isSubsetSum01(i+1,sum-arr[i],arr,dp)==1);
        
        res = res || (isSubsetSum01(i+1,sum,arr,dp)==1);
        
        return dp[i][sum] = (res) ? 1:0;
        
    }
    public static Boolean isSubsetSum01(int n, int arr[], int sum){
        int[][] dp = new int[n+1][sum+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return (isSubsetSum01(0,sum,arr,dp)==1);
    }

    //Method 2 - Tabulation
    static Boolean isSubsetSum02(int n, int arr[], int sum){
        boolean[][] dp = new boolean[n+1][sum+1];
        
        for(int i=n;i>=0;i--){
            for(int j=0;j<=sum;j++){
                if(i==n||j==0){
                    dp[i][j] = (j==0);
                    continue;
                }
                
                if(j-arr[i]>=0)
                    dp[i][j] = dp[i][j] || dp[i+1][j-arr[i]];
                
                dp[i][j] = dp[i][j] || dp[i+1][j];
            }
        }
        
        return dp[0][sum];
    }

    //Method 3 - Final Optimised Solution
    static Boolean isSubsetSum(int N, int arr[], int sum){
        boolean[] dp = new boolean[sum+1];
        dp[0] = true;
        for(int ele:arr){
            for(int i=sum;i>=ele;i--){
                if(dp[i-ele]){
                    if(i==sum) return true;
                    dp[i] = true;
                }
            }
        }
        return dp[sum];
    }

    //Follow up of above question - Find no. of subsets of the array which sum to given sum and also print the subset
    public static int subsetSumProblem(int i,int[] arr,int sum,int[][] dp,String path){
        if(sum==0||i==arr.length){
            if(sum==0){
                System.out.println(path);
                return dp[i][sum] = 1;
            }
            return dp[i][sum] = 0;
        }

        if(dp[i][sum]!=-1) return dp[i][sum];

        int count = 0;
        if(sum-arr[i]>=0) count += subsetSumProblem(i+1,arr,sum-arr[i],dp,path+arr[i]+" ");

        count += subsetSumProblem(i+1,arr,sum,dp,path);

        return dp[i][sum] = count;
    }
    public static int subsetSumProblem(int n,int[] arr,int sum){
        int[][] dp = new int[n+1][sum+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return subsetSumProblem(0,arr,sum,dp,"");
    }

    //Leetcode 416
    //Method 1 - Memoization
    public int canPartition01(int[] nums,int n,int sum,int[][] dp){
        if(sum==0||n==nums.length){
            return dp[n][sum] = (sum==0) ? 1:0;
        }

        if(dp[n][sum]!=-1) return dp[n][sum];

        boolean res = false;
        if(sum-nums[n]>=0)
            res = res || (canPartition01(nums,n+1,sum-nums[n],dp)==1);
        
        res = res || (canPartition01(nums,n+1,sum,dp)==1);

        return dp[n][sum] = (res)? 1:0;
    }
    public boolean canPartition01(int[] nums) {
        int sum = 0;
        int maxEle = 0;
        for(int ele:nums) {
            sum += ele;
            maxEle = Math.max(maxEle, ele);
        }

        if(sum%2!=0 || maxEle>(sum/2) ) return false;

        sum = sum/2;
        int n = nums.length;
        int[][] dp = new int[n+1][sum+1];
        for(int[] d:dp) Arrays.fill(d,-1);

        return (canPartition01(nums,0,sum,dp)==1);
    }

    //Method 2 - Tabulation
    public boolean canPartition02(int[] nums) {
        int n = nums.length;
        
        int sum = 0;
        int maxEle = 0;
        for(int ele:nums) {
            sum += ele;
            maxEle = Math.max(maxEle, ele);
        }

        if(sum%2!=0 || maxEle>(sum/2) ) return false;

        sum = sum/2;
        boolean[][] dp = new boolean[n+1][sum+1];
        
        for(int i=n;i>=0;i--){
            for(int j=0;j<=sum;j++){
                if(i==n || j==0){
                    dp[i][j] = (j==0);
                    continue;
                }

                if(j-nums[i]>=0)
                    dp[i][j] = dp[i][j] || dp[i+1][j-nums[i]];
                
                dp[i][j] = dp[i][j] || dp[i+1][j];
            }
        }

        return dp[0][sum];
    }

    //Method 3 - Tabulation 1D DP
    public boolean canPartition(int[] nums) {
        int sum = 0;
        int maxEle = 0;
        for(int ele:nums) {
            sum += ele;
            maxEle = Math.max(maxEle, ele);
        }

        if(sum%2!=0 || maxEle>(sum/2) ) return false;

        sum = sum/2;

        boolean[] dp = new boolean[sum+1];
        dp[0] = true;

        for(int ele:nums){
            for(int i=sum;i>=ele;i--){
                if(dp[i-ele]){
                    if(i==sum) return true;
                    dp[i] = true;
                }
            }
        }
        return dp[sum];
    }

    //Leetcode 494
    public int findTargetSumWays(int i,int[][] dp,int[] nums,int target,int sum){
        if(i==nums.length){
            return dp[i][target] = (target==sum) ? 1:0;
        }

        if(dp[i][target]!=-1) return dp[i][target];

        int count = 0;
        if(target-nums[i]>=0)
            count += findTargetSumWays(i+1,dp,nums,target-nums[i],sum);
        if(target+nums[i]<=2*sum)
            count += findTargetSumWays(i+1,dp,nums,target+nums[i],sum);
        return dp[i][target] = count;
    }
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int sum = 0;
        for(int ele:nums) sum += ele;

        if(target>sum || target<(-sum)) return 0;

        int[][] dp = new int[n+1][2*sum+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return findTargetSumWays(0,dp,nums,target+sum,sum);
    }

    //https://www.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1
    //Method 1 - Memoization
    public static int knapSack01(int n,int W,int[] wt,int[] val,int[][] dp){
        if(n==0||W==0){
            return dp[n][W] = 0;
        }
        
        if(dp[n][W]!=-1) return dp[n][W];
        
        
        if(W-wt[n-1]>=0){
            dp[n][W] = Math.max(dp[n][W],knapSack01(n-1,W-wt[n-1],wt,val,dp)+val[n-1]);
        }
        
        return dp[n][W] = Math.max(dp[n][W],knapSack01(n-1,W,wt,val,dp));
    }
    static int knapSack01(int W, int[] wt, int[] val, int n) 
    { 
        int[][] dp = new int[n+1][W+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        
        return knapSack01(n,W,wt,val,dp);
    }

    //Method 2 - Tabulation
    static int knapSack02(int W, int[] wt, int[] val, int n) 
    { 
        int[][] dp = new int[n+1][W+1];
        
        for(int i=0;i<=n;i++){
            for(int j=0;j<=W;j++){
                
                if(i==0||j==0){
                    dp[n][W] = 0;
                    continue;
                }
                
                if(j-wt[i-1]>=0)
                    dp[i][j] = Math.max(dp[i][j],dp[i-1][j-wt[i-1]]+val[i-1]);
                
                dp[i][j] = Math.max(dp[i][j],dp[i-1][j]);
            }
        }
        return dp[n][W];
    } 

    //Method 3 - Tabulation 1D DP
    static int knapSack(int W, int wt[], int val[], int n) 
    { 
        int[] dp = new int[W+1];
        
        for(int w=0;w<n;w++){
            for(int bagWeight=W;bagWeight>=wt[w];bagWeight--){
                dp[bagWeight] = Math.max(dp[bagWeight],dp[bagWeight-wt[w]]+val[w]);
                if(w==n-1) return dp[bagWeight];//optional statement even if you remove this statement the code will still work,due to this statemnt it just takes less time.Dry run to understand why this statement is here
            }
        }
        return dp[W];
    } 

    //https://www.geeksforgeeks.org/problems/knapsack-with-duplicate-items4201/1
    //Method 1 - Memoization
    static int knapSack01(int[] dp,int N,int W,int val[],int wt[]){
        if(W==0){
            return dp[W] = 0;
        }
        
        if(dp[W]>0) return dp[W];
        
        for(int i=0;i<N;i++){
            if(W-wt[i]>=0)
                dp[W] = Math.max(dp[W],knapSack01(dp,N,W-wt[i],val,wt)+val[i]);
        }
        return dp[W];
    }
    static int knapSack01(int N, int W, int val[], int wt[])
    {
        int[] dp = new int[W+1];
        
        return knapSack01(dp,N,W,val,wt);
    }

    //Method 2 - Tabulation
    static int knapSack02(int N, int W, int val[], int wt[])
    {
        int[] dp = new int[W+1];
        for(int w=0;w<=W;w++){
            for(int i=0;i<N;i++){
                if(w-wt[i]>=0)
                    dp[w] = Math.max(dp[w],dp[w-wt[i]]+val[i]);
            }
        }
        return dp[W];
    }

    //Method 3 - Tabulation - takes less time than above
    static int knapSack(int N, int W, int val[], int wt[])
    {
        int[] dp = new int[W+1];
        
        for(int w=0;w<N;w++){
            for(int bagW=wt[w];bagW<=W;bagW++){
                dp[bagW] = Math.max(dp[bagW],dp[bagW-wt[w]]+val[w]);
            }
        }
        return dp[W];
    }

    //Leetcode 698
    public boolean canPartitionKSubsets(int[] nums,int k,int idx,int sum,int target,boolean[] vis){
        if(k==1) return true;
        if(sum>target) return false;
        if(sum==target) return canPartitionKSubsets(nums,k-1,0,0,target,vis);
        boolean res = false;
        for(int i=idx;i<nums.length;i++){
            if(!vis[i]){
                vis[i] = true;
                res = res || canPartitionKSubsets(nums,k,i+1,sum+nums[i],target,vis);
                vis[i] = false;
            }
            if(res) break;
        }
        return res;

    }
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        int maxEle = 0;
        for(int ele:nums) {
            sum += ele;
            maxEle = Math.max(maxEle,ele);
        }

        if(sum%k!=0 || maxEle>(sum/k)) return false;
        boolean[] vis = new boolean[nums.length];
        return canPartitionKSubsets(nums,k,0,0,sum/k,vis);
    }

    //https://www.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1
    //Method 1 - Memoization
    static int matrixMultiplication01(int si,int ei,int[][] dp,int[] arr){
        if(si+1==ei){
            return dp[si][ei] = 0;
        }
        
        if(dp[si][ei]!=-1) return dp[si][ei];
        int min = (int)1e9;
        
        for(int cut = si+1;cut<ei;cut++){
            int lans = matrixMultiplication01(si,cut,dp,arr);
            int rans = matrixMultiplication01(cut,ei,dp,arr);
            min = Math.min(min,lans+arr[si]*arr[cut]*arr[ei]+rans);
        }
        return dp[si][ei] = min;
    }
    static int matrixMultiplication01(int N, int arr[])
    {
        if(N==2) return 0;
        int[][] dp = new int[N][N];
        for(int[] d:dp) Arrays.fill(d,-1);
        return matrixMultiplication01(0,N-1,dp,arr);
    }

    //Method 2 - Tabulation - Gap Strategy
    static int matrixMultiplication(int N, int arr[])
    {
        if(N==2) return 0;
        int[][] dp = new int[N][N];
        
        for(int gap=1;gap<N;gap++){
            for(int si=0,ei=gap;ei<N;si++,ei++){
                if(gap==1){
                    dp[si][ei] = 0;
                    continue;
                }
                
                int min = (int)1e9;
                for(int cut=si+1;cut<ei;cut++){
                    min = Math.min(min,dp[si][cut]+arr[si]*arr[cut]*arr[ei]+dp[cut][ei]);
                }
                dp[si][ei] = min;
            }
        }
        return dp[0][N-1];
    }

    //Follow up question of matrix multiplication - cost of one multiplication and addition is given in an cost array.cost[0]->add,cost[1]-multiply
    //Method 1 - Memoization
    public static int MCM01(int si,int ei,int[] cost,int[] arr,int[][] dp){
        if(si+1==ei){
            return dp[si][ei] = 0;
        }

        if(dp[si][ei]!=-1) return dp[si][ei];

        int min = (int)1e9;
        for(int cut=si+1;cut<ei;cut++){
            int lans = MCM01(si,cut,cost,arr,dp);
            int rans = MCM01(cut,ei,cost,arr,dp);
            min = Math.min(min,lans + (arr[si]*(arr[cut]*cost[1] + (arr[cut]-1)*cost[0])*arr[ei]) + rans);
        }
        return dp[si][ei] = min;

    }
    public static int MCM01(int n,int[] cost,int[] arr){
        if(n==2) return 0;
        int[][] dp = new int[n][n];

        for(int[] d:dp) Arrays.fill(d,-1);
        return MCM01(0,n-1,cost,arr,dp);
    }

    //Method 2 - Tabulation - Gap Strategy
    public static int MCM(int N,int[] cost,int[] arr){
        if(N==2) return 0;
        int[][] dp = new int[N][N];
        
        for(int gap=1;gap<N;gap++){
            for(int si=0,ei=gap;ei<N;si++,ei++){
                if(gap==1){
                    dp[si][ei] = 0;
                    continue;
                }
                
                int min = (int)1e9;
                for(int cut=si+1;cut<ei;cut++){
                    min = Math.min(min,dp[si][cut]+ (arr[si]*(arr[cut]*cost[1] +  (arr[cut]-1)*cost[0])*arr[ei]) +dp[cut][ei]);
                }
                dp[si][ei] = min;
            }
        }
        return dp[0][N-1];
    }

    //https://www.geeksforgeeks.org/minimum-maximum-values-expression/
    public static class minMaxPair{
        int minVal = 0;
        int maxVal = 0;
        String maxExpression = "";
        String minExpression = "";

        minMaxPair(int minVal,int maxVal){
            this.minVal = minVal;
            this.maxVal = maxVal;
        }

        minMaxPair(int minVal,int maxVal,String minExpression,String maxExpression){
            this.minVal = minVal;
            this.maxVal = maxVal;
            this.minExpression = minExpression;
            this.maxExpression = maxExpression;
        }
    }
    public static int evaluate(int a,int b,char op){
        if(op == '+')
            return (a+b);
        return a*b;
    }
    public static minMaxPair minMaxEvaluation(int si,int ei,minMaxPair[][] dp,ArrayList<Integer> nums,ArrayList<Character> ops){
        if(si==ei){
            int val = nums.get(si);
            return dp[si][ei] = new minMaxPair(val,val,val+"",val+"");
        }

        if(dp[si][ei] != null) return dp[si][ei];

        minMaxPair ans = new minMaxPair((int)1e8,-(int)1e8);
        for(int cut=si;cut<ei;cut++){
            minMaxPair lans = minMaxEvaluation(si,cut,dp,nums,ops);
            minMaxPair rans = minMaxEvaluation(cut+1,ei,dp,nums,ops);
            char op = ops.get(cut);
            int min = evaluate(lans.minVal,rans.minVal,op);
            int max = evaluate(lans.maxVal,rans.maxVal,op);

            if(ans.minVal>min){
                ans.minVal = min;
                ans.minExpression = "(" + lans.minExpression + " " + op + " " + rans.minExpression + ")";
            }
            if(ans.maxVal<max){
                ans.maxVal = max;
                ans.maxExpression = "(" + lans.maxExpression + " " + op + " " + rans.maxExpression + ")";
            }
        }
        return dp[si][ei] = ans;
    }
    public static void minMaxEvaluation(String exp){
        ArrayList<Integer> nums = new ArrayList<>();
        ArrayList<Character> ops = new ArrayList<>();
        String temp = "";

        for(int i=0;i<exp.length();i++){
            char ch = exp.charAt(i);
            if(ch=='+' || ch=='*'){
                ops.add(ch);
                nums.add(Integer.parseInt(temp));
                temp = "";
            }
            else temp += ch;
        }

        nums.add(Integer.parseInt(temp));
        int n = nums.size();
        minMaxPair[][] dp = new minMaxPair[n][n];
        minMaxPair ans = minMaxEvaluation(0,n-1,dp,nums,ops);
        System.out.println("Minimum Value: " + ans.minVal + "\nMinimum Expression: " + ans.minExpression);
        System.out.println("Maximum Value: " + ans.maxVal + "\nMaximum Expression: " + ans.maxExpression);
    }

    //https://www.geeksforgeeks.org/problems/palindromic-patitioning4845/1
    private static boolean checkPalindrome(int si,int ei,String str){
        while(si<=ei){
            if(str.charAt(si++)!=str.charAt(ei--)) return false;
        }
        return true;
    }
    private static int palindromicPartition(int si,int n,String str,int[] dp){
        if(si==n){
            return dp[si] = 0;
        }
        
        if(dp[si]!=-1) return dp[si];
        
        int ans = (int)1e8;
        
        for(int cut=si;cut<n;cut++){
            if(checkPalindrome(si,cut,str)){
                ans = Math.min(ans,palindromicPartition(cut+1,n,str,dp)+1);
            }
        }
        return dp[si] = ans;
    }
    static int palindromicPartition(String str)
    {
        int n = str.length();
        int[] dp = new int[n+1];;
        Arrays.fill(dp,-1);
        return palindromicPartition(0,n,str,dp)-1;
    }

    //Leetcode 1547
    private int minCost(int si,int ei,List<Integer> list,int[][] dp){
        if(si>ei) return 0;

        if(dp[si][ei]!=0) return dp[si][ei];
        int minCost = (int)1e8;
        int len = list.get(ei+1) - list.get(si-1);
        int cost;
        for(int cut=si;cut<=ei;cut++){
            cost = minCost(si,cut-1,list,dp) + minCost(cut+1,ei,list,dp);
            minCost = Math.min(cost,minCost);
        }

        return dp[si][ei] = minCost+len;
    }
    public int minCost(int n, int[] cuts) {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(n);
        for(int cut:cuts) list.add(cut);
        Collections.sort(list);
        int c = cuts.length;
        int[][] dp = new int[c+1][c+1];
        return minCost(1,c,list,dp);
    }

    //Leetcode 354
    public int maxEnvelopes(int[][] envelopes) {
        if(envelopes.length==1) return 1;
        int n = envelopes.length;
        Arrays.sort(envelopes,(a,b) -> {
            if(a[0]==b[0]) return b[1] - a[1];
            return a[0] - b[0];
        });

        int len = 1;
        int[] dp = new int[n];
        dp[0] = envelopes[0][1];

        for(int i=1;i<n;i++){
            if(envelopes[i][1]>dp[len-1]){
                dp[len++] = envelopes[i][1];
            }
            else{
                int si = 0;
                int ei = len-1;
                int mid;
                while(si<ei){
                    mid = (ei-si)/2 + si;
                    if(dp[mid]<envelopes[i][1]) si = mid+1;
                    else ei = mid;
                }
                dp[ei] = envelopes[i][1];
            }
        }
        return len;
    }

    //Leetcode 132
    public int minCut(int si,int ei,boolean[][] isPalindrome,int[] dp){
        if(isPalindrome[si][ei]) return dp[si] = 0;

        if(dp[si]!=-1) return dp[si];

        int min = (int)1e8;
        for(int cut=si;cut<ei;cut++){
            if(isPalindrome[si][cut])
                min = Math.min(minCut(cut+1,ei,isPalindrome,dp)+1,min);
        }
        return dp[si] = min;
    }
    public int minCut(String s) {
        int n = s.length();
        if(n==1) return 0;

        boolean[][] isPalindrome = new boolean[n][n];
        for(int gap=0;gap<n;gap++){
            for(int si=0,ei=gap;ei<n;si++,ei++){
                if(gap==0||gap==1){
                    isPalindrome[si][ei] = (gap==0) ? true:(s.charAt(si)==s.charAt(ei));
                    continue;
                }
                isPalindrome[si][ei] = (s.charAt(si)==s.charAt(ei)) && isPalindrome[si+1][ei-1];
            }
        }
        int[] dp = new int[n];
        Arrays.fill(dp,-1);
        return minCut(0,n-1,isPalindrome,dp);
    }

    //Leetcode 312
    //Method 1 - Memoization
    public int maxCoins(int si,int ei,int[] nums,int[][] dp){
        if(dp[si][ei]!=-1) return dp[si][ei];

        int lval = (si==0) ? 1:nums[si-1];
        int rval = (ei==nums.length-1) ? 1:nums[ei+1];
        
        int maxAns = 0;
        for(int cut=si;cut<=ei;cut++){
            int lans = (cut==si)? 0:maxCoins(si,cut-1,nums,dp);
            int rans = (cut==ei)? 0:maxCoins(cut+1,ei,nums,dp);

            maxAns = Math.max(maxAns, lans + (lval*nums[cut]*rval) + rans);
        }
        return dp[si][ei] = maxAns;
    }
    
    public int maxCoins01(int[] nums) {
        if(nums.length==1) return nums[0];

        int n = nums.length;
        int[][] dp = new int[n][n];
        for(int[] d:dp) Arrays.fill(d,-1);
        return maxCoins(0,n-1,nums,dp);
    }

    //Method 2 - Gap Method
    public int maxCoins(int[] nums) {
        int n = nums.length;
        List<Integer> list = new ArrayList<>();
        list.add(1);
        for(int num:nums){
            list.add(num);
        }
        list.add(1);

        n = n+2;
        int[][] dp = new int[n][n];

        for(int gap=2;gap<n;gap++){
            for(int i=0;i+gap<n;i++){
                int j = i + gap;

                for(int k=i+1;k<j;k++){
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j] + list.get(i)*list.get(k)*list.get(j));
                }
            }
        }

        return dp[0][n-1];
    }

    //Leetcode 1039
    public int minScoreTriangulation(int si,int ei,int[] values,int[][] dp){
        if(ei-si<=1) return dp[si][ei]=0;

        if(dp[si][ei]!=-1) return dp[si][ei];

        int minAns = (int)1e9;
        for(int cut=si+1;cut<ei;cut++){
            int lans = minScoreTriangulation(si,cut,values,dp);
            int rans = minScoreTriangulation(cut,ei,values,dp);
            minAns = Math.min(minAns,lans + (values[si]*values[cut]*values[ei]) + rans);
        }
        return dp[si][ei] = minAns;
    }
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        
        int[][] dp = new int[n][n];
        for(int[] d:dp) Arrays.fill(d,-1);
        return minScoreTriangulation(0,n-1,values,dp);
    }

    //Leetcode 329
    private int longestIncreasingPath(int i,int j,int n,int m,int[][] dp,int[][] dir,int[][] matrix){
        if(dp[i][j]!=0) return dp[i][j];

        int max = 0;
        int val = matrix[i][j];
        int r,c;
        for(int d=0;d<4;d++){
            r = i + dir[d][0];
            c = j + dir[d][1];
            if(r>=0 && c>=0 && r<n && c<m && matrix[r][c]>val){
                max = Math.max(max,longestIncreasingPath(r,c,n,m,dp,dir,matrix));
            }
        }
        return dp[i][j] = 1 + max;
    }
    public int longestIncreasingPath(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[][] dp = new int[n][m];
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};

        int max = -(int)1e8;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(dp[i][j]==0){
                    max = Math.max(max,longestIncreasingPath(i,j,n,m,dp,dir,matrix));
                }
            }
        }
        return max;
    }

    //https://www.geeksforgeeks.org/problems/optimal-binary-search-tree2214/1
    static int optimalSearchTree(int si,int ei,int[] psum,int[][] dp){
        
        if(dp[si][ei]!=-1) return dp[si][ei];
        
        int minCost = (int)1e9;
        for(int cut=si;cut<=ei;cut++){
            int left = (cut==si)? 0:optimalSearchTree(si,cut-1,psum,dp);
            int right = (cut==ei)? 0:optimalSearchTree(cut+1,ei,psum,dp);
            
            minCost = Math.min(minCost,left+right);
        }
        int sum = (si==0) ? psum[ei] : psum[ei]-psum[si-1];
        return dp[si][ei] = sum+minCost;
    }
    static int optimalSearchTree(int keys[], int freq[], int n)
    {
        if(n==1) return freq[0];
        
        int[] psum = new int[n];
        for(int i=0;i<n;i++){
            psum[i] = (i==0)? freq[0] : freq[i]+psum[i-1];
        }
        
        int[][] dp = new int[n][n];
        for(int[] d:dp) Arrays.fill(d,-1);
        return optimalSearchTree(0,n-1,psum,dp);
    }

    //https://www.geeksforgeeks.org/problems/boolean-parenthesization5610/1
    public static class BooleanPair{
        int trueWays = 0;
        int falseWays = 0;
        BooleanPair(int trueWays,int falseWays){
            this.trueWays = trueWays;
            this.falseWays = falseWays;
        }
    }
    static BooleanPair evaluate(char ch,BooleanPair lans,BooleanPair rans){
        int mod = 1003;
        BooleanPair ans = new BooleanPair(0,0);
        int totalWays = (((lans.trueWays+lans.falseWays)%mod)*((rans.trueWays+rans.falseWays)%mod))%mod;
        if(ch=='&'){
            ans.trueWays = (lans.trueWays*rans.trueWays)%mod;
            ans.falseWays = (totalWays-ans.trueWays + mod)%mod;
        }else if(ch=='|'){
            ans.falseWays = (lans.falseWays*rans.falseWays)%mod;
            ans.trueWays = (totalWays-ans.falseWays+mod)%mod;
        }else{
            ans.trueWays = (lans.trueWays*rans.falseWays)%mod + (lans.falseWays*rans.trueWays)%mod;
            ans.falseWays = (totalWays-ans.trueWays+mod)%mod;
        }
        return ans;
    }
    static BooleanPair booleanPare(int si,int ei,String S,BooleanPair[][] dp){
        if(si==ei){
            char ch = S.charAt(si);
            return new BooleanPair(ch=='T'? 1:0, ch=='F'? 1:0);
        }
        
        if(dp[si][ei]!=null) return dp[si][ei];
        
        BooleanPair ans = new BooleanPair(0,0);
        for(int cut=si+1;cut<ei;cut+=2){
            char op = S.charAt(cut);
            BooleanPair lans = booleanPare(si,cut-1,S,dp);
            BooleanPair rans = booleanPare(cut+1,ei,S,dp);
            
            BooleanPair recAns = evaluate(op,lans,rans);
            ans.trueWays = (ans.trueWays + recAns.trueWays)%1003;
            ans.falseWays = (ans.falseWays + recAns.falseWays)%1003;
            
        }
        return dp[si][ei] = ans;
    }
    
    static int countWays(int N, String S){
        BooleanPair[][] dp = new BooleanPair[N][N];
        BooleanPair ans = booleanPare(0,N-1,S,dp);
        return ans.trueWays;
    }

    //https://www.geeksforgeeks.org/problems/nth-catalan-number0817/1
    public static int findCatalan(int n) {
        if (n <= 1) {
            return 1;
        }
        int mod = (int)1e9 + 7;
        long[] catalan = new long[n + 1];
        Arrays.fill(catalan,0);
        catalan[0] = catalan[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                catalan[i] = (catalan[i] + catalan[j]*catalan[i-j-1])%mod;
            }
        }

        return (int)catalan[n];
    }

    //Leetcode 887
    //Method 1
    private int superEggDrop(int k,int n,int[][] dp){
        if(k==1) return n;
        if(k==0) return 0;
        if(n==0) return 0;
        if(n==1) return 1;

        if(dp[k][n]!=-1) return dp[k][n];

        int l=1,r=n;
        int mid;
        int broken,unbroken;
        while(l<=r){
            mid = (r+l)/2;
            broken = superEggDrop(k-1,mid-1,dp);
            unbroken = superEggDrop(k,n-mid,dp);
            if(broken>=unbroken) r = mid-1;
            else l = mid+1;
        }
        return dp[k][n] = 1 + superEggDrop(k-1,l-1,dp);
    }
    public int superEggDrop01(int k, int n) {
        int[][] dp = new int[k+1][n+1];
        for(int[] d:dp) Arrays.fill(d,-1);

        return superEggDrop(k,n,dp);
    }

    //Method 2
    public int superEggDrop(int k, int n) {
        int[][] dp = new int[k+1][n+1];
        int m = 0; // no. of moves

        while(dp[k][m]<n){
            m++;
            for(int i=1;i<=k;i++){
                dp[i][m] = dp[i][m-1] + dp[i-1][m-1] + 1;
            }
        }
        return m;
    }

    //Leetcode 1235
    private class Job{
        int start;
        int end;
        int profit;

        Job(int start,int end,int profit){
            this.start = start;
            this.end = end;
            this.profit = profit;
        }
    }
    private int getNextIdx(int idx,Job[] jobs){
        int l = idx+1,r=jobs.length-1;
        int target = jobs[idx].end;
        int res = jobs.length;
        while(l<=r){
            int mid = (r-l)/2 + l;

            if(jobs[mid].start>=target) {
                res = mid;
                r = mid-1;
            }else l = mid+1;
        }
        return res;
    }
    private int jobScheduling(int idx,Job[] jobs,int[] dp){
        if(idx>=jobs.length) return 0;

        if(dp[idx]!=0) return dp[idx];

        int max = -(int)1e8;
        max = Math.max(max,jobScheduling(idx+1,jobs,dp));
        max = Math.max(max,jobScheduling(getNextIdx(idx,jobs),jobs,dp) + jobs[idx].profit);

        return dp[idx] = max;
    }
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = profit.length;
        if(n==1) return profit[0];
        Job[] jobs = new Job[n];

        for(int i=0;i<n;i++){
            jobs[i] = new Job(startTime[i],endTime[i],profit[i]);
        }
        Arrays.sort(jobs,(a,b) -> {
            return a.start - b.start;
        });
        int[] dp = new int[n];

        return jobScheduling(0,jobs,dp);
    }

    //Leetcode - 95
    //Method 1 - Recursion
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right){
          this.val = val;
          this.left = left;
          this.right = right;
        }
    }
    public List<TreeNode> generateTrees01(int si,int ei){
        List<TreeNode> res = new ArrayList<>();
        if(si>ei){
            res.add(null);
            return res;
        }

        for(int cut=si;cut<=ei;cut++){
            List<TreeNode> leftTree = generateTrees01(si,cut-1);
            List<TreeNode> rightTree = generateTrees01(cut+1,ei);

            for(TreeNode left:leftTree)
                for(TreeNode right:rightTree){
                    TreeNode root = new TreeNode(cut);
                    root.left = left;
                    root.right = right;
                    res.add(root);
                }
        }

        return res;
    }
    public List<TreeNode> generateTrees01(int n){
        return generateTrees01(1,n);
    }

    //Method 2 - Memoization
    public List<TreeNode> generateTrees(int si,int ei,List<TreeNode>[][] dp){
        if(si>ei){
            List<TreeNode> res = new ArrayList<>();
            res.add(null);
            return res;
        }

        if(dp[si][ei]!=null) return dp[si][ei];

        dp[si][ei] = new ArrayList<>();
        for(int cut=si;cut<=ei;cut++){
            List<TreeNode> left = generateTrees(si,cut-1,dp);
            List<TreeNode> right = generateTrees(cut+1,ei,dp);

            
            for(TreeNode leftTree:left){
                for(TreeNode rightTree:right){
                    dp[si][ei].add(new TreeNode(cut,leftTree,rightTree));
                }
            }
        }
        return dp[si][ei];
    }
    public List<TreeNode> generateTrees(int n) {
        @SuppressWarnings("unchecked")
        List<TreeNode>[][] dp = new ArrayList[n+1][n+1];
        return generateTrees(1,n,dp);
    }

    //Leetcode 1216 - Valid Palindrome III - Locked
    //https://leetcode.ca/all/1216.html
    public static boolean isValidPalindrome(String s,int k){
        int n = s.length();
        if(n==1) return true;
        int[][] dp = new int[n][n];

        for(int gap=0;gap<n;gap++){
            for(int i=0,j=gap;j<n;i++,j++){
                if(gap==0){
                    dp[i][j] = 1;
                    continue;
                }

                if(s.charAt(i)==s.charAt(j)) dp[i][j] = dp[i+1][j-1]+2;

                else dp[i][j] = Math.max(dp[i+1][j],dp[i][j+1]);
            }
        }

        return dp[0][n-1] >= n-k;
    }
    
    //Leetcode 940
    public int distinctSubseqII(String s) {
        int n = s.length();
        int[] dp = new int[n+1];
        dp[0] = 1;
        int[] lastOccurence = new int[26];
        Arrays.fill(lastOccurence,-1);
        int mod = (int)1e9 + 7;

        for(int i=1;i<=n;i++){
            char ch = s.charAt(i-1);
            int idx = ch - 'a';
            dp[i] = (2*dp[i-1])%mod;
            if(lastOccurence[idx] != -1){
                dp[i] = (dp[i] - dp[lastOccurence[idx]-1])%mod;
            }
            lastOccurence[idx] = i;
        }
        return (dp[n]-1 + mod)%mod;
    }

    //Leetcode 1278
    public int palindromePartition(int si,int ei,int k,String s,int[][] dp,int[][] minChanges){
        if(k==1) return dp[k][ei] = minChanges[si][ei];
        if(ei-si+1<=k) return dp[k][ei] = (ei-si+1)<k? (int)1e9:0;

        if(dp[k][ei]!=-1) return dp[k][ei];
        int min = (int)1e9;
        for(int cut=si;cut<ei;cut++){
            int recAns = palindromePartition(si,cut,k-1,s,dp,minChanges);
            min = Math.min(min,recAns+minChanges[cut+1][ei]);
        }
        return dp[k][ei] = min;
    }
    public int palindromePartition(String s, int k) {
        int n = s.length();
        int[][] minChanges = new int[n][n];

        for(int gap=0;gap<n;gap++){
            for(int i=0,j=gap;j<n;i++,j++){
                if(gap==0){
                    minChanges[i][j] = 0;
                }else if(gap==1){
                    minChanges[i][j] = (s.charAt(i)==s.charAt(j))? 0:1;
                }
                else{
                    minChanges[i][j] = (s.charAt(i)==s.charAt(j))? minChanges[i+1][j-1]:minChanges[i+1][j-1]+1;
                }
            }
        }

        int[][] dp = new int[k+1][n+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return palindromePartition(0,n-1,k,s,dp,minChanges);
    }


    //Leetcode 10
    public boolean isMatch(int i,int j,String s,String p,boolean[][] dp,boolean[][] vis){
        if(vis[i][j]) return dp[i][j];

        vis[i][j] = true;
        if(i==s.length() && j==p.length()) return dp[i][j] = true;
        if(j==p.length()) return dp[i][j] = false;

        if(j==p.length()-1 || p.charAt(j+1)!= '*'){
            if(i<s.length() && (p.charAt(j)=='.' || s.charAt(i)==p.charAt(j)))
                return dp[i][j] = isMatch(i+1,j+1,s,p,dp,vis);
            else return dp[i][j] = false;
        }
        else{
            if(isMatch(i,j+2,s,p,dp,vis)) return dp[i][j] = true;

            if(i<s.length() && ((p.charAt(j)=='.' || s.charAt(i)==p.charAt(j)) && isMatch(i+1,j,s,p,dp,vis)))
                return dp[i][j] = true;
            
        }
        return dp[i][j] = false;
    }
    public boolean isMatch_(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean[][] dp = new boolean[n+1][m+1];
        boolean[][] vis = new boolean[n+1][m+1];
        return isMatch(0,0,s,p,dp,vis);
    }

    //Leetcode 198
    private int rob(int i,int n,int[] nums,int[] dp){
        if(i>=n) return 0;
        if(i==n-1) return dp[i] = nums[i];

        if(dp[i]!=-1) return dp[i];
        return dp[i] =  Math.max(rob(i+2,n,nums,dp)+nums[i],rob(i+1,n,nums,dp));
    }
    public int rob(int[] nums) {
        int n = nums.length;
        if(n==1) return nums[0];
        int[] dp = new int[n];
        Arrays.fill(dp,-1);
        return rob(0,n,nums,dp);
    }

    //Leetcode 213
    public int robII(int[] nums) {
        int n = nums.length;
        if(n==1) return nums[0];
        int[] dp = new int[n];
        Arrays.fill(dp,-1);
        int max = rob(0,n-1,nums,dp);
        Arrays.fill(dp,-1);
        return Math.max(max,rob(1,n,nums,dp));
    }

    //Leetcode 120
    public int minimumTotal(List<List<Integer>> triangle) {
        int size = triangle.size();
        Integer[][] dp = new Integer[size][size];
        return helper(0, 0, size, dp, triangle);
    }
    private int helper(int i, int j, int n, Integer[][] dp, List<List<Integer>> list)   {
        if (i >= n)
            return 0;

        if (dp[i][j] != null)
            return dp[i][j];

        return dp[i][j] = list.get(i).get(j) + Math.min(helper(i+1, j, n, dp, list), helper(i+1, j+1, n, dp, list));
    }
    



    //------------------STOCK BUY AND SELL-----------------------------------------//

    //Leetcode 121
    public int maxProfit_121(int[] prices) {
        int buy = -(int)1e8;
        int sell = 0;

        for(int price:prices){
            sell = Math.max(sell,buy+price);
            buy = Math.max(buy,0-price);
        }
        return sell;
    }

    //Leetcode 123
    public int maxProfit_123(int[] prices) {
        int buy1 = -(int)1e8;
        int buy2 = -(int)1e8;
        int sell1 = 0;
        int sell2 = 0;

        for(int price:prices){
            
            buy1 = Math.max(buy1,0-price);
            sell1 = Math.max(sell1,buy1+price);

            buy2 = Math.max(buy2,sell1-price);
            sell2 = Math.max(sell2,buy2+price);

        }
        return sell2;
    }

    //Leetcode 122
    public int maxProfit_122(int[] prices) {
        int buy = -(int)1e8;
        int sell = 0;

        for(int price:prices){
            int temp = sell;
            sell = Math.max(sell,buy+price);
            buy = Math.max(buy,temp-price);
        }
        return sell;
    }

    //Leetcode 309
    public int maxProfit_309(int[] prices) {
        int buy = -(int)1e9;

        int sell1 = 0;
        int sell2 = 0;

        for(int price:prices){
            int temp = sell2;
            sell2 = Math.max(sell2,buy+price);
            buy = Math.max(buy,sell1-price);
            sell1 = temp;
        }
        return sell2;
    }

    //Leetcode 188
    public int maxProfit(int k, int[] prices) {
        int[] buy = new int[k+1];
        Arrays.fill(buy,-(int)1e9);
        int[] sell = new int[k+1];

        for(int price:prices){
            for(int i=1;i<=k;i++){
                sell[i] = Math.max(sell[i],buy[i]+price);
                buy[i] = Math.max(buy[i],sell[i-1]-price);
            }
        }
        return sell[k];
    }

    //Leetcode 139
    public boolean wordBreak(int idx,String s,HashSet<String> wordDictSet,int[] dp){
        if(idx==s.length()){
            return true;
        }

        if(dp[idx]!=-1){
            if(dp[idx]==0) return false;
            return true;
        }

        boolean res = false;
        StringBuilder ans = new StringBuilder();
        for(int i=idx;i<s.length();i++){
            ans.append(s.charAt(i));
            if(wordDictSet.contains(ans.toString())){
                res = wordBreak(i+1,s,wordDictSet,dp);
                if(res){
                    dp[idx] = 1;
                    return true;
                }
                
            }
            
        }
        dp[idx] = 0;
        return res;
    }
    public boolean wordBreak_(String s, List<String> wordDict) {
        HashSet<String> wordDictSet = new HashSet<>();
        for(String str:wordDict){
            wordDictSet.add(str);
        }
        int[] dp = new int[s.length()];
        Arrays.fill(dp,-1);
        return wordBreak(0,s,wordDictSet,dp);
    }

    //Leetcode 140
    private List<String> wordBreak(String s,HashSet<String> dict,HashMap<String,List<String>> dp){
        if(dp.containsKey(s)) return dp.get(s);

        List<String> ans = new ArrayList<>();

        for(int i=1;i<s.length();i++){
            String prefix = s.substring(0,i);
            String suffix = s.substring(i);
            if(dict.contains(prefix)){
                for(String word : wordBreak(suffix,dict,dp)){
                    ans.add(prefix + " " + word);
                }
            }
        }

        if(dict.contains(s)) ans.add(s);

        dp.put(s,ans);
        return ans;
    }
    public List<String> wordBreak(String s, List<String> wordDict) {
        HashSet<String> dict = new HashSet<>();
        for(String str:wordDict){
            dict.add(str);
        }
        HashMap<String,List<String>> dp = new HashMap<>();
        return wordBreak(s,dict,dp);
    }

    //Leetcode 97
    private int isInterleave(int i,int j,int n,int m,int len,String s1,String s2,String s3,int[][] dp){
        if(i==n && j==m && i+j==len){
            return 1;
        }

        if(dp[i][j]!=-1) return dp[i][j];

        if(i<n && s1.charAt(i)==s3.charAt(i+j))
            dp[i][j] = isInterleave(i+1,j,n,m,len,s1,s2,s3,dp);
        
        if(dp[i][j]==1) return dp[i][j];

        if(j<m && s2.charAt(j)==s3.charAt(i+j)){
            return dp[i][j] = isInterleave(i,j+1,n,m,len,s1,s2,s3,dp);
        }

        return dp[i][j] = 0;
    }
    public boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length();
        int m = s2.length();
        int len = s3.length();
        if(n+m!=len) return false;
        int[][] dp = new int[n+1][m+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        return (isInterleave(0,0,n,m,len,s1,s2,s3,dp)==1);
    }

    //Leetcode 568 - Locked
    //Lintcode 874
    //Method 1
    private int maxVacationDays(int city,int week,int[][] flights,int[][] days,int[][] dp){
        if(week>days[0].length) return 0;

        if(dp[city][week]!=-1) return dp[city][week];

        for(int i=0;i<flights.length;i++){
            if(flights[city][i]==1){
                dp[city][week] = Math.max(dp[city][week],maxVacationDays(i,week+1,flights,days,dp));
            }
        }

        return dp[city][week] = Math.max(dp[city][week],maxVacationDays(city,week+1,flights,days,dp)) + days[city][week-1];
    }
    public int maxVacationDays(int[][] flights, int[][] days) {
        int cities = flights.length;
        int weeks = days.length;
        int[][] dp = new int[cities+1][weeks+1];
        for(int[] d:dp) Arrays.fill(d,-1);
        int max = -1;
        for(int city=0;city<cities;city++){
            if(city==0 || (city>0 && flights[0][city]==1)){
                max = Math.max(max,maxVacationDays(city,1,flights,days,dp));
            }
        }
        return max;
    }

    //Method 2 - Tabulation
    public int maxVacationDays01(int[][] flights, int[][] days) {
        int numCities = flights.length;
        int numWeeks = days[0].length;
        int[][] dp = new int[numWeeks+1][numCities];
        for(int[] d:dp) Arrays.fill(d,-1);

        dp[0][0] = 0;
        int ans = -1;
        for(int week = 1;week<=numWeeks;week++){
            for(int currentCity=0;currentCity<numCities;currentCity++){
                dp[week][currentCity] = dp[week-1][currentCity];

                for(int prevCity=0;prevCity<numCities;prevCity++){
                    if(flights[prevCity][currentCity]==1){
                        dp[week][currentCity] = Math.max(dp[week][currentCity],dp[week-1][prevCity]);
                    }
                }

                if(dp[week][currentCity]!=-1){
                    dp[week][currentCity] += days[currentCity][week-1];
                }
                if(week==numWeeks) ans = Math.max(dp[week][currentCity],ans);
            }
        }

        return ans;

    }

    public static void main(String[] args){
        //minMaxEvaluation("1+2*3+4*5");
        int[] arr = {2,3,5,7};
        System.out.println(coinChangePermutationInfi01(arr,10));
        System.out.println(coinChangePermutationInfi02(arr,10));
        System.out.println(coinChangeCombinationInfi(arr,10));
    }

}


