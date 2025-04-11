import java.util.*;

public class Recursion {

    //Find out number of permutations in a string and print it
    private static int printPermutations(String str,String ans){
        if(str.length()==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i=0;i<str.length();i++){
            char ch = str.charAt(i);
            String ros = str.substring(0,i) + str.substring(i+1);
            count += printPermutations(ros,ans+ch);
        }
        return count;
    }
    public static int printPermutations(String str){
        return printPermutations(str,"");
    }

    //Find out number of unique permutations in a string and print it
    private static int printUniquePermutations(String str,String ans){
        if(str.length()==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        boolean[] vis = new boolean[26];
        for(int i=0;i<str.length();i++){
            char ch = str.charAt(i);
            if(!vis[ch-'a']){
                vis[ch-'a'] = true;
                String ros = str.substring(0,i) + str.substring(i+1);
                count += printUniquePermutations(ros,ans+ch);
            }
        }
        return count;
    }
    public static int printUniquePermutations(String str){
        return printUniquePermutations(str,"");
    }

    //Leetcode 46
    private void permute(boolean[] vis,int[] nums,List<Integer> ans,List<List<Integer>> res){
        if(ans.size()==nums.length){
            res.add(new ArrayList<>(ans));
            return;
        }

        for(int i=0;i<nums.length;i++){
            if(!vis[i]){
                ans.add(nums[i]);
                vis[i] = true;
                permute(vis,nums,ans,res);
                vis[i] = false;
                ans.remove(ans.size()-1);
            }
        }
    }
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> ans = new ArrayList<>();
        boolean[] vis = new boolean[nums.length];
        permute(vis,nums,ans,res);
        return res;
    }

    //Contd. Leetcode 46 - Print all permutations and return the count
    private int printPermute(int[] nums,List<Integer> ans,boolean[] vis){
        if(ans.size()==nums.length){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i=0;i<nums.length;i++){
            if(!vis[i]){
                ans.add(nums[i]);
                vis[i] = true;
                count += printPermute(nums,ans,vis);
                vis[i] = false;
                ans.remove(ans.size()-1);
            }
        }
        return count;
    }
    public int printPermute(int[] nums){
        boolean[] vis = new boolean[nums.length];
        List<Integer> ans = new ArrayList<>();
        return printPermute(nums,ans,vis);
    }

    //Leetcode 47
    private void permuteUnique(int[] nums,boolean[] vis,List<Integer> res,List<List<Integer>> ans){
        if(res.size()==nums.length){
            ans.add(new ArrayList<>(res));
            return;
        }

        boolean[] visNum = new boolean[21];
        for(int i=0;i<nums.length;i++){
            if(!vis[i] && !visNum[nums[i]+10]){
                visNum[nums[i]+10] = true;
                vis[i] = true;
                res.add(nums[i]);
                permuteUnique(nums,vis,res,ans);
                vis[i] = false;
                res.remove(res.size()-1);
            }
        }
    }
    public List<List<Integer>> permuteUnique(int[] nums) {
        boolean[] vis = new boolean[nums.length];
        List<Integer> res = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        permuteUnique(nums,vis,res,ans);
        return ans;
    }

    //Contd. leetcode 47 - Print all the unique permutations and return the count
    private int printPermuteUnique(int[] nums,boolean[] vis,List<Integer> res){
        if(res.size()==nums.length){
            System.out.println(res);
            return 1;
        }

        boolean[] visNum = new boolean[21];
        int count = 0;
        for(int i=0;i<nums.length;i++){
            if(!vis[i] && !visNum[nums[i]+10]){
                visNum[nums[i]+10] = true;
                vis[i] = true;
                res.add(nums[i]);
                count += printPermuteUnique(nums,vis,res);
                vis[i] = false;
                res.remove(res.size()-1);
            }
        }
        return count;
    }
    public int printPermuteUnique(int[] nums) {
        boolean[] vis = new boolean[nums.length];
        List<Integer> res = new ArrayList<>();
        return printPermuteUnique(nums,vis,res);
    }

    //Flood Fill - One jump - No. of ways to reach a destination and print it
    private static int floodFill(int sr,int sc,int dr,int dc,int n,int m,boolean[][] vis,int[][] dir,String[] dirs,String path){
        if(sr==dr && sc==dc){
            System.out.println(path);
            return 1;
        }

        vis[sr][sc] = true;
        int count = 0;
        for(int d=0;d<dir.length;d++){
            int x = sr + dir[d][0];
            int y = sc + dir[d][1];
            if(x>=0 && y>=0 && x<n && y<m && !vis[x][y]){
                count += floodFill(x,y,dr,dc,n,m,vis,dir,dirs,path+dirs[d]+" ");
            }
        }
        vis[sr][sc] = false;
        return count;
    }
    public static int floodFill(int n,int m,int sr,int sc,int dr,int dc){
        int[][] dir = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
        String[] dirs = {"R","DR","D","DL","L","UL","U","UR"};
        boolean[][] vis = new boolean[n][m];
        return floodFill(sr,sc,dr,dc,n,m,vis,dir,dirs,"");
    }

