package talk.tree.treeTraversal;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 树的序列化和反序列化 （其中 null 用 # 代替）
 *  注： 中序遍历没有反序列化 例如： # b c a # 有两种实现方式
 *      1： 先序遍历 --》 序列化和反序列化
 *      2： 后序遍历 --》 序列化和反序列化
 *      3： 按层遍历 --》 序列化和反序列化
 */
public class TreeSerializeAndDeserialize {

    public static class TreeNode{
        private int value;
        private TreeNode leftNode;
        private TreeNode rightNode;
        TreeNode(int data){
            this.value = data;
            leftNode = null;
            rightNode = null;
        }
    }
    //此queue用于生产序列化之后的数组
    private Queue<String> queue;

    /**
     * 先序遍历序列化
     * @param root
     */
    public Queue<String> preSerialize(TreeNode root){
        queue = new LinkedList<>();
        pre(root);
        return queue;
    }
    private void pre(TreeNode root){
        if (root == null){
            queue.add("#");
            return;
        }
        queue.add(String.valueOf((root.value)));
        pre(root.leftNode);
        pre(root.rightNode);
    }

    /**
     * 先序遍历反序列化
     * @param queue
     * @return
     */
    public TreeNode preDeserialize(Queue<String> queue){
        if (queue.isEmpty() && queue.peek() == "#"){
            return null;
        }
        return perDes(queue);
    }
    private TreeNode perDes(Queue<String> queue){
        String poll = queue.poll();
        if (poll == "#"){
            return null;
        }
        TreeNode treeNode = new TreeNode(Integer.valueOf(poll));
        treeNode.leftNode = perDes(queue);
        treeNode.rightNode = perDes(queue);
        return treeNode;
    }


    /**
     * 中序遍历序列化
     * @param root
     * @return
     */
    public Queue<String> inSerialize(TreeNode root){
        queue = new LinkedList<>();
        in(root);
        return queue;
    }
    private void in(TreeNode root){
        if (root == null){
            queue.add("#");
            return;
        }
        in(root.leftNode);
        queue.add(String.valueOf((root.value)));
        in(root.rightNode);
    }

    /**
     * 后序遍历序列化
     * @param root
     * @return
     */
    public Queue<String> postSerialize(TreeNode root){
        queue = new LinkedList<>();
        post(root);
        return queue;
    }
    private void post(TreeNode root){
        if (root == null){
            queue.add("#");
            return;
        }
        post(root.leftNode);
        post(root.rightNode);
        queue.add(String.valueOf((root.value)));
    }

    /**
     * 后序遍历反序列
     * @param queue
     * @return
     */
    public TreeNode postDeserialize(Queue<String> queue){
        if (queue.isEmpty() || queue.size() == 0) {
            return null;
        }
        Stack<String> stack = new Stack<>();
        while (!queue.isEmpty()){
            stack.push(queue.poll());
        }
        return postDes(stack);
    }
    private TreeNode postDes(Stack<String> stack){
        String pop = stack.pop();
        if (pop == "#"){
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(pop));
        node.rightNode = postDes(stack);
        node.leftNode = postDes(stack);
        return node;
    }

    /**
     * 按层遍历序列化
     * @param root
     * @return
     */
    public Queue<String> levelSerialize(TreeNode root){
        if (root == null){
            return null;
        }
        queue = new LinkedList<>();
        level(root);
        return queue;
    }
    private void level(TreeNode root){
        Queue<TreeNode> levelQueue = new LinkedList<>();
        levelQueue.add(root);
        TreeNode signNode = root;
        TreeNode curNode = root;
        while (!levelQueue.isEmpty()){
            TreeNode poll = levelQueue.poll();
            if (poll != null) {
                if (poll.leftNode != null && poll.rightNode != null) {
                    levelQueue.add(poll.leftNode);
                    levelQueue.add(poll.rightNode);
                    signNode = poll.rightNode;
                } else if (poll.leftNode == null && poll.rightNode != null) {
                    levelQueue.add(null);
                    levelQueue.add(poll.rightNode);
                    signNode = poll.rightNode;
                } else if (poll.leftNode != null && poll.rightNode == null) {
                    levelQueue.add(poll.leftNode);
                    levelQueue.add(null);
                    signNode = poll.leftNode;
                } else {
                    levelQueue.add(null);
                    levelQueue.add(null);
                }
                queue.add(String.valueOf(poll.value));
                if (poll == curNode) {
//                    queue.add("#");
                    curNode = signNode;
                }
            }else {
                queue.add("*");
            }
        }
    }

    /**
     * 按层遍历反序列化
     * @param queue
     * @return
     */
    public TreeNode levelDeserialize(Queue<String> queue){
        if (queue.isEmpty() || queue.size() == 0){
            return null;
        }
        return levelDes(queue);
    }
    private TreeNode levelDes(Queue<String> queue){
        TreeNode node = new TreeNode(Integer.valueOf(queue.poll()));
        Queue<TreeNode> helpQueue = new LinkedList<>();
        helpQueue.add(node);
        while (!queue.isEmpty()){
            TreeNode helpPoll = helpQueue.poll();
            String poll1 = queue.poll();
            helpPoll.leftNode = poll1 == "*" ? null : new TreeNode(Integer.valueOf(poll1));
            String poll2 = queue.poll();
            helpPoll.rightNode = poll2 == "*" ? null : new TreeNode(Integer.valueOf(poll2));
            if (helpPoll.leftNode != null){
                helpQueue.add(helpPoll.leftNode);
            }
            if (helpPoll.rightNode != null){
                helpQueue.add(helpPoll.rightNode);
            }
        }
        return node;
    }

    /**
     * 先序打印
     * @param root
     */
    public void prePrint(TreeNode root){
        if (root == null){
            return;
        }
        System.out.print(root.value + "\t");
        prePrint(root.leftNode);
        prePrint(root.rightNode);
    }

    /**
     * 后序打印
     * @param root
     */
    public void postPrint(TreeNode root){
        if (root == null){
            return;
        }
        postPrint(root.leftNode);
        postPrint(root.rightNode);
        System.out.print(root.value + "\t");
    }

    /**
     * 按层遍历
     * @param root
     */
    public void levelPrint(TreeNode root){
        if (root == null){
            return;
        }
        Queue<TreeNode> printQueue = new LinkedList<>();
        printQueue.add(root);
        while (!printQueue.isEmpty()){
            TreeNode poll = printQueue.poll();
            System.out.print(poll.value + "\t");
            if (poll.leftNode != null){
                printQueue.add(poll.leftNode);
            }
            if (poll.rightNode != null){
                printQueue.add(poll.rightNode);
            }
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
//        treeNode.rightNode.rightNode.rightNode = new TreeNode(8);
        TreeSerializeAndDeserialize tree = new TreeSerializeAndDeserialize();
        Queue<String> strings = tree.levelSerialize(treeNode);
        System.out.println("==== 反序列化 === ");
        TreeNode treeNode_des = tree.levelDeserialize(strings);
        tree.levelPrint(treeNode_des);
//        strings.stream().forEach(x -> System.out.print(x + "\t"));
    }
}
