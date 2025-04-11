import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Recursion_practice {
    
    //Find out number of permutations in a string and print it
    public static int printPermutations(String str,String ans){
        if(str.length()==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i=0;i<str.length();i++){
            char ch = str.charAt(i);
            String ros = str.substring(0, i) + str.substring(i+1);
            count += printPermutations(ros, ans+ch);
        }
        return count;
    }
    public static int printPermutations(String str){
        return printPermutations(str, "");
    }

    //Find out number of unique permutations in a string and print it
    public static int printPermutationsUnique(String str,String ans){
        if(str.length()==0){
            System.out.println(ans);
            return 1;
        }

        boolean[] vis = new boolean[26];
        int count = 0;
        for(int i=0;i<str.length();i++){
            char ch = str.charAt(i);
            if(!vis[ch-'a']){
                vis[ch-'a'] = true;
                String ros = str.substring(0, i) + str.substring(i+1);
                count += printPermutations(ros, ans+ch);
            }
        }
        return count;
    }
    public static int printPermutationsUnique(String str){
        return printPermutationsUnique(str,"");
    }

    //Leetcode 46
    public void permute(int[] nums,List<List<Integer>> ans,List<Integer> res,boolean[] vis){
        if(res.size()==nums.length){
            ans.add(new ArrayList<>(res));
            return;
        }

        for(int i=0;i<nums.length;i++){
            if(!vis[i]){
                vis[i] = true;
                res.add(nums[i]);
                permute(nums,ans,res,vis);
                vis[i] = false;
                res.remove(res.size()-1);
            }
        }
    }
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        boolean[] vis = new boolean[nums.length];
        permute(nums,ans,res,vis);
        return ans;
    }

    //Contd. Leetcode 46 - Print all permutations and return the count
    public int printPermute(int[] nums,List<Integer> ans,boolean[] vis){
        if(ans.size()==nums.length){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i=0;i<nums.length;i++){
            if(!vis[i]){
                vis[i] = true;
                ans.add(nums[i]);
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
    public void permuteUnique(int[] nums,List<List<Integer>> ans,List<Integer> res,boolean[] vis){
        if(res.size()==nums.length){
            ans.add(new ArrayList<>(res));
            return;
        }

        int prev = -1;
        for(int i=0;i<nums.length;i++){
            if(prev!=-1 && nums[prev]==nums[i]) continue;

            if(!vis[i]){
                vis[i] = true;
                res.add(nums[i]);
                permuteUnique(nums,ans,res,vis);
                res.remove(res.size()-1);
                vis[i] = false;
                prev = i;
            }
        }
    }
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        boolean[] vis = new boolean[nums.length];
        permuteUnique(nums,ans,res,vis);
        return ans;
    }

    //Contd. leetcode 47 - Print all the unique permutations and return the count;
    public int printPermuteUnique(int[] nums,List<Integer> ans,boolean[] vis){
        if(ans.size()==nums.length){
            System.out.println(ans);
            return 1;
        }

        int prev=-1,count=0;
        for(int i=0;i<nums.length;i++){
            if(prev!=-1 && nums[prev]==nums[i]) continue;

            if(!vis[i]){
                vis[i] = true;
                ans.add(nums[i]);
                count += printPermuteUnique(nums, ans, vis);
                vis[i] = false;
                ans.remove(ans.size()-1);
                prev = i;
            }
        }
        return count;
    }
    public int printPermuteUnique(int[] nums){
        Arrays.sort(nums);
        List<Integer> ans = new ArrayList<>();
        boolean[] vis = new boolean[nums.length];
        return printPermuteUnique(nums,ans,vis);
    }

    //Flood Fill - One jump - No. of ways to reach a destination and print it
    public static int floodFill(int n,int m,int sr,int sc,int dr,int dc,boolean[][] vis,int[][] dir,String[] dirs,String ans){
        if(sr==dr && sc==dc){
            System.out.println(ans);
            return 1;
        }

        vis[sr][sc] = true;
        int count = 0;
        for(int d=0;d<dir.length;d++){
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if(r>=0 && c>=0 && r<n && c<m && !vis[r][c])
                count += floodFill(n, m, r, c, dr, dc, vis, dir, dirs, ans+dirs[d]+" ");
        }
        vis[sr][sc] = false;
        return count;

    }
    public static int floodFill(int n,int m,int sr,int sc,int dr,int dc){
        boolean[][] vis = new boolean[n][m];
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0},{-1,1},{1,1},{1,-1},{-1,-1}};
        String[] dirs = {"R","D","L","U","UR","DR","DL","UL"};
        return floodFill(n,m,sr,sc,dr,dc,vis,dir,dirs,"");
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
                    count += floodFill(n, m, r, c, dr, dc, vis, dir, dirs, ans+ " "+rad+dirs[d]);
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
    public static int printAllSubsequencesString(String str,String ans){
        if(str.length()==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        count += printAllSubsequencesString(str.substring(1), ans);
        count += printAllSubsequencesString(str.substring(1),ans+str.charAt(0));

        return count;
    }
    public static int printAllSubsequencesString(String str){
        return printAllSubsequencesString(str,"");
    }

    //Print and return count of all unique subsequences of a string
    static int count = 0;
    public static void printAllSubsequencesStringUnique(String str,String ans,int idx){
        if(idx==str.length()) return;

        boolean[] vis = new boolean[26];
        for(int i=idx;i<str.length();i++){
            char ch = str.charAt(i);
            if(!vis[ch-'a']){
                vis[ch-'a'] = true;
                System.out.println(ans+ch);
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
    public static int printMazePaths(int sr,int sc,int dr,int dc,String psf){
        if(sr==dr && sc==dc){
            System.out.println(psf);
            return 1;
        }
        
        int count = 0;
        if(sc+1<=dc)
            count += printMazePaths(sr, sc+1, dr, dc, psf+"h");
        if(sr+1<=dr && sc+1<=dc)
            count += printMazePaths(sr+1, sc+1, dr, dc, psf+"d");
        if(sr+1<=dr)
            count += printMazePaths(sr+1, sc, dr, dc, psf+"v");
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

    //https://practice.geeksforgeeks.org/problems/rat-in-a-maze-problem/1
    public static void findPath(int sr,int sc,int n,int[][] m,int[][] dir,String[] dirs,String psf,ArrayList<String> ans){
        if(sr==n-1 && sc==n-1){
            ans.add(psf);
            return;
        }
        
        m[sr][sc] = 0;
        for(int d=0;d<dir.length;d++){
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if(r>=0 && c>=0 && r<n && c<n && m[r][c]==1){
                findPath(r,c,n,m,dir,dirs,psf+dirs[d],ans);
            }
        }
        m[sr][sc] = 1;
        return;
    }
    public static ArrayList<String> findPath(int[][] m, int n) {
        ArrayList<String> ans = new ArrayList<>();
        if(m[0][0]==0) return ans;
        if(m[n-1][n-1]==0) return ans;
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        String[] dirs = {"R","D","L","U"};
        findPath(0,0,n,m,dir,dirs,"",ans);
        return ans;
    }

    //https://practice.geeksforgeeks.org/problems/rat-maze-with-multiple-jumps3852/1
    public boolean shortestDistance(int sr,int sc,int n,int[][]matrix,int[][] vis){
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

    //Coin Change - Infinite coin supply - Permutation
    public static int coinChangePermutationInfi(int[] arr,int target,List<Integer> list){
        if(target==0){
            System.out.println(list);
            return 1;
        }
        
        int count = 0;
        for(int ele:arr){
            if(target-ele>=0){
                list.add(ele);
                count += coinChangePermutationInfi(arr, target-ele, list);
                list.remove(list.size()-1);
            }
        }
        return count;
    }
    public static int coinChangePermutationInfi(int[] arr,int target){
        List<Integer> list = new ArrayList<>();
        return coinChangePermutationInfi(arr,target,list);
    }

    //Coin Change - Infinite Coin Supply - Combination
    public static int coinChangeCombinationInfi(int idx,int[] arr,int target,List<Integer> list){
        if(target==0){
            System.out.println(list);
            return 1;
        }

        int count = 0;
        for(int i=idx;i<arr.length;i++){
            if(target-arr[i]>=0){
                list.add(arr[i]);
                count += coinChangeCombinationInfi(i,arr,target-arr[i],list);
                list.remove(list.size()-1);
            }
        }
        return count;
    }
    public static int coinChangeCombinationInfi(int[] arr,int target){
        List<Integer> list = new ArrayList<>();
        return coinChangeCombinationInfi(0,arr,target,list);
    }

    //Coin Change - Single Coin - Permutation
    public static int singleCoinChangePermutation(int[] arr,int target,List<Integer> list){
        if(target==0){
            System.out.println(list);
            return 1;
        }

        int count = 0;
        int coin;
        for(int i=0;i<arr.length;i++){
            if(arr[i]>0 && target-arr[i]>=0){
                coin = arr[i];
                arr[i] = -arr[i];
                list.add(coin);
                count += singleCoinChangePermutation(arr, target-coin, list);
                list.remove(list.size()-1);
                arr[i] = -arr[i];
            }
        }
        return count;
    }
    public static int singleCoinChangePermutation(int[] arr,int target){
        List<Integer> list = new ArrayList<>();
        return singleCoinChangePermutation(arr,target,list);
    }

    //Coin Change - Single Coin - Combination
    public static int singleCoinChangeCombination(int idx,int[] arr,int target,List<Integer> list){
        if(target==0){
            System.out.println(list);
            return 1;
        }

        int count = 0;
        for(int i=idx;i<arr.length;i++){
            if(target-arr[i]>=0){
                list.add(arr[i]);
                count += singleCoinChangeCombination(i+1, arr, target, list);
                list.remove(list.size()-1);
            }
        }
        return count;
    }
    public static int singleCoinChangeCombination(int[] arr,int target){
        List<Integer> list = new ArrayList<>();
        return singleCoinChangeCombination(0,arr,target,list);
    }

    //Infinite Coin Change Permutation Subsequence
    public static int coinChangePermutationInfiSubsequence(int idx,int[] arr,int target,List<Integer> list){
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
            count += coinChangePermutationInfiSubsequence(idx, arr, target-arr[idx], list);
            list.remove(list.size()-1);
        }

        count += coinChangePermutationInfiSubsequence(idx+1, arr, target, list);
        return count;
    }
    public static int coinChangePermutationSubsequenceInfi(int[] arr,int target){
        List<Integer> list = new ArrayList<>();
        return coinChangePermutationInfiSubsequence(0, arr, target, list);
    }

    //Infinite Coin Change Combination Subsequence
    public static int coinChangeCombinationSubsequenceInfi(int idx,int[] arr,int target,List<Integer> list){
        if(idx>=arr.length || target==0){
            if(target==0){
                System.out.println(list);
                return 1;
            }
            return 0;
        }

        int count = 0;
        if(target-arr[idx]>=0){
            list.add(arr[idx]);
            count += coinChangeCombinationSubsequenceInfi(idx, arr, target-arr[idx], list);
            list.remove(list.size()-1);
        }
        
        count += coinChangeCombinationSubsequenceInfi(idx+1, arr, target, list);

        return count;
    }
    public static int coinChangeCombinationSubsequenceInfi(int[] arr,int target){
        List<Integer> list = new ArrayList<>();
        return coinChangeCombinationSubsequenceInfi(0,arr,target,list);
    }

    //Single Coin Change Combination Subsequence
    public static int singleCoinChangeCombinationSubsequence(int idx,int[] arr,int target,List<Integer> list){
        if(target==0 || idx>=arr.length){
            if(target==0){
                System.out.println(list);
                return 1;
            }
            return 0;
        }

        int count = 0;
        if(target-arr[idx]>=0){
            list.add(arr[idx]);
            count += singleCoinChangeCombinationSubsequence(idx+1, arr, target-arr[idx], list);
            list.remove(list.size()-1);
        }
        count += singleCoinChangeCombinationSubsequence(idx+1, arr, target, list);

        return count;
    }
    public static int singleCoinChangeCombinationSubsequence(int[] arr,int target){
        List<Integer> list = new ArrayList<>();
        return singleCoinChangeCombinationSubsequence(0,arr,target,list);
    }

    //Longest Path in Matrix - when travel is possible in all directions
    class Pair{
        int longestPathLength = 0;
        String longestPath = "";
        Pair(int longestPathLength,String longestPath){
            this.longestPathLength = longestPathLength;
            this.longestPath = longestPath;
        }
    }
    public Pair longestPathRec(int n,int m,int sr,int sc,int dr,int dc,int[][] dir,String[] dirs,boolean[][] vis){
        if(sr==dr && sc==dc){
            return new Pair(0,"");
        }

        Pair myAns = new Pair(-1,"");
        vis[sr][sc] = true;
        for(int d=0;d<dir.length;d++){
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if(r>=0 && c>=0 && r<n && c<m && !vis[r][c]){
                Pair recAns = longestPathRec(n, m, r, c, dr, dc, dir, dirs, vis);
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
        Pair ans = longestPathRec(n, m, sr, sc, dr, dc, dir, dirs, vis);
        System.out.println(ans.longestPath);
        return ans.longestPathLength;
    }
    
    //Leetcode 1091 - Some solutions don't run cause time limit exceeded
    public int shortestPathBinaryMatrix(int sr,int sc,int n,int[][] grid,int[][] dir,int[] min,int blocks){
        if(sr==n-1 && sc==n-1){
            if(min[0]>blocks){
                min[0] = blocks;
            }
            return 1;
        }
        
        grid[sr][sc] = 1;
        int myAns = n*n;
        for(int d=0;d<dir.length;d++){
            int r = sr+dir[d][0];
            int c = sc+dir[d][1];

            if(r>=0 && c>=0 && r<n && c<n && grid[r][c]==0){
                int recAns = shortestPathBinaryMatrix(r,c,n,grid,dir,min,blocks+1);
                if(recAns+1<myAns)
                    myAns = recAns+1;
            }
        }
        grid[sr][sc] = 0;
        return myAns;
    }
    public int shortestPathBinaryMatrix(int[][] grid) {
        int[][] dir = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
        if(grid[0][0]==1) return -1;
        int[] min = new int[1];
        min[0] = (int)1e9;
        int n = grid.length;
        shortestPathBinaryMatrix(0,0,n,grid,dir,min,1);
        return (min[0]==(int)1e9) ? -1:min[0];
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
    public void combinationSum(int idx,int[] candidates,int target,List<Integer> res,List<List<Integer>> ans){
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
    public void combinationSum2(int idx,int[] candidates,int target,List<Integer> res,List<List<Integer>> ans){
        if(target==0){
            ans.add(new ArrayList<>(res));
            return;
        }

        int prev = -1;
        for(int i=idx;i<candidates.length;i++){
            if(target-candidates[i]>=0 && prev!=candidates[i]){
                res.add(candidates[i]);
                combinationSum2(i+1,candidates,target-candidates[i],res,ans);
                res.remove(res.size()-1);
                prev = candidates[i];
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
    public void combinationSum3(int idx,int k,int n,List<Integer> res,List<List<Integer>> ans){
        if(n==0 || k<=0){
            if(n==0 && k==0)
                ans.add(new ArrayList<>(res));
            return;
        }

        for(int i=idx;i<10;i++){
            if(n-i>=0){
                res.add(i);
                combinationSum3(i+1,k-1,n-i,res,ans);
                res.remove(res.size()-1);
            }
            else break;
        }
    }
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        combinationSum3(1,k,n,res,ans);
        return ans;
    }

    //Leetcode 77
    public void combinations(int idx,int n,int k,List<Integer> res,List<List<Integer>> ans){
        if(k<=0 || idx>n){
            if(k==0)
                ans.add(new ArrayList<>(res));
            return;
        }

        res.add(idx);
        combinations(idx+1,n,k-1,res,ans);
        res.remove(res.size()-1);

        combinations(idx+1,n,k,res,ans);
    }
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        combinations(1,n,k,res,ans);
        return ans;
    }

    //Queen Combination 1D
    public static int queenCombination1D(int idx,int q,int n,List<Integer> res,List<List<Integer>> ans){
        if(q==0){
            ans.add(new ArrayList<>(res));
            return 1;
        }

        int count = 0;
        for(int i=idx;i<n;i++){
            res.add(i);
            count += queenCombination1D(i+1, q-1, n, res, ans);
            res.remove(res.size()-1);
        }

        return count;
    }
    public static List<List<Integer>> queenCombination1D(int q,int n){
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        System.out.println(queenCombination1D(0,q,n,res,ans));
        return ans;
    }

    //Queen Permutation 1D
    public static int queenPermutation1D(int q,int n,boolean[] vis,List<Integer> res,List<List<Integer>> ans){
        if(q==0){
            ans.add(new ArrayList<>(res));
            return 1;
        }

        int count = 0;
        for(int i=0;i<n;i++){
            if(q-1>=0 && !vis[i]){
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
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        boolean[] vis = new boolean[n];
        System.out.println(queenPermutation1D(q,n,vis,res,ans));
        return ans;
    }

    //Queen Combination 2D
    public static int queenCombination2D(int idx,int n,int m,int q,List<int[]> res,List<List<int[]>> ans){
        if(q==0){
            ans.add(new ArrayList<>(res));
            return 1;
        }

        int count = 0;
        for(int i=idx;i<n*m;i++){
            int r = i/m;
            int c = i%m;
            res.add(new int[]{r,c});
            count += queenCombination2D(i+1, n, m, q-1, res, ans);
            res.remove(res.size()-1);
        }
        return count;
    }
    public static List<List<int[]>> queenCombination2D(int n,int m,int q){
        List<List<int[]>> ans = new ArrayList<>();
        List<int[]> res = new ArrayList<>();
        System.out.println(queenCombination2D(0, n, m, q, res, ans));
        return ans;
    }

    //Queen Permutation 2D
    public static int queenPermutation2D(int q,int n,int m,boolean[]vis,List<int[]> res,List<List<int[]>> ans){
        if(q==0){
            ans.add(new ArrayList<>(res));
            return 1;
        }

        int count = 0;
        for(int i=0;i<n*m;i++){
            int r = i/m;
            int c = i%m;
            if(!vis[i]){
                vis[i] = true;
                res.add(new int[]{r,c});
                count += queenPermutation2D(q-1, n, m, vis, res, ans);
                res.remove(res.size()-1);
                vis[i] = false;
            }
        }
        return count;
    }
    public static List<List<int[]>> queenPermutation2D(int n,int m,int q){
        List<int[]> res = new ArrayList<>();
        List<List<int[]>> ans = new ArrayList<>();
        boolean[] vis = new boolean[n*m];
        System.out.println(queenPermutation2D(q,n,m,vis,res,ans));
        return ans;
    }

    //Leetcode 17
    public String getLetters(char num){
        String ans;
        switch(num){
            case '2':
                ans = "abc";
                break;

            case '3':
                ans = "def";
                break;

            case '4':
                ans = "ghi";
                break;

            case '5':
                ans = "jkl";
                break;

            case '6':
                ans = "mno";
                break;

            case '7':
                ans = "pqrs";
                break;

            case '8':
                ans = "tuv";
                break;

            case '9':
                ans = "wxyz";
                break; 
            
            default:
                ans = "";
        }
        return ans;
    }
    public void letterCombinations(String digits,StringBuilder path,int idx,List<String> ans){
        if(path.length()==digits.length()){
            ans.add(path.toString());
            return;
        }

        String letters = getLetters(digits.charAt(idx));
        for(int i=0;i<letters.length();i++){
            char ch = letters.charAt(i);
            path.append(ch);
            letterCombinations(digits,path,idx+1,ans);
            path.deleteCharAt(path.length()-1);
        }
    }
    public List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();
        if(digits.length()==0) return ans;
        letterCombinations(digits,new StringBuilder(),0,ans);
        return ans;
    }

    //nQueen 01
    public static boolean isSafeToPlaceQueen(int i,int n,int m,boolean[] vis){
        int[][] dir = {{-1,0},{0,-1},{-1,-1},{-1,1}};

        for(int d=0;d<dir.length;d++){
            for(int rad=1;rad<Math.max(n,m);rad++){
                int r = i/m + rad*dir[d][0];
                int c = i%m + rad*dir[d][1];
                if(r>=0 && c>=0 && r<n && c<m)
                    if(vis[r*m+c]) return false;
                else break;
            }
        }
        return true;
    }
    public static int nQueen01(int idx,int n,int m,int q,boolean[] vis,String ans){
        if(q==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i=idx;i<n*m;i++){
            int r = i/m;
            int c = i%m;
            if(isSafeToPlaceQueen(i,n,m,vis)){
                vis[i] = true;
                count += nQueen01(i+1,n,m,q-1,vis,ans+"(" + r + "," + c + ") ");
                vis[i] = false;
            }
        }
        return count;
    }
    public static int nQueen01(int n,int m,int q){
        boolean[] vis = new boolean[n*m];
        return nQueen01(0,n,m,q,vis,"");
    }

    //nQueen02

    static boolean[] row;
    static boolean[] col;
    static boolean[] diag;
    static boolean[] aDiag;

    public static void toggleNQueen(int r,int c,int n,int m){
        row[r] = !row[r];
        col[c] = !col[c];
        diag[c-r+n-1] = !diag[c-r+n-1];
        aDiag[r+c] = !aDiag[r+c];
    }
    public static int nQueen02(int idx,int n,int m,int q,String ans){
        if(q==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i=idx;i<n*m;i++){
            int r = i/m;
            int c = i%m;
            if(!row[r] && !col[c] && !diag[c-r+n-1] && !aDiag[r+c]){
                toggleNQueen(r,c,n,m);
                count += nQueen02(i+1,n,m,q-1,ans+"(" + r + "," + c +") ");
                toggleNQueen(r,c,n,m);
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

    //nQueen 03 - Permutation
    /*I don't think a vis boolean array is needed as row,col,diag and aDiag is already there,
    but as in the sir's code I will let it be there */
    public static int nQueen03_Perm(int n,int m,int q,boolean[][] vis,String ans){
        if(q==0){
            //System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i=0;i<n*m;i++){
            int r = i/m;
            int c = i%m;
            if(vis[r][c] && !row[r] && !col[c] && !diag[c-r+n-1] && !aDiag[r+c]){
                vis[r][c] = true;
                toggleNQueen(r,c,n,m);
                count += nQueen03_Perm(n,m,q-1,vis,ans+"(" + r + "," + c + ")");
                vis[r][c] = false;
                toggleNQueen(r,c,n,m);
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
    public static void toggleQueen(int r,int c,int n,int m){
        col[c] = !col[c];
        diag[c-r+n-1] = !diag[c-r+n-1];
        aDiag[r+c] = !aDiag[r+c];
    }
    public static int nQueen04(int r,int n,int m,int q,String ans){
        if(q==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int c=0;c<m;c++){
            if(!col[c] && !diag[c-r+n-1] && !aDiag[r+c]){
                toggleQueen(r,c,n,m);
                count += nQueen04(r+1,n,m,q-1,ans+"(" + r + "," + c + ")");
                toggleQueen(r,c,n,m);
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

    //nQueen using Bit Masking
    int colBit;
    int diagBit;
    int aDiagBit;
    public void toggleNQueenBit(int r,int c,int n,int m){
        colBit ^= (1<<c);
        diagBit ^= (1 << (c-r+n-1));
        aDiagBit ^= (1 << (r+c));
    }
    public int nQueenBitMask(int r,int n,int m,int q,String ans){
        if(q==0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int c=0;c<m;c++){
            if((colBit & (1<<c)) == 0 && (diagBit & (1<<(c-r+n-1))) == 0 && (diagBit & (1 << (r+c))) == 0){
                toggleNQueenBit(r, c, n, m);
                count += nQueenBitMask(r+1, n, m, q-1, ans + "(" + r + "," + c + ") ");
                toggleNQueenBit(r,c,n,m);
            }
        }
        return count;
    }
    public int nQueenBitMask(int n,int m,int q){
        this.colBit = 0;
        this.diagBit = 0;
        this.aDiagBit = 0;
        return nQueenBitMask(0,n,m,q,"");
    }

    //Leetcode 51
    //Method 1
    public void toggleQueen(int r,int c,int n){
        col[c] = !col[c];
        diag[c-r+n-1] = !diag[c-r+n-1];
        aDiag[r+c] = !aDiag[r+c];
    }
    public void solveNQueens01(int r,int q,int n,StringBuilder rowAns,List<String> res,List<List<String>> ans){
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
                solveNQueens01(r+1,q-1,n,rowAns,res,ans);
                res.remove(res.size()-1);
                toggleQueen(r,c,n);
            }
        }
        return;
    }
    public List<List<String>> solveNQueens01(int n) {
        col = new boolean[n];
        diag = new boolean[n+n-1];
        aDiag = new boolean[n+n-1];
        List<List<String>> ans = new ArrayList<>();
        StringBuilder rowAns = new StringBuilder();
        for(int i=0;i<n;i++)
        rowAns.append(".");
        List<String> res = new ArrayList<>();
        solveNQueens01(0,n,n,rowAns,res,ans);
        return ans;
    }

    //Method 2
    public void solveNQueens(int r,int q,int n,StringBuilder rowAns,List<String> res,List<List<String>> ans){
        if(q==0){
           ans.add(new ArrayList<>(res));
           return;
        }

        for(int c=0;c<n;c++){
            if((colBit & (1<<c)) == 0 && (diagBit & (1<<(c-r+n-1))) == 0 && (aDiagBit & (1 << (r+c))) == 0){
                toggleNQueenBit(r,c,n,n);
                rowAns.setCharAt(c,'Q');
                res.add(rowAns.toString());
                rowAns.setCharAt(c,'.');
                solveNQueens(r+1,q-1,n,rowAns,res,ans);
                res.remove(res.size()-1);
                toggleNQueenBit(r,c,n,n);
            }
        }
        return;
    }
    public List<List<String>> solveNQueens(int n) {
        colBit = 0;
        diagBit = 0;
        aDiagBit = 0;
        List<List<String>> ans = new ArrayList<>();
        StringBuilder rowAns = new StringBuilder();
        for(int i=0;i<n;i++)
        rowAns.append(".");
        List<String> res = new ArrayList<>();
        solveNQueens(0,n,n,rowAns,res,ans);
        return ans;
    }

    //Leetcode 37
    class Solution {
        int[] rowBit;
        int[] colBit;
        int[][] mat;
        public void toggleSudokuBit(int r,int c,int num){
            int mask = (1<<num);
            rowBit[r] ^= mask;
            colBit[c] ^= mask;
            mat[r/3][c/3] ^= mask;
        }
        public boolean solveSudoku(int idx,ArrayList<Integer> locs,char[][] board){
            if(idx==locs.size()){
                return true;
            }
    
            int r = locs.get(idx)/9;
            int c = locs.get(idx)%9;
            boolean res = false;
    
            for(int num=1;num<=9;num++){
                int mask = (1<<num);
                if(((rowBit[r] & mask) == 0)  && ((colBit[c] & mask) ==0) && ((mat[r/3][c/3] & mask) == 0)){
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
            
            ArrayList<Integer> locs = new ArrayList<>();
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(board[i][j] != '.'){
                        int num = board[i][j]-'0';
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
    public void toggleSudokuBit(int r,int c,int mask,int[] row,int[] col,int[][] mat){
        row[r] ^= mask;
        col[c] ^= mask;
        mat[r/3][c/3] ^= mask;
    }
    public boolean isValidSudoku(char[][] board) {
        int[] row = new int[9];
        int[] col = new int[9];
        int[][] mat = new int[9][9];

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j] != '.'){
                    int num = board[i][j] - '0';
                    int mask = (1<<num);
                    if((row[i] & mask) == 0  && (col[j] & mask) == 0 && (mat[i/3][j/3] & mask) == 0)
                        toggleSudokuBit(i,j,mask,row,col,mat);
                    else return false;
                }
            }
        }

        return true;
    }

    //Crypto Arithmetic - Pepcoding
    public static int getNumber(String str,int[] mapping){
        int res = 0;
        for(int i=0;i<str.length();i++){
            res = res*10 + mapping[str.charAt(i)-'a'];
        }
        return res;
    }
    public static int cryptoArithmetic(int idx,String str,String s1,String s2,String s3,int[] mapping,boolean[] isNumberTaken){
        if(idx==str.length()){
            int num1 = getNumber(s1,mapping);
            int num2 = getNumber(s2, mapping);
            int num3 = getNumber(s3, mapping);

            if(num1+num2==num3){
                System.out.println(num1 + "+" + num2 + "=" + num3);
                return 1;
            }
            return 0;
        }

        int count = 0;
        char ch = str.charAt(idx);
        for(int num=0;num<10;num++){
            if(!isNumberTaken[num]){
                isNumberTaken[num] = true;
                mapping[ch-'a'] = num;
                count += cryptoArithmetic(idx+1, str, s1, s2, s3, mapping, isNumberTaken);
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
            if((freq & mask)!=0){
                ans += (char)(i+'a');
            }
        }
        return cryptoArithmetic(0,ans,s1,s2,s3,mapping,isNumberTaken);
    }

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
    public boolean wordBreak(String s, List<String> wordDict) {
        HashSet<String> wordDictSet = new HashSet<>();
        for(String str:wordDict){
            wordDictSet.add(str);
        }
        return wordBreak(0,s,wordDictSet);
    }

    //https://www.hackerrank.com/challenges/crossword-puzzle/problem?isFullScreen=false
    //Crossword Puzzle - Hackerrank
    public static boolean isPossibleToPlaceH(int r,int c,String word,char[][] crosswordArr){
        if(c+word.length()-1>=10) return false;
        
        for(int i=0;i<word.length();i++){
            if(crosswordArr[r][c+i] != '-' && crosswordArr[r][c+i] != word.charAt(i)) return false;
        }
        
        return true;
    }
    public static boolean[] placeH(int r,int c,String word,char[][] crosswordArr){
        boolean[] charLoc = new boolean[word.length()];
        
        for(int i=0;i<word.length();i++){
            if(crosswordArr[r][c+i]=='-'){
                crosswordArr[r][c+i] = word.charAt(i);
                charLoc[i] = true;
            }
        } 
        return charLoc;
    }
    public static void unplaceH(int r,int c,String word,char[][] crosswordArr,boolean[] charLoc){
        
        for(int i=0;i<word.length();i++){
            if(charLoc[i]){
                crosswordArr[r][c+i] = '-';
            }
        }
    }
    public static boolean isPossibleToPlaceV(int r,int c,String word,char[][] crosswordArr){
        if(r+word.length()-1 >= 10) return false;
        
        for(int i=0;i<word.length();i++){
            if(crosswordArr[r+i][c] != '-' && crosswordArr[r+i][c] != word.charAt(i))
                return false;
        }
        return true;
    }
    public static boolean[] placeV(int r,int c,String word,char[][] crosswordArr){
        boolean[] charLoc = new boolean[word.length()];
        
        for(int i=0;i<word.length();i++){
            if(crosswordArr[r+i][c]=='-'){
                crosswordArr[r+i][c] = word.charAt(i);
                charLoc[i] = true;
            }
        }
        return charLoc;
    }
    public static void unplaceV(int r,int c,String word,char[][] crosswordArr,boolean[] charLoc){
        
        for(int i=0;i<word.length();i++){
            if(charLoc[i]){
                crosswordArr[r+i][c] = '-';
            }
        }
    }
    public static boolean crosswordPuzzle(int idx,char[][] crosswordArr,String[] wordsArr){
        if(idx==wordsArr.length){
            return true;
        }
        
        String word = wordsArr[idx];
        boolean res = false;
        for(int r=0;r<10;r++){
            for(int c=0;c<10;c++){
                if(crosswordArr[r][c]=='-' || crosswordArr[r][c]==word.charAt(0)){
                    if(isPossibleToPlaceH(r,c,word,crosswordArr)){
                        boolean[] charLoc = placeH(r,c,word,crosswordArr);
                        res = res || crosswordPuzzle(idx+1,crosswordArr,wordsArr);
                        if(res) return true;
                        unplaceH(r,c,word,crosswordArr,charLoc);
                    }
                    
                    if(isPossibleToPlaceV(r,c,word,crosswordArr)){
                        boolean[] charLoc = placeV(r,c,word,crosswordArr);
                        res = res || crosswordPuzzle(idx+1,crosswordArr,wordsArr);
                        if(res) return true;
                        unplaceV(r,c,word,crosswordArr,charLoc);
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

    public static void main(String[] args){
        System.out.println(nQueen02(7, 7, 7));
        System.out.println("Permutation");
        System.out.println(nQueen03_Perm(7,7,7));
    }
}
