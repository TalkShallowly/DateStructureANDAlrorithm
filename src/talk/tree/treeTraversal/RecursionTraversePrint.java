package talk.tree.treeTraversal;

/**
 * 递归遍历打印
 */
public class RecursionTraversePrint {

    public static class TreeNode{
        private int value;
        private TreeNode left;
        private TreeNode right;
        public TreeNode(int date){
            this.value = date;
            left = null;
            right = null;
        }
    }

    /**
     * 先序遍历
     * @param root
     */
    public static void  preorderTraverse(TreeNode root){
        if (root == null){
            return;
        }
        System.out.print(root.value + "\t");
        preorderTraverse(root.left);
        preorderTraverse(root.right);
    }

    /**
     * 中序遍历
     * @param root
     */
    public static void  inorderTraverse(TreeNode root){
        if (root == null){
            return;
        }
        inorderTraverse(root.left);
        System.out.print(root.value + "\t");
        inorderTraverse(root.right);
    }

    /**
     * 后序遍历
     * @param root
     */
    public static void  postorderTraverse(TreeNode root){
        if (root == null){
            return;
        }
        postorderTraverse(root.left);
        postorderTraverse(root.right);
        System.out.print(root.value + "\t");
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(0);
        treeNode.left = new TreeNode(1);
        treeNode.right = new TreeNode(2);
        treeNode.left.left = new TreeNode(3);
        treeNode.left.right = new TreeNode(4);
        treeNode.right.left = new TreeNode(5);
        treeNode.right.right = new TreeNode(6);
        System.out.print("先序遍历： ");
        preorderTraverse(treeNode);
        System.out.println("\n");
        System.out.print("中序遍历： ");
        inorderTraverse(treeNode);
        System.out.println("\n");
        System.out.print("后序遍历： ");
        postorderTraverse(treeNode);
    }

}
