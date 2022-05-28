package talk.tree.treeTraversal;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 问题： 实现二叉树的按层遍历   ====
 *      1；宽度优先遍历（队列）
 *      2：可以设通过设置flag变量的方式，求发现某一层的结束
 */
public class LevelTraversalBT {

    public static class TreeNode{
        private int value;
        private TreeNode leftNode;
        private TreeNode rightNode;
        public TreeNode(int data){
            this.value = data;
            leftNode = null;
            rightNode = null;
        }

    }

    public void layerTraversal(TreeNode root){
        if (root == null){
            return;
        }
        process(root);
    }

    /**
     * 实现思路： 先将根节点添加到队列中，在弹出（如果有做左节点先加左节点，之后依次循环弹出即可）
     * @param root
     */
    private void process(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode poll = queue.poll();
            if (poll.leftNode != null){
                queue.add(poll.leftNode);
            }
            if (poll.rightNode != null){
                queue.add(poll.rightNode);
            }
            System.out.print(poll.value + "\t");
        }
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        treeNode.leftNode = new TreeNode(2);
        treeNode.rightNode = new TreeNode(3);
        treeNode.leftNode.leftNode = new TreeNode(4);
        treeNode.leftNode.rightNode = new TreeNode(5);
        treeNode.rightNode.leftNode = new TreeNode(6);
        treeNode.rightNode.rightNode = new TreeNode(7);
        LevelTraversalBT levelTraversalBT = new LevelTraversalBT();
        levelTraversalBT.layerTraversal(treeNode);
    }

}
