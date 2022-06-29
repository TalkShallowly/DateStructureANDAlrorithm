package talk.tree.treeTraversal;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 判断一颗二叉树是不是满二叉树
 */
public class IsFull {

    public static class TreeNode{
        private TreeNode leftNode;
        private TreeNode rightNode;
        private int data;
        TreeNode(int _value){
            this.data = _value;
        }
    }

    public static boolean isFull_01(TreeNode root){
        if (root == null){
            return true;
        }
        int number = process_01(root);
        int height = maxDepth(root);
        return number == (1 << height) - 1;
    }


    private static int maxDepth(TreeNode root){
        if (root == null) {
            return 0;
        }
        int result = 0;
        return Math.max(maxDepth(root.leftNode), maxDepth(root.rightNode)) + 1;
    }

    private static int process_01(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int number = 0;
        while (!queue.isEmpty()){
            TreeNode poll = queue.poll();
            if (poll.leftNode != null){
                queue.add(poll.leftNode);
            }
            if (poll.rightNode != null){
                queue.add(poll.rightNode);
            }
            ++number;
        }
        return number;
    }

    /**
     * 满二叉树的节点信息
     */
    public static class NodeInfo{
        private int height;
        private int nodeNumber;
        private boolean isFull;
        NodeInfo(int _height,int _nodeNumber,boolean _isFull){
            this.height = _height;
            this.nodeNumber = _nodeNumber;
            this.isFull = _isFull;
        }
    }

    public static boolean isFull_02(TreeNode root){
        if (root == null){
            return true;
        }
        return process_02(root).isFull;
    }
    private static NodeInfo process_02(TreeNode node){
        if (node == null){
            return new NodeInfo(0,0,true);
        }
        NodeInfo left = process_02(node.leftNode);
        NodeInfo right = process_02(node.rightNode);

        int height = Math.max(left.height,right.height) + 1;
        int number = left.nodeNumber + right.nodeNumber + 1;
        boolean isFull = true;
        if (!left.isFull || !right.isFull){
            isFull = false;
        }
        if (number != (1 << height) - 1){
            isFull = false;
        }
        return new NodeInfo(height,number,isFull);
    }

    public static TreeNode generateRandomNode(int maxLevel,int maxValue){
        return generate(1,maxLevel,maxValue);
    }

    private static TreeNode generate(int level,int maxLevel,int maxValue){
        if (level > maxLevel || Math.random() < 0.5){
            return null;
        }
        TreeNode root = new TreeNode((int)(Math.random() * maxValue) + 1);
        root.leftNode = generate(level + 1,maxLevel,maxValue);
        root.rightNode = generate(level + 1,maxLevel,maxValue);
        return root;
    }

    private static TreeNode test(){
        TreeNode root = new TreeNode(1);
        root.leftNode = new TreeNode(2);
        root.rightNode = new TreeNode(3);
        root.leftNode.leftNode = new TreeNode(4);
        root.leftNode.rightNode = new TreeNode(5);
//        root.rightNode.leftNode = new TreeNode(6);
        root.rightNode.rightNode = new TreeNode(7);
        root.rightNode.rightNode.leftNode = new TreeNode(7);
        return root;
    }

    private static void printNode(TreeNode root){
        if (root == null){
            return;
        }
        System.out.print(root.data + "\t");
        printNode(root.leftNode);
        printNode(root.rightNode);
    }

    public static void main(String[] args) {
        int maxValue = 100;
        int maxLevel = 7;
        int testTime = 10000;
        System.out.println("Start .........");
        for (int i = 0; i < testTime; i++) {
            TreeNode treeNode = generateRandomNode(maxLevel,  maxValue);
            if (isFull_01(treeNode) != isFull_02(treeNode)){
                System.out.println("Oops " + process_01(treeNode));
                printNode(treeNode);
                System.out.println("======");
            }
        }
        System.out.println("Finish...........");

//        System.out.println(isFull_01(test()));
//        System.out.println("=====");
//        System.out.println(isFull_02(test()));

    }
}
