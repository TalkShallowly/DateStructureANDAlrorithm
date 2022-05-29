package talk.tree.treeTraversal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 问题： 打印一棵树的最大宽度
 */
public class TreeMixWidth {

    public static class TreeNode {
        private int value;
        private TreeNode leftNode;
        private TreeNode rightNode;

        TreeNode(int _val) {
            this.value = _val;
        }
    }

    public int treeMixWidth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return process(root);
    }
    private int process(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode signNode = root;
        TreeNode curNode = root;
        int len = queue.size();
        int flag = 0;
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            if (poll.leftNode != null) {
                queue.add(poll.leftNode);
                signNode = poll.leftNode;
                flag++;
            }
            if (poll.rightNode != null) {
                queue.add(poll.rightNode);
                signNode = poll.rightNode;
                flag++;
            }
            /*
            此处为核心逻辑 ： 用 curNode 记录上一层的最后节点
                           用 signNode 记录下一层的最后节点
             */
            if (poll == curNode) {
                curNode = signNode;
                len = Math.max(len, flag);
                flag = 0;
            }
        }
        return len;
    }

    /**
     * 使用map实现
     *
     * @param head
     * @return
     */
    public int maxWidthUseMap(TreeNode head) {
        if (head == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(head);
        // key 在 哪一层，value
        HashMap<TreeNode, Integer> levelMap = new HashMap<>();
        //开始层数为第一层
        levelMap.put(head, 1);
        int curLevel = 1; // 当前你正在统计哪一层的宽度
        int curLevelNodes = 0; // 当前层curLevel层，宽度目前是多少
        int max = 0;
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            int curNodeLevel = levelMap.get(cur);
            if (cur.leftNode != null) {
                levelMap.put(cur.leftNode, curNodeLevel + 1);
                queue.add(cur.leftNode);
            }
            if (cur.rightNode != null) {
                levelMap.put(cur.rightNode, curNodeLevel + 1);
                queue.add(cur.rightNode);
            }
            //如果弹出节点层数 == 当前统计层数 则 当前层的节点数量 + 1
            if (curNodeLevel == curLevel) {
                curLevelNodes++;
            } else {
                //不等于 求当前结束层和上一次的最大节点数量
                max = Math.max(max, curLevelNodes);
                //当前统计层向下
                curLevel++;
                //重置当前统计节点个数 （因为当前循环弹出节点为下一层的节点，需要提前记录, 故为 1）
                curLevelNodes = 1;
            }
        }
        //统计最后一层节点数量
        max = Math.max(max, curLevelNodes);
        return max;
    }

    /**
     * 生成随机二叉树节点
     */
    public TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.leftNode = generate(level + 1, maxLevel, maxValue);
        head.rightNode = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 50;
        int maxValue = 10000;
        int testTime = 10000;
        TreeMixWidth treeMixWidth = new TreeMixWidth();
        System.out.println("start.....");
        for (int i = 0; i < testTime; i++) {
            TreeNode head = treeMixWidth.generateRandomBST(maxLevel, maxValue);
            if (treeMixWidth.treeMixWidth(head) != treeMixWidth.maxWidthUseMap(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("end......");
    }

}
