import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;



public class Trees_practice {
    public static class TreeNode{
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

    //Leetcode 94
    public void inorderTraversal(TreeNode root,List<Integer> ans){
        if(root==null) return;

        inorderTraversal(root.left,ans);
        ans.add(root.val);
        inorderTraversal(root.right,ans);
    }
    public List<Integer> inorderTraversal(TreeNode root) {
        if(root==null) return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();
        inorderTraversal(root,ans);
        return ans;
    }

    //Leetcode 144
    public void preorderTraversal(TreeNode root,List<Integer> ans){
        if(root==null) return;

        ans.add(root.val);
        preorderTraversal(root.left,ans);
        preorderTraversal(root.right,ans);
    }
    public List<Integer> preorderTraversal(TreeNode root) {
        if(root==null) return new ArrayList<Integer>();

        List<Integer> ans = new ArrayList<>();
        preorderTraversal(root,ans);
        return ans;
    }

    //Leetcode 145
    public void postorderTraversal(TreeNode root,List<Integer> ans){
        if(root==null) return;

        postorderTraversal(root.left,ans);
        postorderTraversal(root.right,ans);
        ans.add(root.val);
    }
    public List<Integer> postorderTraversal(TreeNode root) {
        if(root==null) return new ArrayList<Integer>();

        List<Integer> ans = new ArrayList<>();
        postorderTraversal(root,ans);
        return ans;
    }

    //Binary Tree Level Order Traversal
    //Method 1
    public static void BFS_01(TreeNode root){
        if(root==null) return;
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);

        while(que.size()!=0){
            TreeNode node = que.remove();
            System.out.print(node.val+" ");

            if(node.left!=null) que.add(node.left);
            if(node.right!=null) que.add(node.right);
        }
    }

    //Method 2
    public static void BFS_02(TreeNode root){
        if(root==null) return;
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);
        que.add(null);

