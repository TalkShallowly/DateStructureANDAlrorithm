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

    /**
     * 使用容器法:
     * @param head1
     * @param head2
     * @return
     */
    public Node intersectNode_Container(Node head1, Node head2){
        if (head1 == null || head2 == null){
            return null;
        }
        Set<Node> set = new HashSet<>();
        Node cur = head1;
        while (cur != null){
            if (set.contains(cur)){
                break;
            }
            set.add(cur);
            cur = cur.next;
        }
        cur = head2;
        Set<Node> set2 = new HashSet<>();
        while (cur != null){
            if (set.contains(cur)){
                return cur;
            }
            if (set2.contains(cur)){
                break;
            }
            set2.add(cur);
            cur = cur.next;
        }
        return null;
    }

    /**
     * 使用快慢指针法
     * @param head1
     * @param head2
     * @return
     */
    public Node intersectNode_FastAndSlow(Node head1, Node head2){
        if (head1 == null || head2 == null){
            return null;
        }
        //第一步：判断两个链表是否为环状，如果一个为环状，一个不是，则不能相交
        Node loopList1 = isLoopList(head1);
        Node loopList2 = isLoopList(head2);
        //第一种情况： 两个链表均为无环链表
        if (loopList1 == null && loopList2 == null){
            return isLengthIntersect(head1,head2,null);
        }
        //第二种情况： 一个链表有环，一个链表无环
        if (loopList1 == null || loopList2 == null){
            return null;
        }
        //第三种： 两个链表都是有环链表
        if (loopList1 == loopList2) {
            //todo：按照无环链表处理
            return isLengthIntersect(head1,head2,loopList1);
        }
        //第四种：两个链表在同一个环状，但入环节点不一样
        Node curNode = loopList1;
        do {
            if (loopList1 == loopList2){
                return loopList1;
            }
            loopList1 = loopList1.next;
        }while (curNode != loopList1);
        //第五种：两个链表都是不相交的无环链表
        return null;
    }

    /**
     * 判断两个链表是否在有限长度内有相交；
     * @param head1
     * @param head2
     * @param end
     * @return
     */
    private Node isLengthIntersect(Node head1, Node head2, Node end){
        int lengthNode1 = lengthNode(head1,end);
        int lengthNode2 = lengthNode(head2,end);
        //将其中较长的链表和较短的链表记录下来
        Node curLenNode = lengthNode1 >= lengthNode2 ? head1 : head2;
        Node shortNode = curLenNode == head1 ? head2 : head1;
        int i = 0;
        while (shortNode != null){
            while (i++ < Math.abs(lengthNode1 - lengthNode2)){
                curLenNode = curLenNode.next;
            }
            if (curLenNode == shortNode){
                return curLenNode;
            }
            curLenNode = curLenNode.next;
            shortNode = shortNode.next;
        }
        return null;
    }

    /**
     * 返回链表的长度
     * @param head
     * @return
     */
    private int lengthNode(Node head,Node end){
        if (head == null){
            return 0;
        }
        Node node = head;
        int length = 0;
        while (node != end){
            length++;
            node = node.next;
        }
        return length;
    }

    /**
     * 此方法用于判读是否为环状链表，如果是返回入环链表的头节点，不是则返回null
     * @param head
     * @return
     */
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
        Node node = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
//        Node node5= new Node(5);
//        Node node6= new Node(6);
        node.next = node1;node1.next = node2;node2.next = node3;node3.next=node;

        Node copyNode = new Node(9);
        Node copyNode1 = new Node(8);
        Node copyNode2 = new Node(7);
        Node copyNode3 = new Node(6);
//        Node copyNode4 = new Node(5);
        copyNode.next = copyNode1;copyNode1.next = copyNode2;copyNode2.next=copyNode3;copyNode3.next=node3;

        Node testNode1 = new Node(15);
        Node testNode2 = new Node(14);
        Node testNode3 = new Node(13);
        Node testNode4 = new Node(12);
        testNode1.next = testNode2;testNode2.next = testNode3;testNode3.next=testNode4;testNode4.next=node2;

        FindFirstIntersectNode findFirstIntersectNode = new FindFirstIntersectNode();
        Node teNode1 = findFirstIntersectNode.intersectNode_Container(testNode4, copyNode);
        Node teNode2 = findFirstIntersectNode.intersectNode_FastAndSlow(testNode4, copyNode);
        if (teNode1 == teNode2){
            if (teNode1 != null){
                System.out.println("teNode1 " + teNode1.value);
            }
        }else {
            System.out.println("teNode1 == " + teNode1.value);
            System.out.println("teNode2 == " + teNode2.value);
        }
    }
}
