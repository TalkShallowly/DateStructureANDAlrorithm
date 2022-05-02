package talk.list;

import java.util.*;

/**
 * 两个队列实现栈 （原理：使用两个现有的队列。将队列直接的数据也进行相互导送）
 * @author talkshallowly
 */
public class TwoQueueImplementStack {
    private Queue<Integer> pushQueue;
    private Queue<Integer> popQueue;
    TwoQueueImplementStack(){
        pushQueue = new LinkedList<>();
        popQueue = new LinkedList<>();
    }
    public boolean isEmpty(){
        return pushQueue.isEmpty() && popQueue.isEmpty();
    }
    public int size(){
        return pushQueue.size() + popQueue.size();
    }

    /**
     *  1） 两个队列相互导数据时，每次向popQueue中导入N -1 个数，pushQueue只剩余最后一个数
     *  2） 当pushQueue数据被那空时，popQueue进行全量的导入，之后才重第一步进行数据转移 （两队列的交替进行后续有时间研究处理）
     */
    private void pushToPop(){
        while (!pushQueue.isEmpty() && pushQueue.size() > 1){
            popQueue.add(pushQueue.remove());
        }

        if (pushQueue.isEmpty()){
            while (!popQueue.isEmpty()){
                pushQueue.add(popQueue.remove());
            }
        }
    }
    public void push(int value){
        pushQueue.add(value);
        pushToPop();
    }
    public int peek(){
        if (isEmpty()){
            throw new RuntimeException("Stack is empty");
        }
        pushToPop();
        int ans = pushQueue.peek();
        pushToPop();
        return ans;
    }
    public int pop(){
        if (isEmpty()){
            throw new RuntimeException("Stack is empty");
        }
        pushToPop();
        int ans = pushQueue.remove();
        pushToPop();
        return ans;
    }

    public void checkStack(int maxValue,int maxLen){
        Stack<Integer> stack = new Stack<>();
        TwoQueueImplementStack myStack = new TwoQueueImplementStack();
        int len = (int) (Math.random() * maxLen) + 1;
        for (int i = 0; i < len; i++) {
            int value = (int) (Math.random() * maxValue) + 1;
            stack.push(value);
            myStack.push(value);
        }
        for (int i = 0; i < len; i++) {
            if (stack.peek() != myStack.peek()){
                System.out.println("error : myStack = " + myStack.peek() + " Stack = " + stack.peek());
            }
            if (stack.size() != myStack.size()){
                System.out.println("error : myStackSize = " + myStack.size() + " StackSize = " + stack.size());
            }
            if (stack.pop() != myStack.pop()){
                System.out.println("error : myStackPop = " + myStack.pop() + " StackPop = " + stack.pop());
            }
        }
    }

    public static void main(String[] args) {
        TwoQueueImplementStack twoQueueImplementStack = new TwoQueueImplementStack();
        int maxValue = 1000;
        int maxLen = 1000;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            twoQueueImplementStack.checkStack(maxValue,maxLen);
        }
        System.out.println("测试结束");
    }

}
