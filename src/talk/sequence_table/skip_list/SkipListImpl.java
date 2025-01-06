package talk.sequence_table.skip_list;

import java.util.ArrayList;

/**
 * 跳跃表实现:
 */
public class SkipListImpl {

    public static class SkipListNode<K extends Comparable<K>, V>{
        public K key;
        public V value;

        public ArrayList<SkipListNode<K,V>> nextNodes;

        public SkipListNode(K key, V value) {
            this.key = key;
            this.value = value;
            nextNodes = new ArrayList<>();
        }

        //遍历的时候，如果是往右遍历到的null (next == null), 遍历结束
        //头(null), 头节点的null，认为最小
        //node -> 头 ，node(null,""), node.isKeyLess(!null) true
        //node里面的key 是否比otherKey小， true， 不是false
        public boolean isKeyLess(K otherKey){
            return otherKey != null && (key == null || key.compareTo(otherKey) < 0);
        }

        public boolean isKeyEqual(K otherKey){
            return (otherKey == null && key == null) ||
                    (otherKey != null && (key == null || key.compareTo(otherKey) == 0));
        }

    }

    public class SkipListMap<K extends Comparable<K>, V>{

        private static final double PROBABILITY = 0.5;
        private SkipListNode<K,V> head;
        private int size;
        private int maxLevel;


        public SkipListNode<K,V> get (K key){
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> kvSkipListNode = mostRightLessNodeInTree(key).nextNodes.get(0);
            if (kvSkipListNode != null && kvSkipListNode.isKeyEqual(key)) {
                return kvSkipListNode;
            }
            return null;
        }

        public void put (K key, V value){
            if (key == null) {
                return;
            }
            add(key,value);
        }

        public boolean containsKey (K key){
            return get(key) != null;
        }

        public int size(){
            return size;
        }

        public void remove (K key){
            if (key == null) {
                return;
            }
            delete(key);
        }


        private void delete (K key){
            if (containsKey(key)) {
                size--;
                int curLevel = maxLevel;
                SkipListNode<K, V> nextNode = head;
                while (curLevel >= 0){
                    nextNode = mostRightLessNodeInLevel(nextNode, key, curLevel);
                    if (nextNode.nextNodes.get(curLevel) != null && nextNode.nextNodes.get(curLevel).isKeyEqual(key)){
                        nextNode.nextNodes.set(curLevel, nextNode.nextNodes.get(curLevel).nextNodes.get(curLevel));
                    }

                    //在当前的level层只有这一个节点head时候，将其最大层级进行往下调节
                    if (curLevel != 0 && nextNode == head && nextNode.nextNodes.get(curLevel) == null){
                        head.nextNodes.remove(curLevel);
                        maxLevel--;
                    }
                    curLevel--;
                }
            }
        }

        private void add (K key, V value){
            //获取第0层的最右边一个 小于当前 key 的node
            SkipListNode<K, V> lessKeyLatestNode = mostRightLessNodeInTree(key);
            SkipListNode<K, V> kvSkipListNode = lessKeyLatestNode.nextNodes.get(0);

            if (kvSkipListNode != null && kvSkipListNode.isKeyEqual(key)) {
                kvSkipListNode.value = value;
            }else {
                size++;
                //定位当前新节点的 层级
                int newNodeLevel = 0;
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }

                //提升头节点 层级
                while (newNodeLevel > maxLevel) {
                    head.nextNodes.add(null);
                    maxLevel++;
                }

                //初始化新节点层级
                SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
                for (int i = 0; i <= newNodeLevel; i++) {
                    newNode.nextNodes.add(null);
                }

                //从最高层开始 链接新节点
                int curLevel = maxLevel;
                SkipListNode<K, V> curNode;
                while (curLevel >= 0) {
                    curNode = mostRightLessNodeInLevel(head, key, curLevel);
                    //非当前节点层级跳过
                    if (curLevel >= newNodeLevel) {
                        newNode.nextNodes.set(curLevel, curNode.nextNodes.get(curLevel));
                        curNode.nextNodes.set(curLevel, newNode);
                    }
                    curLevel--;
                }
            }
        }


        //返回整棵树上小于当前key的最右节点，相当于0层的小于Key最右节点
        private SkipListNode<K,V> mostRightLessNodeInTree(K key) {
            SkipListNode<K,V> curNode = head;
            int curLevel = maxLevel;
            while (curLevel >= 0) {
                curNode = mostRightLessNodeInLevel(curNode, key, curLevel);
                curLevel--;
            }
            return curNode;
        }

        //获取当前层级节点小于当前key的最右节点
        private SkipListNode<K,V> mostRightLessNodeInLevel(SkipListNode<K,V> curNode, K key, int level) {
            SkipListNode<K, V> nextNode = curNode.nextNodes.get(level);
            while (nextNode != null && nextNode.isKeyLess(key)) {
                curNode = nextNode;
                nextNode = nextNode.nextNodes.get(level);
            }
            return curNode;
        }
    }

}
