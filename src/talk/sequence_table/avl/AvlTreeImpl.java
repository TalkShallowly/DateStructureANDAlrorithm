package talk.sequence_table.avl;

/**
 *
 * AVL ： 平衡特点： 左右两子树高度差不能大于 1
 * 四种违规模式：
 * LL ： 一次右旋
 * LR ： 一次左旋 + 一次右旋
 * RR ： 一次左旋
 * RL ： 一次右旋 + 一次左旋
 */
public class AvlTreeImpl {

    //avl 节点。 K必须是为可比较的
    public static class AvlNode<K extends Comparable<K>,V> {
        public K key;
        public V value;
        public int height;

        public AvlNode<K,V> left;
        public AvlNode<K,V> right;

        public AvlNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }

    public static class AvlTreeMap <K extends Comparable<K>,V> {

        public AvlNode<K,V> root;
        public int size;

        //查询
        public V get(K key) {
            if (key == null) {
                return null;
            }
            AvlNode<K, V> lastIndex = findLastIndex(key);
            return lastIndex != null && key.compareTo(lastIndex.key) == 0 ? lastIndex.value : null;
        }

        //添加
        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            AvlNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                lastNode.value = value;
            } else {
                size++;
                root = add(root, key, value);
            }
        }

        //删除
        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                size--;
                root = delete(root,key);
            }
        }

        public boolean containsKey(K key){
            if (key == null) {
                return false;
            }
            AvlNode<K, V> lastIndex = findLastIndex(key);
            return lastIndex != null && key.compareTo(lastIndex.key) == 0;
        }


//        查询最后一个匹配节点
        public AvlNode<K,V> findLastIndex(K key){
            AvlNode<K, V> pre = root;
            AvlNode<K, V> cur = root;
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

        //查找不小于当前key的最后一个节点
        public AvlNode<K,V> findLastNoSmallIndex(K key){
            AvlNode<K, V> pre = null;
            AvlNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    pre = cur;
                    break;
                }else if (key.compareTo(cur.key) < 0) {
                    pre = cur;
                    cur = cur.left;
                }else{
                    cur = cur.right;
                }
            }
            return pre;
        }

        //查询不大于当前key的最后一个节点
        public AvlNode<K,V> findLastNoBigIndex(K key){
            AvlNode<K, V> pre = null;
            AvlNode<K, V> cur = root;
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


        public int size() {
            return size;
        }

        //返回最小的 Key
        public K firstKey() {
            if (root == null) {
                return null;
            }
            AvlNode<K,V> first = root;
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
            AvlNode<K,V> last = root;
            while (last.right != null) {
                last = last.right;
            }
            return last.key;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            AvlNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.key;
        }

        //返回大于当前key的数据
        public K ceilingKey(K key) {
            if (size == 0) {
                return null;
            }
            AvlNode<K, V> lastNoSmallIndex = findLastNoSmallIndex(key);
            return lastNoSmallIndex == null ? null : lastNoSmallIndex.key;
        }

        private AvlNode<K, V> add(AvlNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new AvlNode<K, V>(key, value);
            } else {
                if (key.compareTo(cur.key) < 0) {
                    cur.left = add(cur.left, key, value);
                } else {
                    cur.right = add(cur.right, key, value);
                }
                cur.height = Math.max(cur.left != null ? cur.left.height : 0, cur.right != null ? cur.right.height : 0) + 1;
                return maintain(cur);
            }
        }



        //删除
        private AvlNode<K,V> delete (AvlNode<K,V> cur, K key) {
            if (key.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right, key);
            } else if (key.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left, key);
            }else {

                //当前节点既无左节点也无右节点
                if (cur.left == null && cur.right == null) {
                    cur = null;
                }
                //当前删除节点无右节点
                else if (cur.right == null){
                    cur = cur.left;
                }
                //当前删除节点无左节点
                else if (cur.left == null) {
                    cur = cur.right;
                }
                //既有左节点又有右节点
                else {
                    //获取右节点最左边的节点 或者 获取左节点最右边的节点
                    AvlNode<K,V> replace = cur.right;
                    while (replace.left != null){
                        replace = replace.left;
                    }
                    cur.right = delete(cur.right, replace.key);
                    replace.right = cur.right;
                    replace.left = cur.left;
                    cur = replace;
                }
            }
            if (cur != null) {
                cur.height = Math.max(cur.left != null ? cur.left.height : 0, cur.right != null ? cur.right.height : 0) + 1;
            }
            return maintain(cur);
        }



        //平衡性调整
        private AvlNode<K,V> maintain(AvlNode<K,V> cur) {
            if (cur == null) {
                return null;
            }
            int leftHeight = cur.left == null ? 0 : cur.left.height;
            int rightHeight = cur.right == null ? 0 : cur.right.height;
            if (Math.abs(leftHeight - rightHeight) > 1) {
                //L 型  再次判断 LL或者LR
                if (leftHeight > rightHeight) {
                    int leftLeftHeight = cur.left != null && cur.left.left != null ? cur.left.left.height : 0;
                    int leftRightHeight = cur.left != null && cur.left.right != null ? cur.left.right.height : 0;
                    //是否 LR
                    if (leftLeftHeight >= leftRightHeight) {
                        //左旋
                        cur = rightRotate(cur);
                    }else {
                        cur.left = leftRotate(cur.left);
                        cur = rightRotate(cur);
                    }
                }else {
                    //R 型  再次判断 RR或者RL
                    int rightRightHeight = cur.right != null && cur.right.right != null ? cur.right.right.height : 0;
                    int rightLeftHeight = cur.right != null && cur.right.left != null ? cur.right.left.height : 0;
                    //是否RL
                    if (rightRightHeight >= rightLeftHeight) {
                        //右旋
                        cur = leftRotate(cur);
                    }else {
                        cur.right = rightRotate(cur.right);
                        cur = leftRotate(cur);
                    }
                }
            }
            return cur;
        }

        //左旋
        private AvlNode<K,V> leftRotate(AvlNode<K,V> cur) {
            AvlNode<K,V> record = cur.right;
            cur.right = record.left;
            record.left = cur;
            cur.height = Math.max(cur.right == null ? 0 : cur.right.height, cur.left == null ? 0 : cur.left.height) + 1;
            record.height = Math.max(cur.height,record.right == null ? 0 : record.right.height) + 1;
            return record;
        }


        //右旋
        private AvlNode<K,V> rightRotate(AvlNode<K,V> cur) {
            AvlNode<K,V> record = cur.left;
            cur.left = record.right;
            record.right = cur;
            cur.height = Math.max(cur.right == null ? 0 : cur.right.height, cur.left == null ? 0 : cur.left.height) + 1;
            record.height = Math.max(cur.height,record.left == null ? 0 : record.left.height) + 1;
            return record;
        }

    }
}
