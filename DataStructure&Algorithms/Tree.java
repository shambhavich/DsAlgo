import java.util.*;

public class Tree {
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
    
    //Leetcode 94
    public void inorderTraversal(TreeNode root,List<Integer> list){
        if(root==null) return;

        inorderTraversal(root.left,list);
        list.add(root.val);
        inorderTraversal(root.right,list);
    }
    public List<Integer> inorderTraversal(TreeNode root) {
        if(root==null) return new ArrayList<>();

        List<Integer> list = new ArrayList<>();
        inorderTraversal(root,list);
        return list;
    }
    
    //Leetcode 144
    private void preorderTraversal(TreeNode root,List<Integer> list){
        if(root==null) return;

        list.add(root.val);
        preorderTraversal(root.left,list);
        preorderTraversal(root.right,list);
    }
    public List<Integer> preorderTraversal(TreeNode root) {
        if(root==null) return new ArrayList<>();

        List<Integer> list = new ArrayList<>();
        preorderTraversal(root,list);
        return list;
    }

    //Leetcode 145
    private void postorderTraversal(TreeNode root,List<Integer> list){
        if(root==null) return;

        postorderTraversal(root.left,list);
        postorderTraversal(root.right,list);
        list.add(root.val);
    }
    public List<Integer> postorderTraversal(TreeNode root) {
        if(root==null) return new ArrayList<>();

        List<Integer> list = new ArrayList<>();
        postorderTraversal(root,list);
        return list;
    }

    //Binary Tree Level Order Traversal
    //Method 1
    public static void BFS_01(TreeNode root){
        if(root==null) return;
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);

