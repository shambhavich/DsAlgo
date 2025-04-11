import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;

public class HashMapAndHeap_practice {

    //Leetcode 215
    //Method 1 - 57ms
    public int findKthLargest_01(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int ele:nums){
            pq.add(ele);
            if(pq.size()>k) pq.remove();
        }

        return pq.peek();
    }

    //Method 2 - 25ms Better method
    private void swap(int i,int j,int[] arr){ //O(1)
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    private void downHeapify(int pi,int li,int[] arr){ //O(log(n))
        int maxIdx = pi;
        int lci = 2*pi + 1;
        int rci = 2*pi + 2;

        if(lci<=li && arr[lci]>arr[maxIdx])
            maxIdx = lci;

        if(rci<=li && arr[rci]>arr[maxIdx])
            maxIdx = rci;

        if(maxIdx!=pi){
            swap(pi,maxIdx,arr);
            downHeapify(maxIdx,li,arr);
        }
    }
    public int findKthLargest(int[] nums, int k) {
        int li = nums.length-1;
        for(int i=li;i>=0;i--){
            downHeapify(i,li,nums);
        }

        while(k-->1){
            swap(0,li--,nums);
            downHeapify(0,li,nums);
        }
        return nums[0];
    }

    //Find Kth smallest in a given array
    //https://practice.geeksforgeeks.org/problems/kth-smallest-element5635/1
    //Method 1
    public static int kthSmallest_01(int[] arr, int l, int r, int k) 
    { 
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
            return b - a;
        });
        
        for(int i=l;i<=r;i++){
            pq.add(arr[i]);
            if(pq.size()>k) pq.remove();
        }
        
        return pq.peek();
    }

    //Method 2
    private void downHeapify_decreasing(int[] arr,int pi,int li){ //O(log(n))
        int minIdx = pi;
        int lci = 2*pi + 1;
        int rci = 2*pi + 2;

        if(lci<=li && arr[lci]<arr[minIdx])
            minIdx = lci;

        if(rci<=li && arr[rci]<arr[minIdx])
            minIdx = rci;

        if(minIdx!=pi){
            swap(pi,minIdx,arr);
            downHeapify_decreasing(arr,minIdx,li);
        }
    }
    public int kthSmallest(int[] nums, int l, int r, int k) 
    {
        int li = r;
        for(int i=li;i>=l;i--){
            downHeapify_decreasing(nums,i,li);
        }

        while(k-->1){
            swap(0,li--,nums);
            downHeapify_decreasing(nums,0,li);;
        }
        return nums[0];
    }

    //Leetcode 703
    class KthLargest {
        private int k;
        PriorityQueue<Integer> pq;
        public KthLargest(int k, int[] nums) {
            this.k = k;
            pq = new PriorityQueue<>();
            for(int ele:nums){
                this.pq.add(ele);
                if(this.pq.size()>this.k) pq.remove();
            }
        }
        
        public int add(int val) {
            this.pq.add(val);
            if(this.pq.size()>this.k) pq.remove();
            return pq.peek();
        }
    }

    //Leetcode 349
    public int[] intersection(int[] nums1, int[] nums2) {
        HashMap<Integer,Integer> hm = new HashMap<>();
        ArrayList<Integer> arr = new ArrayList<>();
        for(int ele:nums1){
            hm.put(ele,0);
        }
        
        for(int ele:nums2){
            if(hm.containsKey(ele)){
                arr.add(ele);
                hm.remove(ele);
            }
        }
        
        
        int[] ans = new int[arr.size()];
        for(int i=0;i<arr.size();i++){
            ans[i] = arr.get(i);
        }
        return ans;
    }

    //Leetcode 350
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer,Integer> hm = new HashMap<>();
        ArrayList<Integer> arr = new ArrayList<>();
        int a;
        for(int ele:nums1){
            hm.put(ele,hm.getOrDefault(ele,0)+1);
        }
        
        for(int ele:nums2){
            if(hm.containsKey(ele)){
                arr.add(ele);
                a = hm.get(ele) - 1;
                if(a==0){
                    hm.remove(ele);
                }else{
                    hm.put(ele,a);
                }
            }
        }
        
        
        int[] ans = new int[arr.size()];
        for(int i=0;i<arr.size();i++){
            ans[i] = arr.get(i);
        }
        return ans;
    }

    //Leetcode 128
    public int longestConsecutive(int[] nums) {
        int n = nums.length;
        if(n==0) return 0;
        Arrays.sort(nums);

        int prev = 1;
        int max = 1;
        for(int i=1;i<n;i++){
            if(nums[i-1]+1 == nums[i]){
                max = Math.max(max,prev+1);
                prev++;
            }else if(nums[i]==nums[i-1]){
                continue;
            }else{
                prev = 1;
            }
        }
        return max;
    }

    //Leetcode 347
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> hm = new HashMap<>();
        
        for(int ele:nums){
            hm.put(ele,hm.getOrDefault(ele,0)+1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) ->{
            return hm.get(b) - hm.get(a);
        });

        for(Integer key:hm.keySet()){
            pq.add(key);
        }

        int[] ans = new int[k];
        int i = 0;
        while(i<k){
            ans[i++] = pq.remove();
        }
        return ans;
    }

    //Leetcode 973
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) ->{
            int d1 = a[0]*a[0] + a[1]*a[1];
            int d2 = b[0]*b[0] + b[1]*b[1];
            return d2 - d1;
        });        

        for(int[] point:points){
            pq.add(new int[] {point[0],point[1]});
            if(pq.size()>k) pq.remove();
        }

        int[][] ans = new int[k][2];
        int i = 0;
        while(pq.size()!=0){
            int[] p = pq.remove();
            ans[i++] = p;
        }
        return ans;
    }

    //Leetcode 378
    //Method 1
    public int kthSmallest_01(int[][] matrix, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
            return b-a;
        });
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                pq.add(matrix[i][j]);
                if(pq.size()>k) pq.remove();
            }
        }
        return pq.peek();
    }

    //Method 2
    public int countLess(int[][] matrix,int mid){
        int i=matrix.length-1,j=0;
        int count = 0;
        while(i>=0 && j<matrix.length){
            if(matrix[i][j]>mid){
                i--;
            }else{
                count += (i+1);
                j++;
            }
        }
        return count;
    } 
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int low = matrix[0][0];
        int high = matrix[n-1][n-1];
        int mid,count;
        while(low<high){
            mid = low + (high-low)/2;
            count = countLess(matrix,mid);
            if(count<k){
                low = mid+1;
            }else{
                high = mid;
            }
        }
        return low;
    }

    //Leetcode 380
    class RandomizedSet {
        //Add values to a list; HashMap stores the idx of each val in the list; When removing replace lastIdx with curr idx val. Retrieve currIdx from hashmap. Make adjustments accordingly in HashMap
        HashMap<Integer,Integer> map;
        ArrayList<Integer> list;
        Random rand;
        public RandomizedSet() {
            map = new HashMap<>();
            list = new ArrayList<>();
            rand = new Random();
        }
        
        public boolean insert(int val) {
            if(map.containsKey(val)) return false;
            list.add(val);
            map.put(val,list.size()-1);
            return true;
        }
        
        public boolean remove(int val) {
            if(!map.containsKey(val)) return false;
            int idx = map.get(val);
            int lastVal = list.get(list.size()-1);
            int lastIdx = list.size()-1;
            list.set(idx,lastVal);
            list.remove(lastIdx);
            map.put(lastVal,idx);
            map.remove(val);
            return true;
        }
    
        public int getRandom() {
            int idx = rand.nextInt(list.size());
            return list.get(idx);
        }
    }

    //Leetcode 895
    //Method 1 - 43ms
    class FreqStack_01 {
        private class pair{
            int val;
            int freq;
            int idx;
            pair(int val,int freq,int idx){
                this.val = val;
                this.freq = freq;
                this.idx = idx;
            }
        }
        private HashMap<Integer,Integer> freqMap;
        private PriorityQueue<pair> pq;
        private int idx;
        public FreqStack_01() {
            this.freqMap = new HashMap<>();
            this.pq = new PriorityQueue<>((a,b) -> {
                if(a.freq==b.freq)
                    return b.idx - a.idx;
                return b.freq - a.freq;
            });
            this.idx = 0;
        }
        
        public void push(int val) {
            freqMap.put(val,freqMap.getOrDefault(val,0)+1);
            pq.add(new pair(val,freqMap.get(val),idx++));
        }
        
        public int pop() {
            pair p = pq.remove();
            freqMap.put(p.val,freqMap.get(p.val)-1);
            if(freqMap.get(p.val)==0) freqMap.remove(p.val);
            return p.val;
        }
    }

    //Method 2 - 27ms
    class FreqStack_02 {
        private HashMap<Integer,Integer> freqMap;
        private ArrayList<Stack<Integer>> freqStack;
        private int maxFreq;
        public FreqStack_02() {
            this.freqMap = new HashMap<>();
            this.freqStack = new ArrayList<>();
            freqStack.add(new Stack<>());
            this.maxFreq = 0;
        }
        
        public void push(int val) {
            int freq = freqMap.getOrDefault(val,0)+1;
            freqMap.put(val,freq);

            if(maxFreq<freq){
                maxFreq = freq;
            }

            if(freq==freqStack.size()){
                freqStack.add(new Stack<>());
            }

            freqStack.get(freq).push(val);
        }
        
        public int pop() {
            int rv = freqStack.get(maxFreq).pop();
            if(freqStack.get(maxFreq).size()==0) 
                freqStack.remove(maxFreq--);

            freqMap.put(rv,freqMap.get(rv)-1);
            if(freqMap.get(rv)==0) freqMap.remove(rv);

            return rv;
        }

    }

    //Method 3 - 25ms
    class FreqStack {
        private HashMap<Integer,Integer> freqMap;
        private ArrayList<ArrayList<Integer>> freqStack;
        private int maxFreq;
        public FreqStack() {
            this.freqMap = new HashMap<>();
            this.freqStack = new ArrayList<>();
            freqStack.add(new ArrayList<>());
            this.maxFreq = 0;
        }
        
        public void push(int val) {
            int freq = freqMap.getOrDefault(val,0)+1;
            freqMap.put(val,freq);
    
            if(maxFreq<freq){
                maxFreq = freq;
            }
    
            if(freq==freqStack.size()){
                freqStack.add(new ArrayList<>());
            }
    
            freqStack.get(freq).add(val);
        }
        
        public int pop() {
            ArrayList<Integer> temp = freqStack.get(maxFreq);
            int rv = temp.get(temp.size()-1);
            temp.remove(temp.size()-1);
            if(temp.size()==0) 
                freqStack.remove(maxFreq--);
    
            freqMap.put(rv,freqMap.get(rv)-1);
            if(freqMap.get(rv)==0) freqMap.remove(rv);
    
            return rv;
        }
    }

    //Leetcode 407
    public int trapRainWater(int[][] heightMap) {
        int n = heightMap.length;
        int m = heightMap[0].length;

        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
            return heightMap[a/m][a%m] - heightMap[b/m][b%m];
        });
        boolean[][] vis = new boolean[n][m];
        for(int i=0;i<n;i++){
            pq.add(i*m+0);
            pq.add(i*m+(m-1));
            vis[i][0] = true;
            vis[i][m-1] = true;
        }
        for(int j=0;j<m;j++){
            pq.add(0*m+j);
            pq.add((n-1)*m+j);
            vis[0][j] = true;
            vis[n-1][j] = true;
        }
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int maxHeight = 0;
        int water = 0;
        while(pq.size()!=0){
            int idx = pq.remove();
            int r = idx/m;
            int c = idx%m;
            maxHeight = Math.max(maxHeight,heightMap[r][c]);
            water += maxHeight - heightMap[r][c];
            for(int d=0;d<4;d++){
                int x = r + dir[d][0];
                int y = c + dir[d][1];
                if(x>=0 && y>=0 && x<n && y<m && !vis[x][y]){
                    pq.add(x*m+y);
                    vis[x][y] = true;
                }
            }
        }

        return water;
    }

    //Leetcode 49
    public List<List<String>> groupAnagrams(String[] strs) {
        if(strs.length==0) return new ArrayList<>();
        HashMap<String,List<String>> hm = new HashMap<>();

        for(String str:strs){
            char[] arr = str.toCharArray();
            Arrays.sort(arr);
            String key = new String(arr);
            hm.putIfAbsent(key,new ArrayList<>());
            hm.get(key).add(str);
        }

        List<List<String>> ans = new ArrayList<>();
        for(String s:hm.keySet()){
            ans.add(hm.get(s));
        }
        return ans;
    }

    //Leetcode 778
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) ->{
            return grid[a/n][a%n] - grid[b/n][b%n];
        });

        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        boolean[][]vis = new boolean[n][n];

        pq.add(0);
        vis[0][0] = true;
        int maxHeight = 0,time = 0;

        while(pq.size()!=0){
            int idx = pq.remove();
            int i = idx/n, j=idx%n;
            int height = grid[i][j];

            time += Math.max(0,height - maxHeight);
            if(i==n-1 && j==n-1) break;
            maxHeight = Math.max(height,maxHeight);

            for(int d=0;d<4;d++){
                int r = i + dir[d][0];
                int c = j + dir[d][1];
                if(r>=0 && c>=0 && r<n && c<n && !vis[r][c]){
                    pq.add(r*n+c);
                    vis[r][c] = true;
                }
            }
        }

        return time;
    }

    //Leetcode 295
    //Method 1
    class MedianFinder_01 {
        PriorityQueue<Integer> maxHeap;
        PriorityQueue<Integer> minHeap;
        public MedianFinder_01() {
            maxHeap = new PriorityQueue<>((a,b) ->{
                return b-a;
            });
            minHeap = new PriorityQueue<>();
        }
        
        public void addNum(int num) {
            if(maxHeap.size()==0 || num<=maxHeap.peek()){
                maxHeap.add(num);
            }else{
                minHeap.add(num);
            }
    
            if(maxHeap.size()-minHeap.size()==2){
                minHeap.add(maxHeap.remove());
            }
            if(maxHeap.size()-minHeap.size()==-1){
                maxHeap.add(minHeap.remove());
            }
        }
        
        public double findMedian() {
            if(maxHeap.size()==minHeap.size()){
                return ((maxHeap.peek()+minHeap.peek())/2.0);
            }else return maxHeap.peek();
        }
    }

    //Method 2
    class MedianFinder {
        PriorityQueue<Integer> maxHeap;
        PriorityQueue<Integer> minHeap;
        public MedianFinder() {
            maxHeap = new PriorityQueue<>((a,b) ->{
                return b-a;
            });
            minHeap = new PriorityQueue<>();
        }
        
        public void addNum(int num) {
            if(minHeap.size()==0 || num>=minHeap.peek()){
                minHeap.add(num);
            }else{
                maxHeap.add(num);
            }
    
            if(minHeap.size()-maxHeap.size()==2){
                maxHeap.add(minHeap.remove());
            }
            if(minHeap.size()-maxHeap.size()==-1){
                minHeap.add(maxHeap.remove());
            }
        }
        
        public double findMedian() {
            if(maxHeap.size()==minHeap.size()){
                return ((maxHeap.peek()+minHeap.peek())/2.0);
            }else return minHeap.peek();
        }
    }

    //Leetcode 23
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a,b)->{
            return a.val - b.val;
        });

        for(int i=0;i<lists.length;i++){
            if(lists[i]!=null) pq.add(lists[i]);
        }

        ListNode dummy = new ListNode(-1);
        ListNode prev = dummy;

        while(pq.size()!=0){
            ListNode rn = pq.remove();
            prev.next = rn;
            prev = prev.next;
            if(rn.next!=null) pq.add(rn.next);
        }
        
        ListNode head = dummy.next;
        dummy.next = null;
        return head;
    }

    //Leetcode 502
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        PriorityQueue<int[]> minCapitalHeap = new PriorityQueue<>((a,b) -> {
            return a[0] - b[0];
        });
        PriorityQueue<int[]> maxProfitHeap = new PriorityQueue<>((a,b) ->{
            return b[1] - a[1];
        });

        for(int i=0;i<capital.length;i++){
            minCapitalHeap.add(new int[]{capital[i],profits[i]});
        }

        while(k-->0){
            while(minCapitalHeap.size()!=0 && minCapitalHeap.peek()[0]<=w){
                maxProfitHeap.add(minCapitalHeap.remove());
            }

            if(maxProfitHeap.isEmpty()){
                break;
            }

            w += maxProfitHeap.remove()[1];
        }

        return w;
    }

    //https://practice.geeksforgeeks.org/problems/find-smallest-range-containing-elements-from-k-lists/1
    static int[] findSmallestRange(int[][] KSortedArray,int n,int k)
	{
	    PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->{
	        return KSortedArray[a/n][a%n] - KSortedArray[b/n][b%n];
	    });
	    
	    int range = (int)1e9;
	    int maxValue = -(int)1e9;
	    int sp = -1;
	    int ep = -1;
	    
	    for(int i=0;i<k;i++){
	        pq.add(i*n+0);
	        maxValue = Math.max(maxValue,KSortedArray[i][0]);
	    }
	    
	    while(pq.size()==k){
	        int idx = pq.remove();
	        int r = idx/n;
	        int c = idx%n;
	        int val = KSortedArray[r][c];
	        
	        if(maxValue - val < range){
	            sp = val;
	            ep = maxValue;
	            range = maxValue - val;
	        }
	        
	        c++;
	        if(c<n){
	            pq.add(r*n+c);
	            maxValue = Math.max(maxValue,KSortedArray[r][c]);
	        }
	    }
	    return new int[]{sp,ep};
	    
	}

    //Leetcode 632
    public int[] smallestRange(List<List<Integer>> nums) {
        int n = nums.size();
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) ->{
            return nums.get(a[0]).get(a[1]) - nums.get(b[0]).get(b[1]);
        });

        int maxValue = -(int)1e9;
        for(int i=0;i<n;i++){
            pq.add(new int[]{i,0});
            maxValue = Math.max(maxValue,nums.get(i).get(0));
        }

        int range = (int)1e9;
        int sp = -1, ep = -1;

        while(pq.size()==n){
            int[] coord = pq.remove();
            int r = coord[0];
            int c = coord[1];
            int val = nums.get(r).get(c);

            if(maxValue - val < range){
                range = maxValue - val;
                sp = val;
                ep = maxValue;
            }

            c++;
            if(c<nums.get(r).size()){
                pq.add(new int[]{r,c});
                maxValue = Math.max(maxValue,nums.get(r).get(c));
            }
        }

        return new int[]{sp,ep};
    }
}
