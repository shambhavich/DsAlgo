import java.util.*;

public class HashMapAndHeap {
    
    //Leetcode 215
    //Method 1
    public int findKthLargest01(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int ele:nums){
            pq.add(ele);
            if(pq.size()>k) pq.remove();
        }

        return pq.peek();
    }

    //Method 2
    private void swap(int i,int j,int[] arr){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    private void downHeapify(int pi,int li,int[] arr){
        int maxIdx = pi;
        int lci = 2*pi + 1;
        int rci = 2*pi + 2;

        if(lci<=li && arr[lci]>arr[maxIdx]){
            maxIdx = lci;
        }
        if(rci<=li && arr[rci]>arr[maxIdx]){
            maxIdx = rci;
        }

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
            swap(0,li,nums);
            li--;
            downHeapify(0,li,nums);
        }
        return nums[0];
    }

    //Find Kth smallest in a given array
    //https://practice.geeksforgeeks.org/problems/kth-smallest-element5635/1
    //Method 1
    public static int kthSmallest01(int[] arr, int l, int r, int k) 
    { 
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) ->{
            return b - a;
        });
        
        for(int ele:arr){
            pq.add(ele);
            if(pq.size()>k) pq.remove();
        }
        
        return pq.peek();
    }

    //Method 2
    private void downHeapifyDecreasing(int pi,int li,int[] arr){
        int minIdx = pi;
        int lci = 2*pi + 1;
        int rci = 2*pi + 2;
        
        if(lci<=li && arr[lci]<arr[minIdx]){
            minIdx = lci;
        }
        if(rci<=li && arr[rci]<arr[minIdx]){
            minIdx = rci;
        }
        
        if(minIdx!=pi){
            swap(minIdx,pi,arr);
            downHeapifyDecreasing(minIdx,li,arr);
        }
    }
    public int kthSmallest(int[] arr, int l, int r, int k) 
    { 
        for(int i=r;i>=0;i--){
            downHeapifyDecreasing(i,r,arr);
        }
        
        while(k-->1){
            swap(0,r,arr);
            r--;
            downHeapifyDecreasing(0,r,arr);
        }
        
        return arr[0];
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
        HashSet<Integer> set = new HashSet<>();

        for(int ele:nums1) set.add(ele);
        ArrayList<Integer> list = new ArrayList<>();
        for(int ele:nums2){
            if(set.contains(ele)){
                list.add(ele);
                set.remove(ele);
            }
        }

        int[] ans = new int[list.size()];
        for(int i=0;i<ans.length;i++){
            ans[i] = list.get(i);
        }
        return ans;
    }

    //Leetcode 350
    public int[] intersect(int[] nums1, int[] nums2) {
        int[] freq = new int[1001];
        for (int ele:nums1)
            freq[ele]++;
        int[] ans = new int[1001];
        int n = 0;
        for (int ele:nums2)
            if (freq[ele]-- > 0)
                ans[n++] = ele;
        return Arrays.copyOf(ans,n);
    }

    //Leetcode 128
    public int longestConsecutive(int[] nums) {
        int n = nums.length;
        if(n==0) return 0;
        Arrays.sort(nums);

        int len = 0;
        int maxLen = 1;

        for(int i=0;i<n;i++){
            if(i>0 && nums[i-1]+1==nums[i]){
                len++;
                maxLen = Math.max(maxLen,len);
            }else if(i>0 && nums[i-1]==nums[i]){
                continue;
            }else{
                len = 1;
            }
        }
        return maxLen;
    }

    //Leetcode 347
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> hm = new HashMap<>();

        for(int ele:nums){
            hm.put(ele,hm.getOrDefault(ele,0)+1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
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
        if(k==points.length) return points;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> {
            return (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1]*a[1]);
        });

        for(int[] point:points){
            pq.add(point);
            if(pq.size()>k) pq.remove();
        }
        return pq.toArray(new int[0][0]);
    }

    //Leetcode 378
    //Method 1
    public int kthSmallest01(int[][] matrix, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
            return b - a;
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
    private int countLess(int mid,int[][] matrix){
        int i = matrix.length-1;
        int j = 0;
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
            count = countLess(mid,matrix);
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
        HashMap<Integer,Integer> map;
        ArrayList<Integer> setList;
        int idx;
        Random rand;
        public RandomizedSet() {
            map = new HashMap<>();
            setList = new ArrayList<>();
            idx = 0;
            rand = new Random();
        }
        
        public boolean insert(int val) {
            if(map.containsKey(val)){
                return false;
            }else{
                setList.add(val);
                map.put(val,idx++);
                return true;
            }
        }
        
        public boolean remove(int val) {
            if(!map.containsKey(val)) return false;
    
            int i = map.get(val);
            if(i==idx-1){
                setList.remove(--idx);
                map.remove(val);
                return true;
            }
    
            int lastVal = setList.get(idx-1);
            setList.set(i,lastVal);
            setList.remove(idx-1);
            map.remove(val);
            map.put(lastVal,i);
            idx--;
            return true;
        }
        
        public int getRandom() {
            int randomInt = rand.nextInt(idx);
            return setList.get(randomInt);
        }
    }

    //Leetcode 895
    class FreqStack {
        HashMap<Integer,Integer> freqMap;
        ArrayList<ArrayList<Integer>> freqStack;
        int maxFreq;
        public FreqStack() {
            this.freqMap = new HashMap<>();
            this.freqStack = new ArrayList<>();
            this.freqStack.add(new ArrayList<>());
            this.maxFreq = 0;
        }
        
        public void push(int val) {
            int freq = freqMap.getOrDefault(val,0) + 1;
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
            if(temp.size()==0) freqStack.remove(maxFreq--);
    
            if(freqMap.get(rv)-1==0) freqMap.remove(rv);
            else freqMap.put(rv,freqMap.get(rv)-1);
    
            return rv;
        }
    }

    //Leetcode 407
    public int trapRainWater(int[][] heightMap) {
        int n = heightMap.length;
        int m = heightMap[0].length;
        if(n<3 || m<3) return 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> {
            return heightMap[a[0]][a[1]] - heightMap[b[0]][b[1]];
        });
        boolean[][] vis = new boolean[n][m];
        for(int j=0;j<m;j++){
            pq.add(new int[]{0,j});
            pq.add(new int[]{n-1,j});
            vis[0][j] = true;
            vis[n-1][j] = true;
        }

        for(int i=1;i<n-1;i++){
            pq.add(new int[]{i,0});
            vis[i][0] = true;
            pq.add(new int[]{i,m-1});
            vis[i][m-1] = true;
        }
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int maxHeight = 0;
        int water = 0;
        int[] mat;
        int r,c;
        while(!pq.isEmpty()){
            mat = pq.remove();
            r = mat[0];
            c = mat[1];
            maxHeight = Math.max(maxHeight,heightMap[r][c]);
            water += maxHeight - heightMap[r][c];
            for(int d=0;d<4;d++){
                int x = r + dir[d][0];
                int y = c + dir[d][1];
                if(x>=0 && y>=0 && x<n && y<m && !vis[x][y]){
                    pq.add(new int[]{x,y});
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
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
            return grid[a/n][a%n] - grid[b/n][b%n];
        });

        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        boolean[][] vis = new boolean[n][n];

        pq.add(0);
        vis[0][0] = true;
        int maxHeight = 0,time = 0;

        while(pq.size()!=0){
            int idx = pq.remove();
            int i = idx/n;
            int j = idx%n;
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
    class MedianFinder {
        private PriorityQueue<Integer> maxHeap;
        private PriorityQueue<Integer> minHeap;
        private int maxHeapSize;
        private int minHeapSize;
        public MedianFinder() {
            maxHeap = new PriorityQueue<>((a,b) -> {
                return b - a;
            });
            minHeap = new PriorityQueue<>();
            maxHeapSize = 0;
            minHeapSize = 0;
        }
        
        public void addNum(int num) {
            if(minHeapSize==0 || num>=minHeap.peek()){
                minHeap.add(num);
                minHeapSize++;
            }else{
                maxHeap.add(num);
                maxHeapSize++;
            }
    
            if(minHeapSize-maxHeapSize==2){
                maxHeap.add(minHeap.remove());
                maxHeapSize++;
                minHeapSize--;
            }
            else if(minHeapSize-maxHeapSize==-1){
                minHeap.add(maxHeap.remove());
                maxHeapSize--;
                minHeapSize++;
            }
        }
        
        public double findMedian() {
            if(maxHeapSize==minHeapSize){
                return ((maxHeap.peek()+minHeap.peek())/(2.0));
            }
            return minHeap.peek();
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
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a,b) -> {
            return a.val - b.val;
        });

        for(ListNode list:lists){
            if(list!=null) pq.add(list);
        }

        ListNode dummy = new ListNode(-1);
        ListNode prev = dummy;

        while(!pq.isEmpty()){
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

        PriorityQueue<int[]> maxProfitHeap = new PriorityQueue<>((a,b) -> {
            return b[1] - a[1];
        });

        for(int i=0;i<capital.length;i++){
            minCapitalHeap.add(new int[]{capital[i],profits[i]});
        }

        while(k-->0){
            while(!minCapitalHeap.isEmpty() && minCapitalHeap.peek()[0]<=w){
                maxProfitHeap.add(minCapitalHeap.remove());
            }

            if(maxProfitHeap.isEmpty()) break;

            w += maxProfitHeap.remove()[1];
        }
        return w;
    }

    //https://practice.geeksforgeeks.org/problems/find-smallest-range-containing-elements-from-k-lists/1
    static int[] findSmallestRange(int[][] KSortedArray,int n,int k)
	{
	    PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
	        return KSortedArray[a/n][a%n] - KSortedArray[b/n][b%n];
	    });
	    
	    int range = (int)1e8;
	    int sp = -1;
	    int ep = -1;
	    int maxVal = -(int)1e8;
	    
	    for(int i=0;i<k;i++){
	        pq.add(i*n+0);
	        maxVal = Math.max(maxVal,KSortedArray[i][0]);
	    }
	    
	    int idx,r,c;
	    
	    while(pq.size()==k){
	        idx = pq.remove();
	        r = idx/n;
	        c = idx%n;
	        
	        if(maxVal - KSortedArray[r][c] < range){
	            sp = KSortedArray[r][c];
	            ep = maxVal;
	            range = ep - sp;
	        }
	        
	        c++;
	        if(c<n){
	            pq.add(r*n+c);
	            maxVal = Math.max(maxVal,KSortedArray[r][c]);
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

    //https://www.geeksforgeeks.org/problems/maximum-sum-combination/0
    static List<Integer> maxCombinations(int n, int k, int A[], int B[]) {
        
        Arrays.sort(A);
        Arrays.sort(B);
        
        boolean[][] vis = new boolean[n][n];
        PriorityQueue<int[]> pq = new PriorityQueue<>((x,y) -> {
            return (A[y[0]] + B[y[1]]) - (A[x[0]] + B[x[1]]);
        });
        
        pq.add(new int[]{n-1,n-1});
        vis[n-1][n-1] = true;
        List<Integer> ans = new ArrayList<>();
        while(k-->0){
            int[] idx = pq.remove();
            int a = idx[0];
            int b = idx[1];
            ans.add(A[a] + B[b]);
            if(k==0) break;
            
            if(a-1>=0 && !vis[a-1][b]){
                pq.add(new int[]{a-1,b});
                vis[a-1][b] = true;
            }
            if(b-1>=0 && !vis[a][b-1]){
                pq.add(new int[]{a,b-1});
                vis[a][b-1] = true;
            }
            if(a==b && a-1>=0 && b-1>=0 &&!vis[a-1][b-1]){
                pq.add(new int[]{a-1,b-1});
                vis[a-1][b-1] = true;
            }
            
        }
        return ans;
    }

    //Leetcode 630
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses,(a,b) -> {
            return a[1] - b[1];
        });

        PriorityQueue<Integer> q = new PriorityQueue<>((a,b) -> {
            return b-a;
        });

        int time = 0;
        for(int[] course:courses){
            if(time+course[0]<=course[1]){
                q.offer(course[0]);
                time += course[0];
            }else if(!q.isEmpty() && q.peek()>course[0]){
                time += course[0] - q.poll();
                q.offer(course[0]);
            }
        }
        return q.size();
    }


}
