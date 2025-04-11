import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SearchingAndSorting_practice {
    
    //Leetcode 704
    public int search(int[] nums, int target) {
        int n = nums.length;

        int si=0,ei=n-1;

        while(si<=ei){
            int mid = (ei-si)/2 + si;

            if(nums[mid]==target) return mid;

            if(nums[mid]>target) ei = mid-1;

            else si = mid + 1;
        }
        return -1;
    }

    //Binary Search in a sorted array which can have duplicate elements and you return first index of occurence
    public int binarySearchFirstIndex(int[] arr,int target){
        int n = arr.length;

        int si=0,ei=n-1;

        while(si<=ei){
            int mid = (ei-si)/2 + si;

            if(arr[mid]==target){
                if(mid>0 && arr[mid-1]==target) ei = mid-1;
                else return mid;
            }

            else if(arr[mid]>target) ei = mid-1;

            else si = mid+1;
        }
        return -1;
    }

    //Binary Search in a sorted array which can have duplicate elements and you return last index of occurence
    public int binarySearchLastIndex(int[] arr,int target){
        int n = arr.length;

        int si=0,ei=n-1;

        while(si<=ei){
            int mid = (ei-si)/2 + si;

            if(arr[mid]==target){
                if(mid<n-1 && arr[mid+1]==target) si = mid+1;
                else return mid;
            }
            else if(arr[mid]>target) ei = mid-1;

            else si = mid+1;
        }
        return -1;
    }

    //Leetcode 34
    public int[] searchRange(int[] nums, int target) {
        int firstIdx = binarySearchFirstIndex(nums,target);
        if(firstIdx==-1) return new int[]{-1,-1};
        return new int[]{firstIdx,binarySearchLastIndex(nums,target)};
    }

    //Perfect Position of Element
    public int insertLocation(int[] arr,int data){
        int n = arr.length;
        int si=0,ei=n-1;

        while(si<=ei){
            int mid = si + (ei-si)/2;

            if(arr[mid]<=data) si = mid+1;
            else ei = mid-1;
        }

        return si;
    }
    public int perfectPosition(int[] arr,int data){
        int insertPos = insertLocation(arr,data);
        int lastIndex = insertPos - 1;

        return (lastIndex>=0 && arr[lastIndex] == data) ? lastIndex : insertPos;
    }

    //Nearest Element
    public int nearestElement(int[] arr,int data){
        
        int n = arr.length;
        if(data <= arr[0] || data>= arr[n-1])
            return data <= arr[0] ? arr[0] : arr[n-1];

        int si=0,ei=n-1;

        while(si<=ei){
            int mid = (ei-si)/2 + si;
            if(arr[mid]<=data) si = mid+1;
            else ei = mid - 1;
        }

        return data - arr[ei] <= arr[si] - data ? arr[ei] : arr[si];
    }

    //https://www.naukri.com/code360/problems/lower-bound_8165382
    public static int lowerBound(int []arr, int n, int x) {
        if(arr[0]>=x) return 0;
        if(arr[n-1]<x) return n;

        int si=0,ei=n-1;
        int mid;
        while(si<ei){
            mid = (ei-si)/2 + si;
            if(arr[mid]>=x) ei = mid;
            else si = mid+1;
        }

        return si;
    }

    //https://www.naukri.com/code360/problems/implement-upper-bound_8165383
    public static int  upperBound(int []arr, int x, int n){
        if(arr[0]>x) return 0;
        if(arr[n-1]<=x) return n;

        int si=0,ei=n-1;
        int mid;
        while(si<ei){
            mid = (ei-si)/2 + si;
            if(arr[mid]<=x) si = mid+1;
            else ei = mid;
        }

        return si;
    }

    //https://www.naukri.com/code360/problems/ceiling-in-a-sorted-array_1825401
    private static int getFloor(int[] arr,int n,int x){
        int si=0,ei=n-1;
        int mid;
        int ans = -1;
        while(si<=ei){
          mid = (ei-si)/2 + si;
          if(arr[mid]<=x){
            ans = arr[mid];
            si = mid+1;
          }else ei = mid-1;
        }
        return ans;
    }
  
    private static int getCeil(int[] arr,int n,int x){
        int si=0,ei=n-1;
        int mid;
        int ans = -1;
        while(si<=ei){
          mid = (ei-si)/2 + si;
          if(arr[mid]>=x){
            ans = arr[mid];
            ei = mid-1;
          }else si = mid+1;
        }
  
        return ans;
    }
    public static int[] getFloorAndCeil(int[] a, int n, int x) {
        return new int[]{getFloor(a,n,x),getCeil(a,n,x)};
    }

    //Quick Sort
    //https://practice.geeksforgeeks.org/problems/quick-sort/1
    public void swap(int i,int j,int[] arr){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public int segregateData(int si,int ei,int[] arr){

        int pivot = ei;
        int i = si;
        int p = si-1;
        while(i<=ei){
            if(arr[i]<=arr[pivot])
                swap(++p,i,arr);
            i++;
        }
        return p;
    }
    public void quickSort(int si,int ei,int[] arr){
        
       if(si>ei) return;

       int pidx = segregateData(si, ei, arr);

       quickSort(si,pidx-1,arr);
       quickSort(pidx+1,ei,arr);
    }
    public void quickSort(int[] arr){
        quickSort(0,arr.length-1,arr);
    }

    //Leetcode 74
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int si=0,ei=n*m-1;

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

    //https://www.geeksforgeeks.org/problems/row-with-max-1s0023/1
    public int rowWithMax1s(int arr[][]) {
        int n = arr.length;
        int m = arr[0].length;
        
        int ans = 0;
        int ansIdx = 0;
        int si,ei,mid;
        for(int i=0;i<n;i++){
            si = 0;
            ei = m-1;
            while(si<ei){
                mid = (ei-si+1)/2 + si;
                if(arr[i][mid]==0) si = mid;
                else ei = mid-1;
            }
            if(arr[i][si]==0){
                if(ans<(m-si-1)){
                    ans = m-si-1;
                    ansIdx = i;
                }
            }
            else{
                ans = m-si;
                ansIdx = i;
                break;
            }
        }
        return (ans==0) ? -1 : ansIdx;
    }

    //Merge Sort
    void merge(int arr[], int l, int m, int r)
    {
        int n1 = m-l+1;
        int n2 = r-m;
        
        int[] left = new int[n1];
        int[] right = new int[n2];
        int k=0;
        for(int i=l;i<=m;i++)
            left[k++] = arr[i];
        k=0;
        for(int i=m+1;i<=r;i++)
            right[k++] = arr[i];
        
        int i=0,j=0;
        k = l;
        while(i<n1 && j<n2){
            if(left[i]<=right[j]){
                arr[k] = left[i++];
            }else{
                arr[k] = right[j++];
            }
            k++;
        }
        
        while(i<n1){
            arr[k++] = left[i++];
        }
        
        while(j<n2){
            arr[k++] = right[j++];
        }
    }
    void mergeSort(int arr[], int l, int r)
    {
        if(l<r){
            int mid = (r-l)/2 + l;
            
            mergeSort(arr,l,mid);
            mergeSort(arr,mid+1,r);
            
            merge(arr,l,mid,r);
        }
    }
    public void mergeSort(int[] arr){
        mergeSort(arr,0,arr.length-1);
    }

    //https://practice.geeksforgeeks.org/problems/inversion-of-array-1587115620/1
    static long inversionAcrossArray(int l,int mid,int r,long[] arr,long[] sortedArr){
        int lsi = l;
        int lei = mid;
        int rsi = mid+1;
        int rei = r;
        long count = 0;
        int k = 0;
        while(lsi<=lei && rsi<=rei){
            if(arr[lsi]>arr[rsi]){
                count += lei-lsi+1;
                sortedArr[k++] = arr[rsi++];
            }else{
                sortedArr[k++] = arr[lsi++];
            }
        }
        
        while(lsi<=lei) sortedArr[k++] = arr[lsi++];
        while(rsi<=rei) sortedArr[k++] = arr[rsi++];
        
        k=0;
        int i=l;
        while(i<=r){
            arr[i++] = sortedArr[k++];
        }
        return count;
    }
    static long inversionCount(int l,int r,long[] arr,long[] sortedArr){
        if(l>=r) return 0;
        int mid = (r-l)/2 + l;
        
        long L = inversionCount(l,mid,arr,sortedArr);
        long R = inversionCount(mid+1,r,arr,sortedArr);
        
        return (L + R + inversionAcrossArray(l,mid,r,arr,sortedArr) );
    }
    static long inversionCount(long arr[], long N)
    {
        if(N<=1) return 0;
        long[] sortedArr = new long[(int)N];
        return inversionCount(0,(int)N-1,arr,sortedArr);
        
    }

    //Leetcode 240
    public boolean searchMatrix2(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int r=0,c=m-1;
        while(r<n && c>=0){
            if(matrix[r][c]==target) return true;
            else if(matrix[r][c]<target) r++;
            else c--;
        }
        return false;
    }

    //Leetcode 1901
    private int findMaxIdx(int c,int n,int[][] mat){
        int max = mat[0][c];
        int maxIdx = 0;
        for(int i=1;i<n;i++){
            if(mat[i][c]>max){
                max = mat[i][c];
                maxIdx = i;
            }
        }
        return maxIdx;
    }
    public int[] findPeakGrid(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;

        int si = 0;
        int ei = m-1;
        int mid;
        while(si<=ei){
            mid = (ei-si)/2 + si;
            int idx = findMaxIdx(mid,n,mat);
            int left = (mid>0) ? mat[idx][mid-1] : -1;
            int right = (mid<m-1) ? mat[idx][mid+1] : -1;
            if(mat[idx][mid]>left && mat[idx][mid]>right) return new int[]{idx,mid};
            else if(mat[idx][mid]<left) ei = mid-1;
            else si = mid+1;
        }
        return new int[]{-1,-1};
    }

    //Leetcode 2387 - Locked
    //https://www.geeksforgeeks.org/problems/median-in-a-row-wise-sorted-matrix1527/1
    private int countSmallEqual(int[][] matrix,int n,int m,int x){
        int count = 0;
        for(int i=0;i<n;i++){
            count += upperBound(matrix[i],x,m);
        }
        return count;
    }
    int median(int[][] matrix, int n, int m) {
        int si = (int)1e7;
        int ei = 0;
        
        for(int i=0;i<n;i++){
            si = Math.min(si,matrix[i][0]);
            ei = Math.max(ei,matrix[i][m-1]);
        }
        
        int mid;
        int req = (n*m)/2;
        
        while(si<ei){
            mid = (ei-si)/2 + si;
            int smallEqual = countSmallEqual(matrix,n,m,mid);
            if(smallEqual<=req) si = mid+1;
            else ei = mid;
        }
        return si;
    }

    //Leetcode 33
    public int search1(int[] nums, int target) {
        int n = nums.length;
        int si=0,ei=n-1;
        int mid;

        while(si<=ei){
            mid = (ei-si)/2 + si;
            if(nums[mid]==target) return mid;
            else if(nums[mid]>=nums[si]){
                if(target<nums[mid] && nums[si]<=target)
                    ei = mid-1;
                else si = mid+1;
            }
            else{
                if(nums[mid]<target && nums[ei]>=target)
                    si = mid+1;
                    else ei = mid-1;
            }
        }
        return -1;
    }

    //Leetcode 81
    //[2,5,6,7,0,0,1,2] tar = 0 //[1,1,1,1,1,1,1,1,1,1,1,6,1,1,1,1,1] tar = 6;
    public boolean search2(int[] nums, int target) {
        int n = nums.length;
        int si = 0,ei = n-1;
        int mid;

        while(si<=ei){
            mid = (ei-si)/2 + si;
            if(nums[mid]==target || nums[si]==target || nums[ei]==target) return true;
            if(nums[mid]>nums[si]){
                if(nums[mid]>target && nums[si]<target) ei = mid-1;
                else si = mid+1;
            }else if(nums[mid]<nums[ei]){
                if(nums[mid]<target && target<nums[ei]) si = mid+1;
                else ei = mid-1;
            }else {
                si++;
                ei--;
            }
        }

        return false;
    }

    //Leetcode 153
    public int findMin(int[] nums) {
        int n = nums.length;
        int si=0,ei=n-1;
        if(nums[si]<=nums[ei]) return nums[si];
        int mid;
        while(si<ei){
            mid = (ei-si)/2 + si;
            if(nums[mid]<nums[ei]) ei = mid;
            else si = mid+1;
        }
        return nums[si];
    }

    //Leetcode 154
    public int findMin_(int[] nums) {
        int n = nums.length;
        int si=0,ei=n-1;
        if(nums[si]<nums[ei]) return nums[si];
        int mid;
        while(si<ei){
            mid = (ei-si)/2 + si;
            if(nums[mid]<nums[ei])
                ei = mid;
            else if(nums[mid]>nums[ei])
                si = mid + 1;
            else ei--;
        }
        return nums[si];
    }

    //Leetcode 162
    public int findPeakElement(int[] nums) {
        int n = nums.length;
        if(n==1) return 0;
        if(nums[0]>nums[1]) return 0;
        if(nums[n-2]<nums[n-1]) return n-1;

        int si=1,ei=n-2;
        int mid;
        while(si<ei){
            mid = (ei-si)/2 + si;
            if(nums[mid]>nums[mid+1] && nums[mid]>nums[mid-1]){
                return mid;
            }
            else if(nums[mid+1] > nums[mid-1]) si = mid+1;
            else ei = mid-1;
        }

        return si;
    }

    //Leetcode 69
    public int mySqrt(int x) {
        if(x==0 || x==1) return x;
        long si=1,ei=x;
        long mid;
        while(si<=ei){
            mid = (si+ei)/2;
            double val1 = Math.pow(mid,2);
            if( val1 <=x && x < (Math.pow(mid+1,2))) return (int)mid;
            if(x < val1){
                ei = mid-1;
            } else{
                si = mid+1;
            }
        }

        return -1;

    }

    //https://www.geeksforgeeks.org/problems/find-nth-root-of-m5843/1
    private int nthRootUtil(int mid,int n,int m){
        long ans = 1;
        for(int i=1;i<=n;i++){
            ans = ans*mid;
            if(ans>m) return 2;
        }
        if(ans==m) return 1;
        return 0;
    }
    public int NthRoot(int n, int m)
    {
        if(m==0 || m==1) return m;
        int si=1,ei=m;
        int mid;
        while(si<=ei){
            mid = (si+ei)/2;
            int midN = nthRootUtil(mid,n,m);
            if(midN==1) return mid;
            else if(midN==2) ei = mid-1;
            else si = mid+1; 
        }
        return -1;
    }

    //https://www.geeksforgeeks.org/problems/rotation4723/1
    public int findKRotation(List<Integer> arr) {
        int n = arr.size();
        int si=0,ei=n-1;
        if(arr.get(si)<arr.get(ei)) return 0;
        
        int mid;
        
        while(si<ei){
            mid = (ei-si)/2 + si;
            if(arr.get(mid)<arr.get(ei)) ei = mid;
            else if(arr.get(mid)>arr.get(ei)) si = mid+1;
            else ei--;
        }
        return si;
    }

    //Leetcode 540
    public int singleNonDuplicate(int[] nums) {
        int n = nums.length;
        int si=0,ei=n-1;
        int mid;

        while(si<=ei){
            mid = (ei-si)/2 + si;
            if(mid>0 && nums[mid-1]==nums[mid]){
                if((ei-mid)%2==0) ei = mid-2;
                else si = mid+1;
            }else if(mid<n-1 && nums[mid]==nums[mid+1]){
                if((mid-si)%2==0) si = mid+2;
                else ei = mid-1;
            }else return nums[mid];
        }
        return -1;
    }

    //Leetcode 167
    public int[] twoSum(int[] numbers, int target) {
        int n = numbers.length;
        int si=0,ei=n-1;
        int sum;
        while(si<ei){
            sum = numbers[si] + numbers[ei];
            if(sum==target) return new int[]{si+1,ei+1};
            else if(sum<target) {
                si++;
                while (si < ei && numbers[si] == numbers[si - 1]) si++;
            }
            else {
                ei--;
                while(si<ei && numbers[ei] == numbers[ei+1]) ei--;
            }
        }
        return new int[]{};
    }

    //Two Sum - Return List<List<Integer>> ans

    //Follow up Leetcode 167 - All the pairs possible that sum to target
    public List<List<Integer>> twoSum_list(int[] numbers,int target,int si,int ei){
        List<List<Integer>> ans = new ArrayList<>();
        while(si<ei){
            int sum = numbers[si] + numbers[ei];
            if(sum==target){
                ans.add(Arrays.asList(numbers[si],numbers[ei]));
                si++;
                ei--;

                while(si<ei && numbers[si]==numbers[si-1]) si++;
                while(si<ei && numbers[si]==numbers[ei+1]) ei--;
            }else if(sum<target) si++;
            else ei--;
        }
        return ans;
    }
    public List<List<Integer>> twoSum_list(int[] numbers,int target){
        int n = numbers.length;
        int si=0,ei=n-1;
        return twoSum_list(numbers, target, si, ei);
    }

    //Leetcode 15
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        if(n<=2) return ans;
        Arrays.sort(nums);

        int j,k,sum;

        for(int i=0;i<n;i++){
            while(i!=0 && i<n && (nums[i]==nums[i-1])) i++;
            j=i+1;
            k=n-1;
            while(j<k){
                sum = nums[i] + nums[j] + nums[k];
                if(sum==0){
                    List<Integer> res = new ArrayList<>();
                    res.add(nums[i]);
                    res.add(nums[j]);
                    res.add(nums[k]);
                    ans.add(res);
                    j++;
                    k--;
                    while(j<k && nums[j-1]==nums[j]) j++;
                    while(j<k && nums[k]==nums[k+1]) k--;
                }
                else if(sum<0) j++;
                else k--;
            }
        }

        return ans;
    }

    //Leetcode 18
    public List<List<Integer>> fourSum(int[] nums, int target) {
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        if(n<4) return ans;

        Arrays.sort(nums);
        long sum;
        int k,l;

        for(int i=0;i<n;i++){
            if(i!=0 && i<n && (nums[i]==nums[i-1])) continue;
            for(int j=i+1;j<n;j++){
                if(j>i+1 && nums[j]==nums[j-1]) continue;
                k=j+1;
                l=n-1;
                while(k<l){
                    sum = (long) nums[i] + nums[j] + nums[k] + nums[l];
                    if(sum==target){
                        ans.add(Arrays.asList(nums[i],nums[j],nums[k],nums[l]));
                        k++;
                        l--;
                        while(k<l && nums[k]==nums[k-1]) k++;
                        while(k<l && nums[l]==nums[l+1]) l--;
                    }
                    else if(sum<target) k++;
                    else l--;
                }
            }
        }

        return ans;
    }

    //K Sum
    public void prepareAns(List<List<Integer>> ans,List<List<Integer>> smallAns,int fixEle){
        for(List<Integer> arr: smallAns){
            List<Integer> list = new ArrayList<>();
            list.add(fixEle);
            for(int ele:arr) list.add(ele);
            ans.add(list);
        }
    }
    public List<List<Integer>> kSum(int[] nums,int target,int k,int si,int ei){
        if(k==2) return twoSum_list(nums,target,si,ei);

        List<List<Integer>> ans = new ArrayList<>();
        for(int i=si; i<ei;){
            List<List<Integer>> smallAns = kSum(nums,target-nums[i],k-1,i+1,ei);
            if(smallAns!=null) prepareAns(ans,smallAns,nums[i]);
            i++;
            while(i<ei && nums[i]==nums[i-1]) i++;
        }
        return ans;
    }
    public List<List<Integer>> kSum(int[] nums,int target,int k){
        if(k==2) return twoSum_list(nums,target);
        Arrays.sort(nums);
        return kSum(nums,target,k,0,nums.length-1);
    }

    //Leetcode 454
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        HashMap<Integer,Integer> hm = new HashMap<>();

        for(int ele1: nums1)
            for(int ele2: nums2)
                hm.put(ele1+ele2,hm.getOrDefault(ele1+ele2,0)+1);

        int count=0,target=0;

        for(int ele1:nums3)
            for(int ele2:nums4)
                count += hm.getOrDefault(target -(ele1+ele2),0);
        return count;
    }

    //Leetcode 658
    //Method 1
    public List<Integer> findClosestElements_01(int[] arr, int k, int x) {
        List<Integer> ans = new ArrayList<>();
        int n = arr.length;
        if(x<=arr[0])
            for(int i=0;i<k;i++)
                ans.add(arr[i]);

        else if(x>=arr[n-1])
            for(int i=n-k;i<n;i++)
                ans.add(arr[i]);
        else{
            int idx = insertLocation(arr,x);
            int lr = Math.max(0,idx-k);
            int rr = Math.min(idx+k,n-1);

            while((rr-lr+1)>k){
                if((x-arr[lr])>(arr[rr]-x))
                    lr++;
                else rr--;
            }

            for(int i=lr;i<=rr;i++) ans.add(arr[i]);
        }
        return ans;
            
    }

    //Method 2
    public List<Integer> findClosestElements_02(int[] arr, int k, int x) {
        List<Integer> ans = new ArrayList<>();
        int n = arr.length;

        if(x<=arr[0])
            for(int i=0;i<k;i++) ans.add(arr[i]);
        
        else if(x>=arr[n-1])
            for(int i=n-k;i<n;i++) ans.add(arr[i]);
        
        else{
            int lr = 0,rr = n-k;
            int mid;
            while(lr<rr){
                mid = (lr+rr)/2;

                if(x-arr[mid]>arr[mid+k]-x) lr = mid+1;
                else rr = mid;
            }
            for(int i=lr;i<lr+k;i++) ans.add(arr[i]);
        }

        return ans;
    }

    //Leetcode 300
    public int insertPosition(List<Integer> list,int ele){
        int si=0,ei=list.size()-1;
        int mid;

        while(si<=ei){
            mid = (ei-si)/2 + si;
            if(list.get(mid)<=ele) si = mid+1;
            else ei = mid-1;
        }

        return (si-1>=0 && (list.get(si-1)==ele)) ? si-1 : si;
    }
    public int lengthOfLIS(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for(int ele:nums){
            int idx = insertPosition(list,ele);
            if(idx==list.size())
                list.add(ele);
            else
                list.set(idx,ele);
        }
        return list.size();
    }

    //Leetcode 875
    public boolean isPossibleToEat(int h,int eatingSpeed,int[] piles){
        int hr = 0;
        for(int i=piles.length-1;i>=0;i--){
            hr += Math.ceil(piles[i]/(eatingSpeed*1.0));
            if(hr>h) return false;
        }
        return true;
    }
    public int minEatingSpeed(int[] piles, int h) {
        int n = piles.length;
        Arrays.sort(piles);

        int si=0,ei=piles[n-1];
        int mid;
        while(si<ei){
            mid = (ei-si)/2 + si;
            if(!isPossibleToEat(h,mid,piles))
                si = mid+1;
            else ei = mid;
        }
        return si;
    }

    //Leetcode 1482
    private boolean isPossibleToMakeMBouquets(int days,int[] bloomDay,int m,int k){
        int count = 0;
        int n = bloomDay.length;
        int bloom;
        for(int i=0;i<n;i++){
            bloom = bloomDay[i];
            if(days >= bloom) count++;
            else{
                m = m - (count/k);
                count = 0;
                if(m <=0) return true;
                if(((n-i-1)/k) < m) return false;
            }
        }
        m -= (count/k);
        return m<=0;
    }
    public int minDays(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        long val = (long)m*k;
        if(val > n) return -1;

        int si = (int)1e9;
        int ei = -(int)1e9;

        for(int bloom : bloomDay){
            si = Math.min(si,bloom);
            ei = Math.max(ei,bloom);
        }

        int mid;
        while(si<ei){
            mid = (ei+si)/2;

            if(isPossibleToMakeMBouquets(mid,bloomDay,m,k)) ei = mid;
            else si = mid+1;
        }
        return si;
    }

    //Leetcode 2226
    public int maximumCandies(int[] candies, long k) {
        long total = 0;
        for(int candy:candies){
            total += candy;
        }

        int si = 0;
        int ei = (int)(total/k);
        int mid,ans=0;
        long count;
        while(si<ei){
            mid = (ei-si+1)/2 + si;
            count = 0;
            for(int candy:candies){
                count += candy/mid;
                if(count >= k) break;
            }
            if(count >= k) {
                ans = Math.max(ans,mid);
                si = mid;
            }
            else ei = mid-1;
        }
        return si;
    }

    //Leetcode 1011
    public boolean isPossibleToShip(int capacity,int days,int[] weights){
        int d = 1;
        int totalWeightPerDay = 0;
        for(int w:weights){
            totalWeightPerDay += w;
            if(totalWeightPerDay>capacity){
                d++;
                totalWeightPerDay = w;
            }
            if(d>days) return false;
        }
        return true;
    }
    public int shipWithinDays(int[] weights, int days) {
        int maxEle=0,sum=0;
        for(int ele:weights){
            maxEle = Math.max(ele,maxEle);
            sum += ele;
        }
        int si=maxEle,ei=sum;
        int mid;
        while(si<ei){
            mid = (ei-si)/2 + si;
            if(!isPossibleToShip(mid,days,weights))
                si = mid+1;
            else ei =  mid;
        }
        return si;
    }

    //https://leetcode.com/discuss/interview-question/348510/Google-or-OA-2019-or-Maximum-Area-Serving-Cake
    //Maximum Area Serving Cake
    public boolean isPossibleToServe(double cakeArea,int guests,int[] radius){
        int g = 0;
        for(int i=radius.length-1;i>=0;i--){
            double area = Math.PI * radius[i] * radius[i];
            g += Math.floor(area/cakeArea);
            if(g>=guests) return true;
        }
        return false;
    }
    public double maximumAreaCake(int[] radius,int guests){
        double si = 0.0,ei = 1e7;
        while((ei-si)>1e-5){
            double cakeArea = (si + ei)/2.0;
            if(!isPossibleToServe(cakeArea,guests,radius))
                ei = cakeArea - 1e-5;
            else si = cakeArea;
        }
        return si;
    }

    //Leetcode 1283
    public int smallestDivisor(int[] nums, int threshold) {
        int si = 1;
        int ei = 1;
        for(int num:nums) ei = Math.max(ei,num);
        ei++;

        int mid,sum;
        while(si<ei){
            mid = (ei-si)/2 + si;
            sum = 0;
            for(int num:nums){
                //(num+mid-1) makes sure that we get the ans rounded to nearest integer greater than or equal to 
                sum += (num + mid - 1)/mid;
                if(sum>threshold) break;
            }
            if(sum>threshold) si = mid+1;
            else ei = mid;
        }
        return si;
    }

    //Leetcode 1539
    public int findKthPositive(int[] arr, int k) {
        int n = arr.length;
        int si=0,ei=n-1;
        int mid;
        while(si<=ei){
            mid = (ei-si)/2 + si;
            int missing = arr[mid] - (mid+1);
            if(missing<k) si = mid+1;
            else ei = mid-1;
        }

        //...........ei si......... // Till 0 to ei missing<k , si to n-1 missing>=k
        // Therefore the kth missing number is equal to arr[ei] + k - (arr[ei] - (ei+1))
        // arr[ei] + (k - missing till ei)
        return k + ei + 1;
    }

    //https://www.geeksforgeeks.org/problems/aggressive-cows/1
    private static boolean canPlace(int dist,int k,int n,int[] stalls){
        int last = stalls[0];
        int count = 1;
        for(int i=1;i<n;i++){
            if(stalls[i] - last>=dist){
                count++;
                last = stalls[i];
            }
            if(count>=k) return true;
        }
        return false;
    }
    public static int solve(int n, int k, int[] stalls) {
        Arrays.sort(stalls);
        int si = 0;
        int ei = stalls[n-1] - stalls[0];
        int mid;
        while(si<ei){
            mid = (ei-si+1)/2 + si;
            if(canPlace(mid,k,n,stalls)) si = mid;
            else ei = mid-1;
        }
        
        return si;
    }

    //https://www.geeksforgeeks.org/problems/allocate-minimum-number-of-pages0937/1
    private static boolean isValid(int pages,int n,int[] books,int m){
        int students = 1;
        int pagesStudent = 0;
        
        for(int i=0;i<n;i++){
            if(pagesStudent+books[i]<=pages){
                pagesStudent += books[i];
            }
            else{
                students++;
                pagesStudent = books[i];
            }
            if(students>m) return false;
        }
        return true;
    }
    public static long findPages(int n, int[] books, int m) {
        if(m>n) return -1;
        
        int si = 0;
        int ei = 0;
        for(int pages:books){
            si = Math.max(si,pages);
            ei += pages;
        }
        
        int mid;
        while(si<ei){
            mid = (ei-si)/2 + si;
            if(isValid(mid,n,books,m)){
                ei = mid;
            }
            else si = mid+1;
        }
        
        return ei;
    }

    //Leetcode 410
    private boolean canSplit(int largestSum,int[] nums,int k){
        int sum = 0;
        int splits = 1;
        for(int num:nums){
            if(sum+num <= largestSum) sum += num;
            else{
                sum = num;
                splits++;
            }
            if(splits>k) return false;
        }
        return true;
    }
    public int splitArray(int[] nums, int k) {
        int n = nums.length;
        int si = 0;
        int ei = 0;
        for(int i=0;i<n;i++){
            si = Math.max(si,nums[i]);
            ei += nums[i];
        }

        int mid;
        while(si<ei){
            mid = (ei-si)/2 + si;
            if(canSplit(mid,nums,k)){
                ei = mid;
            }else si = mid+1;
        }

        return ei;
    }

    //https://www.geeksforgeeks.org/problems/the-painters-partition-problem1535/1
    private static boolean canPaint(long minTime,int[] arr,int k){
        long time = 0;
        int workers = 1;
        for(int len:arr){
            if(time+len>minTime){
                time = len;
                workers++;
            }else{
                time += len;
            }
            if(workers>k) return false;
        }
        return true;
    }
    static long minTime(int[] arr,int n,int k){
        long si = 0;
        long ei = 0;
        for(int len:arr){
            si = Math.max(si,len);
            ei += len;
        }
        
        long mid;
        while(si<ei){
            mid = (ei-si)/2 + si;
            if(canPaint(mid,arr,k)) ei = mid;
            else si = mid+1;
        }
        return ei;
    }

    //Leetcode 774 - Locked
    //Lintcode 848
    private boolean isPossible(double dist,int n,int[] stations,int k){
        int count = 0;
        for(int i=1;i<n;i++){
            count += (stations[i]-stations[i-1])/dist;
            if(count>k) return false;
        }
        return true;
    }
    public double minmaxGasDist(int[] stations, int k) {
        int n = stations.length;
        double si = 0.0;
        double ei = 0.0;
    
        for(int i=1;i<n;i++){
            ei = Math.max(ei,(double)(stations[i]-stations[i-1]));
        }

        double mid;
        while((ei-si)>1e-6){
            mid = (si+ei)/2.0;
            if(isPossible(mid,n,stations,k)) ei = mid;
            else si = mid + 1e-6;
        }

        return ei;
    }

    //Leetcode 4
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if(nums1.length>nums2.length) return findMedianSortedArrays(nums2,nums1);

        int n1 = nums1.length;
        int n2 = nums2.length;
        int n = n1+n2;
        int leftLength = (n1+n2+1)/2;

        int si = 0;
        int ei = n1;
        int mid1,mid2,l1,l2,r1,r2;
        while(si<=ei){
            mid1 = (ei+si)/2;
            mid2 = leftLength - mid1;

            l1 = (mid1>0) ? nums1[mid1-1] : -(int)1e8;
            l2 = (mid2>0) ? nums2[mid2-1] : -(int)1e8;
            r1 = (mid1<n1) ? nums1[mid1] : (int)1e8;
            r2 = (mid2<n2) ? nums2[mid2] : (int)1e8;

            if(l1<=r2 && l2<=r1){
                if(n%2==1) return Math.max(l1,l2);
                else return ((double)(Math.max(l1,l2) + Math.min(r1,r2)))/2.0;
            }else if(l1>r2){
                ei = mid1-1;
            }else si = mid1+1;
        }

        return 0;
    }

    //https://www.geeksforgeeks.org/problems/k-th-element-of-two-sorted-array1317/1
    public long kthElement(int k, int arr1[], int arr2[]) {
        if(arr1.length>arr2.length) return kthElement(k,arr2,arr1);
        int n1 = arr1.length;
        int n2 = arr2.length;
        
        //int leftLength = k;
        int si = Math.max(0,k-n2);
        int ei = Math.min(k,n1);
        
        while(si<=ei){
            int mid1 = (si+ei)/2;
            int mid2 = k - mid1;
            
            int l1 = (mid1>0) ? arr1[mid1-1] : -(int)1e8;
            int l2 = (mid2>0) ? arr2[mid2-1] : -(int)1e8;
            int r1 = (mid1<n1) ? arr1[mid1] : (int)1e8;
            int r2 = (mid2<n2) ? arr2[mid2] : (int)1e8;
            
            if(l1<=r2 && l2<=r1){
                return Math.max(l1,l2);
            }
            else if(l1>r2){
                ei = mid1-1;
            }else si = mid1+1;
        }
        return 0;
    }

    //Leetcode 1231 - Locked
    //Lintcode 1817
    private boolean canDistribute(int sweet,int[] sweetness,int k){
        int count = 0;
        int curr = 0;

        for(int s:sweetness){
            curr += s;
            if(curr>=sweet){
                count++;
                curr = 0;
            }
            if(count>k) return true;
        }
        return false;
    }
    public int maximizeSweetness(int[] sweetness, int k) {
        int n = sweetness.length;
        int si = 0;
        int ei = 0;
        for(int i=0;i<n;i++){
            si = Math.min(si,sweetness[i]);
            ei += sweetness[i];
        }

        int mid;
        while(si<ei){
            mid = (ei-si+1)/2 + si;
            if(canDistribute(mid,sweetness,k)){
                si = mid;
            }else ei = mid-1;
        }

        return si;
    }

    //Leetcode 2064
    public int minimizedMaximum(int n, int[] quantities) {
        int len = quantities.length;

        int si = 1;
        int ei = 0;
        for(int i=0;i<len;i++) ei = Math.max(ei,quantities[i]);

        int mid,count;
        while(si<ei){
            mid = (ei-si)/2 + si;
            count = 0;

            for(int quantity:quantities){
                count += (quantity + mid -1)/mid;
            }

            if(count<=n) ei = mid;
            else si = mid+1;
        }
        return si;
    }

    //Leetcode 1552
    private boolean canPlaceBalls(int force,int n,int[] position,int m){
        int count = 1;
        int prevPosition = position[0];
        for(int i=1;i<n;i++){
            if(position[i]-prevPosition>=force){
                count++;
                prevPosition = position[i];
            }
            if(count>=m) return true;
        }
        return false;
    }
    public int maxDistance(int[] position, int m) {
        int n = position.length;
        Arrays.sort(position);
        int si = 1;
        int ei = (position[n-1] - position[0])/(m-1);

        int mid;
        while(si<ei){
            mid = (ei-si+1)/2 + si;
            if(canPlaceBalls(mid,n,position,m)){
                si = mid;
            }else ei = mid-1;
        }

        return si;
    }

    //Leetcode 2594
    public long repairCars(int[] ranks, int cars) {
        int min = (int)1e8;
        int[] count = new int[101];
        for(int rank:ranks){
            count[rank]++;
            min = Math.min(min,rank);
        }

        long si = 0,ei = (long) min*cars*cars;
        long mid,sum;
        while(si<ei){
            mid = (si+ei) >> 1;

            sum = 0;
            for(int rank=min;rank<=100 && sum<cars;rank++){
                sum += (long) Math.sqrt(mid/rank)*count[rank];
            }

            if(sum>=cars) ei = mid;
            else si = mid+1;
        }
        return si;
    }

    //Leetcode 134
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int sp=0;
        int extraGas = 0;
        int deficit = 0;
        int n =  gas.length;
        for(int i=0;i<n;i++){
            extraGas += gas[i]-cost[i];
            if(extraGas<0){
                deficit += extraGas;
                extraGas = 0;
                sp = i+1;
            }
        }

        return (sp==n || extraGas+deficit<0) ? -1 : sp;
    }

}
