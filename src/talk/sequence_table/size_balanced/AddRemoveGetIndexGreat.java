package talk.sequence_table.size_balanced;

import java.util.*;

/**
 * 使用平衡二叉树实现一个数组, 自然排序
 *
 */
public class AddRemoveGetIndexGreat {


    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        SizeBalancedTreeMap<Integer> sizeBalancedTreeMap = new SizeBalancedTreeMap<>();
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 5; j++) {
                int anInt = rand.nextInt(100);
                list.add(anInt);
                sizeBalancedTreeMap.put(anInt);
            }
            for (int j = 0; j < 5; j++) {
                double random = Math.random();
                if (random < 0.4) {
                    int del = rand.nextInt(list.size());
                    list.remove(del);
                    sizeBalancedTreeMap.remove(del);
                }else if (random < 0.6) {
                    int index = rand.nextInt(list.size());
                    if (!Objects.equals(list.get(index), sizeBalancedTreeMap.get(index))){
                        System.out.println("error.....");
                        return;
                    }
                }
            }
        }
    }



    private static class SBTNode<V> {

        private V value;

        private SBTNode<V> left;
        private SBTNode<V> right;
        private int size;

        public SBTNode(V value) {
            this.value = value;
            this.size = 1;
        }
    }


    private static class SizeBalancedTreeMap<V> {
        private SBTNode<V> root;

        public int size () {
            return root == null ? 0 : root.size;
        }

        public ArrayList<V> printf (){
            int size = this.size();
            ArrayList<V> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(this.get(i));
            }
            return list;
        }

        public V get (int index){
            if (index < 0 || index > this.size()){
                throw new IndexOutOfBoundsException();
            }
            SBTNode<V> cur = root;
            while (cur != null){
                int curIndex = cur.left == null ? 0 : cur.left.size;
                if (index < curIndex){
                    cur = cur.left;
                }else if (index > curIndex){
                    index -= (curIndex + 1);
                    cur = cur.right;
                }else {
                    return cur.value;
                }
            }
            return null;
        }


        //添加
        public void put (V value){
            put (value, this.size());
        }

        public void put (V value, int index){
            if (index < 0 || index > this.size()){
                throw new IndexOutOfBoundsException();
            }
            root = add (root, value, index);
        }

        public void remove (int index){
            if (index < 0 || index >= this.size()){
                throw new IndexOutOfBoundsException();
            }
            root = delete(root, index);
        }

        private SBTNode<V> delete (SBTNode<V> cur, int index){
            if (cur == null){
                return null;
            }
            cur.size--;
            int curIndex = cur.left == null ? 0 : cur.left.size;
            if (index < curIndex){
                cur.left = delete(cur.left, index);
            }
            else if (index > curIndex){
                index -= (curIndex + 1);
                cur.right = delete(cur.right, index);
            }
            else {
                if (cur.right == null){
                    cur = cur.left;
                }
                else if (cur.left == null){
                    cur = cur.right;
                }
                else {
                    //替换cur节点位置
                    SBTNode<V> pre = null;
                    SBTNode<V> rightSmallestNode = cur.right;
                    rightSmallestNode.size--;
                    while (rightSmallestNode.left != null) {
                        pre = rightSmallestNode;
                        rightSmallestNode = rightSmallestNode.left;
                        rightSmallestNode.size--;
                    }
                    if (pre != null){
                        pre.left = rightSmallestNode.right;
                        rightSmallestNode.right = cur.right;
                    }
                    rightSmallestNode.left = cur.left;
                    rightSmallestNode.size = rightSmallestNode.left.size + (rightSmallestNode.right == null ? 0 : rightSmallestNode.right.size) + 1;
                    cur = rightSmallestNode;
                }
            }
            return cur;
        }

        private SBTNode<V> add (SBTNode<V> cur, V value, int index) {
            if (cur == null) {
                return new SBTNode<>(value);
            }else {
                cur.size++;
                int curIndex = cur.left == null ? 0 : cur.left.size;
                if (index < curIndex) {
                    cur.left = add(cur.left, value, index);
                } else if (index > curIndex) {
                    index -= (curIndex + 1);
                    cur.right = add(cur.right, value, index);
                } else {
                    //替换cur节点位置
                    SBTNode<V> add = new SBTNode<>(value);
                    SBTNode<V> pre = cur;
                    SBTNode<V> rightSmallestNode = cur.right;
                    while (rightSmallestNode != null) {
                        rightSmallestNode.size++;
                        pre = rightSmallestNode;
                        rightSmallestNode = rightSmallestNode.left;
                    }
                    pre.left = cur;
                    add.left = cur.left;
                    add.right = cur.right;
                    add.size = cur.size;
                    cur.size = 1;
                    cur = add;
                }
                return maintain(cur);
            }
        }


        //平衡调整
        private SBTNode<V> maintain(SBTNode<V> cur) {
            if (cur == null) {
                return null;
            }
            int leftSize = cur.left == null ? 0 : cur.left.size;
            int leftLeftSize  = leftSize == 0 ? 0 : cur.left.left == null ? 0 : cur.left.left.size;
            int leftRightSize = leftSize == 0 ? 0 : cur.left.right == null ? 0 : cur.left.right.size;

            int rightSize = cur.right == null ? 0 : cur.right.size;
            int rightRightSize = rightSize == 0 ? 0 : cur.right.right == null ? 0 : cur.right.right.size;
            int rightLeftSize = rightSize == 0 ? 0 : cur.right.left == null ? 0 : cur.right.left.size;

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
        private SBTNode<V> leftRotate(SBTNode<V> cur) {
            SBTNode<V> right = cur.right;
            cur.right = right.left;
            right.left = cur;
            right.size = cur.size;
            cur.size = (cur.right == null ? 0 : cur.right.size) + (cur.left == null ? 0 : cur.left.size) + 1;
            return right;
        }

        //右旋
        private SBTNode<V> rightRotate(SBTNode<V> cur) {
            SBTNode<V> left = cur.left;
            cur.left = left.right;
            left.right = cur;
            left.size = cur.size;
            cur.size = (cur.right == null ? 0 : cur.right.size) + (cur.left == null ? 0 : cur.left.size) + 1;
            return left;
        }

    }
}
