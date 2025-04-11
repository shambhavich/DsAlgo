import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class StriverSDE_Sheet{

    //----------------------------ARRAYS--------------------------------------------

    //Leetcode 73
    public void setZeroes(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        boolean row = false, col = false;

        for(int i=0;i<n;i++){
            if(matrix[i][0] == 0) { 
                row = true;
                break;
            }
        }
        
        for(int i=0;i<m;i++){
            if(matrix[0][i] == 0) { 
                col = true;
                break;
            }
        }
    
        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                if(matrix[i][j]==0){
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for(int i=1;i<n;i++){
            if(matrix[i][0] == 0){
                for(int j=1;j<m;j++) matrix[i][j] = 0;
            }
        }

        for(int j=1;j<m;j++){
            if(matrix[0][j] == 0){
                for(int i=1;i<n;i++) matrix[i][j] = 0;
            }
        }


        if(row){
            for(int i=0;i<n;i++) 
                matrix[i][0] = 0;
        }

        if(col){
            for(int j=0;j<m;j++) 
                matrix[0][j] = 0;
        }

    }

    //Leetcode 118
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();

        for(int i=0;i<numRows;i++){
            ans.add(new ArrayList<>());
            List<Integer> temp = ans.get(i);
            if(i==0){
                temp.add(1);
            }
            else if(i==1){
                temp.add(1);
                temp.add(1);
            }else{
                List<Integer> prev = ans.get(i-1);
                for(int j=0;j<=i;j++){
                    if(j==0 || j==i){
                        temp.add(1);
                        continue;
                    }

                    temp.add(prev.get(j-1)+prev.get(j));
                }
            }
        }

        return ans;
    }

    //Leetcode 31
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        if(n==1) return;

        int idx = -1;
        for(int i=n-2;i>=0;i--){
            if(nums[i]<nums[i+1]){
                idx = i;
                break;
            }
        }

        if(idx==-1) {
            Arrays.sort(nums);
            return;
        }

        for(int i=n-1;i>idx;i--){
            if(nums[idx]<nums[i]){
                int temp = nums[idx];
                nums[idx] = nums[i];
                nums[i] = temp;
                break;
            }
        }

        int i = idx+1, j = n-1;
        int temp;
        while(i<j){
            temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
        return;
    }

    //Leetcode 53
    public int maxSubArray(int[] nums) {
        int csum = 0,gsum = -(int)1e9;

        for(int ele:nums){
            csum = Math.max(ele,csum+ele);
            gsum = Math.max(csum,gsum);
        }
        return gsum;
    }

    //Leetcode 75
    public void sortColors(int[] nums) {
        int n = nums.length;

        int low=-1,mid=0,high=n;

        while(mid<high){
            if(nums[mid]==0){
                nums[mid] = nums[++low];
                nums[low] = 0;
                mid++;
            }else if(nums[mid]==1){
                mid++;
            }
            else if(nums[mid]==2){
                nums[mid] = nums[--high];
                nums[high] = 2;
            }
        }

    }

    //Leetcode 121
    public int maxProfit(int[] prices) {
        int Ti0 = 0;
        int Ti1 = -(int)1e8;

        for(int ele:prices){
            Ti0 = Math.max(Ti0,Ti1+ele);
            Ti1 = Math.max(Ti1,0-ele);
        }
        return Ti0;
    }

    //Leetcode 48
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        for(int i=0;i<n;i++){
            for(int j=i;j<n;j++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        int i,j;
        for(int[] mat:matrix){
            i = 0;
            j = n-1;
            while(i<j){
                int temp = mat[i];
                mat[i] = mat[j];
                mat[j] = temp;
                i++;
                j--;
            }
        }
    }

    //Leetcode 56
    public int[][] merge(int[][] intervals) {
        int n = intervals.length;

        Arrays.sort(intervals,(a,b) ->{
            return a[0] - b[0];
        });

        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(intervals[0][0],intervals[0][1]));
        int i=1;
        while(i<n){
            if(list.get(list.size()-1).get(1) >= intervals[i][0]){
                list.get(list.size()-1).set(1, Math.max(intervals[i][1],list.get(list.size()-1).get(1)));
            }else{
                list.add(Arrays.asList(intervals[i][0],intervals[i][1]));
            }
            i++;
        }

        int size = list.size();
        int[][] ans = new int[size][2];
        for(i=0;i<size;i++){
            ans[i] = new int[]{list.get(i).get(0),list.get(i).get(1)};
        }
        return ans;
    }

    //Leetcode 88
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int last = m + n - 1;

        while(m>0 && n>0){
            if(nums1[m-1]>nums2[n-1]){
                nums1[last] = nums1[m-1];
                m--;
            }else{
                nums1[last] = nums2[n-1];
                n--;
            }
            last--;
        }

        while(n>0){
            nums1[last] = nums2[n-1];
            last--;
            n--;
        }
    }

    //https://www.interviewbit.com/problems/repeat-and-missing-number-array/
    public int[] repeatedNumber(final int[] A) {
        int n = A.length;
        int xor = 0;
        for(int i=0;i<n;i++){
            xor = xor ^ A[i];
            xor = xor ^ (i+1);
        }
        
        int number = (xor & ~(xor-1));
        
        int zero = 0;
        int one = 0;
        
        for(int i=0;i<n;i++){
            if((number & A[i]) != 0){
                one  = one ^ A[i];
            }else{
                zero = zero ^ A[i];
            }
            
            if(((i+1) & number) != 0){
                one = one ^ (i+1);
            }else{
                zero = zero ^ (i+1);
            }
        }
        
        int count = 0;
        for(int ele: A){
            if(zero == ele) count++;
        }
        
        if(count==2) return new int[]{zero,one};
        return new int[]{one,zero};
    }

    //https://www.geeksforgeeks.org/problems/inversion-of-array-1587115620/1
    private static long inversionAcrossArray(int l,int mid,int r,long[] sortedArray,long[] arr){
        int lsi = l;
        int lei = mid;
        int rsi = mid+1;
        int rei = r;
        long count = 0;
        int k = 0;
        while(lsi<=lei && rsi<=rei){
            if(arr[lsi]>arr[rsi]){
                count += lei - lsi + 1;
                sortedArray[k++] = arr[rsi++];
            }else{
                sortedArray[k++] = arr[lsi++];
            }
        }

        while(lsi<=lei) sortedArray[k++] = arr[lsi++];
        while(rsi<=rei) sortedArray[k++] = arr[rsi++];

        k = 0;
        int i = l;
        while(i<=r){
            arr[i++] = sortedArray[k++];
        }

        return count;
    }
    private static long inversionCount(int l,int r,long[] sortedArray,long[] arr){
        if(l>=r) return 0;
        int mid = (r-l)/2 + l;

        long L = inversionCount(l,mid,sortedArray,arr);
        long R = inversionCount(mid+1,r,sortedArray,arr);

        return (L + R + inversionAcrossArray(l,mid,r,sortedArray,arr));
    }
    static long inversionCount(long arr[], int n) {
        if(n<=1) return 0;
        long[] sortedArr = new long[n];
        return inversionCount(0,n-1,sortedArr,arr);
    }

    //Leetcode 74
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int si = 0, ei = n*m-1;

        int mid,ele;

        while(si<=ei){
            mid = (ei-si)/2 + si;

            ele = matrix[mid/m][mid%m];

            if(target==ele) return true;
            else if(target<ele) ei = mid-1;
            else si = mid+1;
        }
        return false;
    }

    //Leetcode 50
    public double myPow(double x, int n) {
        double ans = 1.0;
        long p = n;
        if(p<0) p = -1 * p;
        
        while(p>0){
            if(p%2==1){
                ans = ans * x;
                p = p-1;
            }else{
                x = x * x;
                p = p/2;
            }
        }

        if(n<0) ans = (double)(1.0) / (double)(ans);
        return ans;
    }

    //Leetcode 169
    public int majorityElement(int[] nums) {
        int count = 0;
        int majorityEle = 0;

        for(int ele:nums){
            if(count==0){
                count++;
                majorityEle = ele;
            }else if(ele==majorityEle) count++;
            else count--;
        }

        return majorityEle;
    }

    //Leetcode 229
    public List<Integer> majorityElement_(int[] nums) {
        int n = nums.length;
        int count1 = 0,count2 = 0;
        int ele1 = Integer.MIN_VALUE;
        int ele2 = Integer.MIN_VALUE;

        for(int ele:nums){
            if(count1==0 && ele!=ele2){
                count1 = 1;
                ele1 = ele;
            }else if(count2==0 && ele!=ele1){
                count2 = 1;
                ele2 = ele;
            }else if(ele1==ele) count1++;
            else if(ele2==ele) count2++;
            else{
                count1--;
                count2--;
            }
        }

        count1 = 0;
        count2 = 0;
        for(int ele:nums){
            if(ele==ele1) count1++;
            if(ele==ele2) count2++;
        }

        List<Integer> ans = new ArrayList<>();
        if(count1 > (n/3)) ans.add(ele1);
        if(count2 > (n/3)) ans.add(ele2);

        return ans;
    }

    //Leetcode 62
    private int uniquePaths(int i,int j,int m,int n,int[][] dp){
        if(i==m-1 && j==n-1){
            return dp[i][j] = 1;
        }

        if(dp[i][j]!=-1) return dp[i][j];

        int count = 0;
        if(i+1<m) count += uniquePaths(i+1,j,m,n,dp);
        if(j+1<n) count += uniquePaths(i,j+1,m,n,dp);

        return dp[i][j] = count;
    }
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for(int[] d:dp) Arrays.fill(d,-1);
        return uniquePaths(0,0,m,n,dp);
    }

    //Leetcode 493
    private void merge(int l,int mid,int r,int[] nums){
        int lsi = l;
        int rsi = mid + 1;
        ArrayList<Integer> list = new ArrayList<>();
        while(lsi<=mid && rsi<=r){
            if(nums[lsi] < nums[rsi]){
                list.add(nums[lsi++]);
            }else list.add(nums[rsi++]);
        }

        while(lsi<=mid) list.add(nums[lsi++]);
        while(rsi<=r) list.add(nums[rsi++]);

        for(int i=l;i<=r;i++){
            nums[i] = list.get(i-l);
        }
    }
    private int reversePairsAcrossArr(int l,int mid,int r,int[] nums){
        int lsi = l;
        int rsi = mid + 1;

        int count = 0;
        long var = 0;
        while(lsi<=mid && rsi<=r){
            var = (long)2*nums[rsi];
            if(nums[lsi] > var) {
                count += mid - lsi + 1;
                rsi++;
            }
            else{
                lsi++;
            }
        }

        return count;
    }
    private int reversePairs(int l,int r,int[] nums){
        if(l>=r) return 0;
        int mid = (r-l)/2 + l;

        int count = 0;
        count += reversePairs(l,mid,nums);
        count += reversePairs(mid+1,r,nums);
        count += reversePairsAcrossArr(l,mid,r,nums);
        merge(l,mid,r,nums);

        return count;
    }
    public int reversePairs(int[] nums) {
        int n = nums.length;
        return reversePairs(0,n-1,nums);
    }

    //Leetcode 1
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> hm = new HashMap<>();

        for(int i=0;i<nums.length;i++){
            int diff = target - nums[i];
            if(hm.containsKey(diff)){
                return new int[]{hm.get(diff),i};
            }
            hm.put(nums[i],i);
        }
        return new int[]{};
    }

    

    //----------------------------GRAPH--------------------------------------------
    //Leetcode 133
    class Solution_CloneGraph{
        class Node {
            public int val;
            public List<Node> neighbors;
            public Node() {
                val = 0;
                neighbors = new ArrayList<Node>();
            }
            public Node(int _val) {
                val = _val;
                neighbors = new ArrayList<Node>();
            }
            public Node(int _val, ArrayList<Node> _neighbors) {
                val = _val;
                neighbors = _neighbors;
            }
        }
        private HashMap<Node,Node> map;
        private Node cloneGraphDFS(Node node){
            Node newNode = new Node(node.val);
            map.put(node,newNode);

            for(Node nbr:node.neighbors){
                if(!map.containsKey(nbr)){
                    newNode.neighbors.add(cloneGraphDFS(nbr));
                }else{
                    newNode.neighbors.add(map.get(nbr));
                }
            }

            return newNode;
        }
        public Node cloneGraph(Node node) {
            if(node==null) return null;
            map = new HashMap<>();
            return cloneGraphDFS(node);
        }
    }

    //https://www.geeksforgeeks.org/problems/depth-first-traversal-for-a-graph/1
    private void dfs(int u,boolean[] vis,ArrayList<Integer> ans,ArrayList<ArrayList<Integer>> adj){
        vis[u] = true;
        ans.add(u);
        for(int v:adj.get(u)){
            if(!vis[v]){
                dfs(v,vis,ans,adj);
            }
        }
    }
    public ArrayList<Integer> dfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis = new boolean[V];
        ArrayList<Integer> ans = new ArrayList<>();
        dfs(0,vis,ans,adj);
        return ans;
    }

    //https://www.geeksforgeeks.org/problems/bfs-traversal-of-graph/1
    public ArrayList<Integer> bfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] vis = new boolean[V];
        
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        ArrayList<Integer> ans = new ArrayList<>();
        
        while(!q.isEmpty()){
            int u = q.remove();
            if(vis[u]) continue;
            vis[u] = true;
            ans.add(u);
            for(int v:adj.get(u)){
                if(!vis[v]){
                    q.add(v);
                }
            }
        }
        return ans;
    }

    //



    //----------------------------RECURSION--------------------------------------------

    //https://www.geeksforgeeks.org/problems/subset-sums2234/1
    private void subsetSums(int i,int sum,ArrayList<Integer> ans,int n,ArrayList<Integer> arr){
        if(i==n){
            ans.add(sum);
            return;
        }
        
        subsetSums(i+1,sum,ans,n,arr);
        subsetSums(i+1,sum+arr.get(i),ans,n,arr);
        
    }
    ArrayList<Integer> subsetSums(ArrayList<Integer> arr, int n) {
        ArrayList<Integer> ans = new ArrayList<>();
        subsetSums(0,0,ans,n,arr);
        return ans;
    }

    //Leetcode 78
    private void subsets(int i,int[] nums,List<Integer> res,List<List<Integer>> ans){
        if(i==nums.length){
            ans.add(new ArrayList<>(res));
            return;
        }

        res.add(nums[i]);
        subsets(i+1,nums,res,ans);
        res.remove(res.size()-1);
        subsets(i+1,nums,res,ans);
    }
    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> res = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        subsets(0,nums,res,ans);
        return ans;
    }

    //Leetcode 90
    private void subsetsWithDup(int idx,int[] nums,List<Integer> res,List<List<Integer>> ans){
        ans.add(new ArrayList<>(res));

        for(int i=idx;i<nums.length;i++){
            if(i!=idx && nums[i]==nums[i-1]) continue;
            res.add(nums[i]);
            subsetsWithDup(i+1,nums,res,ans);
            res.remove(res.size()-1);
        }
    }
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        subsetsWithDup(0,nums,res,ans);
        return ans;
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

    //Leetcode 60
    public String getPermutation(int n, int k) {
        StringBuilder num = new StringBuilder();
        for(int i=1;i<=n;i++){
            num.append(i);
        }
        int fact = 1;
        for(int i=1;i<n;i++) fact = fact*i;
        k = k-1;
        StringBuilder ans = new StringBuilder();
        while(true){
            if(num.length()==1){
                ans.append(num.toString());
                break;
            }
            ans.append(num.charAt(k/fact));
            num.deleteCharAt(k/fact);
            k = k%fact;
            fact = fact/num.length();
        }
        return ans.toString();

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

    
}
