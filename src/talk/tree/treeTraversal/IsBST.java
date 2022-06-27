package talk.tree.treeTraversal;

import java.util.ArrayList;
import java.util.List;

/**
 * 判断是否为平衡二叉树
 *      条件：每一棵子树的左子树比头节点小
 *                     右子树比头节点大
 *      方式一：中序遍历 --》 是否一直递增
 *
 *      方式二：X的左树是否为BST
 *            X的右树是否为BST
 *            X左树的max是否小于X
 *            X右树的min是否大于X
 */
public class IsBST {

    public static class TreeNode{
        private TreeNode leftNode;
        private TreeNode rightNode;
        private int data;
        TreeNode(int _value){
            this.data = _value;
        }
    }

    public static boolean isBST_1(TreeNode head){
        if (head == null){
            return true;
        }
        List<Integer> list = new ArrayList<>();
        inTraversal(head,list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) <= list.get(i - 1)){
                return false;
            }
        }
        return true;
    }

    private static void inTraversal(TreeNode node, List<Integer> list) {
        if (node == null){
            return;
        }
        inTraversal(node.leftNode,list);
        list.add(node.data);
        inTraversal(node.rightNode,list);
    }


    /**
     * 方式二： 每个节点需要的信息
     */
    public static class NodeInfo{
        private boolean isSearch;
        private int max;
        private int min;
        NodeInfo(boolean _isSearch,int _max,int _min){
            this.isSearch =_isSearch;
            this.max = _max;
            this.min = _min;
        }
    }

    public static boolean isBST_2(TreeNode head){
        if (head == null){
            return true;
        }
        return process(head).isSearch;
    }
    public static NodeInfo process(TreeNode node){
        if (node == null){
            return null;
        }
        NodeInfo leftInfo = process(node.leftNode);
        NodeInfo rightInfo = process(node.rightNode);

        //先定义最大值有何最小值
        int max = node.data;
        int min = node.data;
        //依次获取子节点中的最大值 / 最小值
        if (leftInfo != null){
            max = Math.max(max,leftInfo.max);
            min = Math.min(min,leftInfo.min);
        }
        if (rightInfo != null) {
            max = Math.max(max,rightInfo.max);
            min = Math.min(min,rightInfo.min);
        }
        boolean isSearch = true;
        //如果节点为左节点为非搜索二叉树 或者 左边节点的值 大于等于 头节点的值则为非BST
        if (leftInfo != null && (!leftInfo.isSearch || leftInfo.max >= node.data)){
            isSearch = false;
        }
        //如果节点为右节点为非搜索二叉树 或者 右边节点的值 小于等于 头节点的值则为非BST
        if (rightInfo != null && (!rightInfo.isSearch || rightInfo.min <= node.data)){
            isSearch = false;
        }
        return new NodeInfo(isSearch,max,min);
    }

    // for test
    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.leftNode = generate(level + 1, maxLevel, maxValue);
        head.rightNode = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (isBST_1(head) != isBST_2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
