package talk.sequence_table.size_balanced;

/**
 *  给你一个整数数组nums 以及两个整数lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含lower和upper）之内的 区间和的个数 。
 *
 * 区间和S(i, j)表示在nums中，位置从i到j的元素之和，包含i和j(i ≤ j)。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-of-range-sum
 * @author talkshallowly
 */
public class SBT_CountRangeSum {

    /**
     * 获取区间和在 S(i, j) 等价于 数据以 i 结尾 有多少个区间和落在此范围比上,
     * 则可以进一步推测出 数据以i结尾的前缀和落在 [sum-j,sum- i]的区间上, 使用平衡搜索二叉树
     */

    public static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums.length == 0 || lower > upper) {
            return 0;
        }
        long sum = 0;
        int ans = 0;
        SizeBalancedTreeSet sizeBalancedTreeSet = new SizeBalancedTreeSet();
        //开始时, 添加一个前缀和为0的数据
        sizeBalancedTreeSet.put(0);
        for (int num : nums) {
            sum += num;
            int a = sizeBalancedTreeSet.lessKeySize(sum - upper);
            int b = sizeBalancedTreeSet.lessKeySize(sum - lower + 1);
            ans += b - a;
            sizeBalancedTreeSet.put(sum);
        }
        return ans;
    }



    private static class SBNode {
        private long key;
        private SBNode left;
        private SBNode right;
        private int size;
        private int all;
        public SBNode(long key) {
            this.key = key;
            this.size = 1;
            this.all = 1;
        }
    }


    public static class SizeBalancedTreeSet{
        private SBNode root;

        //添加
        public void put (long key) {
            boolean containsKey = containsKey(key);
            root = add(root, key, containsKey);
        }

        //获取小于当前key的数量有几个
        public int lessKeySize (long key) {
            int ans = 0;
            SBNode cur = root;
            while (cur != null) {
                //右边滑动不记录
                if (cur.key > key) {
                    cur = cur.left;
                }
                //左边滑动 记录当前节点左节点的总数以及当前节点的重复数
                else if(cur.key < key) {
                    ans += cur.all - (cur.right == null ? 0 : cur.right.all);
                    cur = cur.right;
                }else {
                    return ans + (cur.left == null ? 0 : cur.left.all);
                }
            }
            return ans;
        }


        private boolean containsKey(long key){
            return get(key) != null;
        }

        private SBNode get (long key){
            SBNode cur = root;
            while (cur != null){
                if(cur.key == key){
                    return cur;
                }
                cur = cur.key > key ? cur.left : cur.right;
            }
            return null;
        }

        private SBNode add (SBNode cur, long key, boolean containsKey) {
            if (cur == null) {
                return new SBNode(key);
            }
            //更新统计信息, 不包含只更新 平衡因子
            cur.all++;
            if (!containsKey) {
                cur.size++;
            }
            if (cur.key > key){
                cur.left = add(cur.left, key, containsKey);
            }else if(cur.key < key){
                cur.right = add(cur.right, key, containsKey);
            }
            return maintain(cur);
        }


        //平衡性调整
        private SBNode maintain(SBNode cur){
            if (cur == null){
                return null;
            }
            long leftSize = cur.left == null ? 0 : cur.left.size;
            long leftLeftSize = leftSize == 0 ? 0 : cur.left.left == null ? 0 : cur.left.left.size;
            long leftRightSize = leftSize == 0 ? 0 : cur.left.right == null ? 0 : cur.left.right.size;

            long rightSize = cur.right == null ? 0 : cur.right.size;
            long rightRightSize = rightSize == 0 ? 0 : cur.right.right == null ? 0 : cur.right.right.size;
            long rightLeftSize = rightSize == 0 ? 0 : cur.right.left == null ? 0 : cur.right.left.size;

            //四种违规判定
            //RR
            if (leftSize < rightRightSize) {
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            }
            //RL
            else if (leftSize < rightLeftSize) {
                cur.right = rightRotate(cur.right);
                cur = leftRotate(cur);
                cur.right = maintain(cur.right);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            }
            //LL
            else if (rightSize < leftLeftSize) {
                cur = rightRotate(cur);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }
            //LR
            else if (rightSize < leftRightSize) {
                cur.left = leftRotate(cur.left);
                cur = rightRotate(cur);
                cur.right = maintain(cur.right);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            }
            return cur;
        }


        //左旋
        private SBNode leftRotate(SBNode cur){
            //如果有重复数据,需要计算相同size
            int same = cur.all - cur.right.all - (cur.left == null ? 0 : cur.left.all);
            SBNode right = cur.right;
            cur.right = right.left;
            right.left = cur;
            right.size = cur.size;
            cur.size = (cur.left == null ? 0 : cur.left.size) + (cur.right == null ? 0 : cur.right.size) + 1;
            right.all = cur.all;
            cur.all = (cur.left == null ? 0 : cur.left.all) + (cur.right == null ? 0 : cur.right.all) + same;
            return right;
        }

        //右旋
        private SBNode rightRotate (SBNode cur){
            //如果有重复数据,需要计算相同size
            int same = cur.all - cur.left.all - (cur.right == null ? 0 : cur.right.all);
            SBNode left = cur.left;
            cur.left = left.right;
            left.right = cur;
            left.size = cur.size;
            cur.size = (cur.left == null ? 0 : cur.left.size) + (cur.right == null ? 0 : cur.right.size) + 1;
            left.all = cur.all;
            cur.all = (cur.left == null ? 0 : cur.left.all) + (cur.right == null ? 0 : cur.right.all) + same;
            return left;
        }
    }
}
