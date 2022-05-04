package talk.list;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 使用双端链表实现队列和栈
 * @author talkshallowly
 */
public class DoubleListToQueueAndStack {
    public static class DoubleList<E>{
        E data;
        DoubleList pre;
        DoubleList next;
        DoubleList(E data){
            this.data = data;
        }
    }

    /**
     *  与单向链表原理相似
     * @param <V>
     */
    public static class MyQueue<V>{
        private DoubleList head;
        private DoubleList tail;
        private int size;
        public boolean isEmpty(){
            return size == 0;
        }
        public int size(){
            return size;
        }
        public V offer(V value){
            DoubleList currentNode = new DoubleList(value);
            if (tail != null) {
                tail.next = currentNode;
                tail = tail.next;
            }else {
                head = currentNode;
                tail = currentNode;
            }
            size++;
            return value;
        }

        public V peek(){
            return head != null ? (V) head.data : null;
        }
        public V poll(){
            V ans = null;
            if (head == null){
                return ans;
            }else {
                ans = (V)head.data;
                head = head.next;
            }
            return ans;
        }
    }

    /**
     * 双端链表：头节点做插入，尾节点取数据
     * @param <V>
     */
    public static class DoubleListToStack<V>{
        private DoubleList head;
        private DoubleList tail;
        private int size;
        public boolean isEmpty(){
            return size == 0;
        }
        public int size(){
            return size;
        }
        //从头部添加，移动头部为栈顶
        public V pushHead(V value){
            DoubleList currentNode = new DoubleList(value);
            if (head == null){
                head = currentNode;
                tail = currentNode;
            }else {
                currentNode.next = head;
                head.pre = currentNode;
                head = head.next;
            }
            size++;
            return value;
        }

        //从尾部添加
        public V pushTail(V value){
            DoubleList currentNode = new DoubleList(value);
            if (tail == null){
                head = currentNode;
            }else {
                tail.next = currentNode;
                currentNode.pre = tail;
            }
            tail = currentNode;
            size++;
            return value;
        }
        public V peek(){
            return tail == null ? null : (V)tail.data;
        }
        //从头部删除
        public V popHead(){
            V ans = null;
            if (head == null){
                return ans;
            }
            ans = (V)head.data;
            if (tail == head){
                tail = null;
                head = null;
            }else {
                head = head.next;
                head.pre = null;
            }
            size--;
            return ans;
        }
        //从尾部删除
        public V popTail(){
            V ans = null;
            if (tail == null){
                return ans;
            }
            ans = (V)tail.data;
            if (tail == head){
                tail = null;
                head = null;
            }else {
                tail = tail.pre;
                tail.next = null;
            }
            size--;
            return  ans;
        }
    }

    public static class TestMyQueue<V>{
        List<V> list = new ArrayList();
        public boolean isEmpty(){
            return list.isEmpty();
        }
        public int size(){
            return list.size();
        }
        public V offer(V value){
            list.add(value);
            return value;
        }
        public V peek(){
            return list.get(0);
        }
        public V poll(){
            return list.isEmpty() ? null : list.remove(0);
        }

    }

    public static class TestMyStack<V>{
        Stack<V> stack = new Stack<>();
        public boolean isEmpty(){
            return stack.isEmpty();
        }
        public int size(){
            return stack.size();
        }
        public V push(V value){
            return stack.push(value);
        }
        public V peek(){
            return stack.peek();
        }
        public V pop(){
            return stack.pop();
        }
    }

    public static void checkQueue(int len,int value){
        MyQueue<Integer> myQueue = new MyQueue();
        TestMyQueue<Integer> test = new TestMyQueue();
        double random = Math.random();
        if (random < 0.33){
            int num1 = myQueue.offer(value);
            int num2 = test.offer(value);
            if (num1 != num2){
                System.out.println("error....queue..   offer");
            }
        }else if (random < 0.66){
            if (!myQueue.isEmpty()){
                int num1 = myQueue.poll();
                int num2 = test.poll();
                if (num1 != num2){
                    System.out.println("error....queue..   peek");
                }
            }
        }else {
            if (!myQueue.isEmpty()){
                int num1 = myQueue.peek();
                int num2 = test.peek();
                if (num1 != num2){
                    System.out.println("error....queue..   peek");
                }
            }
        }
        if (myQueue.isEmpty() != test.isEmpty()){
            System.out.println("error....queue...... isEmpty");
        }
        if (myQueue.size != test.size()){
            System.out.println("error...queue......size");
        }
    }

    public static void checkStack(int len,int value){
        DoubleListToStack<Integer> doubleListToStack = new DoubleListToStack();
        TestMyStack<Integer> testMyStack = new TestMyStack();
        double random = Math.random();
        if (random < 0.33){
            int num1 = doubleListToStack.pushTail(value);
            int num2 = testMyStack.push(value);
            if (num1 != num2){
                System.out.println("error....stack..   offer");
            }
        }else if (random < 0.66){
            if (!doubleListToStack.isEmpty()){
                int num1 = doubleListToStack.popTail();
                int num2 = testMyStack.pop();
                if (num1 != num2){
                    System.out.println("error....stack..   peek");
                }
            }
        }else {
            if (!doubleListToStack.isEmpty()){
                int num1 = doubleListToStack.peek();
                int num2 = testMyStack.peek();
                if (num1 != num2){
                    System.out.println("error....queue..   peek");
                }
            }
        }
        if (doubleListToStack.size != testMyStack.size()){
            System.out.println("error....size");
        }
        if (doubleListToStack.isEmpty() != testMyStack.isEmpty()){
            System.out.println("error.. isEmpty");
        }
    }


    public static void main(String[] args) {
        int testTime = 200000;
        int len = 10000;
        int value = 500000;
        System.out.println("测试队列开始");
        for (int i = 0; i < testTime; i++) {
            checkQueue(len,value);
        }
        System.out.println("测试队列结束");

        System.out.println("测试Stack开始");
        for (int i = 0; i < testTime; i++) {
            checkStack(len,value);
        }
        System.out.println("测试Stack结束");
    }
}
