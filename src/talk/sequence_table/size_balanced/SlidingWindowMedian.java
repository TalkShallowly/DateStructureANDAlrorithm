package talk.sequence_table.size_balanced;

import java.util.Arrays;
import java.util.Random;

/**
 * 题目二：
 * 有一个滑动窗口：
 * 1） L是滑动窗口最左位置、R是滑动窗口最右位置、一开始LR堵在数组左侧
 * 2） 任何一步都可能往右动，表示某个数进了窗口
 * 3） 任何一步都可能L往右动，表示某个数出了窗口
 *  *
 * @author guojunshan
 * @date 2024-12-27
 */
public class SlidingWindowMedian {


    public static int[] generateNum(int size, int max){
        int[] nums = new int[size];
        //生成 [0, max] 上的随机数
        Random random = new Random();
        for (int i = 0; i < nums.length; i++) {
            nums[i] = random.nextInt(max + 1);
        }
        return nums;
    }

    public static double[] test(int[] nums, int k){
        int n = nums.length;
        double[] ans = new double[n - k + 1];
        for (int i = 0; i <= n - k; i++) {
            // 提取当前窗口
            int[] window = Arrays.copyOfRange(nums, i, i + k);
            // 排序
            Arrays.sort(window);
            // 计算中位数
            if (k % 2 == 0) {
                // 偶数窗口大小，取中间两数的平均值
                ans[i] = (window[k / 2 - 1] + window[k / 2]) / 2.0;
            } else {
                // 奇数窗口大小，取中间值
                ans[i] = window[k / 2];
            }
        }
        return ans;
    }

    public static boolean compare(double[] a, double[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int maxSize = 1000;
        int maxNum = 100000;
        for (int i = 100; i < 150; i++) {
//            int size = new Random().nextInt(maxSize + 1) + 10;
//            int max = new Random().nextInt(maxNum + 1) + 10;
            int size = 10000;
            int max = 10000000;
            int[] ints = generateNum(size, max);
            int k = 100;
            long l = System.currentTimeMillis();
            double[] doubles = slidingWindowMedian(ints, k);
            long l2 = System.currentTimeMillis();
            double[] test = test(ints, k);
            long l3 = System.currentTimeMillis();
//            System.out.println("平衡数结构: " + (l2- l) );
//            System.out.println("数组结构: " + (l3- l2));
            if (!compare(doubles, test)){
                System.out.println("Error.........  ");
            }
        }

    }


    public static class Node implements Comparable<Node> {
        public int index;
        public int value;

        public Node(int i, int v) {
            index = i;
            value = v;
        }

        @Override
        public int compareTo(Node o) {
            return value != o.value ? Integer.compare(value, o.value)
                    : Integer.compare(index, o.index);
        }
    }



    public static double[] slidingWindowMedian(int[] nums, int k){
        SizeBalancedTreeMap<Node> map = new SizeBalancedTreeMap<>();
        //初始化窗口函数,
        for (int i = 0; i < k - 1; i++) {
            map.put(new Node(i,nums[i]));
        }
        double[] ansNum = new double[nums.length - k + 1];
        int index = 0;
        for (int i = k - 1; i < nums.length; i++) {
            map.put(new Node(i,nums[i]));
            if (map.size() % 2 == 0) {
                Node upMid = map.getIndexKey(map.size() / 2 - 1);
                Node downMid = map.getIndexKey(map.size() / 2);
                double ans = (double) (upMid.value + downMid.value) / 2;
                ansNum[index++] = ans;
            }else {
                Node mid = map.getIndexKey(map.size() / 2);
                ansNum[index++] = (double) mid.value;
            }
            map.remove(new Node(i - k + 1,nums[i - k + 1]));

        }
        return ansNum;
    }


    public static class SBNode<K extends Comparable<K>> {
        private K key;
        private SBNode<K> left;
        private SBNode<K> right;
        private int size;

        public SBNode(K key) {
            this.key = key;
            size = 1;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>> {
        private SBNode<K> root;

        public void put (K key) {
            if (key == null) {
                return;
            }
            if (!containsKey(key)) {
                root = add(root,key);
            }
        }

        public int size() {
            return root == null ? 0 : root.size;
        }


        public K getIndexKey (int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("Index out of bounds");
            }
            return getIndex(root, index + 1).key;
        }

        //获取index 指定位置的数据
        private SBNode<K> getIndex (SBNode<K> cur, int index) {
            //当前 index 位置的数据如果是左边节点加上自己, 则直接返回
            if (index == (cur.left != null ? cur.left.size : 0) + 1){
                return cur;
            }
            //当前位置如果小于左边节点的数量,则继续向左查询
            else if (index <= (cur.left != null ? cur.left.size : 0)){
                return getIndex(cur.left, index);
            }else {
                //从右边节点开始查询
                return getIndex(cur.right, index - (cur.left != null ? cur.left.size : 0) - 1);
            }
        }

        //非递归版本
        public SBNode<K> getIndexKey2 (int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("Index out of bounds");
            }
            SBNode<K> cur = root;
            while (cur !=  null){
                int leftSize = cur.left != null ? cur.left.size : 0;
                if (index == leftSize + 1){
                    return cur;
                }else if (index <= leftSize) {
                    cur = cur.left;
                }else {
                    index -= (leftSize + 1);
                    cur = cur.right;
                }
            }
            return null;
        }

        public void remove (K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                root = delete(root,key);
            }
        }

        public boolean containsKey (K key) {
            return get(key) != null;
        }

        public SBNode<K> get (K key){
            if (key == null) return null;
            SBNode<K> cur = root;
            while (cur != null){
                if (cur.key.compareTo(key) == 0) return cur;
                cur = key.compareTo(cur.key) < 0 ? cur.left : cur.right;
            }
            return cur;
        }


        private SBNode<K> delete (SBNode<K> cur, K key) {
            if (cur == null) return null;
            cur.size--;
            if (key.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right, key);
            }else if (key.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left, key);
            }else {
                if (cur.right == null) {
                    cur = cur.left;
                } else if (cur.left == null) {
                    cur = cur.right;
                } else {
                    SBNode<K> pre = null;
                    SBNode<K> next = cur.right;
                    next.size--;
                    while (next.left != null) {
                        pre = next;
                        next = next.left;
                        next.size--;
                    }
                    if (pre != null) {
                        pre.left = next.right;
                        next.right = cur.right;
                    }
                    next.left = cur.left;
                    next.size = next.left.size + (next.right == null ? 0 : next.right.size) + 1;
                    cur = next;
                }
            }
            return cur;
        }

