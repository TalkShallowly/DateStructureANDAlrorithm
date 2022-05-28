package talk.tree.treeTraversal;


import java.util.ArrayList;
import java.util.List;

/**
 * 问题： 将 N 叉数转为 二叉树
 * https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
 */
public class EncodeArrayTreeToBinaryTree {
    /**
     * N 叉数类的定义
     */
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * 二叉树类定义
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Codec {
        /**
         * 将 N 叉数转为 二叉树
         * @return 二叉树的头节点
         */

        public TreeNode encode(Node root) {
            if (root == null) {
                return null;
            }
            TreeNode head = new TreeNode(root.val);
            head.left = en(root.children);
            return head;
        }

        /**
         * 处理 N 叉数的孩子节点 --> 将 N 个同级的节点依次转为第一个数的右子界节点
         *
         * @param children
         * @return
         */
        private TreeNode en(List<Node> children) {
            TreeNode head = null;
            TreeNode cur = null;
            if (children != null){
                for (Node child : children) {
                    TreeNode tNode = new TreeNode(child.val);
                    if (head == null) {
                        head = tNode;
                    } else {
                        cur.right = tNode;
                    }
                    cur = tNode;
                    cur.left = en(child.children);
                }
            }
            return head;
        }

        public Node decode(TreeNode root) {
            if (root == null) {
                return null;
            }
            return new Node(root.val, de(root.left));
        }

        public List<Node> de(TreeNode root) {
            List<Node> children = new ArrayList<>();
            while (root != null) {
                Node cur = new Node(root.val, de(root.left));
                children.add(cur);
                root = root.right;
            }
            return children;
        }
    }

    public static void main(String[] args) {
        Node node = new Node(1);
        node.children = List.of(
                new Node(2,List.of(
                        new Node(5),new Node(6),new Node(7))),
                new Node(3,List.of(
                        new Node(8),new Node(9),new Node(10))),
                new Node(4));
        Codec codec = new EncodeArrayTreeToBinaryTree().new Codec();
        TreeNode encode = codec.encode(node);
        Node decode = codec.decode(encode);
        System.out.println("==");
    }

}
