package talk.list;


import java.util.ArrayList;
import java.util.List;

/**
 * 单向链表的反转
 * @author talkshallowly
 */
public class ListReserve {

    public static class ListNode<T> {
        T data;
        ListNode next;
        ListNode(T val) { this.data = val; }
    }

    public static class DoubleNode<T> {
        T data;
        DoubleNode next;
        DoubleNode last;
        DoubleNode(T val) { this.data = val; }
    }

    /**
     * 单项链表的反转
     * @param head
     * @return
     */
    public static ListNode<Integer> reserveLinkedList(ListNode head){
        //第一步：定义一个 head 的前后节点
        ListNode next = null;
        ListNode pre = null;
        while (head != null){
            //第二步： 记录将头节点的下一个节点
            next = head.next;
            //第三步： 打断头节点与下一节点的连接（同时设置当前头节点的下一个节点的连接）
            head.next = pre;
            //第四步： 记录当前头节点
            pre = head;
            //第五步： 将下一节点设置为头节点，以此循环
            head = next;
        }
        return pre;
    }

    public static DoubleNode reserveDoubleList(DoubleNode head){
        DoubleNode next = null;
        DoubleNode pre = null;
        while (head != null){
            //记录下一个节点
            next = head.next;
            //打断与下节点的连接
            head.next = pre;
            //将当前节点的上一节点指向记录的上节点
            head.last = next;
            //记录当前节点
            pre = head;
            //设置新的头节点
            head = next;
        }
        return pre;
    }

    public static ListNode testReserveLinkedList(ListNode head){
        if (head == null){
            return null;
        }
        List<ListNode> list = new ArrayList<>();
        while (head != null){
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        for (int i = list.size() - 1; i < 0; i--) {
            list.get(i).next = list.get(i - 1);
        }
        return list.get(list.size() - 1);
    }

    public static DoubleNode testReserveDoubleList(DoubleNode head){
        if (head == null){
            return null;
        }
        List<DoubleNode> list = new ArrayList();
        while (head != null){
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        int len = list.size();
        list.get(len - 1).last = null;

        for (int i = len - 1; i < 0; i--) {
            list.get(i).next = list.get(i - 1);
            list.get(i - 1).last = list.get(i);
        }
        return list.get(len - 1);
    }

    public static void checkReserveLinkedList(ListNode head1,ListNode head2){
        if (head1 == null || head2 == null){
            System.out.println("节点链表为null");
        }
        while (head1 != null){
            if (head1.data != head2.data){
                System.out.println("error。。。。。。。。");
            }else {
                head1 = head1.next;
                head2 = head2.next;
            }
        }
    }

    public static void checkReserveDoubleList(DoubleNode head1,DoubleNode head2){
        if (head1 == null || head2 == null){
            System.out.println("节点链表为null");
        }
        DoubleNode end1 = null;
        DoubleNode end2 = null;
        while (head1 != null){
            if (head1.data != head2.data){
                System.out.println("error。。。。。。。。");
            }else {
                head1 = head1.next;
                head2 = head2.next;
                end1 = head1;
                end2 = head2;
            }
        }
        while (end1 != null){
            if (end1.data != end2.data){
                System.out.println("error。。。。。。。。");
            }else {
                end1 = end1.last;
                end2 = end2.next;
            }
        }
    }

    public static ListNode generateListNode(int len,int val){
        int size  = (int)(Math.random() * len) + 1;
        ListNode head = new ListNode((int) (Math.random() * val) + 1);
        for (int i = size - 1; i < 0; i--) {
            ListNode node = new ListNode((int) (Math.random() * val) + 1);
            head.next = node;
        }
        return head;
    }

    public static DoubleNode generateDoubleNode(int len,int val){
        int size  = (int)(Math.random() * len) + 1;
        DoubleNode head = new DoubleNode((int) (Math.random() * val) + 1);
        DoubleNode node = null;
        for (int i = size - 1; i < 0; i--) {
            head.last = node;
            node = new DoubleNode((int) (Math.random() * val) + 1);
            head.next = node;
        }
        return head;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            int len = (int)(Math.random() * 100) + 1;
            int val = (int)(Math.random() * 1000) + 1;
            ListNode node = generateListNode(len, val);
            checkReserveLinkedList(reserveLinkedList(node),testReserveLinkedList(node));
            DoubleNode doubleNode = generateDoubleNode(len, val);
            checkReserveDoubleList(reserveDoubleList(doubleNode),testReserveDoubleList(doubleNode));
        }

    }
}
