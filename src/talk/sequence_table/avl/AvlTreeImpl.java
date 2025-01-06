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

    public class AvlTreeMap <K extends Comparable<K>,V> {

        public AvlNode<K,V> root;
        public int size;

        //查询
        public AvlNode<K,V> get(K key) {
            if (size == 0) {
                return null;
            }
            return get(root,key);
        }

        //添加
        public void put(K key, V value) {
            if (key == null) {
               return;
            }
            AvlNode<K, V> kvAvlNode = get(root, key);
            if (kvAvlNode != null) {
                kvAvlNode.value = value;
            }else {
                root = add(root,key,value);
                size++;
            }
        }

        //删除
        public void remove(K key) {
            AvlNode<K, V> kvAvlNode = get(root, key);
            if (kvAvlNode != null) {
                delete(root,key);
                size--;
            }
        }


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


        public boolean contains(K key) {
            if (key == null || size == 0) {
                return false;
            }
            return get(root, key) != null;
        }

        public int size() {
            return size;
        }

        //返回最小的 Key
        public K firstKey() {
            if (size == 0) {
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
            if (size == 0) {
                return null;
            }
            AvlNode<K,V> last = root;
            while (last.right != null) {
                last = last.right;
            }
            return last.key;
        }

        //返回小于等于当前key的数据
        public K floorKey(K key) {
            if (size == 0) {
                return null;
            }
            AvlNode<K,V> floor = root;
            while (floor.left != null) {
                floor = floor.left;
                if (!(floor.left.key.compareTo(key) > 0)) {
                    return floor.left.key;
                }
            }
            return null;
        }

        //返回大于当前key的数据
        public K ceilingKey(K key) {
            if (size == 0) {
                return null;
            }
            AvlNode<K,V> ceiling = root;
            while (ceiling.right != null) {
                ceiling = ceiling.right;
                if (!(ceiling.right.key.compareTo(key) < 0)) {
                    return ceiling.right.key;
                }
            }
            return null;
        }




        private AvlNode<K,V> get(AvlNode<K,V> cur, K key) {
            if (cur == null){
                return null;
            }
            if (cur.key.compareTo(key) > 0){
                return get(cur.right, key);
            }else if (cur.key.compareTo(key) < 0){
                return get(cur.left, key);
            }else {
                return cur;
            }
        }

        private AvlNode<K,V> add (AvlNode<K,V> cur, K key, V value) {
            if (cur == null) {
                return new AvlNode<>(key, value);
            }
            if (cur.key.compareTo(key) > 0) {
                cur.left = add(cur.left, cur.key, value);
            }else {
                cur.right = add(cur.right, cur.key, value);
            }
            return maintain(cur);
        }


        //删除
        private AvlNode<K,V> delete (AvlNode<K,V> cur, K key) {
            if (cur == null){
                return null;
            }

            if (cur.key.compareTo(key) > 0) {
                cur.left = delete(cur.left, cur.key);
            }else if (cur.key.compareTo(key) < 0){
                cur.right = delete(cur.right, cur.key);
            }else {

                //当前节点既无左节点也无右节点
                if (cur.left == null && cur.right == null) {
                    return null;
                }
                //当前删除节点无右节点
                else if (cur.right == null){
                    return cur.left;
                }
                //当前删除节点无左节点
                else if (cur.left == null) {
                    return cur.right;
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
            return maintain(cur);
        }



        //平衡性调整
        private AvlNode<K,V> maintain(AvlNode<K,V> cur) {
            AvlNode<K,V> record = cur;
            if (cur == null || (cur.left == null && cur.right == null)) {
                return record;
            }

            int leftHeight = cur.left == null ? 0 : cur.left.height;
            int rightHeight = cur.right == null ? 0 : cur.right.height;
            if (Math.abs(leftHeight - rightHeight) > 1) {
                //L 型  再次判断 LL或者LR
                if (leftHeight > rightHeight) {
                    int leftLeftHeight = cur.left.left == null ? 0 : cur.left.left.height;
                    int leftRightHeight = cur.left.right == null ? 0 : cur.left.right.height;
                    //是否 LR
                    if (leftLeftHeight < leftRightHeight) {
                        //左旋
                        cur.left = leftRotate(cur.left);
                    }
                    //统一处理 LL  右旋
                    record = rightRotate(cur);
                }else {
                    //R 型  再次判断 RR或者RL
                    int rightLeftHeight = cur.right.left == null ? 0 : cur.right.left.height;
                    int rightRightHeight = cur.right.right == null ? 0 : cur.right.right.height;
                    //是否RL
                    if (rightRightHeight < rightLeftHeight) {
                        //右旋
                        cur.right = rightRotate(cur.right);
                    }
                    //统一处理 RR 左旋
                    record = leftRotate(cur);
                }
            }
            return record;
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
