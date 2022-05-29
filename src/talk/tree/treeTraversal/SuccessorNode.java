package talk.tree.treeTraversal;


import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 给定二叉树的某个节点，返回该节点的后继节点
 *      解释： 中序遍历的后一个节点
 */
public class SuccessorNode {

    public static class TreeNode{
        private int value;
        private TreeNode leftNode;
        private TreeNode rightNode;
        private TreeNode parentNode;
        TreeNode(int _val){
            this.value = _val;
        }

    }

    /**
     * 给定二叉树的某个节点，返回该节点的后继节点
     * @param node
     * @return
     */
    public static TreeNode getSuccessorNode(TreeNode node){
        if (node == null){
            return node;
        }
        if (node.rightNode != null){
            return getLeftNode(node.rightNode);
        }
        TreeNode parentNode = node.parentNode;
        while (parentNode != null && parentNode.rightNode == node){
            node = parentNode;
            parentNode = node.parentNode;
        }
        return parentNode;
    }
    /**
     * 得到 当前节点的最左边节点
     * @param node
     * @return
     */
    private static TreeNode getLeftNode(TreeNode node){
        if (node.leftNode != null){
            return getLeftNode(node.leftNode);
        }
        return node;
    }

    public static TreeNode test(TreeNode node){
        if (node == null){
            return node;
        }
        TreeNode headNode = getHeadNode(node);
        List<TreeNode> list = new ArrayList<>();
        inProcess(headNode,list);
        int find = 0;
        for (TreeNode treeNode : list) {
            find++;
            if (treeNode == node){
                break;
            }
        }
        return find < list.size() ? list.get(find) : null;
    }

    /**
     * 中序遍历，并将节点按照遍历顺序添加到集合中
     * @param head
     * @param list
     */
    private static void inProcess(TreeNode head,List<TreeNode> list){
        if (head == null){
            return;
        }
        inProcess(head.leftNode,list);
        list.add(head);
        inProcess(head.rightNode,list);
    }

    /**
     * 传递一个节点，得到它的头节点
     * @param node
     * @return
     */
    private static TreeNode getHeadNode(TreeNode node){
        TreeNode head = node;
        while (head.parentNode != null){
            head = head.parentNode;
        }
        return head;
    }

    public static void main(String[] args) {
        TreeNode head = new TreeNode(1);
        head.leftNode = new TreeNode(2);
        head.leftNode.parentNode = head;
        head.leftNode.rightNode = new TreeNode(3);
        head.leftNode.rightNode.parentNode = head.leftNode;
        head.leftNode.rightNode.rightNode = new TreeNode(4);
        head.leftNode.rightNode.rightNode.parentNode = head.leftNode.rightNode;
        TreeNode successorNode = getSuccessorNode(head.leftNode.rightNode.rightNode);
        System.out.println(successorNode == null ? "null" : successorNode.value);
        TreeNode test = test(head.leftNode.rightNode.rightNode);
        System.out.println(test == null ? "null" : test.value);
    }
}
