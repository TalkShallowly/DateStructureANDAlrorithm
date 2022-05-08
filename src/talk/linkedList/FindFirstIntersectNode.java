package talk.linkedList;

import java.util.HashSet;
import java.util.Set;

/**
 * 题目：
 *      给定两个可能有环也可能无环的单链表头节点head1和head2，请实现一个函数：
 *          如果两个链表相交，返回两个链表相交的第一个节点，如果不相交，返回null；‘
 *     要求：
 *          如果两个链表长度之和为N，时间复杂度达到 O(N)    额外空间复杂度O(1)
 *
 *     题解思路：
 *          1）： 容器法： HashSet()结构实现
 *          2）： 快慢指针法： 满足条件的情况： 两链表都为有环，或者无环
 *                      ①： 先判断每条链表是否有环或者无环，若有环， 返回第一个进环节点， 无环， 返回null
 *                             判断技巧：当快指针和满指针相遇时，将快指针重置为头节点开始，每次走一步，再次与慢指针相遇时的节点，即为入环节点
 *                      ②： 都为无环链表：将两链表按同样长度，依次比较地址值即可
 *                      ③： 有环链表：
 *                          A：两链表不相交，但都时有环链表
 *                          B: 两链表入环节点为同一个节点
 *                          C：两链表入环节点为不同节点  ---》 返回任意一个链表入环节点即可
 */
public class FindFirstIntersectNode {

    public static class Node{
        private int value;
        private Node next;
        public Node(int data){
            this.value = data;
            next = null;
        }
    }

    public Node intersectNode_Container(Node head1, Node head2){
        Set<Node> set = new HashSet<>();
        Node cur = head1;
        int len = 0;
        while (cur != null){
            if (set.contains(cur)){
                break;
            }
            len++;
            set.add(cur);
            cur = cur.next;
        }
        cur = head2;
        while (cur != null){
            if (set.contains(cur)){
                return cur;
            }
            cur = cur.next;
        }
        return null;

    }

    private Node isLoopList(Node head){
        if (head == null || head.next == null){
            return null;
        }
        Node fastNode = head;
        Node slowNode = head;
        while (fastNode != null){
            if (fastNode.next == null || fastNode.next.next == null){
                return null;
            }
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
            if (fastNode == slowNode){
                break;
            }
        }
        fastNode = head;
        if (fastNode == slowNode){
            return slowNode;
        }else {
            while (fastNode != slowNode){
                fastNode = fastNode.next;
                slowNode = slowNode.next;
            }
        }
        return slowNode;
    }

    public static void main(String[] args) {
        Node node = new Node(3);
        Node node1 = new Node(2);
        Node node2 = new Node(1);
        Node node3 = new Node(0);
        Node node4 = new Node(-1);
        node.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node3;
        FindFirstIntersectNode findFirstIntersectNode = new FindFirstIntersectNode();
        System.out.println(findFirstIntersectNode.isLoopList(node).value);
    }
}
