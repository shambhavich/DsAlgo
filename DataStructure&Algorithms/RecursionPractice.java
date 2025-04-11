import java.util.*;
public class RecursionPractice {

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
                count += printPermutations(ros,ans+ch);
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

    
}
