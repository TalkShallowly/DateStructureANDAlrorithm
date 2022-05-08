package talk.linkedList.list;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *  使用链币实现队列和栈
 *      1）：Queue ----> FIFO模型
 *      2）：Stack ----> LIFO模型
 * @author talkshallowly
 */
public class LinkedListToQueueAndStack {
    public static class ListNode<T> {
        T data;
        ListNode next;
        ListNode(T val) { this.data = val; }
    }

    /**
     * 根据链表的头尾节点来实现
     * @param <V>
     */
    public static class MyQueue<V>{
        private ListNode<V> head;
        private ListNode<V> tail;
        private int size;
        MyQueue(){
            head = null;
            tail = null;
            size = 0;
        }
        public boolean isEmpty(){
            return size == 0;
        }
        public int size(){
            return size;
        }
        public V offer(V value){
            ListNode<V> cur = new ListNode<>(value);
            if (tail == null){
                head = cur;
                tail = cur;
            }else {
                tail.next = cur;
                tail = tail.next;
            }
            size++;
            return value;
        }

        /**
         * 返回第一个元素（不删除）
         * @return
         */
        public V peek(){
            if (head != null){
                return head.data;
            }
            return null;
        }

        /**
         * 删除第一个元素
         * @return
         */
        public V poll() {
            V ans = null;
            if (head != null) {
                ans = head.data;
                head = head.next;
                size--;
            }
            if (head == null){
                tail = null;
            }
            return ans;
        }
    }

    /**
     * 可以每次移动头节点来实现元素的移动
     * @param <V>
     */
    public static class MyStack<V>{
        private ListNode<V> head;
        private int size;
        MyStack(){
            head = null;
            size = 0;
        }
        public boolean isEmpty(){
            return size == 0;
        }
        public int size(){
            return size;
        }

        public V push(V value){
            ListNode<V> cur = new ListNode<>(value);
            if (head != null) {
                cur.next = head;
            }
            head = cur;
            size++;
            return value;
        }

        public V peek(){
            return head == null ? null : head.data;
        }
        public V pop(){
            V ans = null;
            if (head != null){
                ans = head.data;
                head = head.next;
            }
            size--;
            return ans;
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
        MyStack<Integer> myStack = new MyStack();
        TestMyStack<Integer> testMyStack = new TestMyStack();
        double random = Math.random();
        if (random < 0.33){
            int num1 = myStack.push(value);
            int num2 = testMyStack.push(value);
            if (num1 != num2){
                System.out.println("error....stack..   offer");
            }
        }else if (random < 0.66){
            if (!myStack.isEmpty()){
                int num1 = myStack.pop();
                int num2 = testMyStack.pop();
                if (num1 != num2){
                    System.out.println("error....stack..   peek");
                }
            }
        }else {
            if (!myStack.isEmpty()){
                int num1 = myStack.peek();
                int num2 = testMyStack.peek();
                if (num1 != num2){
                    System.out.println("error....queue..   peek");
                }
            }
        }
        if (myStack.size != testMyStack.size()){
            System.out.println("error....size");
        }
        if (myStack.isEmpty() != testMyStack.isEmpty()){
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