        while(que.size()!=1){
            TreeNode node = que.remove();
            System.out.print(node.val+" ");

            if(node.left!=null) que.add(node.left);
            if(node.right!=null) que.add(node.right);

            if(que.peek()==null){
                System.out.println();
                que.remove();
                que.add(null);
            }
        }
    }

    //Method 3
    public static void BFS_03(TreeNode root){
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);
        int level = 0;

        while(que.size()!=0){
            int size = que.size();
            System.out.print("Level "+level+" -> ");

            while(size-->0){
                TreeNode node = que.remove();
                System.out.print(node.val+" ");

                if(node.left!=null) que.add(node.left);
                if(node.right!=null) que.add(node.right);
            }
            System.out.println();
            level++;
        }
    }

    //Leetcode 102 
    public void levelOrder(TreeNode root,List<List<Integer>> ans){
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);
        int level = 0;

        while(que.size()!=0){
            int size = que.size();
            ans.add(new ArrayList<>());

            while(size-->0){
                TreeNode node = que.remove();
                ans.get(level).add(node.val);

                if(node.left!=null) que.add(node.left);
                if(node.right!=null) que.add(node.right);
            }
            level++;
        }
    }
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if(root==null) return ans;
        levelOrder(root,ans);
        return ans;        
    }

    //Construct Tree

    static int idx = 0;
    public static TreeNode constructTree(int[] arr){
        if(idx >= arr.length || arr[idx]==-1){
            idx++;
            return null;
        }

        TreeNode node = new TreeNode(arr[idx++]);
        node.left = constructTree(arr);
        node.right = constructTree(arr);

        return node;
    }

    public static void display(TreeNode node){
        if(node==null) return;

        StringBuilder sb = new StringBuilder();
        sb.append((node.left==null ? "." : node.left.val + ""));
        sb.append(" <- " + node.val + " -> ");
        sb.append((node.right == null ? "." : node.right.val + ""));
        System.out.println(sb);

        display(node.left);
        display(node.right);
    }

    public static int size(TreeNode node){
        if(node==null) return 0;

        return size(node.left) + size(node.right) + 1;
    }

    public static int height(TreeNode node){
        if(node==null) return -1;

        return Math.max(height(node.left),height(node.right)) + 1;
    }

    public static boolean find(TreeNode node,int data){
        if(node==null) return false;

        if(node.val==data) return true;

        return find(node.left,data) || find(node.right,data);
    }

    //Node to Root Path
    public static boolean nodeToRootPath(TreeNode root,int data,List<TreeNode> ans){
        if(root==null) return false;

        if(root.val==data){
            ans.add(root);
            return true;
        }

        boolean res = false;
        res = nodeToRootPath(root.left, data, ans) || nodeToRootPath(root.right, data, ans);
        if(res){
            ans.add(root);
            return true;
        }
        return res;
    }

    //Root to Node Path
    public static boolean rootToNodePath(TreeNode root,int data,List<TreeNode> ans){
        if(root==null) return false;

        if(root.val == data){
            ans.add(root);
            return true;
        }
        ans.add(root);
        boolean res = rootToNodePath(root.left, data, ans)||rootToNodePath(root.right, data, ans);
        if(!res){
            ans.remove(ans.size()-1);
        }
        return res;
    }

    //Leetcode 236
    //Method 1
    public TreeNode lowestCommonAncestor_01(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> p1 = new ArrayList<>();
        List<TreeNode> p2 = new ArrayList<>();

        nodeToRootPath(root,p.val,p1);
        nodeToRootPath(root,q.val,p2);

        int i = p1.size()-1;
        int j = p2.size()-1;

        TreeNode LCA = null;
        while(i>=0 && j>=0){
            if(p1.get(i)==p2.get(j)) LCA = p1.get(i);
            else break;
            i--;
            j--;
        }
        return LCA;
    }

    //Method 2
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null || p==root || q==root)
            return root;
        
        TreeNode left = lowestCommonAncestor(root.left,p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);
        if(left==null)
            return right;
        else if(right==null)
            return left;
        else
            return root;
    }

    //Method 3
    class Solution {
        TreeNode LCA = null;
        private boolean lowestCommonAncestorUtil(TreeNode root,TreeNode p,TreeNode q){
            if(root==null) return false;
            boolean sf = false;
            if(root.val==p.val||root.val==q.val) sf = true;
            if(LCA!=null) return true;
            boolean lf = lowestCommonAncestorUtil(root.left,p,q);
            if(LCA!=null)return true;
            boolean rf = lowestCommonAncestorUtil(root.right,p,q);
            if((lf&&rf) || (sf&lf) || (sf&rf)) LCA = root;
            return lf||rf||sf;
        }
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            lowestCommonAncestorUtil(root,p,q);
            return LCA;
        }
    }

    //Leetcode 543
    //Method 1
    public int diameterOfBinaryTree_01(TreeNode root) {
        if(root==null) return 0;

        int ld = diameterOfBinaryTree_01(root.left);
        int rd = diameterOfBinaryTree_01(root.right);

        int lh = height(root.left);
        int rh = height(root.right);

        return Math.max(Math.max(ld,rd),lh+rh+2);
    }

    //Method 2
    public int[] diameterOfBinaryTree_(TreeNode root){
        if(root==null) return new int[]{0,-1};

        int[] lans = diameterOfBinaryTree_(root.left);
        int[] rans = diameterOfBinaryTree_(root.right);

        int dia = Math.max(Math.max(lans[0],rans[0]),lans[1]+rans[1]+2);
        int hei = Math.max(lans[1],rans[1])+1;

        return new int[]{dia,hei};
    }
    public int diameterOfBinaryTree_02(TreeNode root) {
        int[] ans = diameterOfBinaryTree_(root); //{dia,height};

        return ans[0];
    }

    //Method 3
    public static int diameterOfBinaryTree(TreeNode root,int[] diaAns){
        if(root==null) return -1;

        int lh = diameterOfBinaryTree(root.left,diaAns);
        int rh = diameterOfBinaryTree(root.right,diaAns);

        diaAns[0] = Math.max(diaAns[0],lh+rh+2);
        return Math.max(lh,rh)+1;
    }
    public int diameterOfBinaryTree(TreeNode root) {
        int[] diaAns = new int[1];
        diameterOfBinaryTree(root,diaAns);
        return diaAns[0];
    }

    //Leetcode 863
    public boolean nodeToRootPath(TreeNode root,TreeNode target,List<TreeNode> path){
        if(root==null) return false;
        if(root==target){
            path.add(root);
            return true;
        }

        boolean res = false;
        res = nodeToRootPath(root.left,target,path) || nodeToRootPath(root.right,target,path);
        if(res) path.add(root);
        return res;
    }
    public void kDown(TreeNode node,TreeNode block,int k,List<Integer> ans){
        if(node==null || node==block || k<0) return;

        if(k==0){
            ans.add(node.val);
            return;
        }

        kDown(node.left,block,k-1,ans);
        kDown(node.right,block,k-1,ans);
    }
    public void kFar(TreeNode root,TreeNode target,int k,List<Integer> ans){
        List<TreeNode> path = new ArrayList<>();
        nodeToRootPath(root,target,path);

        TreeNode prev = null;
        for(int i=0;i<path.size();i++){
            kDown(path.get(i),prev,k-i,ans);
            prev = path.get(i);
        }
    }
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> ans = new ArrayList<>();
        kFar(root,target,k,ans);
        return ans;
    }

    //Leetcode 257
    public void binaryTreePaths(TreeNode root,String s,List<String> ans){
        if(root.left==null && root.right==null){
            ans.add(s);
            return;
        }
        if(root.left!=null) 
        binaryTreePaths(root.left,s+"->"+root.left.val,ans);
        if(root.right!=null)
        binaryTreePaths(root.right,s+"->"+root.right.val,ans);
    }
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> ans = new ArrayList<>();
        if(root==null) return ans;
        binaryTreePaths(root,""+root.val,ans);
        return ans;
    }

    //Leetcode 112
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null) return false;
        if(root.left==null && root.right==null && targetSum-root.val==0)
            return true;
        return hasPathSum(root.left,targetSum-root.val)|| hasPathSum(root.right,targetSum-root.val);
    }

    //Leetcode 113
    public void pathSum(TreeNode root,int targetSum,List<Integer> list,List<List<Integer>> res){
        if(root==null) return;
        list.add(root.val);
        if(root.left==null && root.right==null && targetSum-root.val==0){
            res.add(new ArrayList<>(list));
            list.remove(list.size()-1);
            return;
        }

        pathSum(root.left,targetSum-root.val,list,res);
        pathSum(root.right,targetSum-root.val,list,res);
        list.remove(list.size()-1);
    }
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<Integer> list = new ArrayList<>();
        List<List<Integer>> res =  new ArrayList<>();
        pathSum(root,targetSum,list,res);
        return  res;
    }

    //Max sum path between 2 leaves - GFG
    /*https://www.geeksforgeeks.org/find-maximum-path-sum-two-leaves-binary-tree/ - Assuming here that the root has left and right subtree.
                      A          
                     / \
                    B   C  - Minimum start for the tree 
    */
    public static int maxPathSum_(TreeNode root,int[] max){
        if(root==null) return -(int)1e9;

        if(root.left==null && root.right==null){
            return root.val;
        }

        int left = maxPathSum_(root.left,max);
        int right = maxPathSum_(root.right,max);

        if(root.right!=null && root.right!=null){
            max[0] = Math.max(max[0],left+right+root.val);
        }

        return Math.max(left,right)+root.val;
    }
    public static int maxPathSum_(TreeNode root){
        int[] max = new int[1];
        max[0] = -(int)1e9;
        maxPathSum_(root,max);
        return max[0];
    }

    //https://practice.geeksforgeeks.org/problems/maximum-path-sum/1
    //Same question as above except number of nodes>=2 Meaning A->B tree is allowed
    int maxPathSum(TreeNode root,int[] max){
        if(root==null) return -(int)1e9;
        
        if(root.right==null && root.left==null) return root.val;
        
        int left = maxPathSum(root.left,max);
        int right = maxPathSum(root.right,max);
        
        if(root.left!=null && root.right!=null){
            max[0] = Math.max(max[0],left+right+root.val);
        }
        
        return Math.max(left,right)+root.val;
    }
    int maxPathSum(TreeNode root)
    { 
        int[] max = new int[1];
        max[0] = -(int)1e9;
        int ans = maxPathSum(root,max);
        if(root.left==null || root.right==null){
            max[0] = Math.max(max[0],ans);
        }
        return max[0];
    }

    //Leetcode 1038
    //Method 1
    public void inorderTraversalList(TreeNode root,List<TreeNode> inorderList){
        if(root==null) return;

        inorderTraversalList(root.left,inorderList);
        inorderList.add(root);
        inorderTraversalList(root.right,inorderList);
    }
    public TreeNode bstToGst_(TreeNode root) {
        List<TreeNode> inorderList = new ArrayList<>();
        inorderTraversalList(root,inorderList);
        int sum = 0;
        for(int i=inorderList.size()-1;i>=0;i--){
            TreeNode node = inorderList.get(i);
            sum += node.val;
            node.val = sum;
        }
        return root;
    }

    //Method 2
    int[] pre = new int[1];
    public TreeNode bstToGst(TreeNode root) {
        if(root.right!=null)
            bstToGst(root.right);
        pre[0] = root.val = root.val+pre[0];
        if(root.left!=null)
            bstToGst(root.left);
        return root;
    }

    //Leetcode 116
    //Node class definition
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}
        
        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };

    //Method 1
    public Node connect_01(Node root) {
        if(root==null) return root;

        Queue<Node> que = new LinkedList<>();
        que.add(root);

        while(que.size()!=0){
            int size = que.size();
            que.add(null);
            while(size-->0){
                Node node = que.remove();
                node.next = que.peek();
                if(node.left!=null){
                    que.add(node.left);
                    que.add(node.right);
                }
            }
            que.remove();
        }
        return root;
    }

    //Method 2
    public Node connect(Node root) {
        if(root==null) return root;
        if(root.left!=null){
            root.left.next = root.right;
            if(root.next!=null)
                root.right.next = root.next.left;
        }
        connect(root.left);
        connect(root.right);
        return root;
    }

    //Leetcode 834
    int[] ans;
    int[] count;
    List<List<Integer>> graph;
    int N;
    public void dfs1(int node,int parent){
        for(int child:graph.get(node)){
            if(child!=parent){
                dfs1(child,node);
                count[node] += count[child];
                ans[node] += ans[child] + count[child];
            }
        }
    }
    public void dfs2(int node,int parent){
        for(int child:graph.get(node)){
            if(child!=parent){
                ans[child] = ans[node] - count[child] + N - count[child];
                dfs2(child,node);
            }
        }
    }
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        N = n;
        ans = new int[n];
        count = new int[n];
        Arrays.fill(count,1);
        graph = new ArrayList<>();
        for(int i=0;i<n;i++)
            graph.add(new ArrayList<>());
        
        for(int i=0;i<edges.length;i++){
            graph.get(edges[i][0]).add(edges[i][1]);
            graph.get(edges[i][1]).add(edges[i][0]);
        }
        dfs1(0,-1);
        dfs2(0,-1);
        return ans;
    }

    //Leetcode 117
    public Node connect2_1(Node root) {
        if(root==null) return root;

        Queue<Node> que = new LinkedList<>();
        que.add(root);

        while(que.size()!=0){
            int size = que.size();
            que.add(null);
            while(size-->0){
                Node node = que.remove();
                node.next = que.peek();
                if(node.left!=null)
                    que.add(node.left);
                if(node.right!=null)
                    que.add(node.right);
            }
            que.remove();
        }
        return root;
    }

    public Node connect2_2(Node root) {
        if(root==null) return root;

        Node prevHead = root;
        Node prevCurr = null;
        Node currHead = null;
        Node curr = null;

        while(prevHead!=null){
            prevCurr = prevHead;

            while(prevCurr!=null){
                if(prevCurr.left!=null){
                    if(currHead==null){
                        currHead = prevCurr.left;
                        curr = prevCurr.left;
                    }else{
                        curr.next = prevCurr.left;
                        curr = curr.next;
                    }
                }
                
                if(prevCurr.right!=null){
                    if(currHead==null){
                        currHead = prevCurr.right;
                        curr = prevCurr.right;
                    }else{
                        curr.next = prevCurr.right;
                        curr = curr.next;
                    }
                }
                prevCurr = prevCurr.next;
            }
            
            prevHead = currHead;
            currHead = null;
        }
        return root;
    }
    
    //Leetcode 199
    //Method 1.1 - Level Order Traversal
    public List<Integer> rightSideView_01(TreeNode root) {
        if(root==null)
            return new ArrayList<>();

        List<Integer> rightSideViewList = new ArrayList<>();
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);

        int size;
        while(que.size()!=0){
            size = que.size();
            while(size-->0){
                TreeNode node = que.remove();
                if(size==0)
                    rightSideViewList.add(node.val);
                if(node.left!=null) que.add(node.left);
                if(node.right!=null) que.add(node.right);
            }
        }
        return rightSideViewList;
    }

    //Method 1.2 - Level Order Traversal
    public List<Integer> rightSideView_02(TreeNode root) {
        if(root==null)
            return new ArrayList<>();

        List<Integer> rightSideViewList = new ArrayList<>();
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);

        int size;
        int level = 0;
        while(que.size()!=0){
            size = que.size();
            while(size-->0){
                TreeNode node = que.remove();
                if(level==rightSideViewList.size())
                    rightSideViewList.add(node.val);
                if(node.right!=null) que.add(node.right);
                if(node.left!=null) que.add(node.left);
            }
            level++;
        }
        return rightSideViewList;
    }

    //Method 2 - Recursive - DFS SOLUTION
    public void rightSideView(TreeNode root,int depth,List<Integer> ans){
        if(root==null) return;
        if(depth==ans.size())
            ans.add(root.val);
        rightSideView(root.right,depth+1,ans);
        rightSideView(root.left,depth+1,ans);
    }
    public List<Integer> rightSideView(TreeNode root) {
        if(root==null)
            return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();
        rightSideView(root,0,ans);
        return ans;
    }

    //Right Side View of a Tree - Considering axes - Interview Type
    class TreePair{
        int y;
        TreeNode node;
        TreePair(int y,TreeNode node){
            this.node = node;
            this.y = y;
        }
    }
    public List<Integer> rightSide(TreeNode root){
        if(root==null) return new ArrayList<>();

        Queue<TreePair> que = new LinkedList<>();
        que.add(new TreePair(0, root));
        List<TreePair> list = new ArrayList<>();

        int level = 0;
        int size;
        while(que.size()>0){
            size = que.size();
            while(size-->0){
                TreePair vtx = que.remove();
                if(level==list.size())
                    list.add(vtx);
                else if(vtx.y > list.get(level).y)
                    list.set(level,vtx);

                if(vtx.node.right!=null) que.add(new TreePair(vtx.y+1,vtx.node.right));
                if(vtx.node.left!=null)que.add(new TreePair(vtx.y-1,vtx.node.left));
            }
            level++;
        }

        List<Integer> ans = new ArrayList<>();
        for(TreePair vtx:list){
            ans.add(vtx.node.val);
        }
        return ans;
    }

    //https://practice.geeksforgeeks.org/problems/left-view-of-binary-tree/1
    //Method 1 - Level Order Traversal
    ArrayList<Integer> leftView_01(TreeNode root)
    {
        if(root==null) return new ArrayList<>();
      
        ArrayList<Integer> ans = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        
        int size;
        while(q.size()>0){
            size = q.size();
            ans.add(q.peek().val);
            while(size-->0){
                TreeNode node = q.remove();
                if(node.left!=null) q.add(node.left);
                if(node.right!=null) q.add(node.right);
            }
        }
        return ans;
    }

    //Method 2 - DFS solution
    public void leftView(TreeNode root,int depth,ArrayList<Integer> ans){
        if(root==null) return;
        
        if(depth==ans.size())
            ans.add(root.val);
        leftView(root.left,depth+1,ans);
        leftView(root.right,depth+1,ans);
    }
    ArrayList<Integer> leftView(TreeNode root)
    {
        if(root==null) return new ArrayList<>();
      
        ArrayList<Integer> ans = new ArrayList<>();
        
        leftView(root,0,ans);
        return ans;
    }

    //Left Side View - Considering Axes - Interview Type
    public List<Integer> leftSideView(TreeNode root){
        if(root==null) return new ArrayList<>();

        Queue<TreePair> que = new LinkedList<>();
        que.add(new TreePair(0, root));
        List<TreePair> res = new ArrayList<>();

        int size;
        int level = 0;
        while(que.size()>0){
            size = que.size();
            while(size-->0){
                TreePair vtx = que.remove();

                if(res.size()==level)
                    res.add(vtx);
                else if(vtx.y<res.get(level).y)
                    res.set(level,vtx);

                if(vtx.node.left!=null)
                    que.add(new TreePair(vtx.y-1, vtx.node.left));
                if(vtx.node.right!=null)
                    que.add(new TreePair(vtx.y+1,vtx.node.right));
            }
            level++;
        }

        List<Integer> ans = new ArrayList<>();
        for(TreePair vtx:res)
            ans.add(vtx.node.val);
        return ans;
    }

    //Width of a Binary Tree - it will be used in a lot of questions
    public void width(TreeNode root,int y,int[] maxMin){
        if(root==null) return;
        
        maxMin[0] = Math.min(maxMin[0],y);
        maxMin[1] = Math.max(maxMin[1],y);
        
        width(root.left,y-1,maxMin);
        width(root.right,y+1,maxMin);
    }

    //https://practice.geeksforgeeks.org/problems/top-view-of-binary-tree/1
    ArrayList<Integer> topView(TreeNode root)
    {
        if(root==null) return new ArrayList<Integer>();
        
        int[] maxMin = new int[2];
        //maxMin[0] - stores min value of y. maxMin[1] - stores max vakue of y
        
        width(root,0,maxMin);
        int n = maxMin[1] - maxMin[0] + 1;
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=0;i<n;i++) ans.add(null);
        
        Queue<TreePair> q = new LinkedList<>();
        q.add(new TreePair(-maxMin[0],root));
        int size;
        
        while(q.size()!=0){
            size = q.size();
            while(size-->0){
                TreePair vtx = q.remove();
                if(ans.get(vtx.y) == null) ans.set(vtx.y,vtx.node.val);
                
                if(vtx.node.left!=null)
                    q.add(new TreePair(vtx.y-1,vtx.node.left));
                if(vtx.node.right!=null)
                    q.add(new TreePair(vtx.y+1,vtx.node.right));
            }
        }
        return ans;
    }

    //https://practice.geeksforgeeks.org/problems/bottom-view-of-binary-tree/1
    public ArrayList <Integer> bottomView(TreeNode root)
    {
        if(root==null) return new ArrayList<>();;
        
        int[] maxMin = new int[2];
        //maxMin[0] - stores min value of y and maxMin[1] - stores max vakue of y
        width(root,0,maxMin);
        int n = maxMin[1] - maxMin[0] + 1;
        
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=0;i<n;i++) ans.add(null);
        
        Queue<TreePair> q = new LinkedList<>();
        q.add(new TreePair(-maxMin[0],root));
        
        int size;
        while(q.size()!=0){
            size = q.size();
            while(size-->0){
                TreePair vtx = q.remove();
                ans.set(vtx.y,vtx.node.val);
                
                if(vtx.node.left!=null)
                    q.add(new TreePair(vtx.y-1,vtx.node.left));
                if(vtx.node.right!=null)
                    q.add(new TreePair(vtx.y+1,vtx.node.right));
            }
        }
        return ans;
    }

    //Leetcode 987
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        
        int[] maxMin = new int[2];
        width(root,0,maxMin);
        int n = maxMin[1] - maxMin[0] + 1;
        List<List<Integer>> ans = new ArrayList<>();
        for(int i=0;i<n;i++){
            ans.add(new ArrayList<>());
        }
        if(n==1){
            ans.get(0).add(root.val);
            return ans;
        }
        Queue<TreePair> q = new LinkedList<>();
        q.add(new TreePair(-maxMin[0],root));
        class NodeComparator implements Comparator<TreePair>{
            public int compare(TreePair p1,TreePair p2){
                if(p1.y>p2.y || (p1.y==p2.y && p1.node.val>p2.node.val))
                    return 1;
                if(p1.y<p2.y || (p1.y==p2.y && p1.node.val<p2.node.val))
                    return -1;
                return 0;
            }
        }

        PriorityQueue<TreePair> pq = new PriorityQueue<TreePair>(new NodeComparator());
        int size;
        while(q.size()!=0){
            size = q.size();
            while(size-->0){
                TreePair vtx = q.remove();
                pq.add(vtx);
                if(vtx.node.left!=null)
                    q.add(new TreePair(vtx.y-1,vtx.node.left));
                if(vtx.node.right!=null)
                    q.add(new TreePair(vtx.y+1,vtx.node.right));
            }
            while(!pq.isEmpty()){
                TreePair vtx = pq.remove();
                ans.get(vtx.y).add(vtx.node.val);
            }
        }
        
        return ans;
    }

    //https://practice.geeksforgeeks.org/problems/diagonal-traversal-of-binary-tree/1
    public void diagonalTraversal(TreeNode root,int level,HashMap<Integer,ArrayList<Integer>> map){
        if(root==null) return;
        if(map.containsKey(level)){
            map.get(level).add(root.val);
        }else{
            ArrayList<Integer> arr = new ArrayList<>();
            arr.add(root.val);
            map.put(level,arr);
        }
        diagonalTraversal(root.left,level+1,map);
        diagonalTraversal(root.right,level,map);
    }
    public ArrayList<Integer> diagonal(TreeNode root){
        if(root==null) return new ArrayList<>();
        HashMap<Integer,ArrayList<Integer>> map = new HashMap<>();
        
        diagonalTraversal(root,0,map);
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=0;i<map.size();i++){
            ArrayList<Integer> nodes = map.get(i);
            for(int n:nodes)
                ans.add(n);
        }
        return ans;
    }

    //https://www.geeksforgeeks.org/boundary-traversal-of-binary-tree/
    //https://www.codingninjas.com/studio/problems/boundary-traversal_790725?leftPanelTab=1
    public static void leftBoundary(TreeNode root,ArrayList<Integer> ans){
        if(root==null || root.left==null && root.right==null) return;

        ans.add(root.val);
        if(root.left!=null){
            leftBoundary(root.left, ans);
        }
        else{
            leftBoundary(root.right, ans);
        }
    }
    public static void leaves(TreeNode root,ArrayList<Integer> ans){
        if(root==null) return;

        leaves(root.left,ans);
        if(root.left==null && root.right==null) {
            ans.add(root.val);
            return;
        }
        leaves(root.right,ans);
    }
    public static void rightBoundary(TreeNode root,ArrayList<Integer> ans){
        if(root==null || root.left==null && root.right==null) return;

        if(root.right!=null){
            rightBoundary(root.right, ans);
        }
        else if(root.left!=null){
            rightBoundary(root.left,ans);
        }
        ans.add(root.val);
    }
	public static ArrayList<Integer> traverseBoundary(TreeNode root){
		if(root==null) return new ArrayList<>();

        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(root.val);
        leftBoundary(root.left,ans);
        leaves(root.left,ans);
        leaves(root.right,ans);
        rightBoundary(root.right,ans);

        return ans;
	}

    //Leetcode 1145
    public int numberOfNodes(TreeNode root){
        if(root==null) return 0;

        return numberOfNodes(root.left) + numberOfNodes(root.right) + 1;
    }
    public TreeNode findNode(TreeNode root,int x){
        if(root==null) return null;
        if(root.val==x) return root;

        TreeNode node = findNode(root.left,x);
        if(node!=null) return node;
        node = findNode(root.right,x);
        return node;
    }
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        TreeNode node = findNode(root,x);
        int l = numberOfNodes(node.left);
        int r = numberOfNodes(node.right);
        if((l>n/2) || (r>n/2) || ((n-l-r-1)>n/2)) return true;
        return false;
    }

    //https://practice.geeksforgeeks.org/problems/image-multiplication0627/1
    public long imgMultiply(TreeNode node1,TreeNode node2,int mod){
        if(node1==null || node2==null) return 0;
        
        if(node1==node2)
        return ((node1.val*node2.val)%mod + imgMultiply(node1.left,node2.right,mod)%mod)%mod;
        
        return ((node1.val*node2.val)%mod + imgMultiply(node1.left,node2.right,mod)%mod + imgMultiply(node1.right,node2.left,mod)%mod)%mod;
    }
    public long imgMultiply(TreeNode root)
    {
        int mod = (int)1e9 + 7;
        return imgMultiply(root,root,mod);
    }

    //Leetcode 124
    public int maxPathSum2(TreeNode root,int[] max){
        if(root==null) return 0;
        
        int lmax = maxPathSum2(root.left,max);
        int rmax = maxPathSum2(root.right,max);
        int max1 = Math.max(root.val,Math.max(lmax,rmax)+root.val);
        int max2 = Math.max(max1,lmax+rmax+root.val);
        max[0] = Math.max(max[0],max2);
        return max1;
    }
    public int maxPathSum2(TreeNode root) {
        int[] max = new int[1];
        max[0] = -(int)1e8;
        maxPathSum2(root,max);
        return max[0];
    }

    //Leetcode 98
    //Method 1
    class BSTpair{
        long max = -(long)1e13;
        long min = (long)1e13;
        boolean isValid = true;;
        BSTpair(long max,long min,boolean isValid){
            this.max = max;
            this.min = min;
            this.isValid = isValid;
        }
        BSTpair(){

        }

    }
    public BSTpair isValidBST_(TreeNode root){
        if(root==null) return new BSTpair();

        BSTpair left = isValidBST_(root.left);
        BSTpair right = isValidBST_(root.right);

        BSTpair ans = new BSTpair();
        ans.isValid = (left.isValid && right.isValid && (left.max<root.val) && (right.min>root.val));
        if(!ans.isValid) return ans;

        ans.max = Math.max(right.max,root.val);
        ans.min = Math.min(left.min,root.val);
        return ans;
    }
    public boolean isValidBST01(TreeNode root) {
        if(root.left==null && root.right==null) return true;

        BSTpair ans = isValidBST_(root);
        return ans.isValid;
    }

    //Method 2
    long prevBSTnode = -(long)1e13;
    public boolean isValidBST(TreeNode root) {
        if(root==null) return true;

        if(!isValidBST(root.left)) return false;

        if(root.val<=prevBSTnode) return false;

        prevBSTnode = root.val;

        if(!isValidBST(root.right)) return false;
        
        return true;
    }

    //Leetcode 99
    TreeNode prev,first,middle,last;
    public void recoverTreeUtil(TreeNode root){
        if(root!=null){
            recoverTreeUtil(root.left);
            if(prev!=null && root.val<prev.val){
                if(first==null){
                    first = prev;
                    middle = root;
                }else{
                    last = root;
                }
            }
            prev = root;
            recoverTreeUtil(root.right);
        }
    }
    public void recoverTree(TreeNode root) {
        prev = first = middle = last = null;
        recoverTreeUtil(root);

        if(first!=null && last!=null){
            int data = first.val;
            first.val = last.val;
            last.val = data;
        }else if(first!=null && middle!=null){
            int data = first.val;
            first.val = middle.val;
            middle.val = data;
        }
    }

    //BST BASIC QUESTIONS

    public int maximumEle(TreeNode node){
        TreeNode curr = node;
        while(curr.right!=null) curr = curr.right;
        return curr.val;
    }

    public int minimumEle(TreeNode node){
        TreeNode curr = node;
        while(curr.left!=null) curr = curr.left;
        return curr.val;
    }
    
    public boolean findDataIterative(TreeNode root,int data){
        TreeNode curr = root;
        while(curr!=null){
            if(curr.val==data) return true;
            else if(curr.val>data) curr = curr.left;
            else curr = curr.right;
        }
        return false;
    }

    public boolean findDatarRecursion(TreeNode root,int data){
        if(root==null) return false;

        if(root.val==data) return true;
        else if(root.val>data) return findDatarRecursion(root.left, data);
        else return findDatarRecursion(root.right, data);
    }
    
    //Leetcode 701
    //Method 1 - Recursive Method
    public TreeNode insertIntoBST01(TreeNode root, int val) {
        if(root==null) return new TreeNode(val);
        if(root.val>val){
            root.left = insertIntoBST01(root.left,val);
        }
        else if(root.val<val)
            root.right = insertIntoBST01(root.right,val);
        return root;

    }

    //Method 2 - Iterative Method
    public TreeNode insertIntoBST(TreeNode root, int val) {
        TreeNode curr = root;
        TreeNode prev = null;
        
        while(curr!=null){
            prev = curr;
            if(curr.val>val) 
                curr = curr.left;
            else curr = curr.right;
        }

        TreeNode node = new TreeNode(val);
        if(prev==null) return node;
        if(prev.val>val) prev.left = node;
        else prev.right = node;
        return root;
    }

    //Leetcode 450
    public int rightmostNode(TreeNode node){
        TreeNode curr = node;
        while(curr.right!=null)
            curr=curr.right;
        return curr.val;
    }
    public TreeNode deleteNode(TreeNode root, int key) {
        if(root==null) return null;

        if(root.val>key){
            root.left = deleteNode(root.left,key);
        }else if(root.val<key){
            root.right = deleteNode(root.right,key);
        }else{
            if(root.left==null||root.right==null)
                return (root.left!=null)? root.left:root.right;
            int maxEle = rightmostNode(root.left);
            root.val = maxEle;
            root.left = deleteNode(root.left,maxEle);            
        }
        return root;
    }

    //Inorder Successor & Predecessor in Binary Tree
    //Recursion
    public class allSolPair{
        TreeNode prev;
        TreeNode pred;
        TreeNode succ;

        int ceil = (int)1e9;
        int floor = -(int)1e9;
       
    }
    public void allSolution(TreeNode root,int data,allSolPair ans){
        if(root==null) return;

        ans.ceil = Math.min(ans.ceil,root.val);
        ans.floor = Math.max(ans.floor,root.val);

        allSolution(root.left, data, ans);

        if(root.val==data) ans.pred = ans.prev;
        if(ans.prev!=null && ans.prev.val==data) ans.succ = root;

        ans.prev = root;
        allSolution(root.right, data, ans);
    }
    public allSolPair allSolution(TreeNode root,int data){
        allSolPair ans = new allSolPair();
        allSolution(root,data,ans);
        return ans;
    }

    //Inorder Successor and Predecessor in BST
    //Iterative
    public static void predSuccInBST(TreeNode root,int data){
        TreeNode curr = root;
        TreeNode pred = null;
        TreeNode succ = null;

        while(curr!=null){
            if(curr.val<data){
                pred = curr;
                curr = curr.right;
            }else if(curr.val>data){
                succ = curr;
                curr = curr.right;
            }else{
                if(curr.left!=null){
                    pred = curr.left;
                    while(pred.right!=null){
                        pred = pred.right;
                    }
                }
                if(curr.right!=null){
                    succ = curr.right;
                    while(succ.left!=null){
                        succ = succ.left;
                    }
                }
                break;
            }
        }
    }

    //Leetcode 1339
    long s=0,ans_=0;
    public int maxProductUtil(TreeNode root){
        if(root==null) return 0;
        int sum = root.val + maxProductUtil(root.left) + maxProductUtil(root.right);
        if(s>0) ans_ = Math.max(ans_ , sum*(s-sum));
        return sum;
    }
    public int maxProduct(TreeNode root) {
        long mod = (long)1e9+7;
        s = maxProductUtil(root);
        maxProductUtil(root);
        return (int)(ans_%mod);
    }

    //Leetcode 235
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;
        while(curr!=null){
            if(curr.val<p.val && curr.val<q.val) curr = curr.right;
            else if(curr.val>p.val && curr.val>q.val) curr = curr.left;
            else break;
        }
        return curr;
    }

    //Leetcode 979
    public int distributeCoins(TreeNode root,int[] ans){
        if(root==null) return 0;
        int L = distributeCoins(root.left,ans);
        int R = distributeCoins(root.right,ans);
        ans[0] += Math.abs(L) + Math.abs(R);
        return root.val + L + R - 1;
    }
    public int distributeCoins(TreeNode root) {
        if(root==null) return 0;
        int[] ans = new int[1];
        distributeCoins(root,ans);
        return ans[0];
    }

    //Leetcode 968
    public int minCameraCover(TreeNode root,int[] cameras){
        if(root==null) return 0;

        int l = minCameraCover(root.left,cameras);
        int r = minCameraCover(root.right,cameras);

        if(l==-1||r==-1){
            cameras[0]++;
            return 1;
        }
        if(l==1||r==1){
            return 0;
        }
        return -1;
    }
    public int minCameraCover(TreeNode root) {
        int[] cameras = new int[1];
        int ans = minCameraCover(root,cameras);
        if(ans==-1) cameras[0]++;
        return cameras[0];
    }

    //Leetcode 114
    public void flatten(TreeNode root) {
        if(root==null || root.left==null && root.right==null)
            return;
        
        if(root.left!=null){
            flatten(root.left);
            TreeNode temp = root.right;
            root.right = root.left;
            root.left = null;
            TreeNode t = root.right;
            while(t.right!=null) t = t.right;
            t.right = temp;
            flatten(temp);
        }
        else
            flatten(root.right);
    }

    //Leetcode 173
    class BSTIterator {
        Stack<TreeNode> stack;
        public BSTIterator(TreeNode root) {
            stack = new Stack<TreeNode>();
            TreeNode curr = root;
            while(curr!=null){
                stack.push(curr);
                curr = curr.left;
            }
        }
        
        public int next() {
            TreeNode curr = stack.pop();
            int result = curr.val;
            if(curr.right!=null){
                curr = curr.right;
                while(curr!=null){
                    stack.push(curr);
                    curr = curr.left;
                }
            }
            return result;
        }
        
        public boolean hasNext() {
            return !stack.isEmpty();
        }
    }

    //Leetcode 510 - Inorder Successor in BST II  - Locked
    //https://wentao-shao.gitbook.io/leetcode/binary-tree/510.inorder-successor-in-bst-ii
    class Node_{
        public int val;
        public Node_ left;
        public Node_ right;
        public Node_ parent;

        Node_(int val,Node_ left,Node_ right,Node_ parent){
            this.val = val;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    public Node_ inorderSuccessor(Node_ node){
        if(node.right!=null){
            Node_ curr = node.right;
            while(curr.left!=null){
                curr = curr.left;
            }
            return curr;
        }
        else{
            while(node.parent!=null && node==node.parent.right){
                node = node.parent;
            }
            return node.parent;
        }
    }

    //Leetcode 1008
    class Solution_BSTfromPreorder {
        int idx = 0;
        public TreeNode bstFromPreorder(int[] preorder,int lrange,int rrange){
            if(idx>=preorder.length || preorder[idx]<lrange || preorder[idx]>rrange){
                return null;
            }
            int data = preorder[idx++];
            TreeNode p = new TreeNode(data);
            p.left = bstFromPreorder(preorder,lrange,data);
            p.right = bstFromPreorder(preorder,data,rrange);
            return p;
        }
        public TreeNode bstFromPreorder(int[] preorder) {
            if(preorder.length==1){
                return new TreeNode(preorder[0]);
            }
            return bstFromPreorder(preorder,-(int)1e8,(int)1e8);
        }
    }

    //Leetcode 105
    public TreeNode buildTree(int psi,int pei,int[] preorder,int isi,int iei,int[] inorder){
        if(psi>pei) return null;

        TreeNode node = new TreeNode(preorder[psi]);
        int idx = isi;
        while(inorder[idx]!=preorder[psi]) idx++;
        int count = idx - isi;
        node.left = buildTree(psi+1,psi+count,preorder,isi,idx-1,inorder);
        node.right = buildTree(psi+count+1,pei,preorder,idx+1,iei,inorder);
        return node;
    }
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length==1) return new TreeNode(preorder[0]);
        return buildTree(0,preorder.length-1,preorder,0,inorder.length-1,inorder);
    }

    //Leetcode 106
    public TreeNode buildTree_(int isi,int iei,int[] inorder,int psi,int pei,int[] postorder){
        if(pei<psi) return null;
        TreeNode node = new TreeNode(postorder[pei]);
        int idx = isi;
        while(inorder[idx]!=node.val) idx++;
        int count = idx - isi;
        node.left = buildTree_(isi,idx-1,inorder,psi,psi+count-1,postorder);
        node.right = buildTree_(idx+1,iei,inorder,psi+count,pei-1,postorder);
        return node;
    }
    public TreeNode buildTree_(int[] inorder, int[] postorder) {
        int n = inorder.length;
        if(n==1) return new TreeNode(inorder[0]);
        return buildTree_(0,n-1,inorder,0,n-1,postorder);
    }

    //https://practice.geeksforgeeks.org/problems/construct-bst-from-post-order/1
    class Solution_BSTfromPostorder{
        public static TreeNode constructTree(int[] idx,int lrange,int rrange,int[] post){
            if(idx[0]<0 || post[idx[0]] < lrange || post[idx[0]]>rrange) return null;
            
            int data = post[idx[0]--];
            TreeNode node = new TreeNode(data);
            node.right = constructTree(idx,data,rrange,post);
            node.left = constructTree(idx,lrange,data,post);
            return node;
        }
        public static TreeNode constructTree(int post[],int n)
        {
            if(n==1) return new TreeNode(post[0]);
            int[] idx = new int[1];
            idx[0] = n-1;
            return constructTree(idx,-(int)1e8,(int)1e8,post);
        }
    }

    //Leetcode 889
    public TreeNode constructFromPrePost(int prsi,int prei,int[] preorder,int posi,int poei,int[] postorder){
        if(prsi>prei) return null;
        if(prsi==prei) return new TreeNode (preorder[prsi]);

        TreeNode node = new TreeNode(preorder[prsi]);
        int idx = posi;
        while(postorder[idx]!=preorder[prsi+1]) idx++;

        int count = idx-posi+1;
        node.left = constructFromPrePost(prsi+1,prsi+count,preorder,posi,posi+count-1,postorder);
        node.right = constructFromPrePost(prsi+count+1,prei,preorder,idx+1,poei-1,postorder);
        return node;
    }
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        int n = preorder.length;
        if(n==1) return new TreeNode(preorder[0]);
        return constructFromPrePost(0,n-1,preorder,0,n-1,postorder);
    }

    //https://practice.geeksforgeeks.org/problems/construct-tree-from-inorder-and-levelorder/1
    public TreeNode buildTree(int isi,int iei,int[] inorder,int[] level,int n){
        if(n<=0) return null;
        TreeNode root = new TreeNode(level[0]);
        int idx = -1;
        for(int i=isi;i<=iei;i++){
            if(inorder[i]==root.val){
                idx = i;
                break;
            }
        }
        
        
        HashSet<Integer> set = new HashSet<>();
        for(int i=isi;i<idx;i++)
            set.add(inorder[i]);
        
        int[] leftLevel = new int[idx-isi];
        int[] rightLevel = new int[iei-idx];
        
        int li=0,ri=0;
        for(int i=1;i<n;i++){
            if(set.contains(level[i]))
                leftLevel[li++] = level[i];
            else
                rightLevel[ri++] = level[i];
        }
        
        root.left = buildTree(isi,idx-1,inorder,leftLevel,idx-isi);
        root.right = buildTree(idx+1,iei,inorder,rightLevel,iei-idx);
        return root;
    }
    public TreeNode buildTree_fromInorderLevelOrder(int inord[], int level[])
    {
        int n = inord.length;
        if(n==1) return new TreeNode(inord[0]);
        return buildTree(0,n-1,inord,level,n);
    }

    //https://practice.geeksforgeeks.org/problems/convert-level-order-traversal-to-bst/1

    //Method 1 - Queue
    class MaxMin{
        TreeNode node;
        int min;
        int max;
        MaxMin(TreeNode node,int min,int max){
            this.node = node;
            this.min = min;
            this.max = max;
        }
        
    }
    public TreeNode constructBST_01(int[] arr)
    {
        int n = arr.length;
        if(n==1) return new TreeNode(arr[0]);
        Queue<MaxMin> que = new LinkedList<>();
        TreeNode root = new TreeNode(arr[0]);
        que.add(new MaxMin(root,-(int)1e9,(int)1e9));
        
        int i = 1;
        
        while(!que.isEmpty()){
            
            MaxMin ele = que.poll();
            TreeNode p = ele.node;
            
            if(i<n && arr[i]<p.val && arr[i]>ele.min){
                TreeNode leftEle = new TreeNode(arr[i++]);
                p.left = leftEle;
                que.add(new MaxMin(leftEle,ele.min,p.val));
            }
            
            if(i<n && arr[i]>p.val && arr[i]<ele.max){
                TreeNode rightEle = new TreeNode(arr[i++]);
                p.right = rightEle;
                que.add(new MaxMin(rightEle,p.val,ele.max));
            }
            
        }
        return root;
    }

    //Method 2 - Recursion
    public TreeNode constructBST(TreeNode root,int data){
        if(root==null)
            return new TreeNode(data);
        
        if(data<root.val)
            root.left = constructBST(root.left,data);
        else
            root.right = constructBST(root.right,data);
        return root;
    }
    public TreeNode constructBST(int[] arr)
    {
        int n = arr.length;
        if(n==1) return new TreeNode(arr[0]);
        TreeNode root = null;
        for(int i=0;i<n;i++)
            root = constructBST(root,arr[i]);
        return root;
    }

    //https://www.geeksforgeeks.org/convert-binary-tree-to-doubly-linked-list-using-inorder-traversal/
    class binaryTreeToDLL_Solution{
        TreeNode dummy = new TreeNode(-1);
        TreeNode prev = dummy;
        public TreeNode binaryTreeToDLLUtil(TreeNode root){
            if(root==null) return root;

            binaryTreeToDLLUtil(root.left);

            prev.right = root;
            root.left = prev;
            prev = root;

            binaryTreeToDLLUtil(root.right);

            return root;
        }
        public TreeNode binaryTreeToDLL(TreeNode root){
            if(root==null) return null;

            binaryTreeToDLLUtil(root);

            TreeNode head = dummy.right;
            dummy.right = null;
            head.left = null;

            return head;
        }
    }

    //https://www.geeksforgeeks.org/convert-a-binary-tree-to-a-circular-doubly-link-list/
    class binaryTreeToCDLL_Solution{
        TreeNode dummy = new TreeNode(-1);
        TreeNode prev = dummy;
        public TreeNode binaryTreeToCDLLUtil(TreeNode root){
            if(root==null) return null;

            binaryTreeToCDLLUtil(root.left);

            prev.right = root;
            root.left = prev;
            prev = root;

            binaryTreeToCDLLUtil(root.right);

            return root;
        }
        public TreeNode binaryTreeToCDLL(TreeNode root){
            if(root==null) return null;

            binaryTreeToCDLLUtil(root);

            TreeNode head = dummy.right;
            prev.right = head;
            head.left = prev;
            dummy.left = null;

            return head;
        }
    }

    //https://www.geeksforgeeks.org/in-place-conversion-of-sorted-dll-to-balanced-bst/
    class sortedDLLToBalancedBST_Solution{
        class Node{
            int data;
            Node next;
            Node prev;
            Node(int data){
                this.data = data;
                this.next=this.prev=null;
            }
        }
        Node head;
        public int countNodes(Node head){
            int count = 0;
            Node temp = head;
            while(temp!=null){
                count++;
                temp = temp.next;
            }
            return count;
        }
        public Node sortedDLLToBalancedBST(int n){
            if(n<=0) return null;

            Node left = sortedDLLToBalancedBST(n/2);

            Node root = head;
            root.prev = left;
            head = head.next;

            root.next = sortedDLLToBalancedBST(n - n/2 - 1);

            return root;
        }
        public Node sortedDLLToBalancedBST(Node head){

            this.head = head;
            int n = countNodes(head);
            return sortedDLLToBalancedBST(n);
        }
    }

    //Leetcode 230
    class kthSmallestElement_Solution{
        int idx;
        public void kthSmallest(TreeNode root,int[] ans){
            if(root==null) return;

            kthSmallest(root.left,ans);
            idx--;
            if(idx==0) {
                ans[0] = root.val;
                return;
            }
            if(idx<0) return;

            kthSmallest(root.right,ans);

        }
        public int kthSmallest(TreeNode root, int k) {
            idx = k;
            int[] ans = new int[1];
            kthSmallest(root,ans);
            return ans[0];
        }
    }

    //Leetcode 437
    public void pathSumUtil(TreeNode root,int target,long prefixSum,HashMap<Long,Integer> map,int[] ans){
        if(root==null) return;

        prefixSum += root.val;
        ans[0] += map.getOrDefault(prefixSum-target,0);

        map.put(prefixSum,map.getOrDefault(prefixSum,0)+1);

        pathSumUtil(root.left,target,prefixSum,map,ans);
        pathSumUtil(root.right,target,prefixSum,map,ans);

        map.put(prefixSum,map.get(prefixSum)-1);
        if(map.get(prefixSum)==0) map.remove(prefixSum);
    }
    public int pathSumIII(TreeNode root, int targetSum) {
        int[] ans = new int[1];
        HashMap<Long,Integer> map = new HashMap<>();
        map.put((long)0,1);
        pathSumUtil(root,targetSum,0,map,ans);
        return ans[0];
    }

    //Leetcode 662
    public class NodeIdx{
        TreeNode node;
        long idx;
        NodeIdx(TreeNode node,long idx){
            this.node = node;
            this.idx = idx;
        }
    }
    public int widthOfBinaryTree(TreeNode root) {
        long width = 0;
        Queue<NodeIdx> q = new LinkedList<>();
        q.add(new NodeIdx(root,1));
        while(q.size()!=0){
            int size = q.size();
            long fi = q.peek().idx;
            long li = q.peek().idx;
            while(size-->0){
                NodeIdx ele = q.remove();
                li = ele.idx;

                if(ele.node.left!=null) q.add(new NodeIdx(ele.node.left,2*li));
                if(ele.node.right!=null) q.add(new NodeIdx(ele.node.right,2*li+1));
            }
            width = Math.max(width,li-fi+1);
        }
        return (int)width;
    }

    //Leetcode 337
    public int[] robUtil(TreeNode root){
        if(root==null) return new int[]{0,0};

        int[] lres = robUtil(root.left);
        int[] rres = robUtil(root.right);

        int rob = lres[1] + root.val + rres[1];
        int notRob = Math.max(lres[0],lres[1]) + Math.max(rres[0],rres[1]);
        return new int[] {rob,notRob};
    }
    public int rob(TreeNode root) {
        int[] ans = robUtil(root);
        return Math.max(ans[0],ans[1]);
    }

    //Leetcode 1372
    class LRZigZag{
        int l = 0;
        int r = 0;
        LRZigZag(int l,int r){
            this.l = l;
            this.r = r;
        }
    }
    public LRZigZag longestZigZag(TreeNode root,int[] max){
        if(root==null) return new LRZigZag(-1,-1);

        LRZigZag left = longestZigZag(root.left,max);
        LRZigZag right = longestZigZag(root.right,max);
        max[0] = Math.max(max[0],Math.max(left.r,right.l)+1);
        return new LRZigZag(left.r+1,right.l+1);
    }
    public int longestZigZag(TreeNode root) {
        int[] max = new int[1];
        longestZigZag(root,max);
        return max[0];
    }

    //Leetcode 653
    public void inorder(List<Integer> inorderList,TreeNode root){
        if(root==null) return;

        inorder(inorderList,root.left);
        inorderList.add(root.val);
        inorder(inorderList,root.right);

    }
    public boolean findTarget(TreeNode root, int k) {
        List<Integer> inorderList = new ArrayList<>();
        inorder(inorderList,root);

        int l = 0,r=inorderList.size()-1;
        int sum;
        while(l<r){
            sum = inorderList.get(l)+inorderList.get(r);

            if(sum==k) return true;
            else if(sum<k) l++;
            else r--;
        }
        return false;
    }

    //Leetcode 297
    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if(root==null) return null;
    
            StringBuilder sb = new StringBuilder();
            Stack<TreeNode> st = new Stack<>();
            st.push(root);
    
            while(!st.isEmpty()){
                TreeNode node = st.pop();
                if(node!=null){
                    sb.append(node.val+",");
                    st.push(node.right);
                    st.push(node.left);
                }
                else sb.append("#,");
            }
            return sb.toString();
        }
    
        // Decodes your encoded data to tree.
        public TreeNode deserialize(int[] idx,String[] arr){
            if(arr[idx[0]].equals("#")) return null;
    
            TreeNode root = new TreeNode(Integer.parseInt(arr[idx[0]]));
            idx[0] = idx[0]+1;
            root.left = deserialize(idx,arr);
            idx[0] = idx[0]+1;
            root.right = deserialize(idx,arr);
            return root;
        }
        public TreeNode deserialize(String data) {
            if(data==null) return null;
    
            int[] idx = new int[1];
            idx[0] = 0;
            String[] arr = data.split(",");
            return deserialize(idx,arr);
        }
    }

    //Morris Inorder Traversal
    public TreeNode rightMostNode(TreeNode node,TreeNode curr){
        TreeNode rmost = node;
        while(rmost.right!=null && rmost.right!=curr){
            rmost = rmost.right;
        }
        return rmost;
    }
    public List<Integer> morrisInorderTraversal(TreeNode root){
        if(root==null) return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();
        TreeNode curr = root;
        TreeNode next = null;
        while(curr!=null){
            next = curr.left;
            if(next==null){
                ans.add(curr.val);
                curr = curr.right;
            }else{
                TreeNode rmost = rightMostNode(next,curr);
                if(rmost.right==null){
                    rmost.right = curr;
                    curr = curr.left;
                }else{
                    rmost.right = null;
                    ans.add(curr.val);
                    curr = curr.right;
                }
            }
        }
        
        return ans;
    }

    //Morris Preorder Traversal
    public List<Integer> morrisPreorderTraversal(TreeNode root){
        if(root==null) return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();
        TreeNode curr = root;
        TreeNode next = null;
        while(curr!=null){
            next = curr.left;
            if(next==null){
                ans.add(curr.val);
                curr = curr.right;
            }
            else{
                TreeNode rmost = rightMostNode(next,curr);
                if(rmost.right==null){
                    ans.add(curr.val);
                    rmost.right = curr;
                    curr = curr.left;
                }else{//thread break
                    rmost.right = null;
                    curr = curr.right;
                }
            }
        }
        return ans;
    }

    //Leetcode 230
    public int kthSmallest(TreeNode root, int k) {

        TreeNode curr = root;
        TreeNode next = null;

        while(curr!=null){
            next = curr.left;
            if(next==null){
                if(--k==0) break;
                curr = curr.right;
            }else{
                TreeNode rmost = rightMostNode(next,curr);
                if(rmost.right==null){
                    rmost.right = curr;
                    curr = curr.left;
                }else{
                    rmost.right = null;
                    if(--k==0) break;
                    curr = curr.right;
                }
            }
        }

        return curr.val;
    }

    //Leetcode 222
    public int countNodes(TreeNode root) {
        if(root==null) return 0;

        TreeNode leftNode = root;
        TreeNode rightNode = root;
        int lheight = 0;
        int rheight = 0;
        while(leftNode!=null){
            lheight++;
            leftNode = leftNode.left;
        }

        while(rightNode!=null){
            rheight++;
            rightNode = rightNode.right;
        }

        if(lheight==rheight){
            return (int)Math.pow(2,lheight) - 1;
        }
        else return 1 + countNodes(root.left) + countNodes(root.right);
    }

    //Leetcode 129
    //Method - 1ms solution
    public void sumNumbersUtil(StringBuilder sb,int[] sum,TreeNode root){
        sb.append(root.val);
        if(root.left==null && root.right==null){
            sum[0] += Integer.parseInt(sb.toString());
        }else{
            if(root.left!=null)
                sumNumbersUtil(sb,sum,root.left);
            if(root.right!=null)
                sumNumbersUtil(sb,sum,root.right);
        }
        sb.deleteCharAt(sb.length()-1);
    }
    public int sumNumbers_(TreeNode root) {
        int[] sum = new int[1];
        StringBuilder sb = new StringBuilder();
        sumNumbersUtil(sb,sum,root);
        return sum[0];
    }

    //Method 2 - 0ms solution
    public void sumNumbersUtil(TreeNode root,int[] sum,int num){
        num = num*10 + root.val;
        if(root.left==null && root.right==null)
            sum[0] += num;
        if(root.left!=null) sumNumbersUtil(root.left,sum,num);
        if(root.right!=null) sumNumbersUtil(root.right,sum,num);
    }
    public int sumNumbers(TreeNode root) {
        int[] sum = new int[1];
        sum[0] = 0;
        sumNumbersUtil(root,sum,0);
        return sum[0];
    }

    //Leetcode 270 - Locked
    //Lintcode 900
    public class closestBSTValue_Solution{
        double min;
        int ele;
        public void closestValueUtil(TreeNode root, double target){
            if(root==null) return;
    
            double diff = Math.abs(root.val - target);
            if(diff<min){
                min = diff;
                ele = root.val;
            }
            if(target<root.val) closestValueUtil(root.left, target);
            else closestValueUtil(root.right, target);
        }
        public int closestValue(TreeNode root, double target) {
            min = Double.MAX_VALUE;
            ele = 0;
            closestValueUtil(root,target);
            return ele;
        }
    }

    //Leetcode 272
    //Lintcode 901
    private class TNode{
        public double diff;
        public TreeNode node;
        TNode(double diff,TreeNode node){
            this.diff = diff;
            this.node = node;
        }
    }
    class TNodeComparator implements Comparator<TNode>{
        public int compare(TNode node1,TNode node2){
            if(node1.diff>node2.diff)
                return 1;
            else if(node1.diff<node2.diff)
                return -1;
            else return 0;
        }
    }
    public void inorder(TreeNode root,double target,int k,PriorityQueue<TNode> pq){
        if(root==null) return;

        inorder(root.left,target,k,pq);
        pq.add(new TNode(Math.abs(target-root.val),root));
        inorder(root.right,target,k,pq);
    }
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> ans = new ArrayList<>();
        PriorityQueue<TNode> pq = new PriorityQueue<>(new TNodeComparator());
        inorder(root,target,k,pq);
        int i=0;
        while(i<k){
            ans.add(pq.poll().node.val);
            i++;
        }
        return ans;
    }

    //Iterative Inorder Traversal
    public static List<Integer> inorderIterative(TreeNode root){
        if(root==null) return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;

        while(curr!=null || st.size()>0){

            while(curr!=null){
                st.push(curr);
                curr = curr.left;
            }

            curr = st.pop();
            ans.add(curr.val);

            curr = curr.right;
        }

        return ans;
    }

    //Iterative Preorder Traversal
    public static List<Integer> preorderIterative(TreeNode root){
        if(root==null) return new ArrayList<>();
        
        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;

        while(curr!=null || st.size()>0){

            while(curr!=null){
                ans.add(curr.val);
                if(curr.right!=null) st.push(curr.right);
                curr = curr.left;
            }

            if(st.size()>0){
                curr = st.pop();
                ans.add(curr.val);
                if(curr.right!=null) st.push(curr.right);
            }

            if(curr!=null) curr = curr.left;
        }

        return ans;
    }

    //Iterative Postorder Traversal
    public static List<Integer> postorderIterative(TreeNode root) {
        if(root==null) return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;

        while(curr!=null || st.size()>0){

            while(curr!=null){
                st.push(curr);
                st.push(curr);
                curr = curr.left;
            }

            if(!st.empty()) curr = st.pop();

            if(!st.empty() && st.peek()==curr) curr = curr.right;
            else {
                ans.add(curr.val);
                curr = null;
            }
        }

        return ans;
    }

    //--------------------------------------EXTRA QUESTIONS--------------------------------------------

    //Leetcode 103
    public void levelorder(TreeNode root,List<List<Integer>> ans){
        if(root == null)
        return;
        
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int level = 0;
        while(q.size()!=0){
            int size = q.size();
            ans.add(new ArrayList<>());
            while(size-->0){
                TreeNode node = q.poll();
                ans.get(level).add(node.val);

                if(node.left!=null) q.add(node.left);
                if(node.right!=null) q.add(node.right);
            }
            level++;
        }
    }
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        levelorder(root,ans);
        for(int i =0;i<ans.size();i++){
            if(i%2 != 0){
              Collections.reverse(ans.get(i));
            }
        }
        return ans;
    }

    //Leetcode 107
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if(root == null)
        return ans;
        
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int level = 0;
    
        while(q.size()!=0){
            int size = q.size();
            ans.add(new ArrayList<>());
            while(size-->0){
                TreeNode node = q.poll();
                ans.get(level).add(node.val);

                if(node.left!=null) q.add(node.left);
                if(node.right!=null) q.add(node.right);
            }
            level++;
        }

        Collections.reverse(ans);
        return ans;
    }

    //Leetcode 109
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public ListNode findMiddle(ListNode head){
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while(fast!=null && fast.next!=null){
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        if(prev!=null){
            prev.next = null;
        }

        return slow;
    }
    public TreeNode sortedListToBST(ListNode head) {
        if(head==null) return null;

        ListNode mid = findMiddle(head);
        TreeNode root = new TreeNode(mid.val);

        if(head==mid) return root;

        root.left = sortedListToBST(head);
        root.right = sortedListToBST(mid.next);

        return root;
    }

    //Leetcode 108
    public TreeNode sortedArrayToBST(int si,int ei,int[] nums){
        if(si>ei || ei >= nums.length || si<0){
            return null;
        }

        int mid = si + (ei-si+1)/2;
        TreeNode root = new TreeNode(nums[mid]);
        if(nums[mid]==nums[si]) return root;

        root.left = sortedArrayToBST(si,mid-1,nums);
        root.right = sortedArrayToBST(mid+1,ei,nums);

        return root;

    }
    public TreeNode sortedArrayToBST(int[] nums) {
        int n = nums.length;
        return sortedArrayToBST(0,n-1,nums);
    }

    //Leetcode 100
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null && q==null) return true;

        if(p==null || q==null) return false;

        if(p.val!=q.val) return false;

        return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }

    //Leetcode 341
    public interface NestedInteger {
        
        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    //Method 1 - Using Stack
    public class NestedIterator_ implements Iterator<Integer> {

        private Stack<Iterator<NestedInteger>> stack;
    
        public NestedIterator_(List<NestedInteger> nestedList) {
            stack = new Stack<>();
            stack.push(nestedList.iterator());
        }
    
        @Override
        public Integer next() {
            hasNext(); // Ensure the stack top points to an integer
            return stack.pop().next().getInteger();
        }
    
        @Override
        public boolean hasNext() {
            while (!stack.isEmpty()) {
                if (!stack.peek().hasNext()) {
                    stack.pop(); // Remove the exhausted iterator
                } else {
                    NestedInteger next = stack.peek().next();
                    if (next.isInteger()) {
                        // If the next element is an integer, push it to the stack
                        stack.push(List.of(next).iterator());
                        return true;
                    }
                    stack.push(next.getList().iterator());
                }
            }
            return false;
        }
    }

    //Method 2 - Using Recursion
    public class NestedIterator implements Iterator<Integer> {
        int idx;
        List<Integer> list;
        public NestedIterator(List<NestedInteger> nestedList) {
            idx = 0;
            list = new ArrayList<>();
            flatten(nestedList,list);
        }
    
        private void flatten(List<NestedInteger> nestedList,List<Integer> list){
            for(NestedInteger nestedInteger: nestedList){
                if(nestedInteger.isInteger()){
                    list.add(nestedInteger.getInteger());
                }else{
                    flatten(nestedInteger.getList(),list);
                }
            }
        }
    
        @Override
        public Integer next() {
            return list.get(idx++);
        }
    
        @Override
        public boolean hasNext() {
            return idx < list.size();
        }
    }

    
}