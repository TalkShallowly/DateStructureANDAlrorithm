package talk.linkedList.list;

import java.util.Stack;

/**
 * 实现一个特殊的栈，可以返回插入元素的最小值
 * @author talkshallowly
 */
public class FindMinByStack {

    public static class SpecialStack{
        private Stack<Integer> dataStack;
        private Stack<Integer> minStack;
        SpecialStack(){
            dataStack = new Stack<>();
            minStack = new Stack<>();
        }
        public boolean isEmpty(){
            return dataStack.isEmpty();
        }
        public int size(){
            return dataStack.size();
        }
        public void push(int value){
            if (!isEmpty()){
                if (value <= minStack.peek()){
                    minStack.push(value);
                }else {
                    minStack.push(minStack.peek());
                }
            }else {
                minStack.push(value);
            }
            dataStack.push(value);
        }
        public int peek(){
            return dataStack.peek();
        }
        public int pop(){
            minStack.pop();
            return dataStack.pop();
        }
        public int getMin(){
            return minStack.peek();
        }
    }

    public static void main(String[] args) {
        SpecialStack stack = new SpecialStack();
        stack.push(23);
        stack.push(3);
        stack.push(24);
        stack.push(12);
        stack.push(2);
        stack.push(13);
        stack.push(14);
        stack.push(15);
        stack.push(16);
        stack.push(1);
        stack.push(15);
        stack.push(16);
        stack.push(18);
        System.out.println("stack.getMin() = " + stack.getMin());
        System.out.println("stack.getMin() = " + stack.getMin());

    }
}
