import java.util.*;
public class StackAndQueue_practice {
    
    //Next Greater Element on Right (NGOR)
    //Method 1
    public static int[] NGOR(int[] arr){
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

    //Method 2
    //https://practice.geeksforgeeks.org/problems/next-larger-element-1587115620/1?utm_source=geeksforgeeks&utm_medium=article_practice_tab&utm_campaign=article_practice_tab
    public static long[] nextLargerElement(long[] arr, int n)
    { 
        if(n==1) return new long[] {-1};
        
        long[] ans = new long[n];
        Stack<Long> st = new Stack<Long>();
        
        for(int i=n-1;i>=0;i--){
            while(st.size()!=0 && st.peek()<=arr[i])
                st.pop();
                
            if(st.size()==0) ans[i] = -1;
            else ans[i] = st.peek();
            st.push(arr[i]);
        }
        return ans;
    }

    //Next Greater Element on Left
    //Method 1
    public static int[] NGOL(int[] arr){
        int n = arr.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();

        for(int i=0;i<n;i++){
            while(st.size()!=0 && arr[st.peek()]<=arr[i])
                st.pop();
            
            if(st.size()==0) ans[i] = -(int)1e9;
            else ans[i] = arr[st.peek()];
            st.push(i);
        }
        return ans;
    }

    //Method 2
    public static int[] NGOL(int n,int[] arr){
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
    public static int[] NSOR(int[] arr){
        int n = arr.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();

        for(int i=n-1;i>=0;i--){
            while(st.size()!=0 && arr[st.peek()]>=arr[i])
                st.pop();
            
            if(st.size()==0) ans[i] = (int)1e9;
            else ans[i] = arr[st.peek()];
            st.push(i);
        }
        return ans;
    }

    //Method 2
    public static int[] NSOR(int n,int[] arr){
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        Arrays.fill(ans,n);

        for(int i=0;i<n;i++){
            while(st.size()!=0 && arr[st.peek()]>arr[i])
                ans[st.pop()] = i;
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
            
            if(st.size()==0) ans[i] = -(int)1e9;
            else ans[i] = arr[st.peek()];
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

    //Leetcode 503
    //Method 1
    public int[] nextGreaterElements_01(int[] nums) {
        if(nums.length==1) return new int[] {-1};

        int n = nums.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        for(int i=(2*n)-1;i>=0;i--){
            while(st.size()!=0 && st.peek()<=nums[i%n])
                st.pop();
                
            if(st.size()==0) ans[i%n] = -1;
            else ans[i%n] = st.peek();
            st.push(nums[i%n]);
        }
        return ans;
    }

    //Method 2
    public int[] nextGreaterElements(int[] nums) {
        if(nums.length==1) return new int[] {-1};

        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans,-1);
        Stack<Integer> st = new Stack<>();
        for(int i=0;i<(2*n);i++){
            while(st.size()!=0 && nums[st.peek()]<nums[i%n])
                ans[st.pop()] = nums[i%n];
            
            if(i<n) st.push(i);
        }
        return ans;
    }

    //Stock Span Problem
    //https://practice.geeksforgeeks.org/problems/stock-span-problem-1587115621/1#
    public static int[] calculateSpan(int price[], int n)
    {
        if(n==1) return new int[] {1};
        
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        st.push(-1);
        
        for(int i=0;i<n;i++){
            while(st.peek()!=-1 && price[st.peek()]<=price[i])
                st.pop();
            ans[i] = i - st.peek();
            st.push(i);
        }
        return ans;
    }

    //Leetcode 901
    class StockSpanner {
    
        class IndexPricePair{
            int idx = 0;
            int price = 0;
            IndexPricePair(int idx,int price){
                this.idx = idx;
                this.price = price;
            }
        }
        int i;
        Stack<IndexPricePair> st;
        public StockSpanner() {
            st = new Stack<>();
            st.push(new IndexPricePair(-1,0));
            i = 0;
        }
        
        public int next(int price) {
            while(st.peek().idx!=-1 && st.peek().price <= price){
                st.pop();
            }
            int ans = i - st.peek().idx;
            st.push(new IndexPricePair(i,price));
            i++;
            return ans;
        }
    }

    //Leetcode 20
    public boolean isValid(String s) {
        Stack<Character> st = new Stack<>();

        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);

            if(c=='(' || c=='{' || c=='[')
                st.push(c);
            else{
                if(!st.isEmpty()){
                    switch(c){

                        case ')':
                        if(st.peek()=='('){
                            st.pop();
                            break;
                        }else return false;

                        case '}':
                        if(st.peek()=='{'){
                            st.pop();
                            break;
                        }
                        else return false;

                        case ']':
                        if(st.peek()=='['){
                            st.pop();
                            break;
                        }
                        else return false;

                    }
                }
                else return false;
            }
        }

        if(st.empty()) return true;
        else return false;
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
    //Method 1 - 569 ms
    public String minRemoveToMakeValid_01(String s) {
        StringBuilder sb = new StringBuilder(s);
        Stack<Integer> st = new Stack<>();
        int n = s.length();
        for(int i=0;i<n;i++){
            if(s.charAt(i)=='(') st.push(i);

            else if(s.charAt(i)==')'){
                if(!st.empty()) st.pop();
                else sb.setCharAt(i,'$');
            }
        }

        while(!st.empty()){
            sb.setCharAt(st.pop(),'$');
        }

        String ans = "";
        for(int i=0;i<n;i++){
            char ch = sb.charAt(i);
            if(ch != '$') ans += ch;
        }
        return ans;
    }

    //Method 2 - 5ms
    public String minRemoveToMakeValid(String s) {
        char[] arr = s.toCharArray();
        int open = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == '('){
                open++;
            }else if(arr[i] == ')'){
                if(open == 0){
                    arr[i] = '*';
                }else{
                    open--;
                }
            }
        }

        for(int i = arr.length - 1; i >= 0; i--){
            if(open > 0 && arr[i] == '('){
                arr[i] = '*';
                open--;
            }
        }

        int p = 0;

        for(int i = 0; i < arr.length; i++){
            if(arr[i] != '*'){
                arr[p++] = arr[i];
            }
        }
        return new String(arr).substring(0, p);
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
            }else{
                st.push(i);
            }
        }
        return len;
    }
    
    //Leetcode 735
    public int[] asteroidCollision(int[] asteroids) {

        Stack<Integer> st = new Stack<>();

        for(int asteroid:asteroids){
            if(asteroid>0){
                st.push(asteroid);
                continue;
            }

            while(st.size()!=0 && st.peek()>0 && st.peek()<-asteroid){
                st.pop();
            }

            if(st.size()!=0 && st.peek()>0 && st.peek()>-asteroid){
                continue;
            }
            if(st.size()!=0 && st.peek()>0 && st.peek()==-asteroid){
                st.pop();
                continue;
            }
            
            st.push(asteroid);
        }

        int n = st.size();
        int[] ans = new int[n];

        for(int i=n-1;i>=0;i--){
            ans[i] = st.pop();
        }
        return ans;
    }

    //Leetcode 84
    //Method 1
    public int largestRectangleArea_01(int[] heights) {
        int n = heights.length;
        int[] nsor = NSOR(n,heights);
        int[] nsol = NSOL(n,heights);
        int maxArea = 0;
        for(int i=0;i<n;i++){
            int w = nsor[i]-nsol[i]-1;
            maxArea = Math.max(maxArea,heights[i]*w);
        }
        return maxArea;
    }

    //Method 2
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
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

        while(st.size()>1){
            h = heights[st.pop()];
            w = n - st.peek() - 1;
            maxArea = Math.max(maxArea,h*w);
        }

        return maxArea;
    }

    //Leetcode 85
    //Method 1
    public int maximalRectangle_01(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int maxArea = 0;
        int[] heights = new int[m];

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                heights[j] = (matrix[i][j]=='1')? heights[j]+1:0;
            }
            maxArea = Math.max(maxArea,largestRectangleArea_01(heights));
        }

        return maxArea;
    }

    //Method 2
    public int maximalRectangle(char[][] matrix) {
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

    //Leetcode 221
    public int maximalSquare(int[] heights){
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
    public int maximalSquare(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[] heights = new int[m];
        int maxArea = 0;
        
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                heights[j] = (matrix[i][j]=='1')? heights[j] + 1 : 0;
            }
            maxArea = Math.max(maxArea,maximalSquare(heights));
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
            if(st.size()==0 && ch==0) continue;
            st.push(ch);
        }

        while(k>0 && !st.empty()){
            st.pop();
            k--;
        }

        StringBuilder sb = new StringBuilder();
        while(!st.empty()){
            sb.append(st.pop());
        }
        sb.reverse();
        
        return (sb.length()==0)? "0":sb.toString();
    }

    //Leetcode 316 //Leetcode 1081 - Same solution
    public String removeDuplicateLetters(String s) {
        int[] freq = new int[26];
        boolean[] vis = new boolean[26];

        for(int i=0;i<s.length();i++){
            freq[s.charAt(i) - 'a']++;
        }
        Stack<Character> st = new Stack<>();
        int idx = -1;
        char ch;
        for(int i=0;i<s.length();i++){
            ch = s.charAt(i);
            idx = ch - 'a';
            freq[idx]--;
            if(vis[idx]) continue;

            while(st.size()!=0 && ch<st.peek() && freq[st.peek() - 'a']!=0){
                char top = st.pop();
                vis[top - 'a'] = false;
            }
            st.push(ch);
            vis[idx] = true;

        }

        StringBuilder sb = new StringBuilder();
        while(!st.empty()){
            sb.insert(0,st.pop());
        }
        return sb.toString();
    }

    //Leetcode 42
    //Method 1 - Brute Force
    public int trap_01(int[] height) {
        int n = height.length;
        int water = 0;

        for(int i=1;i<n-1;i++){
            int left = height[i];

            for(int j=0;j<i;j++){
                left = Math.max(left,height[j]);
            }
            int right = height[i];
            
            for(int j=i+1;j<n;j++){
                right = Math.max(right,height[j]);
            }

            water += (Math.min(left,right) - height[i]);
        }
        return water;
    }

    //Method 2 - DP
    public int trap_02(int[] height) {
        int n = height.length;
        int water = 0;

        int[] left = new int[n];
        int[] right = new int[n];
        int prev = 0;
        for(int i=0;i<n;i++){
            left[i] = Math.max(height[i],prev);
            prev = left[i];
        }

        prev = 0;
        for(int i=n-1;i>=0;i--){
            right[i] = Math.max(height[i],prev);
            prev = right[i];
            water += Math.min(left[i],right[i]) - height[i];
        }

        return water;
    }

    //Method 3 - Stack
    public int trap_03(int[] height) {
        int n = height.length;
        int water = 0;
        Stack<Integer> st = new Stack<>();

        for(int i=0;i<n;i++){
            while(st.size()!=0 && height[st.peek()]<=height[i]){
                int idx = st.pop();
                if(st.size()==0) break;
                int w = i - st.peek() - 1;
                int h = Math.min(height[st.peek()],height[i]) - height[idx];
                water += h*w;
            }
            st.push(i);
        }
        return water;
    }

    //Method 4 - 
    public int trap(int[] height) {
        int n = height.length;
        int water = 0;
        int i=0,j=n-1,lmax=0,rmax=0;

        while(i<j){
            lmax = Math.max(lmax,height[i]);
            rmax = Math.max(rmax,height[j]);

            water += (lmax<=rmax)? (lmax-height[i++]) : (rmax-height[j--]); 
        }
        
        return water;
    }

    //Leetcode 155
    class MinStack {
        Stack<Long> st;
        long globalMin;
        public MinStack() {
            st = new Stack<>();
            globalMin = Integer.MAX_VALUE; 
        }
        
        public void push(int val) {
            if(st.size()==0){
                st.push((long)val);
                globalMin = val;
            }
            else if(val>=globalMin){
                st.push((long)val);
            }else{
                st.push((val-globalMin)+val);
                globalMin = val;
            }
        }
        
        public void pop() {
            if(st.peek()<globalMin)
                globalMin = (globalMin-st.peek()) + globalMin;
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
        int ans = 0;
        int close = 0; //
        int n = s.length();
        
        for(int i=0;i<n;i++){
           if(s.charAt(i)=='(') close++;
           else{
               if(i+1==s.length() || s.charAt(i+1)!=')') ans++;
               else i++;

               if(close>0) close--;
               else ans++;
           }
        }
        return ans + 2*close;
    }

}
