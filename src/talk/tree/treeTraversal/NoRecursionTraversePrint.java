package talk.tree.treeTraversal;

import java.util.Stack;

/**
 * 树的遍历，非递归打印， ---》 使用stack实现
 */
public class NoRecursionTraversePrint {
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

    public static void preorderTraverse(TreeNode root){
        if (root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        System.out.print("preorder : ");
        while (!stack.isEmpty()){
            TreeNode pop = stack.pop();
            System.out.print(pop.value + "\t");
            if (pop.right != null){
                stack.push(pop.right);
            }
            if (pop.left != null){
                stack.push(pop.left);
            }
        }
        System.out.println();
    }

    public static void inorderTraverse(TreeNode root){
        if (root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        System.out.print("inorder :  ");
        while (!stack.isEmpty() || root != null){
            if (root != null){
                stack.push(root);
                root = root.left;
            }else {
                root = stack.pop();
                System.out.print(root.value + "\t");
                root = root.right;
            }
        }
        System.out.println();
    }

    /**
     * 使用双栈结构实现
     * @param root
     */
    public static void postorderTraverse_DoubleStack(TreeNode root){
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> helpStack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode pop = stack.pop();
            helpStack.push(pop);
            if (pop.left != null){
                stack.push(pop.left);
            }
            if (pop.right != null){
                stack.push(pop.right);
            }
        }
        System.out.print("postorder_DoubleStack : ");
        while (!helpStack.isEmpty()){
            System.out.print(helpStack.pop().value + "\t");
        }
        System.out.println();
    }

    /**
     * 使用但栈结构实现（画图）
     * @param root
     */
    public static void postorderTraverse_SingleStack(TreeNode root){
        if (root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode help = null;
        System.out.print("postorder_SingleStack : ");
        while (!stack.isEmpty()){
            TreeNode peek = stack.peek();
            if (peek.left != null && peek.left != help && peek.right != help){
                stack.push(peek.left);
            }else if (peek.right != null && peek.right != help){
                stack.push(peek.right);
            }else {
                help = stack.pop();
                System.out.print(help.value + "\t");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(0);
        treeNode.left = new TreeNode(1);
        treeNode.right = new TreeNode(2);
        treeNode.left.left = new TreeNode(3);
        treeNode.left.right = new TreeNode(4);
        treeNode.right.left = new TreeNode(5);
        treeNode.right.right = new TreeNode(6);
        preorderTraverse(treeNode);
        inorderTraverse(treeNode);
        postorderTraverse_DoubleStack(treeNode);
        postorderTraverse_SingleStack(treeNode);
    }
}