    //Flood Fill - Multiple jumps - No. of ways to reach a destination and print it
    public static int floodFillMultiJumps(int n,int m,int sr,int sc,int dr,int dc,boolean[][] vis,int[][] dir,String[] dirs,String ans){
        if(sr==dr && sc==dc){
            System.out.println(ans);
            return 1;
        }

        vis[sr][sc] = true;
        int count = 0;
        for(int d=0;d<dir.length;d++){
            for(int rad=1;rad<Math.max(n,m);rad++){
                int r = sr + rad*dir[d][0];
                int c = sc + rad*dir[d][1];
                if(r>=0 && c>=0 && r<n && c<m && !vis[r][c]){
                    count += floodFill(n, m, r, c, dr, dc, vis, dir, dirs, ans+rad+dirs[d]+" ");
                }
                else break;
            }
        }
        vis[sr][sc] = false;
        return count;

    }
    public static int floodFillMultiJumps(int n,int m,int sr,int sc,int dr,int dc){
        boolean[][] vis = new boolean[n][m];
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0},{-1,1},{1,1},{1,-1},{-1,-1}};
        String[] dirs = {"R","D","L","U","UR","DR","DL","UL"};
        return floodFill(n, m, sr, sc, dr, dc, vis, dir, dirs, "");
    }

    //Print and return count of all subsequences of a string
    private static int printAllSubsequencesString(String str,String ans){
        if(str.length()==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        count += printAllSubsequencesString(str.substring(1),ans+str.charAt(0));
        count += printAllSubsequencesString(str.substring(1), ans);

        return count;
    }
    public static int printAllSubsequencesString(String str){
        return printAllSubsequencesString(str,"");
    }

    //Print and return count of all unique subsequences of a string
    static int count = 0;
    public static void printAllSubsequencesStringUnique(String str,String ans,int idx){
        System.out.println(ans);
        if(idx==str.length()) return;

        boolean[] vis = new boolean[26];
        for(int i=idx;i<str.length();i++){
            char ch = str.charAt(i);
            if(!vis[ch-'a']){
                vis[ch-'a'] = true;
                count++;
                printAllSubsequencesStringUnique(str, ans+ch, i+1);
            }
        }
    }
    public static int printAllSubsequencesStringUnique(String str){
        printAllSubsequencesStringUnique(str,"",0);
        return count;
    }

    //No. of ways to reach a destination - single jump
    private static int printMazePaths(int sr,int sc,int dr,int dc,String path){
        if(sr==dr && sc==dc){
            System.out.println(path);
            return 1;
        }

        int count = 0;
        if(sr+1<=dr)
            count += printMazePaths(sr+1,sc,dr,dc,path+"D");
        if(sc+1<=dc)
            count += printMazePaths(sr,sc+1,dr,dc,path+"R");
        if(sr+1<=dr && sc+1<=dc)
            count += printMazePaths(sr+1,sc+1,dr,dc,path+"V");
        return count;
    }
    public static int printMazePaths(int sr,int sc,int dr,int dc){
        return printMazePaths(sr,sc,dr,dc,"");
    }

    //No. of ways to reach a destination - multiple jumps
    public static int printMazePathsMultiJumps(int sr,int sc,int dr,int dc,String psf){
        if(sr==dr && sc==dc){
            System.out.println(psf);
            return 1;
        }

        int count = 1;
        for(int i=1;sc+i<=dc;i++)
            count += printMazePaths(sr+i, sc, dr, dc, psf+i+"h");
        for(int i=1;sr+i<=dr && sc+i<=dc; i++)
            count += printMazePaths(sr+i,sc+i,dr,dc,psf+i+"d");
        for(int i=1;sr+i<=dr;i++)
            count += printMazePaths(sr+i,sc,dr,dc,psf+i+"v");
        return count;
    }
    public static int printMazePathsMultiJumps(int sr,int sc,int dr,int dc){
        return printMazePathsMultiJumps(sr,sc,dr,dc,"");
    }

    //https://www.geeksforgeeks.org/problems/rat-in-a-maze-problem/1
    private void findPath(int i,int j,int n,ArrayList<String> ans,String path,int[][] dir,String[] dirs,int[][] mat){
        if(i==n-1 && j==n-1){
            ans.add(path);
            return;
        }
        
        int r,c;
        for(int d=0;d<4;d++){
            r = i + dir[d][0];
            c = j + dir[d][1];
            if(r>=0 && c>=0 && r<n && c<n && mat[r][c]==1){
                mat[r][c] = 0;
                findPath(r,c,n,ans,path+dirs[d],dir,dirs,mat);
                mat[r][c] = 1;
            }
        }
    }
    public ArrayList<String> findPath(int[][] mat) {
        int n = mat.length;
        if(mat[0][0]==0 || mat[n-1][n-1]==0 ) return new ArrayList<>();
        
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        String[] dirs = {"R","D","L","U"};
        ArrayList<String> ans = new ArrayList<>();
        mat[0][0] = 0;
        findPath(0,0,n,ans,"",dir,dirs,mat);
        return ans;
    }

    //https://www.geeksforgeeks.org/problems/rat-maze-with-multiple-jumps3852/1
    private boolean shortestDistance(int sr,int sc,int n,int[][]matrix,int[][] vis){
        if(sr==n-1 && sc==n-1) return true;
        
        boolean res = false;
        
        for(int i=1;i<=matrix[sr][sc];i++){
            if(sc+i<n){
                res = shortestDistance(sr,sc+i,n,matrix,vis);
                if(res){
                    vis[sr][sc+i] = 1;
                    break;
                }
            }
            
            if(sr+i<n){
                res = res || shortestDistance(sr+i,sc,n,matrix,vis);
                if(res){
                    vis[sr+i][sc] = 1;
                    break;
                }
            }
        }
        
        return res;
    }
    public int[][] ShortestDistance(int[][] matrix)
    {
        int n = matrix.length;
        if(matrix[0][0]==0 && n!=1){
            int[][] ans = {{-1}};
            return ans;
        }
        
        int[][] vis = new int[n][n];
        vis[0][0] = 1;
        if(!shortestDistance(0,0,n,matrix,vis)){
            return new int[][] {{-1}};
        }
        return vis;
    }

    //Coin Change - Infinite Coin Supply - Permutation
    private static int coinChangePermutationInfi(int[] arr,int target,ArrayList<Integer> res){
        if(target==0){
            System.out.println(res);
            return 1;
        }

        int count = 0;
        for(int i=0;i<arr.length;i++){
            if(target-arr[i]>=0){
                res.add(arr[i]);
                count += coinChangePermutationInfi(arr,target-arr[i],res);
                res.remove(res.size()-1);
            }
        }
        return count;
    }
    public static int coinChangePermutationInfi(int[] arr,int target){
        ArrayList<Integer> res = new ArrayList<>();
        return coinChangePermutationInfi(arr,target,res);
    }

    //Coin Change - Infinite Coin Supply - Combination
    private static int coinChangeCombinationInfi(int idx,ArrayList<Integer> res,int[] arr,int target){
        if(target==0 || idx==arr.length){
            if(target==0){
                System.out.println(res);
                return 1;
            }
            return 0;
        }

        int count = 0;
        for(int i=idx;i<arr.length;i++){
            if(target-arr[i]>=0){
                res.add(arr[i]);
                count += coinChangeCombinationInfi(i,res,arr,target-arr[i]);
                res.remove(res.size()-1);
            }
        }

        return count;
    }
    public static int coinChangeCombinationInfi(int[] arr,int target){
        ArrayList<Integer> res = new ArrayList<>();
        return coinChangeCombinationInfi(0,res,arr,target);
    }

    //Coin Change - Single Coin - Permutation
    private static int singleCoinChangePermutation(ArrayList<Integer> res,int[] arr,int target){
        if(target==0){
            System.out.println(res);
            return 1;
        }

        int count = 0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]>0 && target-arr[i]>=0){
                arr[i] = -arr[i];
                res.add(arr[i]);
                count += singleCoinChangePermutation(res,arr,target-arr[i]);
                res.remove(res.size()-1);
                arr[i] = -arr[i];
            }
        }
        return count;
    }
    public static int singleCoinChangePermutation(int[] arr,int target){
        ArrayList<Integer> res = new ArrayList<>();
        return singleCoinChangePermutation(res,arr,target);
    }

    //Coin Change - Single Coin - Combination
    private static int singleCoinChangeCombination(int idx,ArrayList<Integer> res,int[] arr,int target){
        if(target==0){
            System.out.println(res);
            return 1;
        }

        int count = 0;
        for(int i=idx;i<arr.length;i++){

            if(target-arr[i]>=0){
                res.add(arr[i]);
                count += singleCoinChangeCombination(i+1,res,arr,target-arr[i]);
                res.remove(res.size()-1);
            }
        }

        return count;
    }
    public static int singleCoinChangeCombination(int[] arr,int target){
        ArrayList<Integer> res = new ArrayList<>();
        return singleCoinChangeCombination(0,res,arr,target);
    }


    //Infinite Coin Change Combination Subsequence
    private static int coinChangeCombinationSubsequenceInfi(int idx,int[] arr,int target,List<Integer> list){
        if(target==0 || idx==arr.length){
            if(target==0){
                System.out.println(list);
                return 1;
            }
            return 0;
        }

        int count = 0;
        if(target-arr[idx]>=0){
            list.add(arr[idx]);
            count += coinChangeCombinationSubsequenceInfi(idx,arr,target-arr[idx],list);
            list.remove(list.size()-1);
        }

        count += coinChangeCombinationSubsequenceInfi(idx+1,arr,target,list);
        return count;
    }
    public static int coinChangeCombinationSubsequenceInfi(int[] arr,int target){
        List<Integer> list = new ArrayList<>();
        return coinChangeCombinationSubsequenceInfi(0,arr,target,list);
    }

    //Single Coin Change Combination Subsequence
    private static int singleCoinChangeCombinationSubsequence(int idx,int[] arr,int target,List<Integer> list){
        if(target==0 || idx==arr.length){
            if(target==0){
                System.out.println(list);
                return 1;
            }
            return 0;
        }

        int count = 0;
        if(target-arr[idx]>=0){
            list.add(arr[idx]);
            count += singleCoinChangeCombinationSubsequence(idx+1,arr,target-arr[idx],list);
            list.remove(list.size()-1);
        }

        count += singleCoinChangeCombinationSubsequence(idx+1,arr,target,list);
        return count;
    }
    public static int singleCoinChangeCombinationSubsequence(int[] arr,int target){
        List<Integer> list = new ArrayList<>();
        return singleCoinChangeCombinationSubsequence(0,arr,target,list);
    }

    //Longest Path in Matrix - when travel is possible in all directions
    protected class Pair{
        int longestPathLength;
        String longestPath;
        Pair(int len,String path){
            this.longestPathLength = len;
            this.longestPath = path;
        }
    }
    private Pair longestPathRec(int n,int m,int sr,int sc,int dr,int dc,boolean[][] vis,int[][] dir,String[] dirs){
        if(sr==dr && sc==dc){
            return new Pair(0,"");
        }

        Pair myAns = new Pair(-1,"");
        vis[sr][sc] = true;
        for(int d=0;d<dir.length;d++){
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if(r>=0 && c>=0 && r<n && c<m && !vis[r][c]){
                Pair recAns = longestPathRec(n,m,r,c,dr,dc,vis,dir,dirs);
                if(recAns.longestPathLength+1>myAns.longestPathLength){
                    myAns.longestPathLength = recAns.longestPathLength+1;
                    myAns.longestPath = dirs[d]+recAns.longestPath;
                }
            }
        }
        vis[sr][sc] = false;
        return myAns;
    }
    public int longestPathRec(int n,int m,int sr,int sc,int dr,int dc){
        int[][] dir = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
        String[] dirs = {"E","SE","S","SW","W","NW","N","NE"};

        boolean[][] vis = new boolean[n][m];
        Pair ans = longestPathRec(n,m,sr,sc,dr,dc,vis,dir,dirs);
        System.out.println(ans.longestPath);
        return ans.longestPathLength;
    }

    //Leetcode 1091 - Some solutions don't run cause time limit exceeded
    private int shortestPathBinaryMatrix(int r,int c,int n,int[][] grid,int[][] dir){
        if(r==n-1 && c==n-1){
            return 1;
        }

        grid[r][c] = 1;
        int myAns = n*n;
        for(int d=0;d<dir.length;d++){
            int x = r + dir[d][0];
            int y = c + dir[d][1];

            if(x>=0 && y>=0 && x<n && y<n && grid[x][y]==0){
                int recAns = shortestPathBinaryMatrix(x,y,n,grid,dir);
                if(recAns+1<myAns) myAns = recAns + 1;
            }
        }
        grid[r][c] = 0;
        return myAns;
    }
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if(grid[0][0]==1 || grid[n-1][n-1]==1) return -1;
        if(n==1) return 1;
        int[][] dir = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
        int ans = shortestPathBinaryMatrix(0,0,n,grid,dir);
        return (ans==n*n) ? -1:ans;
    }

    //https://practice.geeksforgeeks.org/problems/shortest-source-to-destination-path3544/1
    int shortestDistance(int sr,int sc,int x,int y,int[][] grid,int[][] dir,int[] min,int dist){
        if(sr==x && sc==y){
            if(min[0]>dist){
                min[0] = dist;
            }
            return 0;
        }
        int n = grid.length;
        int m = grid[0].length;
        int myAns = (int)1e9;
        
        for(int d=0;d<dir.length;d++){
            int r = sr+dir[d][0];
            int c = sc+dir[d][1];
            if(r>=0 && c>=0 && r<n && c<m && grid[r][c]==1){
                grid[r][c]=0;
                int recAns = shortestDistance(r,c,x,y,grid,dir,min,dist+1);
                if(myAns>recAns+1)
                    myAns = recAns+1;
                grid[r][c] = 1;
            }
        }
        return myAns;
    }
    int shortestDistance(int N, int M, int A[][], int X, int Y) {
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        if(A[0][0]==0) return -1;
        if(A[X][Y]==0) return -1;
        int[] min = new int[1];
        min[0] = (int)1e9;
        shortestDistance(0,0,X,Y,A,dir,min,0);
        return (min[0]==(int)1e9)? -1:min[0];
    }

    //Leetcode 39
    private void combinationSum(int idx,int[] candidates,int target,List<Integer> res,List<List<Integer>> ans){
        if(target==0){
            ans.add(new ArrayList<>(res));
            return;
        }

        for(int i=idx;i<candidates.length;i++){
            if(target-candidates[i]>=0){
                res.add(candidates[i]);
                combinationSum(i,candidates,target-candidates[i],res,ans);
                res.remove(res.size()-1);
            }
        }
    }
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        combinationSum(0,candidates,target,res,ans);
        return ans;
    }

    //Leetcode 40
    private void combinationSum2(int idx,int[] candidates,int target,List<Integer> res,List<List<Integer>> ans){
        if(target==0){
            ans.add(new ArrayList<>(res));
            return;
        }

        for(int i=idx;i<candidates.length;i++){
            if(candidates[i]>target) break;
            if(i!=idx && candidates[i]==candidates[i-1]) continue;
            if(target-candidates[i]>=0){
                res.add(candidates[i]);
                combinationSum2(i+1,candidates,target-candidates[i],res,ans);
                res.remove(res.size()-1);
            }
        }
    }
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        combinationSum2(0,candidates,target,res,ans);
        return ans;
    }

    //Leetcode 216
    private void combinationSum3(int num,int k,int target,List<Integer> res,List<List<Integer>> ans){
        if(target==0 || k==0 || num>9){
            if(target==0 && k==0){
                ans.add(new ArrayList<>(res));
            }
            return;
        }

        if(target-num>=0){
            res.add(num);
            combinationSum3(num+1,k-1,target-num,res,ans);
            res.remove(res.size()-1);
        }

        combinationSum3(num+1,k,target,res,ans);

    }
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<Integer> res = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        combinationSum3(1,k,n,res,ans);
        return ans;
    }

    //Leetcode 77
    private void combine(int num,int n,int k,List<Integer> res,List<List<Integer>> ans){
        if(num>n || k==0){
            if(k==0){
                ans.add(new ArrayList<>(res));
            }
            return;
        }

        res.add(num);
        combine(num+1,n,k-1,res,ans);
        res.remove(res.size()-1);

        combine(num+1,n,k,res,ans);
        
    }
    public List<List<Integer>> combine(int n, int k) {
        List<Integer> res = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        combine(1,n,k,res,ans);
        return ans;
    }

    //Queen Combination 1D
    private static int queenCombination1D(int idx,int q,int n,List<Integer> res,List<List<Integer>> ans){
        if(idx==n || q==0){
            if(q==0){
                ans.add(new ArrayList<>(res));
                return 1;
            }
            return 0;
        }

        int count = 0;

        /* 

        for(int i=idx;i<n;i++){
            res.add(i);
            count += queenCombination1D(i+1,q-1,res,ans);
            res.remove(res.size()-1);
        }

        */
        
        res.add(idx);
        count += queenCombination1D(idx+1,q-1,n,res,ans);
        res.remove(res.size()-1);

        count += queenCombination1D(idx+1,q,n,res,ans);

        return count;
    }
    public static List<List<Integer>> queenCombination1D(int q,int n){
        if(q>n) return new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        System.out.println(queenCombination1D(0,q,n,res,ans));
        return ans;
    }

    //Queen Permutation 1D
    private static int queenPermutation1D(int q,int n,boolean[] vis,List<Integer> res,List<List<Integer>> ans){
        if(q==0){
            ans.add(new ArrayList<>(res));
            return 1;
        }

        int count = 0;
        for(int i=0;i<n;i++){
            if(!vis[i]){
                vis[i] = true;
                res.add(i);
                count += queenPermutation1D(q-1,n,vis,res,ans);
                res.remove(res.size()-1);
                vis[i] = false;
            }
        }
        return count;
    }
    public static List<List<Integer>> queenPermutation1D(int q,int n){
        if(q>n) return new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        boolean[] vis = new boolean[n];
        System.out.println(queenPermutation1D(q,n,vis,res,ans));
        return ans;
    }

    //Queen Combination 2D
    private static int queenCombination2D(int idx,int n,int m,int q,List<int[]> res,List<List<int[]>> ans){
        if(idx==n*m || q==0){
            if(q==0){
                ans.add(new ArrayList<>(res));
                return 1;
            }
            return 0;
        }

        int count = 0;
        int r = idx/m;
        int c = idx%m;
        res.add(new int[]{r,c});
        count += queenCombination2D(idx+1,n,m,q-1,res,ans);
        res.remove(res.size()-1);

        count+= queenCombination2D(idx+1,n,m,q,res,ans);
        return count;
    }
    public static List<List<int[]>> queenCombination2D(int n,int m,int q){
        if(q>n*m) return new ArrayList<>();
        List<List<int[]>> ans = new ArrayList<>();
        List<int[]> res = new ArrayList<>();
        System.out.println(queenCombination2D(0,n,m,q,res,ans));
        return ans;
    }

    //Queen Permutation 2D
    private static int queenPermutation2D(int q,int n,int m,boolean[][] vis,List<int[]> res,List<List<int[]>> ans){
        if(q==0){
            ans.add(new ArrayList<>(res));
            return 1;
        }

        int count = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(!vis[i][j]){
                    res.add(new int[]{i,j});
                    vis[i][j] = true;
                    count += queenPermutation2D(q-1,n,m,vis,res,ans);
                    res.remove(res.size()-1);
                    vis[i][j] = false;
                }
            }
        }

        return count;

    }
    public static List<List<int[]>> queenPermutation2D(int n,int m,int q){
        if(q>n*m) return new ArrayList<>();
        List<List<int[]>> ans = new ArrayList<>();
        List<int[]> res = new ArrayList<>();
        boolean[][] vis = new boolean[n][m];
        System.out.println(queenPermutation2D(q,n,m,vis,res,ans));
        return ans;
    }

    //Leetcode 17
    private void fillMap(HashMap<Integer,String> map){
        map.put(2,"abc");
        map.put(3,"def");
        map.put(4,"ghi");
        map.put(5,"jkl");
        map.put(6,"mno");
        map.put(7,"pqrs");
        map.put(8,"tuv");
        map.put(9,"wxyz");
    }
    private void letterCombinations(int idx,HashMap<Integer,String> map,String digits,List<String> ans,String res){
        if(idx==digits.length()){
            ans.add(res);
            return;
        }

        int num = digits.charAt(idx)-'0';
        String letters = map.get(num);
        for(int i=0;i<letters.length();i++){
            letterCombinations(idx+1,map,digits,ans,res+letters.charAt(i));
        }

    }
    public List<String> letterCombinations(String digits) {
        if(digits.length()==0) return new ArrayList<>();
        List<String> ans = new ArrayList<>();
        HashMap<Integer,String> map = new HashMap<>();
        fillMap(map);
        letterCombinations(0,map,digits,ans,"");
        return ans;
    }

    //nQueen 01
    private static boolean isSafeToPlaceQueen(int i,int n,int m,boolean[][] vis){
        int[][] dir = {{0,-1},{-1,0},{-1,-1},{-1,1}};
        int r = i/m;
        int c = i%m;
        for(int d=0;d<4;d++){
            for(int rad=1;rad<Math.max(n,m);rad++){
                int x = r + rad*dir[d][0];
                int y = c + rad*dir[d][1];
                if(x>=0 && y>=0 && x<n && y<m){
                    if(vis[x][y]) return false;
                }else break;
            }
        }
        return true;
    }
    private static int nQueen01(int idx,int n,int m,int q,boolean[][] vis,String ans){
        if(q==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i=idx;i<n*m;i++){
            int r = i/m;
            int c = i%m;
            if(isSafeToPlaceQueen(i,n,m,vis)){
                vis[r][c] = true;
                count += nQueen01(i+1,n,m,q-1,vis,ans + "(" + r + "," + c + ") ");
                vis[r][c] = false;
            }
        }
        return count;

    }
    public static int nQueen01(int n,int m,int q){
        boolean[][] vis = new boolean[n][m];
        return nQueen01(0,n,m,q,vis,"");
    }

    //nQueen02
    private static boolean[] row;
    private static boolean[] col;
    private static boolean[] diag;
    private static boolean[] aDiag;
    private static void toggleNQueen(int r,int c,int n){
        row[r] = !row[r];
        col[c] = !col[c];
        diag[c-r+n-1] = !diag[c-r+n-1];
        aDiag[r+c] = aDiag[r+c];
    }
    private static int nQueen02(int idx,int n,int m,int q,String ans){
        if(q==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i=idx;i<n*m;i++){
            int r = i/m;
            int c = i%m;
            if(!row[r] && !col[c] && !diag[c-r+n-1] && !aDiag[r+c]){
                toggleNQueen(r,c,n);
                count += nQueen02(i+1,n,m,q-1,ans+ "(" + r + "," + c + ") ");
                toggleNQueen(r,c,n);
            }
        }
        return count;
    }
    public static int nQueen02(int n,int m,int q){
        row = new boolean[n];
        col = new boolean[m];
        diag = new boolean[n+m-1];
        aDiag = new boolean[n+m-1];
        return nQueen02(0,n,m,q,"");
    }

    //nQueen03_Perm
    //nQueen 03 - Permutation
    /*I don't think a vis boolean array is needed as row,col,diag and aDiag is already there,
    but as in the sir's code I will let it be there */
    public static int nQueen03_Perm(int n,int m,int q,boolean[][] vis,String ans){
        if(q==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i=0;i<n*m;i++){
            int r = i/m;
            int c = i%m;
            if(vis[r][c] && !row[r] && !col[c] && !diag[c-r+n-1] && !aDiag[r+c]){
                vis[r][c] = true;
                toggleNQueen(r,c,n);
                count += nQueen03_Perm(n,m,q-1,vis,ans+"(" + r + "," + c + ") ");
                vis[r][c] = false;
                toggleNQueen(r,c,n);
            }
        }
        return count;
    }
    public static int nQueen03_Perm(int n,int m,int q){
        row = new boolean[n];
        col = new boolean[m];
        diag = new boolean[n+m-1];
        aDiag = new boolean[n+m-1];
        boolean[][] vis = new boolean[n][m];
        return nQueen03_Perm(n,m,q,vis,"");
    }

    //nQueen04
    private static void toggleQueen(int r,int c,int n){
        col[c] = !col[c];
        diag[c-r+n-1] = !diag[c-r+n-1];
        aDiag[r+c] = !aDiag[r+c];
    }
    private static int nQueen04(int r,int n,int m,int q,String ans){
        if(q==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int c=0;c<m;c++){
            if(!col[c] && !diag[c-r+n-1] && !aDiag[r+c]){
                toggleQueen(r,c,n);
                count += nQueen04(r+1,n,m,q-1,ans+"(" + r + "," + c + ") " );
                toggleQueen(r,c,n);
            }
        }

        return count;
        
    }
    public static int nQueen04(int n,int m,int q){
        col = new boolean[m];
        diag = new boolean[n+m-1];
        aDiag = new boolean[n+m-1];
        return nQueen04(0,n,m,q,"");
    }

    //nQueen using bitmasking
    private static int colBit;
    private static int diagBit;
    private static int aDiagBit;
    private static void toggleNQueenBit(int r,int c,int n){
        colBit ^= (1<<c);
        diagBit ^=  (1 << (c-r+n-1));
        aDiagBit ^= (1<< (r+c));
    }
    private static int nQueenBitMask(int r,int n,int m,int q,String ans){
        if(q==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int c=0;c<m;c++){
            if((colBit & (1<<c)) == 0 && (diagBit & (1<<(c-r+n-1))) == 0 && (aDiagBit & (1<<(r+c))) == 0){
                toggleNQueenBit(r,c,n);
                count += nQueenBitMask(r+1,n,m,q-1,ans+"(" + r + "," + c + ") ");
                toggleNQueenBit(r,c,n);
            }
        }
        return count;
    }
    public static int nQueenBitMask(int n,int m,int q){
        colBit = 0;
        diagBit = 0;
        aDiagBit = 0;
        return nQueenBitMask(0,n,m,q,"");
    }

    //Leetcode 51
    //Method 1
    class Solution_M1 {
        private boolean[] col;
        private boolean[] diag;
        private boolean[] aDiag;
        private void toggleQueen(int r,int c,int n){
            col[c] = !col[c];
            diag[c-r+n-1] = !diag[c-r+n-1];
            aDiag[r+c] = !aDiag[r+c];
        }
        private void solveNQueens(int r,int n,int q,StringBuilder rowAns,List<String> res,List<List<String>> ans){
            if(q==0){
                ans.add(new ArrayList<>(res));
                return;
            }
    
            for(int c=0;c<n;c++){
                if(!col[c] && !diag[c-r+n-1] && !aDiag[r+c]){
                    toggleQueen(r,c,n);
                    rowAns.setCharAt(c,'Q');
                    res.add(rowAns.toString());
                    rowAns.setCharAt(c,'.');
                    solveNQueens(r+1,n,q-1,rowAns,res,ans);
                    res.remove(res.size()-1);
                    toggleQueen(r,c,n);
                }
            }
        }
        public List<List<String>> solveNQueens(int n) {
            col = new boolean[n];
            diag = new boolean[n+n-1];
            aDiag = new boolean[n+n-1];
            List<List<String>> ans = new ArrayList<>();
            StringBuilder rowAns = new StringBuilder();
            for(int i=0;i<n;i++){
                rowAns.append(".");
            }
            List<String> res = new ArrayList<>();
            solveNQueens(0,n,n,rowAns,res,ans);
            return ans;
        }
    }

    //Method 2
    class Solution_M2 {
        private int col;
        private int diag;
        private int aDiag;
        private void toggleQueen(int r,int c,int n){
            col ^= (1<<c);
            diag ^= (1 << (c-r+n-1));
            aDiag ^= (1 << (r+c));
        }
        private void solveNQueens(int r,int n,int q,StringBuilder rowAns,List<String> res,List<List<String>> ans){
            if(q==0){
                ans.add(new ArrayList<>(res));
                return;
            }
    
            for(int c=0;c<n;c++){
                if((col & (1<<c)) == 0 && (diag & (1<<(c-r+n-1))) == 0 && (aDiag & (1<<(r+c))) == 0){
                    toggleQueen(r,c,n);
                    rowAns.setCharAt(c,'Q');
                    res.add(rowAns.toString());
                    rowAns.setCharAt(c,'.');
                    solveNQueens(r+1,n,q-1,rowAns,res,ans);
                    res.remove(res.size()-1);
                    toggleQueen(r,c,n);
                }
            }
        }
        public List<List<String>> solveNQueens(int n) {
            col = 0;
            diag = 0;
            aDiag = 0;
            List<List<String>> ans = new ArrayList<>();
            StringBuilder rowAns = new StringBuilder();
            for(int i=0;i<n;i++){
                rowAns.append(".");
            }
            List<String> res = new ArrayList<>();
            solveNQueens(0,n,n,rowAns,res,ans);
            return ans;
        }
    }
    

    //Leetcode 37
    class Solution_SolveSudoku{
        int[] rowBit;
        int[] colBit;
        int[][] mat;
        private void toggleSudokuBit(int r,int c,int num){
            int mask = (1<<num);
            rowBit[r] ^= mask;
            colBit[c] ^= mask;
            mat[r/3][c/3] ^= mask;
        }
        private boolean solveSudoku(int idx,List<Integer> locs,char[][] board){
            if(idx==locs.size()){
                return true;
            }
    
            int i = locs.get(idx);
            int r = i/9;
            int c = i%9;
            boolean res = false;
    
            for(int num=1;num<=9;num++){
                int mask = (1<<num);
                if((rowBit[r] & mask)==0 && (colBit[c] & mask)==0  && (mat[r/3][c/3] & mask)==0){
                    board[r][c] = (char)(num+'0');
                    toggleSudokuBit(r,c,num);
                    res = res || solveSudoku(idx+1,locs,board);
                    if(res) return true;
                    board[r][c] = '.';
                    toggleSudokuBit(r,c,num);
                }
            }
            return res;
        }
        public void solveSudoku(char[][] board) {
            rowBit = new int[9];
            colBit = new int[9];
            mat = new int[3][3];
    
            List<Integer> locs = new ArrayList<>();
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(board[i][j] != '.'){
                        int num = board[i][j] - '0';
                        toggleSudokuBit(i,j,num);
                    }else{
                        locs.add(i*9+j);
                    }
                }
            }
            solveSudoku(0,locs,board);
        }
    }

    //Leetcode 36
    class Solution_ValidSudoku{
        int[] rowBit;
        int[] colBit;
        int[][] mat;
        private void toggleSudokuBit(int r,int c,int mask){
            rowBit[r] ^= mask;
            colBit[c] ^= mask;
            mat[r/3][c/3] ^= mask;
        }
        public boolean isValidSudoku(char[][] board) {
            rowBit = new int[9];
            colBit = new int[9];
            mat = new int[3][3];
    
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(board[i][j] != '.'){
                        int num = board[i][j] - '0';
                        int mask = (1<<num);
                        if((rowBit[i] & mask) == 0 && (colBit[j] & mask) == 0 && (mat[i/3][j/3] & mask) == 0)
                            toggleSudokuBit(i,j,mask);
                        else return false;
                    }
                }
            }
    
            return true;
        }
    }

    //Crypto Arithmetic - Pepcoding - GFG
    //https://www.geeksforgeeks.org/solving-cryptarithmetic-puzzles-set-2/?ref=ml_lbp
    private static int getNumber(String s,int[] mapping){
        int res = 0;
        for(int i=0;i<s.length();i++){
            res = res*10 + mapping[s.charAt(i)-'a'];
        }
        return res;
    }
    private static int cryptoArithmetic(int idx,String str,String s1,String s2,String s3,int[] mapping,boolean[] isNumberTaken){
        if(idx==str.length()){
            int num1 = getNumber(s1,mapping);
            int num2 = getNumber(s2,mapping);
            int num3 = getNumber(s3,mapping);

            if(num1+num2==num3){
                System.out.println(num1 + "+" + num2 + "=" + num3);
                return 1;
            }
            return 0;
        }

        char ch = str.charAt(idx);
        int count = 0;
        for(int num=0;num<10;num++){
            if(!isNumberTaken[num]){
                isNumberTaken[num] = true;
                mapping[ch-'a'] = num;
                count += cryptoArithmetic(idx+1,str,s1,s2,s3,mapping,isNumberTaken);
                mapping[ch-'a'] = -1;
                isNumberTaken[num] = false;
            }
        }
        return count;

    }
    public static int cryptoArithmetic(String s1,String s2,String s3){
        int[] mapping = new int[26];
        Arrays.fill(mapping,-1);
        boolean[] isNumberTaken = new boolean[10];

        int freq = 0;
        String str = s1+s2+s3;
        for(int i=0;i<str.length();i++){
            freq |= (1<<(str.charAt(i)-'a'));
        }

        String ans = "";
        for(int i=0;i<26;i++){
            int mask = (1<<i);
            if((freq & mask) != 0){
                ans += (char)(i+'a');
            }
        }
        return cryptoArithmetic(0,ans,s1,s2,s3,mapping,isNumberTaken);

    }

    //Leetcode 139
    //Leetcode 139 - Word Break - Time Limit Exceeded for some test cases. All test cases will be passed by DP only.
    public boolean wordBreak(int idx,String s,HashSet<String> wordDictSet){
        if(idx==s.length()){
            return true;
        }

        boolean res = false;
        StringBuilder ans = new StringBuilder();
        for(int i=idx;i<s.length();i++){
            ans.append(s.charAt(i));
            if(wordDictSet.contains(ans.toString())){
                res = res || wordBreak(i+1,s,wordDictSet);
            }
            if(res) return true;
        }
        return res;
    }
    public boolean wordBreak_(String s, List<String> wordDict) {
        HashSet<String> wordDictSet = new HashSet<>();
        for(String str:wordDict){
            wordDictSet.add(str);
        }
        return wordBreak(0,s,wordDictSet);
    }

    //Leetcode 140
    private void wordBreak(int start,String s,HashSet<String> dict,List<String> ans,String sentence){
		if(start==s.length()){
			ans.add(sentence.trim());
			return;
		}

		StringBuilder sb = new StringBuilder();
		for(int i=start;i<s.length();i++){
			sb.append(s.charAt(i));
			if(dict.contains(sb.toString())){
				wordBreak(i+1,s,dict,ans,sentence+sb.toString()+" ");
			}
		}
	}
    public List<String> wordBreak(String s, List<String> wordDict) {
        HashSet<String> dict = new HashSet<>();
		for(String word:wordDict){
			dict.add(word);
		}
		List<String> ans = new ArrayList<>();
		wordBreak(0,s,dict,ans,"");
		return ans;
    }

    //https://www.hackerrank.com/challenges/crossword-puzzle/problem?isFullScreen=false
    //Crossword Puzzle - Hackerrank
    private static boolean isPossibleToPlaceH(int r,int c,String word,char[][] crossword){
        if(c+word.length()-1>=10) return false;
        
        for(int i=0;i<word.length();i++){
            if(crossword[r][c+i]!='-' && crossword[r][c+i]!=word.charAt(i)) return false;
        }
        return true;
    }
    
    private static boolean[] placeH(int r,int c,String word,char[][] crossword){
        boolean[] charLoc = new boolean[word.length()];
        
        for(int i=0;i<word.length();i++){
            if(crossword[r][c+i]=='-'){
                crossword[r][c+i] = word.charAt(i);
                charLoc[i] = true;
            }
        }
        return charLoc;
    }
    
    private static void unplaceH(int r,int c,String word,char[][] crossword,boolean[] charLoc){
        for(int i=0;i<word.length();i++){
            if(charLoc[i]){
                crossword[r][c+i] = '-';
            }
        }
    }
    
    private static boolean isPossibleToPlaceV(int r,int c,String word,char[][] crossword){
        if(r+word.length()-1>=10) return false;
        
        for(int i=0;i<word.length();i++){
            if(crossword[r+i][c]!='-' && crossword[r+i][c]!=word.charAt(i)){
                return false;
            }
        }
        return true;
    }
    
    private static boolean[] placeV(int r,int c,String word,char[][] crossword){
        boolean[] charLoc = new boolean[word.length()];
        
        for(int i=0;i<word.length();i++){
            if(crossword[r+i][c]=='-'){
                crossword[r+i][c] = word.charAt(i);
                charLoc[i] = true;
            }
        }
        return charLoc;
    }
    
    private static void unplaceV(int r,int c,String word,char[][] crossword,boolean[] charLoc){
        for(int i=0;i<word.length();i++){
            if(charLoc[i]){
                crossword[r+i][c] = '-';
            }
        }
    }
    
    private static boolean crosswordPuzzle(int idx,char[][] crossword,String[] words){
        if(idx==words.length){
            return true;
        }
        
        String word = words[idx];
        boolean res = false;
        for(int r=0;r<10;r++){
            for(int c=0;c<10;c++){
                if(crossword[r][c]=='-' || crossword[r][c]==word.charAt(0)){
                    if(isPossibleToPlaceH(r,c,word,crossword)){
                        boolean[] charLoc = placeH(r,c,word,crossword);
                        res = res || crosswordPuzzle(idx+1,crossword,words);
                        if(res) return true;
                        unplaceH(r,c,word,crossword,charLoc); 
                    }
                    
                    if(isPossibleToPlaceV(r,c,word,crossword)){
                        boolean[] charLoc = placeV(r,c,word,crossword);
                        res = res || crosswordPuzzle(idx+1,crossword,words);
                        if(res) return true;
                        unplaceV(r,c,word,crossword,charLoc);
                    }
                }
            }
        }
        
        return res;
    }
    public static List<String> crosswordPuzzle(List<String> crossword, String words) {
        char[][] crosswordArr = new char[10][];
        for(int i=0;i<10;i++){
            crosswordArr[i] = crossword.get(i).toCharArray();
        }
        String[] wordsArr = words.split(";");
        crosswordPuzzle(0,crosswordArr,wordsArr);
        for(int i=0;i<10;i++){
            crossword.set(i,String.valueOf(crosswordArr[i]));
        }
        return crossword;
    }

    //Leetcode 131
    private boolean isPalindrome(int si,int ei,String s){
        while(si<=ei){
            if(s.charAt(si++) != s.charAt(ei--)) return false;
        }
        return true;
    }
    private void partition(int idx,String s,List<String> res,List<List<String>> ans){
        if(idx==s.length()){
            ans.add(new ArrayList<>(res));
            return;
        }

        for(int i=idx;i<s.length();i++){
            if(isPalindrome(idx,i,s)){
                res.add(s.substring(idx,i+1));
                partition(i+1,s,res,ans);
                res.remove(res.size()-1);
            }
        }
    }
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        List<String> res = new ArrayList<>();
        partition(0,s,res,ans);
        return ans;
    }

    //https://www.geeksforgeeks.org/problems/m-coloring-problem-1587115620/1
    private boolean isSafeToColour(int col,int node,int[] colour,boolean[][] graph){
        for(int i=0;i<graph.length;i++){
            if(graph[node][i]==true){
                if(colour[i]==col) return false;
            }
        }
        return true;
    }
    private boolean graphColoring(int node,int m,int n,int[] colour,boolean[][] graph){
        if(node==n) return true;
        
        boolean res = false;
        for(int i=1;i<=m;i++){
            if(isSafeToColour(i,node,colour,graph)){
                colour[node] = i;
                res = res || graphColoring(node+1,m,n,colour,graph);
                if(res) return res;
                colour[node] = 0;
            }
        }
        return res;
    }
    public boolean graphColoring(boolean graph[][], int m, int n) {
        int[] colour = new int[n];
        return graphColoring(0,m,n,colour,graph);
    }

    
    public static void main(String[] args){
        int[] arr = {1,1,2,3,4,5,7,8,9};
        singleCoinChangeCombination(arr,10);
    }

}
