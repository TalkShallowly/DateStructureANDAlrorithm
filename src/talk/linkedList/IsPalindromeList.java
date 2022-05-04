package talk.linkedList;

import java.util.PriorityQueue;
import java.util.Stack;

/**
 * 判断链表是否是回文结构
 */
public class IsPalindromeList<T> {

    public static class Node<T>{
        private T value;
        private Node<T> next;
        public Node(T val){
            this.value = val;
        }
    }

    /**
     * 第一种：使用stack结构实现
     * @param firstNode
     */
    public boolean isPalindromeList_stack(Node<T> firstNode){
        if (firstNode == null || firstNode.next == null){
            return true;
        }
        Node<T> node = firstNode;
        Stack<Node> stack = new Stack<>();
        while (node != null){
            stack.add(node);
            node = node.next;
        }
        node = firstNode;
        while (node != null){
            if (node.value == stack.pop().value){
                node = node.next;
            }else {
                return false;
            }
        }
        return true;
    }

    /**
     * 第二种： 使用快慢指针 -- 中点反转法则；
     * @param firstNode
     * @return
     */
    public boolean isPalindromeList_midpoint(Node<T> firstNode){
        if (firstNode == null || firstNode.next == null){
            return true;
        }
        Node<T> copyList = copyList(firstNode);
        Node<T> midpointNode = midpointNode(copyList);
        Node<T> midpointNode_head = midpointNode.next;
        midpointNode.next = null;
        Node<T> reverseHeadNode = reverseList(midpointNode_head);
        int nodeNumber = countNodeNumber(firstNode);
        if ((nodeNumber & 1) != 0) {
            Node<T> endNode = endNode(copyList);
            Node<T> midpointNode_endNode = endNode(midpointNode_head);
            midpointNode_endNode.next = new Node<>(endNode.value);
        }
        while (copyList != null){
            if (copyList.value != reverseHeadNode.value){
                return false;
            }
            copyList = copyList.next;
            reverseHeadNode = reverseHeadNode.next;
        }
        return true;
    }

    public Node<T> midpointNode(Node<T> firstNode){
        Node<T> quickPointer = firstNode;
        Node<T> slowPointer = firstNode;
        while (quickPointer.next != null){
            if (quickPointer.next.next == null){
                return slowPointer;
            }
            quickPointer = quickPointer.next.next;
            slowPointer = slowPointer.next;
        }
        return slowPointer;
    }


    private int countNodeNumber(Node<T> firstNode){
        if (firstNode == null){
            return 0;
        }
        Node<T> node = firstNode;
        int count = 1;
        while (node.next != null){
            count++;
            node = node.next;
        }
        return count;
    }


    public Node<Integer> generateNode(int maxLen,int maxValue){
        Node<Integer> firstNode = new Node<>((int)(Math.random() * maxValue) + 1);
        Node<Integer> node = firstNode;
        int len = (int)(Math.random() * maxLen) + 1;
        for (int i = 0; i < len; i++) {
            node.next = new Node<>((int)(Math.random() * maxValue) + 1);
            node = node.next;
        }
        Node<Integer> copyList = (Node<Integer>) copyList((Node<T>) firstNode);
        Node<Integer> reverseNode = null;
        if ((len & 1) == 1){
            reverseNode = (Node<Integer>) reverseList((Node<T>) copyList.next);
        }else {
            reverseNode = (Node<Integer>) reverseList((Node<T>) copyList);
        }
        return  (Node<Integer>) mergeNode((Node<T>) reverseNode, (Node<T>) firstNode);
    }

    private Node<T> copyList(Node<T> firstNode){
        Node<T> head = new Node<>(firstNode.value);
        Node<T> copyNode = head;
        Node<T> targetNode = firstNode;
        while (targetNode.next != null){
            copyNode.next = new Node<>(targetNode.next.value);
            targetNode = targetNode.next;
            copyNode = copyNode.next;
        }
        return head;
    }

    private Node<T> mergeNode(Node<T> node1,Node<T> node2){
        Node<T> endNode = endNode(node1);
        endNode.next = node2;
        return node1;
    }

    private Node<T> endNode(Node<T> firstNode){
        Node<T> node = firstNode;
        while (node.next != null){
            node = node.next;
        }
        return node;
    }

    /**
     * 反转链表
     * @param firstNode
     * @return
     */
    private Node<T> reverseList(Node<T> firstNode){
        Node<T> curNode = null;
        Node<T> preNode = null;
        while (firstNode != null){
            //第一步：记录头节点的下一个节点
            curNode = firstNode.next;
            //第二步：将头节点与下个节点打断
            firstNode.next = preNode;
            //第三步：记录当前头节点
            preNode = firstNode;
            //将头节点向下移动， 依次循环
            firstNode = curNode;
        }
        return preNode;
    }

    public static void main(String[] args) {
        IsPalindromeList<Integer> isPalindromeList = new IsPalindromeList<>();
        int maxLen = 1000;
        int maxValue = 10000;
        int testTime = 100000;
        System.out.println("start...");
        for (int i = 0; i < testTime; i++) {
            Node<Integer> integerNode = isPalindromeList.generateNode(maxLen, maxValue);
            boolean palindromeList_stack = isPalindromeList.isPalindromeList_stack(integerNode);
            boolean palindromeList_midpoint = isPalindromeList.isPalindromeList_midpoint(integerNode);
            if (palindromeList_stack != palindromeList_midpoint){
                System.out.println("palindromeList_stack : " + palindromeList_stack + " == palindromeList_midpoint : " + palindromeList_midpoint);
            }
        }
        System.out.println("end...");
    }
}
