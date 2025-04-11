import java.util.*;
public class GenericTree {
    public static class TreeNode{
        int val;
        ArrayList<TreeNode> childs;

        TreeNode(int val){
            this.val = val;
            childs = new ArrayList<>();
        }
    }

    //Size of Generic Tree
    public static int size(TreeNode root){
        int n = 0;
        for(TreeNode child:root.childs){
            n += size(child);
        }
        return n+1;
    }

    //Height of Generic Tree
    public int height(TreeNode root){

        int h = -1;
        for(TreeNode child:root.childs){
            h = Math.max(h,height(child));
        }
        return h + 1;
    }

    //Maximum Value in GT
    public static int maximumValue(TreeNode root){
        int max = root.val;
        for(TreeNode child:root.childs){
            max = Math.max(max,maximumValue(child));
        }
        return max;
    }

    //Find an element
    //Method 1
    public static boolean find(TreeNode root,int data){
        if(root.val==data) return true;

        boolean res = false;
        for(TreeNode child:root.childs){
            res = res || find(child,data);
        }

        return res;
    }

    //Method 2
    public static int find01(TreeNode root,int data){
        if(root.val==data) return 0;

        int depth = -1;
        for(TreeNode child:root.childs){
            depth = find01(child, data);
            if(depth!=-1) break;
        }

        if(depth!=-1) depth++;
        return depth;
    }

    //Node To Root Path
    private static boolean nodeToRootPath(TreeNode root,int data,ArrayList<TreeNode> ans){
        if(root.val==data){
            ans.add(root);
            return true;
        }

        boolean res = false;
        for(TreeNode child:root.childs){
            res = res || nodeToRootPath(child,data,ans);
            if(res) break;
        }

        if(res) ans.add(root);
        return res;
    }
    public static ArrayList<TreeNode> nodeToRootPath(TreeNode root,int data){
        if(root==null) return new ArrayList<>();

        ArrayList<TreeNode> ans = new ArrayList<>();
        nodeToRootPath(root,data,ans);
        return ans;
    }

    //Lowest Common Ancestor
    public static TreeNode LCA(TreeNode root,int d1,int d2){
        ArrayList<TreeNode> l1 = new ArrayList<>();
        nodeToRootPath(root, d1, l1);

        ArrayList<TreeNode> l2 = new ArrayList<>();
        nodeToRootPath(root, d2, l2);

        int i = l1.size();
        int j = l2.size();

        TreeNode LCA = null;
        while(i>=0 && j>=0){
            if(l1.get(i) != l2.get(j)) break;

            LCA = l1.get(i);
            i--;
            j--;
        }
        return LCA;
    }

    //Burning Tree
    //Method 1
    private static void kDown(TreeNode root,TreeNode blockNode,int time,List<List<Integer>> ans){
        if(root==blockNode) return;

        if(ans.size()==time) ans.add(new ArrayList<>());

        ans.get(time).add(root.val);
        for(TreeNode child:root.childs)
            kDown(child, blockNode, time+1, ans);
    }
    public static List<List<Integer>> burningTree_01(TreeNode root,int target){
        ArrayList<TreeNode> list = nodeToRootPath(root, target);

        TreeNode blockNode = null;
        List<List<Integer>> ans = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            kDown(list.get(i),blockNode,i,ans);
            blockNode = list.get(i);
        }

        return ans;
    }

    //Method 2
    public int burningTree_02(TreeNode root,int target,List<List<Integer>> ans){
        if(root.val==target){
            kDown(root,null,0,ans);
            return 1;
        }

        int time = -1;
        TreeNode blockNode = null;
        for(TreeNode child:root.childs){
            time = burningTree_02(child,target,ans);
            if(time!=-1){
                blockNode = child;
                break;
            }
        }

        if(time!=-1){
            kDown(root, blockNode, time, ans);
            time++;
        }
        return time;
    }
    public List<List<Integer>> burningTree_02(TreeNode root,int target){
        List<List<Integer>> ans = new ArrayList<>();
        burningTree_02(root,target,ans);
        return ans;
    }

    //Line Wise Level Order Traversal
    public static List<List<Integer>> lineWiseLevelOrder(TreeNode root){
        LinkedList<TreeNode> q = new LinkedList<>();
        q.add(root);
        List<List<Integer>> ans = new ArrayList<>();
        int level = 0;
        while(q.size()!=0){
            int size = q.size();
            while(size-->0){
                TreeNode node = q.remove();
                if(ans.size()==level) ans.add(new ArrayList<>());
                ans.get(level).add(node.val);

                for(TreeNode child:node.childs){
                    q.add(child);
                }
            }
            level++;
        }
        return ans;
    }

    //TWO TREES ARE GIVEN - Return whether they are a mirror or not
    public static boolean isMirror(TreeNode node1, TreeNode node2) {
        if (node1.childs.size() != node2.childs.size())
            return false;
        if (node1.val != node2.val)
            return false;

        for (int i = 0, j = node1.childs.size() - 1; j >= 0; i++, j--) {
            TreeNode child1 = node1.childs.get(i);
            TreeNode child2 = node2.childs.get(j);
            if (!isMirror(child1, child2))
                return false;
        }

        return true;
    }

    //Flatten a Generic Tree
    public static TreeNode flatten(TreeNode root){
        if(root.childs.size()==0) return root;

        int n = root.childs.size();
        TreeNode gtail = flatten(root.childs.get(n-1));

        for(int i=n-2;i>=0;i--){
            TreeNode temp = flatten(root.childs.get(i));
            temp.childs.add(root.childs.get(i+1));
            root.childs.remove(i+1);
        }

        return gtail;

    }
}
