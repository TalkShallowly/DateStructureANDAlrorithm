package talk.linkedList.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 使用两个栈实现队列的效果（原理：两个栈的数据相互拷贝转换）
 * @author talkshallowly
 */
public class TwoStackImplementQueue {
    private Stack<Integer> pushStack;
    private Stack<Integer> popStack;
    TwoStackImplementQueue(){
        pushStack = new Stack();
        popStack = new Stack();
    }
    /**
     * push栈向pop栈倒数据
     */
    private void pushToPop(){
        if (popStack.isEmpty()){
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
    }
    public boolean isEmpty(){
        return pushStack.isEmpty() && popStack.isEmpty();
    }
    public int size(){
        return pushStack.size() + popStack.size();
    }
    public void offer(int value){
        pushStack.push(value);
        pushToPop();
    }
    public int poll(){
        if (isEmpty()){
            throw new RuntimeException("当前队列中无数据");
        }
        pushToPop();
        return popStack.pop();
    }
    public int peek(){
        if (isEmpty()){
            throw new RuntimeException("当前队列中无数据");
        }
        pushToPop();
        return popStack.peek();
    }
    public static class TestMyQueue<V>{
        List<V> list = new ArrayList();
        public boolean isEmpty(){
            return list.isEmpty();
        }
        public int size(){
            return list.size();
        }
        public void offer(V value){
            list.add(value);
        }
        public V peek(){
            return list.get(0);
        }
        public V poll(){
            return list.isEmpty() ? null : list.remove(0);
        }
    }

    public static void checkQueue(int count,int maxValue){
        TwoStackImplementQueue twoStackImplementQueue = new TwoStackImplementQueue();
        TestMyQueue<Integer> test = new TestMyQueue();
        double random = Math.random();
        for (int i = 0; i < count; i++) {
            int value = (int)(Math.random() * maxValue) + 1;
            twoStackImplementQueue.offer(value);
            test.offer(value);
        }
        for (int i = 0; i < count; i++) {
            if (twoStackImplementQueue.poll() != test.poll()){
                System.out.println("error.....");
            }
        }

//        if (random < 0.5){
//            if (!twoStackImplementQueue.isEmpty()){
//                int num1 = twoStackImplementQueue.poll();
//                int num2 = test.poll();
//                if (num1 != num2){
//                    System.out.println("error....queue..   peek");
//                }
//            }
//        }else {
//            if (!twoStackImplementQueue.isEmpty()){
//                int num1 = twoStackImplementQueue.peek();
//                int num2 = test.peek();
//                if (num1 != num2){
//                    System.out.println("error....queue..   peek");
//                }
//            }
//        }
        if (twoStackImplementQueue.isEmpty() != test.isEmpty()){
            System.out.println("error....queue...... isEmpty");
        }
        if (twoStackImplementQueue.size() != test.size()){
            System.out.println("error...queue......size");
        }
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int value = 10000;
        for (int i = 0; i < testTime; i++) {
            checkQueue(1000, value);
        }
    }
}
