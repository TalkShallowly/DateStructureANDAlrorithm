package talk.tree.treeTraversal;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 判断一棵树是否完全二叉树
 *    按层遍历：
 *      当第一次遇到左右节点不全，则剩下的节点全为叶子节点
 */
public class IsCBT {

    public static class TreeNode{
        private TreeNode leftNode;
        private TreeNode rightNode;
        private int data;
        TreeNode(int _value){
            leftNode = null;
            rightNode = null;
            this.data = _value;
        }
    }

    /**
     * 按层遍历
     * @param root
     * @return
     */
    public static boolean isCBT_1(TreeNode root){
        if (root == null){
            return true;
        }
        return levelTraverse(root);
    }

    private static boolean levelTraverse(TreeNode head){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(head);
        TreeNode left = null;
        TreeNode right = null;
        boolean swatch = false;
        while (!queue.isEmpty()){
            TreeNode poll = queue.poll();
            left = poll.leftNode;
            right = poll.rightNode;
            // 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
            if ((swatch && (left != null || right != null)) || (left == null && right != null)) {
                return false;
            }
            if (left != null){
                queue.add(left);
            }
            if (right != null){
                queue.add(right);
            }
            if (left == null || right == null) {
                swatch = true;
            }
        }
        return true;
    }

    // 对每一棵子树，是否是满二叉树、是否是完全二叉树、高度

    /**
     * 第二种方式：根据没每个节点需要的信息：递归获取
     *      需要信息：
     *          1：是否是满二叉树
     *          2：是否是完全二叉树
     *          3：当前节点在树中的高度
     */
    public static class Info {
        public boolean isFull;
        public boolean isCBT;
        public int height;
        public Info(boolean _full, boolean _cbt, int _height) {
            isFull = _full;
            isCBT = _cbt;
            height = _height;
        }
    }

    /**
     * 第二种方式： 递归信息获取
     * @param head
     * @return
     */
    public static boolean isCBT_2(TreeNode head) {
        if (head == null) {
            return true;
        }
        return process(head).isCBT;
    }
    private static Info process(TreeNode node) {
        if (node == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(node.leftNode);
        Info rightInfo = process(node.rightNode);
        //当前节点的高度
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        //当前节点是否是满二叉树
        boolean isFull = leftInfo.isFull
                &&
                rightInfo.isFull
                && leftInfo.height == rightInfo.height;

        boolean isCBT = false;
        if (isFull) {
            isCBT = true;
        } else { // 以x为头整棵树，不满
            if (leftInfo.isCBT && rightInfo.isCBT) {
                if (leftInfo.isCBT
                        && rightInfo.isFull
                        && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }
                if (leftInfo.isFull
                        &&
                        rightInfo.isFull
                        && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }
                if (leftInfo.isFull
                        && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
                    isCBT = true;
                }
            }
        }
        return new Info(isFull, isCBT, height);
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (isCBT_1(head) != isCBT_2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
