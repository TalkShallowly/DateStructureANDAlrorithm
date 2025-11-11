package talk.sequence_table.size_balanced;


/**
 * Size Balanced : 任何一个叔叔节点不能小于自己任意一个侄子节点的节点个数
 *  四种违规类型：
 *     LL , LR , RR , RL
 * <p>
 *  节点平衡性： 每次添加的时候进行数据递归调整， 删除可以不进行调整
 */
public class SizeBalancedTreeImpl {

    private static class SBNode<K extends Comparable<K>, V>{
        private K key;
        private V value;

        private SBNode<K,V> left;
        private SBNode<K,V> right;
        private int size;

        public SBNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.size = 1;
        }
    }

    public static class SizeBalancedTree<K extends Comparable<K>, V>{
        private SBNode<K,V> root;


        public V get (K key) {
            if (key == null) {
                return null;
            }
            SBNode<K, V> lastIndex = findLastIndex(key);
            return lastIndex != null && lastIndex.key.compareTo(key) == 0 ? lastIndex.value : null;
        }

        public void put (K key, V value) {
            if (key == null) {
                return;
            }
            SBNode<K, V> lastIndex = findLastIndex(key);
            if (lastIndex != null && lastIndex.key.compareTo(key) == 0) {
                lastIndex.value = value;
            }else {
                root = add(root, key, value);
            }
        }

        public void remove (K key) {
            if (key == null) {
                return;
            }
            SBNode<K, V> lastIndex = findLastIndex(key);
            if (lastIndex != null && lastIndex.key.compareTo(key) == 0) {
                root = delete(root, key);
            }
        }

        public boolean containsKey (K key) {
            if (key == null) {
                return false;
            }
            return findLastIndex(key) != null && findLastIndex(key).key.compareTo(key) == 0;
        }

        public int size () {
            return root == null ? 0 : root.size;
        }


        public SBNode<K,V> findLastIndex(K key){
            SBNode<K, V> pre = root;
            SBNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.key) == 0) {
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return pre;
        }

        private SBNode<K,V> add (SBNode<K,V> cur,K key, V value) {
            if (cur == null) {
                return new SBNode<>(key,value);
            }
            cur.size++;
            if (cur.key.compareTo(key) > 0) {
                cur.left = add(cur.left,key,value);
            }else {
                cur.right = add(cur.right,key,value);
            }
            return maintain(cur);
        }

        private SBNode<K,V> delete (SBNode<K,V> cur,K key) {
            if (cur == null){
                return null;
            }
            cur.size--;
            if (key.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left,key);
            }else if (key.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right,key);
            }else {

                //无左
                if (cur.left == null) {
                    cur = cur.right;
                }
                //无右
                else if (cur.right == null) {
                    cur = cur.left;
                }
                //有左有右
                else {
                    //获取右边节点最左节点
                    SBNode<K, V> pre = null;
                    SBNode<K, V> next = cur.right;
                    next.size--;
                    while (next.left != null) {
                        pre = next;
                        next = next.left;
                        next.size--;
                    }
                    //提取断连右边节点最左节点,替换cur
                    if (pre != null) {
                        pre.left = next.right;
                        next.right = cur.right;
                    }
                    next.left = cur.left;
                    next.size = next.left.size + (next.right == null ? 0 : next.right .size) + 1;
                    cur = next;
                }
            }
//            可以不需要删除的调平
//            cur = maintain(cur);
            return cur;
        }


        //平衡性调整
        private SBNode<K,V> maintain(SBNode<K,V> cur) {
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
        private SBNode<K,V> leftRotate(SBNode<K,V> cur) {
            SBNode<K,V> rightNode = cur.right;
            cur.right = rightNode.left;
            rightNode.left = cur;
            rightNode.size = cur.size;
            cur.size = (cur.left == null ? 0 : cur.left.size) +  (cur.right == null ? 0 : cur.right.size) + 1;
            return rightNode;
        }

        //右旋
        private SBNode<K,V> rightRotate(SBNode<K,V> cur) {
            SBNode<K,V> leftNode = cur.left;
            cur.left = leftNode.right;
            leftNode.right = cur;
            leftNode.size = cur.size;
            cur.size = (cur.left == null ? 0 : cur.left.size) +  (cur.right == null ? 0 : cur.right.size) + 1;
            return leftNode;
        }


        //返回小于等于当前key的数据
        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SBNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.key;
        }

        //返回大于当前key的数据
        public K ceilingKey(K key) {
            if (root == null) {
                return null;
            }
            SBNode<K, V> lastNoSmallIndex = findLastNoSmallIndex(key);
            return lastNoSmallIndex == null ? null : lastNoSmallIndex.key;
        }

        //返回最小的 Key
        public K firstKey() {
            if (root == null) {
                return null;
            }
            SBNode<K,V> first = root;
            while (first.left != null) {
                first = first.left;
            }
            return first.key;
        }

        //返回最大的key
        public K lastKey(){
            if (root == null) {
                return null;
            }
            SBNode<K,V> last = root;
            while (last.right != null) {
                last = last.right;
            }
            return last.key;
        }

        //查询不大于当前key的最后一个节点
        public SBNode<K,V> findLastNoBigIndex(K key){
            SBNode<K, V> pre = null;
            SBNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    pre = cur;
                    break;
                }else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                }else {
                    pre = cur;
                    cur = cur.right;
                }
            }
            return pre;
        }

        //查询不小于当前key的第一个节点
        public SBNode<K,V> findLastNoSmallIndex(K key){
            SBNode<K, V> next = null;
            SBNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    next = cur;
                    break;
                } else if (key.compareTo(cur.key) > 0) {
                    cur = cur.right;
                }else {
                    next = cur;
                    cur = cur.left;
                }
            }
            return next;
        }

    }


}
