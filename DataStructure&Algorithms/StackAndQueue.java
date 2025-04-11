import java.util.*;
public class StackAndQueue {

    //Next Greater Element on Right (NGOR)
    //Method 1
    public static int[] NGOR01(int[] arr){
        int n = arr.length;
        int[] ans = new int[n];
        Arrays.fill(ans,-1);
        Stack<Integer> st = new Stack<>();

        for(int i=n-1;i>=0;i--){
            while(st.size()!=0 && st.peek()<=arr[i])
                st.pop();
            if(st.size()!=0) ans[i] = st.peek();
            st.push(i);
        }

        return ans;
    }

    //Method 2
    public static int[] NGOR02(int[] arr){
        int n = arr.length;
        int[] ans = new int[n];
        Arrays.fill(ans,-1);

        Stack<Integer> st = new Stack<>();
        for(int i=0;i<n;i++){
            while(st.size()!=0 && arr[st.peek()]<arr[i]) 
                ans[st.pop()] = i;
            st.push(i);
        }
        return ans;
    }

    //Leetcode 496
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int n = nums2.length;
        HashMap<Integer,Integer> map = new HashMap<>();
        Stack<Integer> st = new Stack<>();
        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty() && st.peek()<nums2[i]){
                st.pop();
            }
            if(!st.isEmpty()) map.put(nums2[i],st.peek());
            else map.put(nums2[i],-1);
            st.push(nums2[i]);
        }

        n = nums1.length;
        int[] ans = new int[n];
        for(int i=0;i<n;i++){
            ans[i] = map.get(nums1[i]);
        }
        return ans;
    }

    //Next Greater Element on Left (NGOL)
    //Method 1
    public static int[] NGOL01(int[] arr){
        int n = arr.length;
        int[] ans = new int[n];
        Arrays.fill(ans,-1);

        Stack<Integer> st = new Stack<>();
        for(int i=0;i<n;i++){
            while(!st.isEmpty() && arr[st.peek()]<=arr[i])
                st.pop();
            if(!st.isEmpty()) arr[i] = st.peek();
            st.push(i);
        }
        return ans;
    }

    //Method 2
    public static int[] NGOL02(int[] arr){
        int n = arr.length;
        int[] ans = new int[n];
        Arrays.fill(ans,-1);

        Stack<Integer> st = new Stack<>();
        for(int i=n-1;i>=0;i--){
            while(st.size()!=0 && arr[st.peek()]<arr[i]) 
                ans[st.pop()] = i;
            st.push(i);
        }
        return ans;
    }

    //Next Smallest Element on Right
    //Method 1
    public static int[] NSOR01(int[] arr){
        int n = arr.length;
        int[] ans = new int[n];
        Arrays.fill(ans,n);
        Stack<Integer> st = new Stack<>();

        for(int i=n-1;i>=0;i--){
            while(st.size()!=0 && st.peek()>=arr[i])
                st.pop();
            if(st.size()!=0) ans[i] = st.peek();
            st.push(i);
        }

        return ans;
    }

    //Method 2
    public static int[] NSOR02(int[] arr){
        int n = arr.length;
        int[] ans = new int[n];
        Arrays.fill(ans,n);
        Stack<Integer> st = new Stack<>();

        for(int i=0;i<n;i++){
            while(!st.isEmpty() && st.peek()>arr[i]){
                arr[st.pop()] = i;
            }
            st.push(i);
        }
        return ans;
    }

    //Next Smallest Element on Left
    //Method 1
    public static int[] NSOL(int[] arr){
        int n = arr.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();

        for(int i=0;i<n;i++){
            while(st.size()!=0 && arr[st.peek()]>=arr[i])
                st.pop();
            
            if(st.size()==0) ans[i] = -1;
            else ans[i] = st.peek();
            st.push(i);
        }
        return ans;
    }

    //Method 2
    public static int[] NSOL(int n,int[] arr){
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        Arrays.fill(ans,-1);
        for(int i=n-1;i>=0;i--){
            while(st.size()!=0 && arr[st.peek()]>arr[i]) 
                ans[st.pop()] = i;
            st.push(i);
        }
        return ans;
    }

    //Stock Span Problem
    //https://www.geeksforgeeks.org/problems/stock-span-problem-1587115621/1
    public static int[] calculateSpan(int price[], int n) {
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        st.push(-1);
        for(int i=0;i<n;i++){
            while(st.peek()!=-1 && price[st.peek()]<=price[i]){
                st.pop();
            }
            
            ans[i] = i - st.peek();
            st.push(i);
        }
        
        return ans;
    }

    //Leetcode 503
    public int[] nextGreaterElements(int[] nums) {
        if(nums.length==1) return new int[]{-1};

        int n = nums.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        int idx;
        for(int i=2*n-1;i>=0;i--){
            idx = i%n;
            while(!st.isEmpty() && st.peek()<=nums[idx])
                st.pop();
            
            if(!st.isEmpty()) ans[idx] = st.peek();
            else ans[idx] = -1;
            st.push(nums[idx]);
        }
        return ans;
    }

    //Leetcode 901
    class StockSpanner {
        private Stack<int[]> st;
        private int idx;
        public StockSpanner() {
            st = new Stack<>();
            st.push(new int[]{-1,(int)1e8});
            idx = 0;
        }
        
        public int next(int price) {
            while(!st.isEmpty() && st.peek()[1]<=price)
                st.pop();
            int ans;
            if(!st.isEmpty()) ans = idx - st.peek()[0];
            else ans = 1;
            st.push(new int[]{idx++,price});
            return ans;
        }
    }

    //Leetcode 20
    public boolean isValid(String s) {
        int n = s.length();
        Stack<Character> st = new Stack<>();

        for(int i=0;i<n;i++){
            char ch = s.charAt(i);

            if(ch=='(' || ch=='[' || ch=='{'){
                st.push(ch);
            }else{
                if(!st.isEmpty()){
                    if(ch==')' && st.peek()=='('){
                        st.pop();
                    }else if(ch==']' && st.peek()=='['){
                        st.pop();
                    }else if(ch=='}' && st.peek()=='{'){
                        st.pop();
                    }else return false;
                }
                else return false;
            }

        }

        if(st.isEmpty()) return true;
        return false;
    }

    //Leetcode 946
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> st = new Stack<>();
        int j = 0;

        for(int x:pushed){
            st.push(x);

            while(!st.isEmpty() && st.peek()==popped[j]){
                st.pop();
                j++;
            }
        }

        return j==pushed.length;
    }

    //Leetcode 1249
    public String minRemoveToMakeValid(String s) {
        StringBuilder sb = new StringBuilder(s);
        int n = s.length();
        int open = 0;
        for(int i=0;i<n;i++){
            if(s.charAt(i)=='('){
                open++;
            }else if(s.charAt(i)==')'){
                if(open==0) {
                    sb.setCharAt(i,'*');
                }
                else open--;
            }
        }

        for(int i=n-1;i>=0;i--){
            if(open>0 && s.charAt(i)=='('){
                sb.setCharAt(i,'*');
                open--;
            }
            if(open==0) break;
        }

        
        StringBuilder st = new StringBuilder();
        for(int i=0;i<n;i++){
            if(sb.charAt(i)!='*') st.append(sb.charAt(i));
        }

        return st.toString();
    }

    //Leetcode 32
    public int longestValidParentheses(String s) {
        int n = s.length();
        if(n<=1) return 0;
        int len = 0;
        Stack<Integer> st = new Stack<>();
        st.push(-1);

        for(int i=0;i<n;i++){
            char ch = s.charAt(i);
            if(st.peek()!=-1 && ch==')' && s.charAt(st.peek())=='('){
                st.pop();
                len = Math.max(len,i-st.peek());
            }
            else st.push(i);
        }
        return len;
    }

    //Leetcode 84
    //Method 1
    public int largestRectangleArea01(int[] heights) {
        int n = heights.length;
        if(n==1) return heights[0];
        int maxArea = 0;
        int[] nsor = NSOR01(heights);
        int[] nsol = NSOL(heights);
        int w;
        for(int i=0;i<n;i++){
            w = nsor[i] - nsol[i] - 1;
            maxArea = Math.max(maxArea,heights[i]*w);
        }
        return maxArea;
    }

    //Method 2
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        if(n==1) return heights[0];

        Stack<Integer> st = new Stack<>();
        st.push(-1);
        int maxArea = 0;
        int h,w;

        for(int i=0;i<n;i++){
            while(st.peek()!=-1 && heights[st.peek()]>=heights[i]){
                h = heights[st.pop()];
                w = i - st.peek() - 1;
                maxArea = Math.max(maxArea,h*w);
            }
            st.push(i);
        }

        while(st.peek()!=-1){
            h = heights[st.pop()];
            w = n - st.peek() - 1;
            maxArea = Math.max(maxArea,h*w);
        }
        return maxArea;
    }

    //Leetcode 85
    //Method 1
    public int maximalRectangle01(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int maxArea = 0;
        int[] heights = new int[m];

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                heights[j] = (matrix[i][j]=='1')? heights[j]+1:0;
            }
            maxArea = Math.max(maxArea,largestRectangleArea01(heights));
        }

        return maxArea;
    }

    //Method 2
    public int maximalRectangle02(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int maxArea = 0;
        int[] heights = new int[m];

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                heights[j] = (matrix[i][j]=='1')? heights[j]+1:0;
            }
            maxArea = Math.max(maxArea,largestRectangleArea(heights));
        }

        return maxArea;
    }

    //Method 3
    private void updateHeightAndLeftBoundary(char[] matrix,int[] heights,int[] leftBoundary){
        int left = -1;
        for(int i=0;i<matrix.length;i++){
            if(matrix[i]=='1'){
                heights[i]++;
                leftBoundary[i] = Math.max(leftBoundary[i],left);
            }else{
                heights[i] = 0;
                leftBoundary[i] = -1;
                left = i;
            }
        }
    }
    private void updateRightBoundary(char[] matrix,int[] rightBoundary){
        int len = rightBoundary.length;
        int right = len;
        for(int i=len-1;i>=0;i--){
            if(matrix[i]=='1'){
                rightBoundary[i] = Math.min(rightBoundary[i],right);
            }else{
                rightBoundary[i] = len;
                right = i;
            }
        }
    }
    private int calculateMaxArea(int[] heights,int[] leftBoundary,int[] rightBoundary){
        int width;
        int area;
        int maxArea = 0;
        for(int i=0;i<heights.length;i++){
            width = rightBoundary[i] - leftBoundary[i] - 1;
            area = heights[i]*width;
            maxArea = Math.max(maxArea,area);
        }
        return maxArea;
    }
    public int maximalRectangle(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[] heights = new int[m];
        int[] leftBoundary = new int[m];
        Arrays.fill(leftBoundary,-1);
        int[] rightBoundary = new int[m];
        Arrays.fill(rightBoundary,m);
        int maxArea = 0;
        for(int i=0;i<n;i++){
            updateHeightAndLeftBoundary(matrix[i],heights,leftBoundary);
            updateRightBoundary(matrix[i],rightBoundary);
            maxArea = Math.max(maxArea,calculateMaxArea(heights,leftBoundary,rightBoundary));
        }
        return maxArea;
    }

    //Leetcode 221
    //Method 1
    public int maximalSquareUtil(int[] heights){
        int n = heights.length;
        Stack<Integer> st = new Stack<>();
        st.push(-1);
        int maxArea = 0;
        int h,w;

        for(int i=0;i<n;i++){
            while(st.peek()!=-1 && heights[st.peek()]>=heights[i]){
                h = heights[st.pop()];
                w = i - st.peek() - 1;
                maxArea = Math.max(maxArea,(h<w)? h*h : w*w);
            }
            st.push(i);
        }

        while(st.size()!=1){
            h = heights[st.pop()];
            w = n - st.peek() - 1;
            maxArea = Math.max(maxArea,(h<w)? h*h : w*w);
        }
        return maxArea;
    }
    public int maximalSquare01(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[] heights = new int[m];
        int maxArea = 0;
        
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                heights[j] = (matrix[i][j]=='1')? heights[j] + 1 : 0;
            }
            maxArea = Math.max(maxArea,maximalSquareUtil(heights));
        }
        return maxArea;
    }

    //Method 2
    private int calculateMaxArea_maximalSquare(int[] heights,int[] leftBoundary,int[] rightBoundary){
        int w,h;
        int maxArea = 0;
        for(int i=0;i<heights.length;i++){
            w = rightBoundary[i] - leftBoundary[i] - 1;
            h = heights[i];
            maxArea = Math.max(maxArea,(h<w)? h*h : w*w);
        }
        return maxArea;
    }
    public int maximalSquare(char[][] matrix) {
         int n = matrix.length;
        int m = matrix[0].length;

        int[] heights = new int[m];
        int[] leftBoundary = new int[m];
        Arrays.fill(leftBoundary,-1);
        int[] rightBoundary = new int[m];
        Arrays.fill(rightBoundary,m);
        int maxArea = 0;
        for(int i=0;i<n;i++){
            updateHeightAndLeftBoundary(matrix[i],heights,leftBoundary);
            updateRightBoundary(matrix[i],rightBoundary);
            maxArea = Math.max(maxArea,calculateMaxArea_maximalSquare(heights,leftBoundary,rightBoundary));
        }
        return maxArea;
    }

    //Leetcode 402
    public String removeKdigits(String num, int k) {
        int n = num.length();
        Stack<Integer> st = new Stack<>();

        for(int i=0;i<n;i++){
            int ch = num.charAt(i) - '0';
            while(k>0 && !st.empty() && st.peek()>ch){
                st.pop();
                k--;
            }
            if(st.empty() && ch==0) continue;
            st.push(ch);
        }

        while(k>0 && !st.empty()){
            st.pop();
            k--;
        }

        StringBuilder sb = new StringBuilder();
        while(!st.empty())
            sb.append(st.pop());
        sb.reverse();

        return (sb.length()==0) ? "0":sb.toString();
    }

    //Leetcode 316 - Same as leetcode 1081
    public String removeDuplicateLetters(String s) {
        int n = s.length();
        int[] freq = new int[26];
        boolean[] vis = new boolean[26];

        for(int i=0;i<n;i++){
            freq[s.charAt(i)-'a']++;
        }

        Stack<Character> st = new Stack<>();
        int idx;
        char ch;
        for(int i=0;i<n;i++){
            ch = s.charAt(i);
            idx = ch - 'a';
            freq[idx]--;
            if(vis[idx]) continue;

            while(!st.empty() && ch<st.peek() && freq[st.peek()-'a']!=0){
                char top = st.pop();
                vis[top-'a'] = false;
            }
            st.push(ch);
            vis[idx] = true;
        }
        StringBuilder sb = new StringBuilder();
        while(!st.empty()){
            sb.append(st.pop());
        }
        sb.reverse();
        return sb.toString();
    }

    //Leetcode 42
    //Method 1 - Brute Force
    public int trap01(int[] height) {
        int n = height.length;
        int water = 0;

        for(int i=1;i<n-1;i++){
            int left = height[i];
            for(int j=0;j<i;j++)
                left = Math.max(left,height[j]);

            int right = height[i];
            for(int j=i+1;j<n;j++)
                right = Math.max(right,height[j]);

            water += Math.min(left,right) - height[i];
        }
        return water;
    }

    //Method 2 - DP
    public int trap02(int[] height) {
        int n = height.length;
        int water = 0;

        int[] left = new int[n];
        int prev = 0;
        for(int i=0;i<n;i++){
            left[i] = Math.max(height[i],prev);
            prev = left[i];
        }

        prev = 0;
        for(int i=n-1;i>=0;i--){
            prev = Math.max(height[i],prev);
            water += Math.min(prev,left[i]) - height[i];
        }

        return water;
    }

    //Method 3 - Using Stack
    public int trap03(int[] height) {
        int n = height.length;
        Stack<Integer> st = new Stack<>();
        int water = 0;
        int h,w,idx;
        for(int i=0;i<n;i++){
            while(!st.empty() && height[st.peek()]<=height[i]){
                idx = st.pop();
                if(st.empty()) break;
                w = i - st.peek() - 1;
                h = Math.min(height[st.peek()],height[i]) - height[idx];
                water += h*w;
            }
            st.push(i);
        }
        return water;
    }

    //Method 4 - Most optimal solution
    public int trap(int[] height) {
        int n = height.length;
        int i = 0,j=n-1,lmax=0,rmax=0;
        int water = 0;

        while(i<j){
            lmax = Math.max(lmax,height[i]);
            rmax = Math.max(rmax,height[j]);

            water += (lmax<=rmax) ? (lmax-height[i++]) : (rmax-height[j--]);
        }
        return water;
    }

    //Leetcode 155
    //Method 1
    class MinStack01 {
        Stack<Integer> st;
        Stack<Integer> minValSt;
        int globalMin;
        public MinStack01() {
            st = new Stack<>();
            minValSt = new Stack<>();
            globalMin = 0;
        }
        
        public void push(int val) {
            if(st.empty() || val<=globalMin){
                st.push(val);
                minValSt.push(val);
                globalMin = val;
            }else{
                st.push(val);
            }
        }
        
        public void pop() {
            if(st.peek()==globalMin){
                minValSt.pop();
                if(!minValSt.empty()) globalMin = minValSt.peek();
            }
            st.pop();
        }
        
        public int top() {
            return st.peek();
        }
        
        public int getMin() {
            return globalMin;
        }
    }

    //Method 2
    class MinStack {
        Stack<Long> st;
        long globalMin;
        public MinStack() {
            st = new Stack<>();
        }
        
        public void push(int val) {
            if(st.empty()){
                st.push((long)val);
                globalMin = val;
            }
            else if(val<globalMin){
                st.push((val - globalMin) + val);
                globalMin = val;
            }else{
                st.push((long)val);
            }
        }
        
        public void pop() {
            if(st.peek()<globalMin)
                globalMin = (globalMin - st.peek()) + globalMin;
            st.pop();
        }
        
        public int top() {
            if(st.peek()<globalMin)
                return (int)globalMin;
            long a = st.peek();
            return (int)a;
        }
        
        public int getMin() {
            return (int)globalMin;
        }
    }

    //Leetcode 1541
    public int minInsertions(String s) {
        int n = s.length();
        int ans= 0;
        int close = 0;
        char ch;
        for(int i=0;i<n;i++){
            ch = s.charAt(i);
            if(ch=='(') close++;
            else{
                if(i+1==n || s.charAt(i+1)!=')') ans++;
                else i++;

                if(close>0) close--;
                else ans++;
            }
        }
        return ans + 2*close;
    }

    //Leetcode 277 - Locked
    //Lintcode 645
    
    //This method is here for just show. This is does not represent the actual inner working of the function
    boolean knows(int a, int b){
        return knows(a,b);
    }

    public int findCelebrity(int n) {
        int ans = 0;
        for(int i=1;i<n;i++){
            if(knows(ans,i))
                ans = i;
        }

        for(int i=0;i<n;i++){
            if(ans!=i){
                if(knows(ans,i) || !knows(i,ans)) return -1;
            }
        }
        return ans;
    }

    //Sort a stack using recursion - you cannot use for, while loop
    //https://www.geeksforgeeks.org/problems/sort-a-stack/1
    private void sortedInsert(int ele,Stack<Integer> s){
        if(s.empty() || ele>=s.peek()){
            s.push(ele);
        }else{
            int top = s.pop();
            sortedInsert(ele,s);
            s.push(top);
        }
    }
    public Stack<Integer> sort(Stack<Integer> s) {
        if(!s.empty()){
            int top = s.pop();
            
            sort(s);
            
            sortedInsert(top,s);
        }
        return s;
    }

}
