package talk.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 使用数组实现队列和栈
 * @author talkshallowly
 */
public class ArrayToQueueAndStack {
    public static class ArrayToQueue{
        private int[] ints;
        private final int limitLen;
        private int start;
        private int end;
        private int size;
        ArrayToQueue(int length){
            ints = new int[length];
            this.limitLen = length;
            start = 0;
            end = 0;
            size = 0;
        }
        public int size() {
            return size;
        }
        public boolean isEmpty() {
            return size == 0;
        }
        public int offer(int value){
            if (size >= limitLen){
                throw new RuntimeException("队列已满，无法添加");
            }else {
                if (end >= limitLen) {
                    end = 0;
                }
                ints[end++] = value;
                size++;
            }
            return value;
        }
        public int peek(){
            if (start >= limitLen){
                start = 0;
            }
            return ints[start];
        }
        public int poll(){
            int ans = 0;
            if (size != 0){
                if (start >= limitLen) {
                    start = 0;
                }
                ans = ints[start++];
                size--;
            }else {
                throw new RuntimeException("队列已空，无法删除");
            }
            return ans;
        }
    }

    public static class ArrayToStack{
        private int[] arr;
        private int index;
        private int size;
        private final int limitLength;
        ArrayToStack(int length){
            this.limitLength = length;
            arr = new int[length];
        }
        public boolean isEmpty(){
            return size == 0;
        }
        public int size(){
            return size;
        }

        public int push(int value){
            if (size >= limitLength){
                throw new RuntimeException("栈空间已满，无法放入！");
            }
            arr[index++] = value;
            size++;
            return value;
        }
        public int peek(){
            return arr[index-1];
        }
        public int pop(){
            if (size == 0){
                throw new RuntimeException("栈内无任何数据，请先添加！");
            }
            size--;
            return arr[--index];
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
        ArrayToQueue arrayToQueue = new ArrayToQueue(len);
        TestMyQueue<Integer> test = new TestMyQueue();
        double random = Math.random();
        if (random < 0.33){
            int num1 = arrayToQueue.offer(value);
            int num2 = test.offer(value);
            if (num1 != num2){
                System.out.println("error....queue..   offer");
            }
        }else if (random < 0.66){
            if (!arrayToQueue.isEmpty()){
                int num1 = arrayToQueue.poll();
                int num2 = test.poll();
                if (num1 != num2){
                    System.out.println("error....queue..   peek");
                }
            }
        }else {
            if (!arrayToQueue.isEmpty()){
                int num1 = arrayToQueue.peek();
                int num2 = test.peek();
                if (num1 != num2){
                    System.out.println("error....queue..   peek");
                }
            }
        }
        if (arrayToQueue.isEmpty() != test.isEmpty()){
            System.out.println("error....queue...... isEmpty");
        }
        if (arrayToQueue.size != test.size()){
            System.out.println("error...queue......size");
        }
    }

    public static void checkStack(int len,int value){
        ArrayToStack arrayToStack = new ArrayToStack(len);
        TestMyStack<Integer> testMyStack = new TestMyStack();
        double random = Math.random();
        if (random < 0.33){
            int num1 = arrayToStack.push(value);
            int num2 = testMyStack.push(value);
            if (num1 != num2){
                System.out.println("error....stack..   offer");
            }
        }else if (random < 0.66){
            if (!arrayToStack.isEmpty()){
                int num1 = arrayToStack.pop();
                int num2 = testMyStack.pop();
                if (num1 != num2){
                    System.out.println("error....stack..   peek");
                }
            }
        }else {
            if (!arrayToStack.isEmpty()){
                int num1 = arrayToStack.peek();
                int num2 = testMyStack.peek();
                if (num1 != num2){
                    System.out.println("error....queue..   peek");
                }
            }
        }
        if (arrayToStack.size != testMyStack.size()){
            System.out.println("error....size");
        }
        if (arrayToStack.isEmpty() != testMyStack.isEmpty()){
            System.out.println("error.. isEmpty");
        }
    }

    public static void main(String[] args) {
        int testTime = 50000;
        System.out.println("测试队列开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * 1000) + 1;
            for (int j = 0; j < len; j++) {
                checkQueue(len, (int) (Math.random() * 1000) + 1);
                checkStack(len, (int) (Math.random() * 1000) + 1);
            }
        }
        System.out.println("测试队列结束");
    }
}
