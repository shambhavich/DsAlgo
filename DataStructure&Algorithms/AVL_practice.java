public class AVL_practice {
    
    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        int bal = 0;
        int height = 0;

        TreeNode(int val){
            this.val = val;
        }
    }
    public static void updateBalanceAndHeight(TreeNode root){
        if(root==null) return;

        int lh = -1;
        int rh = -1;
        if(root.left!=null)
            lh = root.left.height;
        if(root.right!=null)
            rh = root.right.height;
        
        root.bal = lh - rh;
        root.height = Math.max(lh,rh)+1;
    }
    public static TreeNode rightRotation(TreeNode A){
        TreeNode B = A.left;
        TreeNode BRight = B.right;

       B.right = A;
       A.left = BRight;

       updateBalanceAndHeight(A);
       updateBalanceAndHeight(B);

       return B;
    }
    public static TreeNode leftRotation(TreeNode A){
        TreeNode B = A.right;
        TreeNode BLeft = B.left;

        B.left = A;
        A.right = BLeft;

        updateBalanceAndHeight(A);
        updateBalanceAndHeight(B);

        return B;
    }
    public static TreeNode getRotation(TreeNode root){
        updateBalanceAndHeight(root);
        if(root.bal==2){
            if(root.left.bal==1){
                root = rightRotation(root);
            }
            else{
                root.left = leftRotation(root.left);
                return rightRotation(root);
            }
        }else if(root.bal==-2){
            if(root.right.bal==-1){
                root = leftRotation(root);
            }else{
                root.right = rightRotation(root.right);
                return leftRotation(root);
            }
        }

        return root;
    }
    public static TreeNode insertIntoBST(TreeNode root,int key){
        if(root==null){
            return new TreeNode(key);
        }

        if(root.val>key){
            root.left = insertIntoBST(root.left, key);
        }else if(root.val<key){
            root.right = insertIntoBST(root.right, key);
        }

        root = getRotation(root);
        return root;
    }
    public static int maximumEle(TreeNode root){
        TreeNode curr = root;
        while(curr.right!=null) curr = curr.right;

        return curr.val;
    }
    public static TreeNode deleteNode(TreeNode root,int key){
        if(root==null) return null;

        if(root.val>key)
            root.left = deleteNode(root.left,key);
        else if(root.val<key)
            root.right = deleteNode(root.right,key);
        else{
            if(root.left==null || root.right==null){
                return root.left!=null ? root.left:root.right;
            }

            int maxValue = maximumEle(root.left);
            root.val = maxValue;

            root.left = deleteNode(root.left,maxValue);
        }

        return getRotation(root);
    }

    //If a BST is given and you need to create it an AVL
    public static TreeNode postOrder(TreeNode root){
        if(root==null) return null;

        root.left = postOrder(root.left);
        root.right = postOrder(root.right);

        return getRotation(root);
    }
}