//
//        private SBNode<K> delete2 (SBNode<K> cur, K key) {
//            if (cur == null) return null;
//            cur.size--;
//            if (key.compareTo(cur.key) > 0) {
//                cur.right = delete(cur.right, key);
//            }else if (key.compareTo(cur.key) < 0) {
//                cur.left = delete(cur.left, key);
//            }else {
//                if (cur.right == null) {
//                    cur = cur.left;
//                } else if (cur.left == null) {
//                    cur = cur.right;
//                } else {
//                    SBNode<K> next = cur.right;
//                    while (next.left != null) {
//                        next = next.left;
//                    }
//                    next.right= delete2(cur.right,next.key);
//                    next.left = cur.left;
//                    next.size = next.left.size + (next.right == null ? 0 : next.right.size) + 1;
//                    cur = next;
//                }
//            }
//            return cur;
//        }

        private SBNode<K> add (SBNode<K> cur, K key) {
            if (cur == null) {
                return new SBNode<>(key);
            }else {
                cur.size++;
                if (cur.key.compareTo(key) < 0) {
                    cur.right = add(cur.right, key);
                }else  {
                    cur.left = add(cur.left, key);
                }
                return maintain(cur);
            }
        }

        //平衡性
        public SBNode<K> maintain(SBNode<K> cur){
            if (cur == null){
                return null;
            }
            int leftSize = cur.left == null ? 0 : cur.left.size;
            int leftLeftSize = leftSize == 0 ? 0 : cur.left.left == null ? 0 : cur.left.left.size;
            int leftRightSize = leftSize == 0 ? 0 : cur.left.right == null ? 0 : cur.left.right.size;

            int rightSize = cur.right == null ? 0 : cur.right.size;
            int rightRightSize = rightSize == 0 ? 0 : cur.right.right == null ? 0 : cur.right.right.size;
            int rightLeftSize = rightSize == 0 ? 0 : cur.right.left == null ? 0 : cur.right.left.size;

            //四种违规检查
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
        public SBNode<K> leftRotate(SBNode<K> cur) {
            SBNode<K> right = cur.right;
            cur.right = right.left;
            right.left = cur;
            right.size = cur.size;
            cur.size = (cur.left == null ? 0 : cur.left.size) + (cur.right == null ? 0 : cur.right.size) + 1;
            return right;
        }

        //右旋
        public SBNode<K> rightRotate(SBNode<K> cur){
            SBNode<K> left = cur.left;
            cur.left = left.right;
            left.right = cur;
            left.size = cur.size;
            cur.size = (cur.left == null ? 0 : cur.left.size) + (cur.right == null ? 0 : cur.right.size) + 1;
            return left;
        }

    }




}
