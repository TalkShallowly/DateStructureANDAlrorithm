package talk.tree.treeTraversal;

import javax.print.DocFlavor;

/**
 * 判断一颗二叉树是否是平衡二叉树
 *     条件：左树的最大高度和右树的最大高度的差值不能大于1
 */
public class IsBalanced {

    public static class TreeNode{
        private TreeNode leftNode;
        private TreeNode rightNode;
        private int data;
        TreeNode(int _value){
            this.data = _value;
        }
    }

    public static boolean isBalanced_01(TreeNode root){
        if (root == null){
            return true;
        }
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process_1(root,ans);
        return ans[0];
    }
    private static int process_1(TreeNode node,boolean[] ans){
        if (node == null || !ans[0]){
            return -1;
        }
        int leftHeight = process_1(node.leftNode, ans);
        int rightHeight = process_1(node.rightNode, ans);
        if (Math.abs(leftHeight - rightHeight) > 1){
            ans[0] = false;
        }
        return Math.max(leftHeight,rightHeight) + 1;
    }


    public static class NodeInfo{
        private int height;
        private boolean isBalanced;
        NodeInfo(int _height, boolean _isBalanced){
            this.height = _height;
            this.isBalanced = _isBalanced;
        }
    }

    public static boolean isBalanced_02(TreeNode root){
        if (root == null){
            return true;
        }
        return process(root).isBalanced;
    }
    private static NodeInfo process(TreeNode node){
        if (node == null){
            return new NodeInfo(0,true);
        }
        NodeInfo left = process(node.leftNode);
        NodeInfo right = process(node.rightNode);
        int height = Math.max(left.height,right.height) + 1;
        boolean isBalanced = true;
        if (!left.isBalanced){
            isBalanced = false;
        }
        if (!right.isBalanced){
            isBalanced = false;
        }
        if (Math.abs(left.height - right.height) > 1){
            isBalanced = false;
        }
        return new NodeInfo(height,isBalanced);
    }

    public static TreeNode generateRandomBST(int maxLevel,int value){
        return generate(1,maxLevel,value);
    }
    private static TreeNode generate(int level,int maxLevel,int value){
        if (level > maxLevel || Math.random() > 0.5){
            return null;
        }
        TreeNode head = new TreeNode((int)(Math.random() * value) + 1);
        head.leftNode = generate(level + 1,maxLevel,(int)(Math.random() * value) + 1);
        head.rightNode = generate(level + 1,maxLevel,(int)(Math.random() * value) + 1);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 12;
        int maxValue = 1000;
        int testTime = 1000000;
        System.out.println("start........");
        for (int i = 0; i < testTime; i++) {
            TreeNode root = generateRandomBST(maxLevel, maxValue);
            if (isBalanced_01(root) != isBalanced_02(root)){
                System.out.println("Oops");
            }
        }
        System.out.println("finish........");
    }
}
