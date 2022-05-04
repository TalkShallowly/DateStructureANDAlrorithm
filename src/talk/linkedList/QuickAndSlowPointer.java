package talk.linkedList;

/**
 * 快慢指针问题： 返回中点问题
 */
public class QuickAndSlowPointer<T> {

    public static class Node<T>{
        private T value;
        private Node<T> next;
        public Node(T val){
            this.value = val;
        }
    }

    /**
     * 此方法实现
     *          奇数个数 ---》 中点节点
     *          偶数个数 ---》 上中点
     * @param firstNode
     * @return
     */
    public Node<T> findMidpoint(Node<T> firstNode){
        if (firstNode == null || firstNode.next == null){
            return firstNode;
        }
        Node<T> quickPointer = firstNode;
        Node<T> slowPointer = firstNode;
        while (quickPointer.next != null){
            if (quickPointer.next.next == null) {
                return slowPointer;
            }
            quickPointer = quickPointer.next.next;
            slowPointer = slowPointer.next;
        }
        return slowPointer;
    }

    /**
     * 此方法实现：
     *          奇数节点 ---》 中点的下一个节点
     *          偶数节点 ---》 下中点
     * @param firstNode
     * @return
     */
    public Node<T> findMidpoint_floor(Node<T> firstNode){
        if (firstNode == null || firstNode.next == null){
            return firstNode;
        }
        Node<T> quickPointer = firstNode;
        Node<T> slowPointer = firstNode;
        while (quickPointer != null){
            if (quickPointer.next == null || quickPointer.next.next == null) {
                slowPointer = slowPointer.next;
                break;
            }
            quickPointer = quickPointer.next.next;
            slowPointer = slowPointer.next;
        }
        return slowPointer;
    }

    /**
     * 此方法实现：
     *          奇数个数 ---》 返回中点前一个节点
     *          偶数个数 ---》 返回上中点的前一个节点
     * @param firstNode
     * @return
     */
    public Node<T> findMidpoint_round(Node<T> firstNode){
        if (firstNode == null || firstNode.next == null){
            return firstNode;
        }
        Node<T> quickPointer = firstNode;
        Node<T> slowPointer = firstNode;
        if (quickPointer.next.next != null){
            quickPointer = quickPointer.next.next;
        }
        while (quickPointer.next != null){
            if (quickPointer.next.next == null){
//                slowPointer = slowPointer.next;
                break;
            }
            quickPointer = quickPointer.next.next;
            slowPointer = slowPointer.next;
        }
        return slowPointer;
    }

    /**
     * 返回链表的节点个数
     * @param firstNode
     * @return
     */
    private int nodeNumber(Node<T> firstNode){
        int count = 0;
        Node<T>  curNode = firstNode;
        ++count;
        while (curNode.next != null){
            curNode = curNode.next;
            count++;
        }
        return count;
    }

    public Node<T> test_midpoint(Node<T> firstNode){
        if (firstNode == null || firstNode.next == null){
            return firstNode;
        }
        int count = nodeNumber(firstNode);
        Node<T> node = firstNode;
        if ((count & 1) == 0){
            for (int i = 0; i < (count >> 1) - 1; i++){
                node = node.next;
            }
        }else {
            for (int i = 0; i < (count >> 1); i++){
                node = node.next;
            }
        }
        return node;
    }

    public Node<T> test_midpoint_floor(Node<T> firstNode){
        if (firstNode == null || firstNode.next == null){
            return firstNode;
        }
        int count = nodeNumber(firstNode);
        Node<T> node = firstNode;
        if ((count & 1) == 0){
            for (int i = 0; i < (count >> 1); i++){
                node = node.next;
            }
        }else {
            for (int i = 0; i < (count >> 1) + 1; i++){
                node = node.next;
            }
        }
        return node;
    }

    public Node<T> test_midpoint_round(Node<T> firstNode){
        if (firstNode == null || firstNode.next == null){
            return firstNode;
        }
        int count = nodeNumber(firstNode);
        Node<T> node = firstNode;
        if ((count & 1) == 0){
            for (int i = 0; i < (count >> 1) - 2; i++){
                node = node.next;
            }
        }else {
            for (int i = 0; i < (count >> 1) - 1; i++){
                node = node.next;
            }
        }
        return node;
    }

    public  Node<Integer> generateRandomNode(int maxLen,int maxValue){
        Node<Integer> firstNode = new Node<>((int)(Math.random() * maxValue) + 1);
        int len = (int)(Math.random() * maxLen) + 1;
        Node<Integer> node = firstNode;
        for (int i = 0; i < len; i++) {
            int value = (int)(Math.random() * maxValue) + 1;
            node.next = new Node<>(value);
            node = node.next;
        }
        return firstNode;
    }

    public void checkNode(Node<T> firstNode){
        double random = Math.random();
        if (random < 0.33){
            if (findMidpoint(firstNode).value != test_midpoint(firstNode).value){
                throw new RuntimeException("oops .. findMidpoint ");
            }
        }else if (random < 0.66){
            if (findMidpoint_floor(firstNode).value != test_midpoint_floor(firstNode).value){
                throw new RuntimeException("oops ..  findMidpoint_floor");
            }
        }else {
            if (findMidpoint_round(firstNode).value != test_midpoint_round(firstNode).value){
                throw new RuntimeException(" oops .. findMidpoint_round");
            }
        }
    }
    public static void main(String[] args) {
//        Node<Integer> firstNode = new Node<Integer>(12);
//        Node<Integer> node = firstNode;
//        for (int i = 0; i < 3; i++) {
//            node.next = new Node<>(i);
//            node = node.next;
//        }
//        QuickAndSlowPointer<Integer> quickAndSlowPointer = new QuickAndSlowPointer<>();
//        Node<Integer> midpoint = quickAndSlowPointer.findMidpoint(firstNode);
//        Node<Integer> test_midpoint = quickAndSlowPointer.test_midpoint(firstNode);
//        System.out.println("test_midpoint = " + test_midpoint.value);
//        System.out.println("midpoint = " + midpoint.value);
//        Node<Integer> midpoint_floor = quickAndSlowPointer.findMidpoint_floor(firstNode);
//        Node<Integer> test_midpoint_floor = quickAndSlowPointer.test_midpoint_floor(firstNode);
//        System.out.println("test_midpoint_floor = " + test_midpoint_floor.value);
//        System.out.println("midpoint_floor = " + midpoint_floor.value);
//        Node<Integer> midpoint_round = quickAndSlowPointer.findMidpoint_round(firstNode);
//        Node<Integer> test_midpoint_round = quickAndSlowPointer.test_midpoint_round(firstNode);
//        System.out.println("test_midpoint_round = " + test_midpoint_round.value);
//        System.out.println("midpoint_round = " + midpoint_round.value);

        int maxLen = 10000;
        int maxValue = 1000;
        int testTime = 100000;
        QuickAndSlowPointer<Integer> quickAndSlowPointer = new QuickAndSlowPointer<>();
        System.out.println("start....");
        for (int i = 0; i < testTime; i++) {
            Node<Integer> node = quickAndSlowPointer.generateRandomNode(maxLen, maxValue);
            quickAndSlowPointer.checkNode(node);
        }
        System.out.println("end.....");
    }
}
