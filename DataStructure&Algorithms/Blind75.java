import java.util.*;

public class Blind75 {
    
    //Leetcode 1
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;

        HashMap<Integer,Integer> hm = new HashMap<>();

        for(int i=0;i<n;i++){
            int diff = target - nums[i];
            if(hm.containsKey(diff)){
                return new int[] {hm.get(diff),i};
            }
            hm.put(nums[i],i);
        }
        throw new IllegalArgumentException("No to sum solution");
    }

    //Leetcode 121
    public int maxProfit(int[] prices) {
        int Ti0 = 0;
        int Ti1 = -(int)1e9;

        for(int ele:prices){
            Ti0 = Math.max(Ti0,Ti1+ele);
            Ti1 = Math.max(Ti1,0-ele);
        }
        return Ti0;
    }

    //Leetcode 217
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();

        for(int ele:nums){
            if(set.contains(ele)) return true;
            set.add(ele);
        }
        return false;
    }

    //Leetcode 238
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;

        int[] ans = new int[n];
        int prefixProduct = 1;
        for(int i=0;i<n;i++){
            ans[i] = prefixProduct;
            prefixProduct *= nums[i];
        }

        int suffixProduct = 1;
        for(int i=n-1;i>=0;i--){
            ans[i] *= suffixProduct;
            suffixProduct *= nums[i];
        }
        return ans;
    }

    //Leetcode 53
    public int maxSubArray(int[] nums) {
        int csum = 0,gsum = -(int)1e9;

        for(int ele:nums){
            csum = Math.max(ele,csum+ele);
            gsum = Math.max(gsum,csum);
        }
        return gsum;
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //Leetcode 226
    private TreeNode invertTreeUtil(TreeNode root){
        if(root==null) return null;
        TreeNode left = invertTreeUtil(root.right);
        root.right = invertTreeUtil(root.left);

        root.left = left;
        return root;
    }
    public TreeNode invertTree(TreeNode root) {
        return invertTreeUtil(root);
    }

    
}
