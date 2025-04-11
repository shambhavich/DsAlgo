import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class ArraysAndStrings_practice {
    
    //Inplace rotation of an array - Left rotation
    public static void swap(int i,int j,int[] arr){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public static void reverse(int i,int j,int[] arr){
        if(i<j){
            swap(i++,j--,arr);
        }
    }
    public static int[] inplaceRotationByK(int[] arr,int k){
        int n = arr.length;
        if(n==0|| k==n || k==0) return arr;

        k = k%n;
        if(k<0) k = k+n;

        reverse(0,n-1,arr);
        reverse(0,n-1-k,arr);
        reverse(n-k,n-1,arr);

        return arr;

    }

    //Inplace segregation of positive and negative numbers
    public static int[] inplaceSegregationPositiveNegative(int[] arr){
        int n = arr.length;
        if(n==0) return arr;

        int pivot = -1;
        int idx = 0;

        while(idx<n){
            if(arr[idx]>0){
                swap(++pivot,idx,arr);
            }
            idx++;
        }

        return arr;
    }

    //https://practice.geeksforgeeks.org/problems/move-all-negative-elements-to-end1813/1?utm_source=geeksforgeeks&utm_medium=ml_article_practice_tab&utm_campaign=article_practice_tab
    public void segregateElements(int[] arr, int n)
    {
        int[] arr2 = new int[n];
        
        int pivot = -1;
        int idx = 0;
        
        while(idx<n){
            arr2[idx] = arr[idx];
            if(arr[idx]>=0){
                swap(++pivot,idx,arr);
            }
            idx++;
        }
        
        pivot = n;
        idx = n-1;
        
        while(idx>=0){
            if(arr2[idx]<0){
                swap(--pivot,idx,arr2);
            }
            idx--;
        }
        
        while(pivot<n){
            arr[pivot] = arr2[pivot];
            pivot++;
        }
    }

    //Segregate zero and ones
    //https://practice.geeksforgeeks.org/problems/segregate-0s-and-1s5106/1
    public void segregateZeroAndOnes(int[] arr,int n){
        int pivot = -1;
        int idx = 0;

        while(idx<n){
            if(arr[idx]==0){
                swap(++pivot,idx,arr);
            }
            idx++;
        }
    }

    //https://practice.geeksforgeeks.org/problems/sort-an-array-of-0s-1s-and-2s4231/1
    //Segregate zeroes, ones and twos
    public static void sort012(int[] a, int n)
    {
        int pivot = -1;
        int idx = 0;
        
        while(idx<n){
            if(a[idx]==0){
                swap(++pivot,idx,a);
            }
            idx++;
        }
        
        idx = pivot+1;
        
        while(idx<n){
            if(a[idx]==1){
                swap(++pivot,idx,a);
            }
            idx++;
        }
    }

    //Leetcode 189
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k%n;
        if(n==0|| k==n || k==0) return;

        reverse(0,n-1,nums);
        reverse(0,k-1,nums);
        reverse(k,n-1,nums);
    }

    //Leetcode 11
    public int maxArea(int[] height) {
        int n = height.length;
        int i=0,j=n-1;
        int maxWater = 0;

        while(i<j){
            if(height[i]<=height[j]){
                maxWater = Math.max(maxWater,(j-i)*height[i++]);
            }else{
                maxWater = Math.max(maxWater,(j-i)*height[j--]);
            }
        }
        return maxWater;
    }

    //https://practice.geeksforgeeks.org/problems/max-sum-in-the-configuration/1
    int max_sum(int A[], int n)
    {
        int sum = 0,rotatedSum = 0;
        
        for(int i=0;i<n;i++){
            sum += A[i];
            rotatedSum += A[i]*i;
        }
        
        int maxSum = rotatedSum;
        for(int i=0;i<n-1;i++){
            rotatedSum = rotatedSum - sum + A[i]*n;
            maxSum = Math.max(rotatedSum,maxSum);
        }
        return maxSum;
        
    }

    //Leetcode 3
    public int lengthOfLongestSubstring(String s) {
        if(s.length()<1) return 0;

        int n = s.length();
        int si=0,ei=0,len=0,count=0;
        int[] freq = new int[256];
        while(ei<n){
            if(freq[s.charAt(ei++)]++ > 0) count++;

            while(count>0){
                if(freq[s.charAt(si++)]-- > 1) count--;
            }
            len = Math.max(len,ei-si);
        }
        return len;
    }

    //Leetcode 159 - Locked
    //Lintcode 928
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if(s.length()<1) return 0;

        int n = s.length();
        int si=0,ei=0,len=0,count=0;

        int[] freq = new int[256];

        while(ei<n){
            if(freq[s.charAt(ei++)]++ == 0) count++;

            while(count>2){
               if(freq[s.charAt(si++)]-- == 1) count--;
            }
            len = Math.max(len,ei-si);
        }
        return len;
    }

    //Leetcode 76
    public String minWindow(String s, String t) {
        int ns = s.length();
        int nt = t.length();

        if(nt>ns) return "";
        int req = nt;
        int si=0,ei=0,len=(int)1e9,head=0;
        int[] freq = new int[128];
        for(int i=0;i<nt;i++){
            freq[t.charAt(i)]++;
        }
        while(ei<ns){
            if(freq[s.charAt(ei++)]-- > 0)
                req--;
            
            while(req==0){
                if(ei-si<len){
                    len = ei-si;
                    head = si;
                }
                if(freq[s.charAt(si++)]++ == 0)
                    req++;
            }
        }

        return len == (int)1e9 ? "" : s.substring(head,head+len);
    }

    //https://practice.geeksforgeeks.org/problems/smallest-distant-window3132/1
    public int findSubString( String str) {
        int n = str.length();
        int[] freq = new int[128];
        int req = 0;
        for(int i=0;i<n;i++){
            if(freq[str.charAt(i)]++ == 0) req++;
        }
        int count = req;
        freq = new int[128];
        int si=0,ei=0,len=(int)1e8;
        
        while(ei<n){
            
            if(freq[str.charAt(ei++)]++ == 0) req--;
            
            while(req==0){
                len = Math.min(len,ei-si);
                if(freq[str.charAt(si++)]-- == 1) req++;
            }
            if(len==count) break;
        }
        
        return len;
    }

    //Leetcode 340 - Locked
    //Lintcode 386
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int n = s.length();

        int si=0,ei=0,len=0,count=0;
        int[] freq = new int[128];

        while(ei<n){

            if(freq[s.charAt(ei++)]++ == 0) count++;

            while(count>k){
                if(freq[s.charAt(si++)]-- == 1) count--;
            }

            len = Math.max(len,ei-si);
        }

        return len;
    }
    
    //Leetcode 1456
    public boolean check(Character ch){
        if(ch=='a' || ch=='e' || ch=='i' || ch=='o' || ch=='u') return true;
        return false;
    }
    public int maxVowels(String s, int k) {
        int n = s.length();

        int si=0,ei=0,vowels=0,maxVowels=0;

        while(ei<n){
            if(check(s.charAt(ei++))) vowels++;
            if(ei-si==k){
                maxVowels = Math.max(maxVowels,vowels);
                if(check(s.charAt(si++))) vowels--;
            }
        }
        return maxVowels;
    }

    //Leetcode 992
    public int subarraysWithAtmostKDistinct(int[] nums, int k) {
        int n = nums.length;

        int si=0,ei=0,count=0,ans=0;

        int[] freq = new int[20001];

        while(ei<n){
            if(freq[nums[ei++]]++ == 0) count++;

            while(count>k){
                if(freq[nums[si++]]-- == 1) count--;
            }
            ans += ei-si;
        }
        return ans;
    }
    public int subarraysWithKDistinct(int[] nums, int k){
        return subarraysWithAtmostKDistinct(nums,k) - subarraysWithAtmostKDistinct(nums,k-1); 
    }

    //Leetcode 1248
    public int numberOfSubarraysAtMost(int[] nums,int k){
        int n = nums.length;

        int si=0,ei=0,count=0,ans=0;

        while(ei<n){
            if(nums[ei++]%2 != 0) count++;

            while(count>k){
                if(nums[si++]%2 != 0) count--;
            }
            ans += ei-si;
        }

        return ans;
    }
    public int numberOfSubarrays(int[] nums, int k) {
        if(nums.length<k) return 0;
        return numberOfSubarraysAtMost(nums,k) - numberOfSubarraysAtMost(nums,k-1);
    }

    //https://practice.geeksforgeeks.org/problems/longest-k-unique-characters-substring0853/1
    public int longestkSubstr(String s, int k) {
        int n = s.length();
        if(n<k) return -1;
        
        int si=0,ei=0,len=-1,count=0;
        int[] freq = new int[26];
        
        while(ei<n){
            if(freq[s.charAt(ei++) - 'a']++ == 0) count++;
            
            while(count>k){
                if(freq[s.charAt(si++) - 'a']-- == 1) count--;
            }
            
            if(count==k) len = Math.max(len,ei-si);
        }
        
        return len;
    }

    //Leetcode 904
    public int totalFruit(int[] fruits) {
        if(fruits.length<3) return fruits.length;
        int n = fruits.length;

        int si=0,ei=0,count=0,numberOfFruits=0;
        int[] freq = new int[n];

        while(ei<n){
            if(freq[fruits[ei++]]++ == 0) count++;

            while(count>2){
                if(freq[fruits[si++]]-- == 1) count--;
            }
            numberOfFruits = Math.max(numberOfFruits,ei-si);
        }
        return numberOfFruits;
    }

    //Leetcode 930
    public int numSubarraysWithAtmostSum(int[] nums,int goal){
        int n = nums.length;

        int si=0,ei=0,count=0,ans=0;

        while(ei<n){
            if(nums[ei++] == 1) count++;

            while(count>goal){
                if(nums[si++] == 1) count--;
            }
            ans += ei-si;
        }
        return ans;
    }
    public int numSubarraysWithSum(int[] nums, int goal) {
        return numSubarraysWithAtmostSum(nums,goal) - ((goal!=0) ? numSubarraysWithAtmostSum(nums,goal-1):0);
    }

    //Leetcode 485
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxOnes = 0,count=0;

        for(int ele:nums){
            if(ele==0){
                maxOnes = Math.max(maxOnes,count);
                count = 0;
            }
            else count++;
        }
        maxOnes = Math.max(maxOnes,count);
        return maxOnes;
    }

    //Leetcode 487 - Locked
    //Lintcode 883
    //Method 1
    public int findMaxConsecutiveOnes_02_M1(int[] nums) {
        int n = nums.length;

        int si=0,ei=0,maxOnes=0,countZeroes=0;

        while(ei<n){
            if(nums[ei++] == 0) countZeroes++;
            
            while(countZeroes > 1){
                if(nums[si++] == 0) countZeroes--;
            }
            maxOnes = Math.max(maxOnes,ei-si);
        }
        return maxOnes;
    }

    //Method 2
    public int findMaxConsecutiveOnes_02_M2(int[] nums) {
        int n = nums.length;

        int si=0,ei=0,maxOnes=0,firstZero=-1;

        while(ei<n){
            if(nums[ei++] == 0){
                si = firstZero+1;
                firstZero = ei-1;
            }
            maxOnes = Math.max(maxOnes,ei-si);
        }
        return maxOnes;
    }

    //Leetcode 1004
    public int longestOnes(int[] nums, int k) {
        int n = nums.length;

        int si=0,ei=0,maxOnes=0,countZeroes=0;

        while(ei<n){
            if(nums[ei++]==0) countZeroes++;

            while(countZeroes>k){
                if(nums[si++]==0) countZeroes--;
            }
            maxOnes = Math.max(maxOnes,ei-si);
        }
        return maxOnes;
    }

    //Leetcode 974
    //Method 1 - 3ms
    public int subarraysDivByK(int[] nums, int k) {
        int[] rem = new int[k];
        rem[0] = 1;
        int sum=0,ans=0;
        int r;
        for(int ele:nums){
            sum += ele;
            r = (sum%k + k)%k;

            ans += rem[r];
            rem[r]++;
        }
        return ans;
    }

    //Method 2 - 21ms
    public int subarraysDivByK_01(int[] nums, int k) {

        HashMap<Integer,Integer> rem = new HashMap<>();
        rem.put(0,1);
        int sum=0,ans=0;
        int r;
        for(int ele:nums){
            sum += ele;
            r = (sum%k + k)%k;

            ans += rem.getOrDefault(r,0);
            rem.put(r,rem.getOrDefault(r,0)+1);
        }
        return ans;
    }

    //Follow up of above question - Longest Subarray Divisible By K
    //https://practice.geeksforgeeks.org/problems/longest-subarray-with-sum-divisible-by-k1259/1
    int longSubarrWthSumDivByK(int a[], int n, int k)
    {
        int[] rem = new int[k];
        Arrays.fill(rem,-2);
        rem[0] = -1;
        int sum=0,maxLen=0;
        int r;
        
        for(int i=0;i<n;i++){
            sum += a[i];
            r =(sum%k + k)%k;
            
           if(rem[r]!=-2) maxLen = Math.max(maxLen,i-rem[r]);
           else rem[r] = i;
        }
        return maxLen;
    }

    //https://practice.geeksforgeeks.org/problems/count-subarrays-with-equal-number-of-1s-and-0s-1587115620/1
    static int countSubarrWithEqualZeroAndOne(int arr[], int n)
    {
        int sum=0,ans=0;
        HashMap<Integer,Integer> hm = new HashMap<>();
        hm.put(0,1);
        
        for(int ele:arr){
            if(ele==0) sum += -1;
            else sum += ele;
            
            ans += hm.getOrDefault(sum,0);
            hm.put(sum,hm.getOrDefault(sum,0)+1);
        }
        
        return ans;
    }

    //Leetcode 525
    //Method 1
    public int findMaxLength01(int[] nums) {
        int n = nums.length;

        int sum=0,maxLen=0;
        HashMap<Integer,Integer> hm = new HashMap<>();
        hm.put(0,-1);

        for(int i=0;i<n;i++){
            if(nums[i]==0) sum += -1;
            else sum += nums[i];

            if(hm.containsKey(sum)) maxLen = Math.max(maxLen,i-hm.get(sum));
            else hm.put(sum,i);
        }
        return maxLen;
    }

    //Method 2
    public int findMaxLength(int[] nums) {
        int n = nums.length;
        int[] map = new int[2*n+1];
        Arrays.fill(map,-2);
        map[n] = -1;
        int sum=0,maxLen=0;

        for(int i=0;i<n;i++){
            if(nums[i]==0) sum += -1;
            else sum += nums[i];

            if(map[sum+n]!=-2) maxLen = Math.max(maxLen,i-map[sum+n]);
            else map[sum+n] = i;
        }
        return maxLen;

    }

    //Leetcode 239
    //Method 1
    public int[] maxSlidingWindow_(int[] nums, int k) {
        int n = nums.length;
        
        int[] ans = new int[n-k+1];
        int idx = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
            return nums[b] - nums[a];
        });

        for(int i=0;i<n;i++){
            while(pq.size()!=0 && pq.peek() <= i-k)
                pq.remove();
            
            pq.add(i);

            if(i>=k-1){
                ans[idx++] = nums[pq.peek()];
            }
        }
        return ans;
    }

    //Method 2
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n-k+1];
        Deque<Integer> dq = new LinkedList<>();
        int idx = 0;
        for(int i=0;i<n;i++){
            while(!dq.isEmpty() && dq.getFirst()<= i-k)
                dq.removeFirst();
            
            while(!dq.isEmpty() && nums[dq.getLast()] <= nums[i])
                dq.removeLast();

            dq.addLast(i);

            if(i>=k-1){
                ans[idx++] = nums[dq.getFirst()];
            }
        }
        return ans;
    }

    //Leetcode 2398
    public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        Deque<Integer> q = new ArrayDeque<>();
        int n = chargeTimes.length;
        long sum = 0;
        int start = 0;
        int maxRobots = 0;
        int currCharge;
        int currCost;
        for(int i=0;i<n;i++){
            currCharge = chargeTimes[i];
            currCost = runningCosts[i];

            while(!q.isEmpty() && chargeTimes[q.getLast()] <= currCharge){
                q.removeLast();
            }

            q.add(i);

            sum += currCost;
            while(!q.isEmpty() && chargeTimes[q.getFirst()] + (i-start+1)*sum > budget){
                if(q.getFirst()==start) q.removeFirst();
                sum -= runningCosts[start++];
            }

            maxRobots = Math.max(maxRobots,i-start+1);
        }

        return maxRobots;
    }

    //Kadane's Algo
    
    // Method 1 - [-1,-7,-8,-9] -> max sum Subarray, if 0(no subarray exist);
    public static int kadanesAlgo(int[] arr){
        int gsum=0,csum=0;

        for(int ele:arr){
            csum += ele;
            if(csum>gsum)
                gsum = csum;
            if(csum<0) csum=0;
        }
        return gsum;
    }

    //Indices of subarray 
    public static int[] kadanesAlgoSubarray(int[] arr){
        int gsum=0,csum=0,csi=0,gsi=0,gei=0;

        for(int i=0;i<arr.length;i++){
            csum += arr[i];
            if(csum>gsum){
                gsum = csum;

                gsi = csi;
                gei = i;
            }
            if(csum<=0){
                csum = 0;
                csi = i+1;
            }
        }

        return new int[] {gsum, gsi,gei};
    }


    // Method 2 - [-1,-7,-8,-9] -> max sum Subarray if -1 (0,0);
    public static int kadanesAlgoGeneric(int[] arr){
        int csum=0,gsum=-(int)1e9;

        for(int ele:arr){
            csum = Math.max(ele,csum+ele);
            gsum = Math.max(gsum,csum);
        }
        return gsum;
    }

    public static int[] kadanesAlgoGenericSubarray(int[] arr){
        int gsum = -(int) 1e9, csum = 0, gsi = 0, gei = 0, csi = 0;

        for(int i=0;i<arr.length;i++){
            csum += arr[i];
            
            if(arr[i]>csum){
                csum = arr[i];
                csi = i;
            }
            if(csum>gsum){
                gsum = csum;
                gsi = csi;
                gei = i;
            }
        }

        return new int[] {gsum,gsi,gei};
    }

    //Leetcode 1191
    class Solution {
        int mod = (int)1e9 + 7;
        public int kadanesAlgo(int[] arr,int k){
            int n = arr.length;
            long gsum=0,csum=0;
    
            for(int i=0; i<k*n; i++){
                int ele = arr[i%n];
                csum += ele;
                if(csum>gsum)
                    gsum = csum;
                if(csum<=0) csum=0;
            }
            return (int)gsum % mod;
        }
        public int kConcatenationMaxSum(int[] arr, int k) {
            long sum = 0;
            for(int i=0;i<arr.length;i++){
                sum += arr[i];
            }
            if(k==1) return kadanesAlgo(arr,1);
            int s = kadanesAlgo(arr,2);
            if(k==2) return s;
            if(sum<=0){
                return s;
            }else{
                return (int) ((s + (k-2)*sum)%mod);
            }
    
        }
    }

    //https://practice.geeksforgeeks.org/problems/maximum-sum-rectangle2948/1
    int maximumSumRectangle(int R, int C, int M[][]) {
        
        int[] colPrefixSum = new int[C];
        int maxSum = -(int)1e9;
        
        for(int fixedRow=0;fixedRow<R;fixedRow++){
            
            Arrays.fill(colPrefixSum,0);
            
            for(int row=fixedRow;row<R;row++){
                
                for(int col=0;col<C;col++)
                    colPrefixSum[col] += M[row][col];
                
                int sum = kadanesAlgoGeneric(colPrefixSum);
                maxSum = Math.max(maxSum,sum);
            }
        }
        return maxSum;
    }

    //Follow Up Question - Print maximum sum rectangle
    public static void printMaximumSumRectangle(int R,int C,int[][] M){
        int[] colPrefixSum = new int[C];
        int maxSum = -(int)1e9;
        int r1=0,r2=0,c1=0,c2=0;
        
        for(int fixedRow=0;fixedRow<R;fixedRow++){
            
            Arrays.fill(colPrefixSum,0);
            
            for(int row=fixedRow;row<R;row++){
                
                for(int col=0;col<C;col++)
                    colPrefixSum[col] += M[row][col];
                
                int[] sum = kadanesAlgoGenericSubarray(colPrefixSum);
                if(sum[0]>maxSum){
                    r1 = fixedRow;
                    r2 = row;
                    c1 = sum[1];
                    c2 = sum[2];
                    maxSum = sum[0];
                }
            }
        }

        for(int i=r1;i<=r2;i++){
            for(int j=c1;j<=c2;j++){
                System.out.print(M[i][j]+" ");
            }
            System.out.println();
        }
    }

    //Leetcode 781
    //Method 1 - 1ms
    public int numRabbits01(int[] answers) {
        int[] freq = new int[1000];
        for(int i=0;i<1000;i++){
            freq[i] = i+1;
        }
        int rabbits = 0;
        for(int ele:answers){
            if(freq[ele]==ele+1 || freq[ele]==0){
                rabbits += ele+1;
                freq[ele] = ele;

            }
            else{
                freq[ele]--;
            }
        }
        return rabbits;
    }

    //Method 2 - 0ms
    public int numRabbits(int[] answers) {
        int[] freq = new int[1000];

        int rabbits = 0;
        for(int ans:answers){
            if(freq[ans]==0 || ((freq[ans]%(ans+1))==0)){
                rabbits += ans+1;
            }
            freq[ans]++;
        }
        return rabbits;
    }

    //Leetcode 1074
    public int subarraySum(int[] arr,int target){
        int count = 0,psum=0;
        HashMap<Integer,Integer> hm = new HashMap<>();
        hm.put(0,1);
        for(int ele:arr){
            psum += ele;
            count += hm.getOrDefault(psum-target,0);
            hm.put(psum,hm.getOrDefault(psum,0)+1);
        }
        return count;
    }
    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[] colPrefixSum = new int[m];
        int ans = 0;

        for(int fixedRow=0;fixedRow<n;fixedRow++){
            Arrays.fill(colPrefixSum,0);

            for(int row=fixedRow;row<n;row++){
                for(int col=0;col<m;col++)
                    colPrefixSum[col] += matrix[row][col];
                ans += subarraySum(colPrefixSum,target);
            }
        }
        return ans;
    }

    //Leetcode 363
    public int maxSumSubmatrix(int[][] matrix, int k) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[] colPrefixSum = new int[m];
        int maxSum = -(int)1e9;

        for(int base=0;base<n;base++){
            Arrays.fill(colPrefixSum,0);
            for(int row=base;row<n;row++){
                int csum=0, gsum=-(int)1e9;
                for(int col=0;col<m;col++){
                    colPrefixSum[col] += matrix[row][col];
                    int ele = colPrefixSum[col];

                    csum = Math.max(ele,csum+ele);
                    gsum = Math.max(gsum,csum);

                    if(gsum==k) return k;
                }

                if(gsum<k){
                    maxSum = Math.max(maxSum,gsum);
                }
                else{
                    TreeSet<Integer> map = new TreeSet<>();
                    map.add(0);
                    int sum = 0;
                    for(int col=0;col<m;col++){
                        sum += colPrefixSum[col];

                        if(map.contains(sum-k)) return k;

                        Integer val = map.ceiling(sum-k);
                        if(val!=null) maxSum = Math.max(maxSum,sum-val);
                        map.add(sum);
                    }
                }

            }
        }
        return maxSum;
    }

    //Leetcode 152
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int l=0,r=0,res = nums[0];

        for(int i=0;i<n;i++){
            l = (l==0 ? 1:l)*nums[i];
            r = (r==0 ? 1:r)*nums[n-1-i];
            res = Math.max(res,Math.max(l,r));
        }
        return res;
    }

    //Leetcode 26
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        int idx = 0;

        for(int i=1;i<n;i++){
            if(nums[i]>nums[idx]){
                nums[++idx] = nums[i];
            }
        }
        return idx+1;
    }

    //Leetcode 27
    public int removeElement(int[] nums, int val) {
        int idx = 0;
        for(int i=0;i<nums.length;i++){
            if(val!=nums[i]){
                nums[idx++] = nums[i];
            }
        }
        return idx;
    }

    //Leetcode 28
    public int strStr(String haystack, String needle) {
        int m = haystack.length();
        int n = needle.length();
        if(n>m) return -1;
        for(int i=0; i<m-n+1; i++){
            if(haystack.substring(i,i+n).equals(needle)) return i;
        }

        return -1;
    }

    //Leetcode 362 - Locked
    //Lintcode 3662
    public class HitCounter {
        private int[] times;
        private int[] hits;
    
        public HitCounter() {
            this.times = new int[300];
            this.hits = new int[300];
        }
    
        public void hit(int timestamp) {
            int idx = timestamp%300;
            if(times[idx]!=timestamp){
                times[idx] = timestamp;
                hits[idx] = 1;
            }else hits[idx]++;
        }
    
        public int getHits(int timestamp) {
            int res = 0;
            for(int i=0;i<300;i++){
                if(timestamp-times[i] < 300){
                    res += hits[i];
                }
            }
            return res;
        }
    }
}