        while(que.size()!=0){
            TreeNode node = que.remove();
            System.out.println(node.val);

            if(root.left!=null) que.add(root.left);
            if(root.right!=null) que.add(root.right);
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
            System.out.print(node.val);

            if(root.left!=null) que.add(root.left);
            if(root.right!=null) que.add(root.right);

            if(que.peek()==null){
                System.out.println();
                que.remove();
                que.add(null);
            }
        }
    }

    //Method 3 
    public static void BFS_03(TreeNode root){
        if(root==null) return;
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
    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root==null) return new ArrayList<>();

        List<List<Integer>> ans = new ArrayList<>();
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

        return ans;
    }

    //Construct Tree

    static int idx = 0;
    public TreeNode constructTree(int[] arr){
        if(idx >= arr.length || arr[idx]==-1){
            idx++;
            return null;
        }

        TreeNode node = new TreeNode(arr[idx++]);
        node.left = constructTree(arr);
        node.right = constructTree(arr);

        return node;
    }

    public void display(TreeNode node){
        if(node==null) return;

        StringBuilder sb = new StringBuilder();
        sb.append((node.left==null ? "." : node.left.val + ""));
        sb.append(" <- " + node.val + " -> ");
        sb.append((node.right == null ? "." : node.right.val + ""));
        System.out.println(sb);

        display(node.left);
        display(node.right);
    }

    public int size(TreeNode node){
        if(node==null) return 0;

        return size(node.left) + size(node.right) + 1;
    }

    public int height(TreeNode node){
        if(node==null) return -1;

        return Math.max(height(node.left),height(node.right)) + 1;
    }

    public boolean find(TreeNode node,int data){
        if(node==null) return false;

        if(node.val==data) return true;

        return find(node.left,data) || find(node.right,data);
    }

    //Leetcode 104
    public int maxDepth(TreeNode root) {
        if(root==null) return 0;

        return Math.max(maxDepth(root.left),maxDepth(root.right)) + 1;
    }

    //Node To Root Path
    public static boolean nodeToRootPath(TreeNode root,int data,List<TreeNode> ans){
        if(root==null) return false;

        if(root.val==data) {
            ans.add(root);
            return true;
        }

        boolean res = false;
        res = nodeToRootPath(root.left,data,ans) || nodeToRootPath(root.right,data,ans);
        if(res) ans.add(root);
        return res;
    }

    //Root To Node Path
    public static boolean rootToNodePath(TreeNode root,int data,List<TreeNode>ans){
        if(root==null) return false;

        if(root.val==data){
            ans.add(root);
            return true;
        }

        ans.add(root);
        boolean res = nodeToRootPath(root.left,data,ans) || nodeToRootPath(root.right,data,ans);
        if(!res) ans.remove(ans.size()-1);
        return res;
    }

    //https://www.geeksforgeeks.org/problems/burning-tree/1
    private static void kDownBurningTree(TreeNode root,TreeNode block,int time,int[] ans){
        if(root==null || root==block) return;
        
        ans[0] = Math.max(ans[0],time);
        
        kDownBurningTree(root.left,block,time+1,ans);
        kDownBurningTree(root.right,block,time+1,ans);
    }
    public static int minTime(TreeNode root, int target) 
    {
        int[] ans = new int[1];
        List<TreeNode> list = new ArrayList<>();
        nodeToRootPath(root,target,list);
        TreeNode prev = null;
        for(int i=0;i<list.size();i++){
            ans[0] = Math.max(ans[0],i);
            kDownBurningTree(list.get(i),prev,i,ans);
            prev = list.get(i);
        }
        return ans[0];
    }

    //Leetcode 236
    //Method 1
    public TreeNode lowestCommonAncestor01(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> list1 = new ArrayList<>();
        nodeToRootPath(root,p.val,list1);

        List<TreeNode> list2 = new ArrayList<>();
        nodeToRootPath(root,q.val,list2);
        int i = list1.size()-1;
        int j = list2.size()-1;

        TreeNode LCA = null;
        while(i>=0 && j>=0){
            if(list1.get(i)==list2.get(j)) LCA = list1.get(i);
            else break;
            i--;
            j--;
        }
        return LCA;

    }

    //Method 2
    public TreeNode lowestCommonAncestor02(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null || p==root || q==root) return root;

        TreeNode left = lowestCommonAncestor02(root.left,p,q);
        TreeNode right = lowestCommonAncestor02(root.right,p,q);
        if(left==null) return right;
        else if(right==null) return left;
        else return root;
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
    public int diameterOfBinaryTree(TreeNode root,int[] diameter){
        if(root==null) return -1;

        int lh = diameterOfBinaryTree(root.left,diameter);
        int rh = diameterOfBinaryTree(root.right,diameter);

        diameter[0] = Math.max(diameter[0], lh+rh+2);
        return Math.max(lh,rh)+1;
    }
    public int diameterOfBinaryTree(TreeNode root) {
        int[] diameter = new int[1];
        diameter[0] = -(int)1e8;
        diameterOfBinaryTree(root,diameter);
        return diameter[0];
    }

    //Leetcode 863
    private boolean nodeToRootPath(TreeNode root,TreeNode target,List<TreeNode> path){
        if(root==null) return false;

        if(root==target) {
            path.add(root);
            return true;
        }

        boolean res = (nodeToRootPath(root.left,target,path) || nodeToRootPath(root.right,target,path));
        if(res) path.add(root);
        return res;
    }
    private void kDown(TreeNode root,TreeNode block,int k,List<Integer> ans){
        if(root==null || root==block) return;

        if(k==0){
            ans.add(root.val);
            return;
        }

        kDown(root.left,block,k-1,ans);
        kDown(root.right,block,k-1,ans);
    }
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<TreeNode> path = new ArrayList<>();
        nodeToRootPath(root,target,path);
        List<Integer> ans = new ArrayList<>();
        TreeNode prev = null;
        for(int i=0;i<path.size();i++){
            if(k-i>=0){
                kDown(path.get(i),prev,k-i,ans);
                prev = path.get(i);
            }
            else break;
        }
        return ans;
    }

    //Leetcode 257
    private void binaryTreePaths(String str,TreeNode root,List<String> ans){
        if(root.left==null && root.right==null){
            ans.add(str);
            return;
        }

        if(root.left!=null)
            binaryTreePaths(str+"->"+root.left.val,root.left,ans);
        if(root.right!=null)
            binaryTreePaths(str+"->"+root.right.val,root.right,ans);
    }
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> ans = new ArrayList<>();
        if(root==null) return ans;
        binaryTreePaths(root.val+"",root,ans);
        return ans;
    }

    //Leetcode 112
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null) return false;
        if(root.left==null && root.right==null && targetSum-root.val==0) return true;
        return hasPathSum(root.left,targetSum-root.val) || hasPathSum(root.right,targetSum-root.val);
    }

    //Leetcode 113
    private void pathSum(TreeNode root,int targetSum,List<Integer> res,List<List<Integer>> ans){
        if(root==null) return;
        res.add(root.val);

        if(root.left==null && root.right==null){
            if(targetSum-root.val==0){
                ans.add(new ArrayList<>(res));
            }
            res.remove(res.size()-1);
            return;
        }

        pathSum(root.left,targetSum-root.val,res,ans);
        pathSum(root.right,targetSum-root.val,res,ans);
        res.remove(res.size()-1);
    }
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if(root==null) return new ArrayList<>();

        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        pathSum(root,targetSum,res,ans);
        return ans;
    }

    //Max sum path between 2 leaves - GFG
    /*https://www.geeksforgeeks.org/find-maximum-path-sum-two-leaves-binary-tree/ - Assuming here that the root has left and right subtree.
                      A          
                     / \
                    B   C  - Minimum start for the tree 
    */
    public static int maxPathSum(TreeNode root,int[] max){
        if(root==null) return -(int)1e9;

        if(root.left==null && root.right==null){
            return root.val;
        }
        int left = maxPathSum(root.left,max);
        int right = maxPathSum(root.right,max);

        if(root.left!=null && root.right!=null){
            max[0] = Math.max(max[0],left+right+root.val);
        }

        return Math.max(left,right)+root.val;
    }
    public static int maxPathSum_(TreeNode root){
        int[] max = new int[1];
        max[0] = -(int)1e8;

        maxPathSum(root,max);
        return max[0];
    }

    //https://www.geeksforgeeks.org/problems/maximum-path-sum/1
    int maxPathSum(TreeNode root)
    { 
        int[] max = new int[1];
        max[0] = -(int)1e9;
        int ans = maxPathSum(root,max);
        if(root.left==null || root.right==null)
            max[0] = Math.max(max[0],ans);
        return max[0];
    } 

    //Leetcode 1038
    private void bstToGst(TreeNode root,int[] pre){
        if(root==null) return;

        bstToGst(root.right,pre);
        root.val += pre[0];
        pre[0] = root.val;
        bstToGst(root.left,pre);
    }
    public TreeNode bstToGst(TreeNode root) {
        int[] pre = new int[1];
        bstToGst(root,pre);
        return root;
    }

    //Leetcode 116
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
    }
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

    //Leetcode 117
    //Method 1
    public Node connect01(Node root) {
        if(root==null) return root;

        Queue<Node> que = new LinkedList<>();
        que.add(root);

        while(que.size()!=0){
            int size = que.size();
            que.add(null);
            while(size-->0){
                Node node = que.remove();
                node.next = que.peek();
                if(node.left!=null) que.add(node.left);
                if(node.right!=null) que.add(node.right);
            }
            que.remove();
        }

        return root;
    }

    //Method 2
    public Node connect02(Node root) {
        if(root==null) return root;

        Node prevHead = root;
        Node prevCurr = null;
        Node curr = null;
        Node currHead = null;

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

    //Leetcode 834
    class Solution_SumOfDistancesInTree {
        private int N;
        private void dfs1(int node,int par,int[] count,int[] ans,List<List<Integer>> graph){
            for(int child:graph.get(node)){
                if(child!=par){
                    dfs1(child,node,count,ans,graph);
                    count[node] += count[child];
                    ans[node] += count[child] + ans[child];
                }
            }
        }
        private void dfs2(int node,int par,int[] count,int[] ans,List<List<Integer>> graph){
            for(int child:graph.get(node)){
                if(child!=par){
                    ans[child] = ans[node] - count[child] + N - count[child];
                    dfs2(child,node,count,ans,graph);
                }
            }
        }
        public int[] sumOfDistancesInTree(int n, int[][] edges) {
            List<List<Integer>> graph = new ArrayList<>();
            this.N = n;
            for(int i=0;i<n;i++) graph.add(new ArrayList<>());
            for(int[] edge:edges){
                int u = edge[0];
                int v = edge[1];
                graph.get(u).add(v);
                graph.get(v).add(u);
            }
    
            int[] ans = new int[n];
            int[] count = new int[n];
            Arrays.fill(count,1);
    
            dfs1(0,-1,count,ans,graph);
            dfs2(0,-1,count,ans,graph);
            return ans;
        }
    }

    

    //Leetcode 101
    private boolean isSymmetric(TreeNode p,TreeNode q){
        if(p==null || q==null){
            return p==q;
        }

        return p.val==q.val && isSymmetric(p.left,q.right) && isSymmetric(p.right,q.left);
    }
    public boolean isSymmetric(TreeNode root) {
        return isSymmetric(root,root);
    }

    //Leetcode 156 - Locked
    //Lintcode 649
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        TreeNode prev = null;
        TreeNode prevRight = null;
        TreeNode curr = root;

        while(curr!=null){
            TreeNode currLeft = curr.left;
            TreeNode currRight = curr.right;
            curr.left = prevRight;
            curr.right = prev;
            prev = curr;
            prevRight = currRight;
            curr = currLeft;
        }
        return prev;
    }


    //Leetcode 199
    //Method 1.1
    public List<Integer> rightSideView_01(TreeNode root) {
        if(root==null) return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();

        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);

        while(que.size()!=0){
            int size = que.size();
            while(size-->0){
                TreeNode node = que.remove();
                if(size==0) ans.add(node.val);
                if(node.left!=null) que.add(node.left);
                if(node.right!=null) que.add(node.right);
            }
        }
        return ans;
    }

    //Method 1.2
    public List<Integer> rightSideView_02(TreeNode root) {
        if(root==null) return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();

        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);
        int level = 0;

        while(que.size()!=0){
            int size = que.size();
            while(size-->0){
                TreeNode node = que.remove();
                if(level==ans.size()) ans.add(node.val);
                if(node.right!=null) que.add(node.right);
                if(node.left!=null) que.add(node.left);
            }
            level++;
        }
        return ans;
    }

    //Method 2 - Recursive - DFS solution
    public void rightSideView(TreeNode root,int depth,List<Integer> ans){
        if(root==null) return;

        if(depth==ans.size()) ans.add(root.val);
        rightSideView(root.right,depth+1,ans);
        rightSideView(root.left,depth+1,ans);
    }
    public List<Integer> rightSideView(TreeNode root) {
        if(root==null) return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();
        rightSideView(root,0,ans);
        return ans;
    }

    //Right Side View of Tree - Interview Type
    class TreePair{
        TreeNode node;
        int y;
        TreePair(TreeNode node,int y){
            this.node = node;
            this.y = y;
        }
    }

    public List<Integer> rightSide(TreeNode root){
        if(root==null) new ArrayList<>();

        Queue<TreePair> q = new LinkedList<>();
        q.add(new TreePair(root,0));
        List<TreePair> ans = new ArrayList<>();
        int level = 0;
        while(q.size()!=0){
            int size = q.size();
            while(size-->0){
                TreePair vtx = q.remove();
                if(level==ans.size()) ans.add(vtx);
                else if(ans.get(level).y < vtx.y) ans.set(level,vtx);
                if(vtx.node.right!=null) q.add(new TreePair(vtx.node.right,vtx.y+1));
                if(vtx.node.left!=null) q.add(new TreePair(vtx.node.left,vtx.y-1));
            }
            level++;
        }

        List<Integer> res = new ArrayList<>();
        for(TreePair p:ans){
            res.add(p.node.val);
        }
        return res;
    }

    //https://www.geeksforgeeks.org/problems/left-view-of-binary-tree/1
    //Method 1 - Using Level Order Traversal
    ArrayList<Integer> leftView01(TreeNode root)
    {
        if(root==null) return new ArrayList<>();
        
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        ArrayList<Integer> ans = new ArrayList<>();
        int level = 0;
        int size;
        while(q.size()!=0){
            size = q.size();
            while(size-->0){
                TreeNode vtx = q.remove();
                if(level==ans.size()) ans.add(vtx.val);
                if(vtx.left!=null) q.add(vtx.left);
                if(vtx.right!=null) q.add(vtx.right);
            }
            level++;
        }
        return ans;
    }

    //Method 2 - DFS solution
    private void leftView(TreeNode root,int depth,ArrayList<Integer> ans){
        if(root==null) return;
        
        if(depth==ans.size()) ans.add(root.val);
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
    public List<Integer> leftSide(TreeNode root){
        if(root==null) new ArrayList<>();

        Queue<TreePair> q = new LinkedList<>();
        q.add(new TreePair(root,0));
        List<TreePair> ans = new ArrayList<>();
        int level = 0;
        while(q.size()!=0){
            int size = q.size();
            while(size-->0){
                TreePair vtx = q.remove();
                if(level==ans.size()) ans.add(vtx);
                else if(ans.get(level).y > vtx.y) ans.set(level,vtx);

                if(vtx.node.left!=null) q.add(new TreePair(vtx.node.left,vtx.y-1));
                if(vtx.node.right!=null) q.add(new TreePair(vtx.node.right,vtx.y+1));
            }
            level++;
        }

        List<Integer> res = new ArrayList<>();
        for(TreePair p:ans){
            res.add(p.node.val);
        }
        return res;
    }

    //Width of Binary Tree - Will be used in a lot of questions
    void width(TreeNode root,int y,int[] maxMin){
        if(root==null) return;
        
        maxMin[0] = Math.min(maxMin[0],y);
        maxMin[1] = Math.max(maxMin[1],y);
        
        width(root.left,y-1,maxMin);
        width(root.right,y+1,maxMin);
    }

    //https://www.geeksforgeeks.org/problems/top-view-of-binary-tree/1
    ArrayList<Integer> topView(TreeNode root) {
        
        if(root==null) return new ArrayList<>();
        
        int[] maxMin = new int[2];
        //0 -> min width , 1 -> max width
        
        width(root,0,maxMin);
        int n = maxMin[1] - maxMin[0] + 1;
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=0;i<n;i++) ans.add(null);
        
        Queue<TreePair> q = new LinkedList<>();
        q.add(new TreePair(root,-maxMin[0]));
        
        while(!q.isEmpty()){
            
            TreePair vtx = q.remove();
            
            if(ans.get(vtx.y)==null) ans.set(vtx.y,vtx.node.val);
            
            if(vtx.node.left!=null) q.add(new TreePair(vtx.node.left,vtx.y-1));
            if(vtx.node.right!=null) q.add(new TreePair(vtx.node.right,vtx.y+1));
        }
        
        return ans;
    }

    //https://practice.geeksforgeeks.org/problems/bottom-view-of-binary-tree/1
    public ArrayList<Integer> bottomView(TreeNode root)
    {
        if(root==null) return new ArrayList<>();
        
        int[] maxMin = new int[2];
        width(root,0,maxMin);
        int n = maxMin[1] - maxMin[0] + 1;
        
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=0;i<n;i++) ans.add(null);
        Queue<TreePair> q = new LinkedList<>();
        q.add(new TreePair(root,-maxMin[0]));
        
        while(q.size()!=0){
            int size = q.size();
            while(size-->0){
                TreePair vtx = q.remove();
                ans.set(vtx.y,vtx.node.val);
                
                if(vtx.node.left!=null) q.add(new TreePair(vtx.node.left,vtx.y-1));
                if(vtx.node.right!=null) q.add(new TreePair(vtx.node.right,vtx.y+1));
            }
        }
        
        return ans;
    }

    //Leetcode 987
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        int[] minMax = new int[2];
        width(root,0,minMax);
        int w = minMax[1] - minMax[0] + 1;

        List<List<Integer>> ans = new ArrayList<>();
        for(int i=0;i<w;i++){
            ans.add(new ArrayList<>());
        }

        PriorityQueue<TreePair> pq = new PriorityQueue<TreePair>((a,b) -> {
            if(a.y!=b.y){
                return a.y - b.y;
            }else{
                return a.node.val - b.node.val;
            }
        });

        Queue<TreePair> q = new LinkedList<>();
        q.add(new TreePair(root,-minMax[0]));

        while(!q.isEmpty()){
            int size = q.size();
            while(size-->0){
                TreePair vtx = q.remove();
                pq.add(vtx);

                if(vtx.node.left!=null) q.add(new TreePair(vtx.node.left,vtx.y-1));
                if(vtx.node.right!=null) q.add(new TreePair(vtx.node.right,vtx.y+1));
            }
            while(!pq.isEmpty()){
                TreePair vtx = pq.remove();
                ans.get(vtx.y).add(vtx.node.val);
            }
        }

        return ans;
    }

    //https://www.geeksforgeeks.org/problems/diagonal-traversal-of-binary-tree/1
    private void diagonalTraversal(TreeNode root,int level,HashMap<Integer,ArrayList<Integer>> hm){
        if(root==null) return;
        
        if(!hm.containsKey(level)){
            hm.put(level,new ArrayList<>());
            hm.get(level).add(root.val);
        }else{
            hm.get(level).add(root.val);
        }
        
        diagonalTraversal(root.left,level+1,hm);
        diagonalTraversal(root.right,level,hm);
    }
    public ArrayList<Integer> diagonal(TreeNode root){
        if(root==null) new ArrayList<>();
        
        HashMap<Integer,ArrayList<Integer>> hm = new HashMap<>();
        diagonalTraversal(root,0,hm);
        
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=0;i<hm.size();i++){
            ArrayList<Integer> nodes = hm.get(i);
            for(int n:nodes)
                ans.add(n);
        }
        return ans;
    }

    //https://www.geeksforgeeks.org/problems/boundary-traversal-of-binary-tree/1
    private void leftBoundary(TreeNode root,ArrayList<Integer> ans){
        if(root==null) return;
        
        if(root.left!=null || root.right!=null){
            ans.add(root.val);
            if(root.left!=null) leftBoundary(root.left,ans);
            else leftBoundary(root.right,ans);
        }
    }
    private void leaves(TreeNode root,ArrayList<Integer> ans){
        if(root==null) return;
        
        leaves(root.left,ans);
        if(root.left==null && root.right==null) ans.add(root.val);
        leaves(root.right,ans);
    }
    private void rightBoundary(TreeNode root,ArrayList<Integer> ans){
        if(root==null) return;
        
        if(root.right!=null || root.left!=null){
            if(root.right!=null) rightBoundary(root.right,ans);
            else rightBoundary(root.left,ans);
            ans.add(root.val);
        }
    }
	ArrayList<Integer> boundary(TreeNode root)
	{
	    ArrayList<Integer> ans = new ArrayList<>();
	    ans.add(root.val);
	    
	    leftBoundary(root.left,ans);
	    
	    leaves(root.left,ans);
	    leaves(root.right,ans);
	    
	    rightBoundary(root.right,ans);
	    
	    return ans;
	}

    //https://www.geeksforgeeks.org/problems/mirror-tree/1
    public void mirror(TreeNode root) {
        if(root==null) return;
        mirror(root.left);
        mirror(root.right);
        TreeNode node = root.left;
        root.left = root.right;
        root.right = node;
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
        if(n==1) return false;

        TreeNode node = findNode(root,x);
        int l = numberOfNodes(node.left);
        int r = numberOfNodes(node.right);

        if((l>n/2) || (r>n/2) || ((n-l-r-1)>n/2)) return true;
        return false;
    }

    //https://www.geeksforgeeks.org/problems/image-multiplication0627/1
    private long imgMultiply(TreeNode node1,TreeNode node2,int mod){
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
    private int maxPathSum2(TreeNode root,int[] max){
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

    //Leetcode 250 - Locked
    //Lintcode 921
    private boolean countUnivalSubtrees(TreeNode root,int[] ans){
        if(root==null) return true;

        boolean l = countUnivalSubtrees(root.left,ans);
        boolean r = countUnivalSubtrees(root.right,ans);

        if(!l || !r) return false;
        int lval = root.left!=null ? root.left.val : root.val;
        int rval = root.right!=null ? root.right.val : root.val;
        if(lval==rval && root.val==lval){
            ans[0]++;
            return true;
        }
        return false;
    }
    public int countUnivalSubtrees(TreeNode root) {
        int[] ans = new int[1];
        countUnivalSubtrees(root,ans);
        return ans[0];
    }

    //Leetcode 404
    private void sumOfLeftLeaves(TreeNode root,TreeNode par,int[] sum){
        if(root==null) return;

        if(root.left==null && root.right==null && par.left==root) sum[0] += root.val;
        sumOfLeftLeaves(root.left,root,sum);
        sumOfLeftLeaves(root.right,root,sum);
    }
    public int sumOfLeftLeaves(TreeNode root) {
        int[] sum = new int[1];
        sumOfLeftLeaves(root.left,root,sum);
        sumOfLeftLeaves(root.right,root,sum);
        return sum[0];
    }

    //Leetcode 98
    //Method 1
    protected class BSTpair{
        long max = -(long)1e13;
        long min = (long)1e13;
        boolean isValid = true;
        BSTpair(long max,long min,boolean isValid){
            this.max = max;
            this.min = min;
            this.isValid = isValid;
        }
        BSTpair(){

        }
    }
    private BSTpair isValidBST_(TreeNode root){
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
    long prev = -(long)1e13;
    public boolean isValidBST(TreeNode root) {
        if(root==null) return true;

        if(!isValidBST(root.left)) return false;

        if(prev>=root.val) return false;
        prev = root.val;
        
        if(!isValidBST(root.right)) return false;

        return true;
    }

    //Leetcode 99
    class RecoverTree{
        TreeNode prev,first,middle,last;
        private void recoverTreeUtil(TreeNode root){
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

            int data;
            if(first!=null && last!=null){
                data = first.val;
                first.val = last.val;
                last.val = data;
            }else if(first!=null && middle!=null){
                data = first.val;
                first.val = middle.val;
                middle.val = data;
            }
        }
    }

    //Leetcode 

    //Leetcode 701
    //Method 1 - Recursive Method
    public TreeNode insertIntoBST01(TreeNode root, int val) {
        if(root==null) return new TreeNode(val);

        if(root.val>val) root.left = insertIntoBST01(root.left,val);

        else if(root.val<val) root.right = insertIntoBST01(root.right,val);

        return root;
    }

    //Method 2 - Iterative Method
    public TreeNode insertIntoBST02(TreeNode root, int val) {
        TreeNode curr = root;
        TreeNode prev = null;

        while(curr!=null){
            prev = curr;
            if(curr.val>val) curr = curr.left;
            else curr = curr.right;
        }

        TreeNode node = new TreeNode(val);
        if(prev==null) return node;
        if(prev.val>val) prev.left = node;
        else prev.right = node;
        return root;
    }

    //Leetcode 450
    public TreeNode rightmostNode(TreeNode root){
        TreeNode curr = root;
        while(curr.right!=null) curr = curr.right;
        return curr;
    }
    public TreeNode deleteNode(TreeNode root, int key) {
        if(root==null) return root;

        if(root.val>key)
            root.left = deleteNode(root.left,key);
        else if(root.val<key)
            root.right = deleteNode(root.right,key);
        else{
            if(root.left==null || root.right==null)
                return (root.left!=null)? root.left : root.right;
            TreeNode maxEle = rightmostNode(root.left); 
            TreeNode left = deleteNode(root.left,maxEle.val);
            maxEle.left = left;
            maxEle.right = root.right;
            root = maxEle;
        }
        return root;
    }

    //BST BASIC QUESTIONS
    public int maximumEle(TreeNode root){
        TreeNode curr = root;
        while(curr.right!=null){
            curr = curr.right;
        }
        return curr.val;
    }

    public int minimumEle(TreeNode root){
        TreeNode curr = root;
        while(curr.left!=null){
            curr = curr.left;
        }
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

    public boolean findDataRecursion(TreeNode root,int data){
        if(root==null) return false;

        if(root.val==data) return true;

        else if(root.val>data) return findDataRecursion(root.left,data);

        else return findDataRecursion(root.right,data);
    }

    //Inorder Successor and Predecessor in Binary Tree
    protected class allSolPair{
        TreeNode prev;
        TreeNode pred;
        TreeNode succ;

        int ceil = (int)1e9;
        int floor = -(int)1e9;
    }
    private void allSolution(TreeNode root,int data,allSolPair ans){
        if(root==null) return;

        if(root.val>data) ans.ceil = Math.min(ans.ceil,root.val);
        if(root.val<data) ans.floor = Math.max(ans.floor,root.val);

        allSolution(root.left,data,ans);

        if(root.val==data) ans.pred = ans.prev;
        if(ans.prev!=null && ans.prev.val==data) ans.succ = root;

        ans.prev = root;
        allSolution(root.right,data,ans); 
    }
    public allSolPair allSolution(TreeNode root,int data){
        allSolPair ans = new allSolPair();
        allSolution(root,data,ans);
        return ans;
    }

    //Inorder Successor and Predecessor in BST
    //Iterative Solution
    public static void predSuccInBST(TreeNode root,int data){
        TreeNode curr = root;
        TreeNode pred = null;
        TreeNode succ = null;

        while(curr!=null){
            if(curr.val>data){
                pred = curr;
                curr = curr.left;
            }else if(curr.val<data){
                succ = curr;
                curr = curr.right;
            }else{
                if(curr.left!=null){
                    pred = curr.left;
                    while(pred.right!=null) pred = pred.right;
                }
                if(curr.right!=null){
                    succ = curr.right;
                    while(succ.left!=null) succ = succ.left;
                }
                break;
            }
        }
    }

    //Leetcode 1339
    private long maxProduct(TreeNode root,long[] ans){
        if(root==null) return 0;
        long leftSum = maxProduct(root.left,ans);
        long rightSum = maxProduct(root.right,ans);
        if(ans[0]>0){
            ans[1] = Math.max(ans[1],leftSum*(ans[0]-leftSum));
            ans[1] = Math.max(ans[1],rightSum*(ans[0]-rightSum));
        }
        return root.val + leftSum + rightSum;
    }
    public int maxProduct(TreeNode root) {
        long[] ans = new long[2]; //0 -> totalSum, 1-> ans
        ans[0] = maxProduct(root,ans);
        maxProduct(root,ans);
        int mod = (int)1e9 + 7;
        return (int)(ans[1]%mod);
    }

    //Leetcode 235
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;
        while(curr!=null){
            if(curr.val>p.val && curr.val>q.val) curr = curr.left;
            else if(curr.val<p.val && curr.val<q.val) curr = curr.right; 
            else break;
        }
        return curr;
    }

    //Leetcode 979
    public int distributeCoins(TreeNode root,int[] ans){
        if(root==null) return 0;
        int l = distributeCoins(root.left,ans);
        int r = distributeCoins(root.right,ans);
        ans[0] += Math.abs(l) + Math.abs(r);
        return root.val + l + r - 1;
    }
    public int distributeCoins(TreeNode root) {
        int[] ans = new int[1];
        distributeCoins(root,ans);
        return ans[0];
    }

    //Leetcode 968
    public int minCameraCover(TreeNode root,int[] ans){
        if(root==null) return 0;
        int left = minCameraCover(root.left,ans);
        int right = minCameraCover(root.right,ans);
        if(left==-1 || right==-1){
            ans[0]++;
            return 1;
        }
        if(left==1||right==1){
            return 0;
        }
        return -1;
    }
    public int minCameraCover(TreeNode root) {
        int[] ans = new int[1];
        if(minCameraCover(root,ans)==-1) ans[0]++;
        return ans[0];
    }

    //Leetcode 114
    public TreeNode lastNode(TreeNode root){
        TreeNode curr = root;
        while(curr.right!=null) curr = curr.right;
        return curr;
    }
    public void flatten(TreeNode root) {
        if(root==null) return;

        flatten(root.left);
        if(root.left!=null){
            TreeNode temp = root.right;
            root.right = root.left;
            root.left = null;
            TreeNode last = lastNode(root.right);
            last.right = temp;
            flatten(temp);
        }
        else flatten(root.right);
    }

    //Leetcode 173
    class BSTIterator {
        private Stack<TreeNode> st;
        public BSTIterator(TreeNode root) {
            this.st = new Stack<>();
            TreeNode curr = root;
            while(curr!=null){
                this.st.push(curr);
                curr = curr.left;
            }
        }
        
        public int next() {
            TreeNode curr = this.st.pop();
            int result = curr.val;
            curr = curr.right;
            while(curr!=null){
                this.st.push(curr);
                curr = curr.left;
            }
            return result;
        }
        
        public boolean hasNext() {
            return !this.st.isEmpty();
        }
    }

    //Leetcode 510 - Inorder Successor in BST II  - Locked
    //https://leetcode.ca/all/510.html
    class Node_ {
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
        Node_ curr = node;

        if(curr.right!=null){
            curr = curr.right;
            while(curr.left!=null) curr = curr.left;
            return curr;
        }else{
            while(node.parent!=null && node==node.parent.right){
                node = node.parent;
            }
            return node.parent;
        }

    }

    //Leetcode 1008
    private TreeNode bstFromPreorder(int[] preorder,int[] idx,int lrange,int rrange){
        if(idx[0]>=preorder.length || preorder[idx[0]]<lrange || preorder[idx[0]]>rrange) return null;

        int data = preorder[idx[0]++];
        TreeNode p = new TreeNode(data);
        p.left = bstFromPreorder(preorder,idx,lrange,data);
        p.right = bstFromPreorder(preorder,idx,data,rrange);
        return p;
    }
    public TreeNode bstFromPreorder(int[] preorder) {
        if(preorder.length==1) return new TreeNode(preorder[0]);
        int[] idx = new int[1];
        return bstFromPreorder(preorder,idx,-(int)1e8,(int)1e8);
    }

    //Leetcode 105
    private TreeNode buildTree01(int psi,int pei,int[] preorder,int isi,int iei,int[] inorder){
        if(psi>pei) return null;
        TreeNode node = new TreeNode(preorder[psi]);
        int idx = isi;
        while(inorder[idx]!=preorder[psi]) idx++;
        int count = idx-isi;
        node.left = buildTree01(psi+1,psi+count,preorder,isi,idx-1,inorder);
        node.right = buildTree01(psi+count+1,pei,preorder,idx+1,iei,inorder);
        return node;
    }
    public TreeNode buildTree01(int[] preorder, int[] inorder) {
        if(preorder.length==1) return new TreeNode(preorder[0]);
        return buildTree01(0,preorder.length-1,preorder,0,inorder.length-1,inorder);
    }

    //Leetcode 106
    private TreeNode buildTree(int isi,int iei,int[] inorder,int psi,int pei,int[] postorder){
        if(psi>pei) return null;
        TreeNode node = new TreeNode(postorder[pei]);
        int idx = isi;
        while(inorder[idx]!=postorder[pei]) idx++;
        int count = idx - isi;
        node.left = buildTree(isi,idx-1,inorder,psi,psi+count-1,postorder);
        node.right = buildTree(idx+1,iei,inorder,psi+count,pei-1,postorder);
        return node;

    }
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if(inorder.length==1) return new TreeNode(inorder[0]);
        int n = inorder.length;
        return buildTree(0,n-1,inorder,0,n-1,postorder);
    }

    //https://www.geeksforgeeks.org/problems/construct-bst-from-post-order/1
    private TreeNode constructTree(int lrange,int rrange,int[] post,int[] idx){
        if(idx[0]<0 || (post[idx[0]]<lrange)|| (post[idx[0]]>rrange)){
            return null;
        }
        
        TreeNode node = new TreeNode(post[idx[0]--]);
        node.right = constructTree(node.val,rrange,post,idx);
        node.left = constructTree(lrange,node.val,post,idx);
        return node;
    }
    public TreeNode constructTree(int post[],int n)
    {
        if(n==1) return new TreeNode(post[0]);
        int[] idx = new int[1];
        idx[0] = n-1;
        return constructTree(-(int)1e8,(int)1e8,post,idx);
    }

    //https://www.geeksforgeeks.org/problems/construct-tree-from-inorder-and-levelorder/1
    private TreeNode buildTree(int isi,int iei,int[] inord,int[] level,int n){
        if(n<=0) return null;
        TreeNode root = new TreeNode(level[0]);
        int idx = -1;
        for(int i=isi;i<=iei;i++){
            if(root.val==inord[i]){
                idx = i;
                break;
            }
        }
        
        HashSet<Integer> set = new HashSet<>();
        for(int i=isi;i<idx;i++){
            set.add(inord[i]);
        }
        
        int[] leftLevel = new int[idx-isi];
        int[] rightLevel = new int[iei-idx];
        
        int li = 0,ri=0;
        for(int i=1;i<n;i++){
            if(set.contains(level[i]))
                leftLevel[li++] = level[i];
            else rightLevel[ri++] = level[i];
        }
        
        root.left = buildTree(isi,idx-1,inord,leftLevel,idx-isi);
        root.right = buildTree(idx+1,iei,inord,rightLevel,iei-idx);
        return root;
    }
    TreeNode buildTree_fromInorderLevelOrder(int inord[], int level[])
    {
        int n = inord.length;
        if(n==1) return new TreeNode(inord[0]);
        return buildTree(0,n-1,inord,level,n);
    }

    //Leetcode 889
    private TreeNode constructFromPrePost(int prsi,int prei,int posi,int poei,int[] preorder,int[] postorder){
        if(prsi>prei) return null;
        if(prsi==prei) return new TreeNode(preorder[prsi]);

        TreeNode node = new TreeNode(preorder[prsi]);
        int idx = posi;
        while(postorder[idx]!=preorder[prsi+1]) idx++;

        int count = idx-posi+1;
        node.left = constructFromPrePost(prsi+1,prsi+count,posi,idx,preorder,postorder);
        node.right = constructFromPrePost(prsi+count+1,prei,idx+1,poei-1,preorder,postorder);
        return node;
    }
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        int n = preorder.length;
        if(n==1) return new TreeNode(preorder[0]);
        return constructFromPrePost(0,n-1,0,n-1,preorder,postorder);
    }

    //https://www.geeksforgeeks.org/problems/convert-level-order-traversal-to-bst/1
    //Method 1
    private Node constructBST(List<Integer> level,int n){
        if(n<=0) return null;
        int element = level.get(0);
        Node root = new Node(element);
        
        List<Integer> leftLevel = new ArrayList<>();
        List<Integer> rightLevel = new ArrayList<>();
        int n1 = 0;
        for(int i=1;i<n;i++){
            if(level.get(i)<element){
                leftLevel.add(level.get(i));
                n1++;
            }else rightLevel.add(level.get(i));
        }
        
        root.left = constructBST(leftLevel,n1);
        root.right = constructBST(rightLevel,n-n1-1);
        return root;
    }
    public Node constructBST(int[] arr)
    {
        int n = arr.length;
        if(n==1) return new Node(arr[0]);
        List<Integer> level = new ArrayList<>();
        for(int ele:arr) level.add(ele);
        return constructBST(level,n);
    }

    //Method 2
    private TreeNode constructBST(TreeNode root,int ele){
        if(root==null) return new TreeNode(ele);
        
        if(ele<root.val)
            root.left = constructBST(root.left,ele);
        else 
            root.right = constructBST(root.right,ele);
        return root;
    }
    public TreeNode constructBST02(int[] arr)
    {
        if(arr.length==1) return new TreeNode(arr[0]);
        TreeNode root = null;
        for(int ele:arr){
            root = constructBST(root,ele);
        }
        return root;
    }

    //https://www.geeksforgeeks.org/problems/binary-tree-to-dll/1
    class Solution_bToDLL
    {
        private Node prev,head;
        private void bToDLLUtil(Node root){
            if(root==null) return;
            
            bToDLLUtil(root.left);
            
            if(head==null) head = root;
            if(prev!=null) {
                prev.right = root;
            }
            root.left = prev;
            prev = root;
            
            bToDLLUtil(root.right);
        
        }
        Node bToDLL(Node root)
        {
            prev = null;
            head = null;
            
            bToDLLUtil(root);
            return head;
        }
    }

    //https://www.geeksforgeeks.org/problems/binary-tree-to-cdll/1
    class Solution_bToCDLL
    { 
        //Function to convert binary tree into circular doubly linked list.
        TreeNode dummy = new TreeNode(-1);
        TreeNode prev = dummy;
        private TreeNode bTreeToClistUtil(TreeNode root){
            if(root==null) return root;
            
            bTreeToClistUtil(root.left);
            
            prev.right = root;
            root.left = prev;
            prev = root;
            
            bTreeToClistUtil(root.right);
            
            return root;
        }
        TreeNode bTreeToClist(TreeNode root)
        {
            bTreeToClistUtil(root);
            
            TreeNode head = dummy.right;
            dummy.right = null;
            head.left = prev;
            prev.right = head;
            
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
        private int countNodes(Node head){
            int n = 0;
            Node curr = head;
            while(curr!=null){
                n++;
                curr = curr.next;
            }
            return n;
        }
        private Node sortedDLLToBalancedBST(int n){
            if(n<=0) return null;

            Node left = sortedDLLToBalancedBST(n/2);

            Node root = head;
            root.prev = left;
            head = head.next;

            root.next = sortedDLLToBalancedBST(n - n/2  - 1);

            return root;
        }
        public Node sortedDLLToBalancedBST(Node head){
            this.head = head;
            int n = countNodes(head);
            return sortedDLLToBalancedBST(n);
        }
    }

    //Leetcode 230
    class kthSmallest_Solution{
        int n;
        TreeNode ans;
        private void kthSmallest(TreeNode root){
            if(root==null || this.n==0) return;

            kthSmallest(root.left);

            this.n--;
            if(this.n==0) {
                ans = root;
                return;
            }

            kthSmallest(root.right);
            
        }
        public int kthSmallest(TreeNode root, int k) {
            this.n = k;
            kthSmallest(root);
            return ans.val;
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
        if(root==null) return 0;
        int[] ans = new int[1];
        HashMap<Long,Integer> map = new HashMap<>();
        map.put((long)0,1);
        pathSumUtil(root,targetSum,0,map,ans);
        return ans[0];
    }

    //Leetcode 662
    protected class NodeIdx{
        TreeNode node;
        int idx;
        NodeIdx(TreeNode node,int idx){
            this.node = node;
            this.idx = idx;
        }
    }
    public int widthOfBinaryTree(TreeNode root) {
        Queue<NodeIdx> que = new LinkedList<>();
        que.add(new NodeIdx(root,1));

        int maxWidth = 0;
        int size;
        while(que.size()!=0){
            size = que.size();
            int fi = que.peek().idx;
            while(size-->0){
                NodeIdx vtx = que.remove();
                if(size==0){
                    int li = vtx.idx;
                    maxWidth = Math.max(maxWidth,li-fi+1);
                }
                if(vtx.node.left!=null) que.add(new NodeIdx(vtx.node.left,2*vtx.idx));
                if(vtx.node.right!=null) que.add(new NodeIdx(vtx.node.right,2*vtx.idx + 1));
            }
        }

        return maxWidth;
    }

    //Leetcode 337
    public int[] robUtil(TreeNode root){
        if(root==null) return new int[] {0,0};
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
    protected class LRZigZag{
        int l = 0;
        int r = 0;
        LRZigZag(int l,int r){
            this.l = l;
            this.r = r;
        }
    }
    private LRZigZag longestZigZag(TreeNode root,int[] max){
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
    public boolean findTarget(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        inorderTraversal(root,list);

        int si=0,ei=list.size()-1;
        int sum;
        while(si<ei){
            sum = list.get(si) + list.get(ei);
            if(sum==k) return true;
            else if(sum<k) si++;
            else ei--;
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
                }else sb.append("#,");
            }
            return sb.toString();
        }
    
        // Decodes your encoded data to tree.
        private TreeNode deserialize(int[] idx,String[] arr){
            if(arr[idx[0]].equals("#")) return null;
    
            TreeNode root = new TreeNode(Integer.parseInt(arr[idx[0]]));
            idx[0] = idx[0] + 1;
            root.left = deserialize(idx,arr);
            idx[0] = idx[0] + 1;
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
    private TreeNode rightMostNode(TreeNode node,TreeNode curr){
        TreeNode rmost = node;
        while(rmost.right!=null && rmost.right!=curr){
            rmost = rmost.right;
        }
        return rmost;
    }
    public List<Integer> morrisInorderList(TreeNode root){
        if(root==null) return new ArrayList<>();

        TreeNode curr = root;
        TreeNode next;
        List<Integer> list = new ArrayList<>();
        while(curr!=null){
            next = curr.left;
            if(next==null){
                list.add(curr.val);
                curr = curr.right;
            }else{
                TreeNode rmost = rightMostNode(next,curr);
                if(rmost.right==null){
                    rmost.right=curr;
                    curr = curr.left;
                }else{
                    rmost.right = null;
                    list.add(curr.val);
                    curr = curr.right;
                }
            }
        }

        return list;
    }

    //Morris Preorder Traversal
    public List<Integer> morrisPreorderList(TreeNode root){
        if(root==null) return new ArrayList<>();

        TreeNode curr = root;
        TreeNode next;
        List<Integer> list = new ArrayList<>();
        while(curr!=null){
            next = curr.left;
            if(next==null){
                list.add(curr.val);
                curr = curr.right;
            }else{
                TreeNode rmost = rightMostNode(next,curr);
                if(rmost.right==null){
                    list.add(curr.val);
                    rmost.right=curr;
                    curr = curr.left;
                }else{
                    rmost.right = null;
                    curr = curr.right;
                }
            }
        }

        return list;
    }

    //https://www.geeksforgeeks.org/problems/median-of-bst/1
    private int morrisInorderCountNodes(TreeNode root){

        int count = 0;
        TreeNode curr = root;
        TreeNode next = null;
        while(curr!=null){
            next = curr.left;
            if(next==null){
                count++;
                curr = curr.right;
            }else{
                TreeNode rmost = rightMostNode(next,curr);
                if(rmost.right==null){
                    rmost.right = curr;
                    curr = curr.left;
                }else{
                    count++;
                    rmost.right = null;
                    curr = curr.right;
                }
            }
        }
        
        return count;
    }
    private void kthSmallest(TreeNode root,int n,int[] arr){
        
        int count = 0;
        TreeNode curr = root;
        TreeNode next = null;
        boolean even = (n%2==0)? true:false;
        while(curr!=null){
            next = curr.left;
            if(next==null){
                count++;
                if(even){
                    if(count==n/2) arr[0] = curr.val;
                    if(count==(n/2 + 1)) {
                        arr[1] = curr.val;
                        break;
                    }
                }else{
                    if(count==(n+1)/2) {
                        arr[0] = arr[1] = curr.val;
                        break;
                    }
                }
                curr = curr.right;
            }else{
                TreeNode rmost = rightMostNode(next,curr);
                if(rmost.right==null){
                    rmost.right = curr;
                    curr = curr.left;
                }else{
                    count++;
                    if(even){
                        if(count==n/2) arr[0] = curr.val;
                        if(count==(n/2 + 1)) {
                            arr[1] = curr.val;
                            break;
                        }
                    }else{
                        if(count==(n+1)/2) {
                            arr[0] = arr[1] = curr.val;
                            break;
                        }
                            
                    }
                    rmost.right = null;
                    curr = curr.right;
                }
            }
        }
    }
    public float findMedian(TreeNode root)
    {
        int[] arr = new int[2];
        int n = morrisInorderCountNodes(root);
        kthSmallest(root,n,arr);
        if(n%2!=0) return (float)arr[0];
        else{
            float val = (float)arr[0] + (float)arr[1];
            return (float) val/2;
        }
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
        }else{
            return 1 + countNodes(root.left) + countNodes(root.right);
        }
    }

    //Leetcode 270 - Locked
    //Lintcode 900
    private void closestValue(TreeNode root,double target,double[] minDiff,int[] val){
        if(root==null) return;

        double diff = Math.abs(root.val-target);
        if(diff<minDiff[0]){
            minDiff[0] = diff;
            val[0] = root.val;
        }
        if(target<root.val) closestValue(root.left,target,minDiff,val);
        else closestValue(root.right,target,minDiff,val);
    }
    public int closestValue(TreeNode root,double target) {
        double[] minDiff = new double[1];
        minDiff[0] = Double.MAX_VALUE;
        int[] val = new int[1];

        closestValue(root,target,minDiff,val);
        return val[0];
    }

    //Leetcode 272 - Locked
    //Lintcode 901
    private  void closestKValues(TreeNode root,double target,int k,List<Integer> ans){

        if(root==null) return;

        closestKValues(root.left,target,k,ans);

        if(ans.size()<k){
            ans.add(root.val);
        }else{
            if(Math.abs(ans.get(0)-target) <= Math.abs(root.val - target)) return;

            ans.remove(0);
            ans.add(root.val);
        }

        closestKValues(root.right,target,k,ans);
    }
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> ans = new ArrayList<>();
        closestKValues(root,target,k,ans);
        return ans;
    }

    //Leetcode 129
    private void sumNumbers(int num,int[] sum,TreeNode root){
        if(root==null) return;
        num = num*10 + root.val;
        if(root.left==null && root.right==null){
            sum[0] += num;
            return;
        }
        sumNumbers(num,sum,root.left);
        sumNumbers(num,sum,root.right);
    }
    public int sumNumbers(TreeNode root) {
        int[] sum = new int[1];
        sumNumbers(0,sum,root);
        return sum[0];
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
    public List<Integer> preorderIterative(TreeNode root) {
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

            if(st.size()>0) curr = st.pop();
        }

        return ans;
    }

    //Iterative Postorder Traversal
    public List<Integer> postorderIterative(TreeNode root){
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

            if(!st.empty() && curr==st.peek()) curr = curr.right;
            else{
                ans.add(curr.val);
                curr = null;
            }
        }

        return ans;
    }

    //--------------------------------------EXTRA QUESTIONS--------------------------------------------

    //Leetcode 103
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if(root==null) return new ArrayList<>();
        List<List<Integer>> ans = levelOrder(root);
        for(int i=0;i<ans.size();i++){
            if(i%2!=0){
                Collections.reverse(ans.get(i));
            }
        }
        return ans;
    }

    //Leetcode 111
    private void minDepth(TreeNode root,int[] ans,int depth){
        if(depth+1>=ans[0]) return;
        if(root.left==null && root.right==null){
            ans[0] = Math.min(ans[0],depth+1);
            return;
        }

        if(root.left!=null) minDepth(root.left,ans,depth+1);
        if(root.right!=null) minDepth(root.right,ans,depth+1);

    }
    public int minDepth(TreeNode root) {
        int[] ans = new int[1];
        if(root==null) return ans[0];
        ans[0] = (int)1e8;
        minDepth(root,ans,0);
        return ans[0];
    }

    //Leetcode 107
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if(root == null)
        return ans;
        
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        List<Integer> res;
        while(q.size()!=0){
            int size = q.size();
            res = new ArrayList<>();
            while(size-->0){
                TreeNode node = q.poll();
                res.add(node.val);

                if(node.left!=null) q.add(node.left);
                if(node.right!=null) q.add(node.right);
            }
            ans.add(0,res);
        }
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
    private ListNode findMiddle(ListNode head){
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while(fast!=null && fast.next!=null){
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        if(prev!=null) prev.next = null;

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
    private TreeNode sortedArrayToBST(int si,int ei,int[] nums){
        if(si>ei || ei>=nums.length || si<0) return null;

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

        if((p==null && q!=null) || (p!=null && q==null)) return false;

        if(p.val!=q.val) return false;

        return (isSameTree(p.left,q.left) && isSameTree(p.right,q.right));
        
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

    public class NestedIterator implements Iterator<Integer> {
        private int idx;
        private List<Integer> list;
        private void flatten(List<NestedInteger> nestedList){
            for(NestedInteger nestedInteger:nestedList){
                if(nestedInteger.isInteger()){
                    list.add(nestedInteger.getInteger());
                }else{
                    flatten(nestedInteger.getList());
                }
            }
        }
        public NestedIterator(List<NestedInteger> nestedList) {
            idx = 0;
            list = new ArrayList<>();
            flatten(nestedList);
        }
    
        @Override
        public Integer next() {
            return list.get(idx++);
        }
    
        @Override
        public boolean hasNext() {
            return idx<list.size();
        }
    }

    
    
}