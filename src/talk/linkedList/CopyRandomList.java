package talk.linkedList;

import java.sql.SQLOutput;
import java.util.HashMap;

public class CopyRandomList {

    public static class Node{
        private int val;
        private Node next;
        private Node random;
        public Node(int value){
            this.val = value;
            next = null;
            random = null;
        }

    }

    public Node copyRandomList(Node headNode){
        HashMap<Node,Node> map = new HashMap<>();
        Node curNode = headNode;
        while (curNode!= null){
            map.put(curNode,new Node(curNode.val));
            curNode = curNode.next;
        }
        curNode = headNode;
        while (curNode != null){
            map.get(curNode).next = map.get(curNode.next);
            map.get(curNode).random = map.get(curNode.random);
            curNode = curNode.next;
        }
        return map.get(headNode);
    }

    public Node copyRandomList2(Node headNode){
        if (headNode == null){
            return null;
        }
        Node curNode = headNode;
        Node next= null;
        //此处循环用于连接新节点  1->1'->2->2'->3->3'
        while (curNode != null){
            next = curNode.next;
            curNode.next = new Node(curNode.val);
            curNode.next.next = next;
            curNode = next;
        }
        curNode = headNode;
        Node copyNode = null;
        //此处循环处理新节点的random节点，
        while (curNode != null){
            next = curNode.next.next;
            copyNode = curNode.next;
            //copyNode都是拷贝的新节点，它的random节点 == 它的源节点的random节点的拷贝节点（源节点的random节点的下一节点）
            copyNode.random = curNode.random == null ? null : curNode.random.next;
            curNode = next;
        }
        curNode = headNode;
        //此处用于标记拷贝新节点的头节点
        Node res = headNode.next;
        //此循环用于解决新节点的next节点（同时需要将源节点与新节点进行分离）
        while (curNode != null){
            next = curNode.next.next;
            copyNode = curNode.next;
            curNode.next = next;
            copyNode.next = next == null ? null : next.next;
            curNode = next;
        }
        return res;
    }



    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(3);
        Node node3 = new Node(5);
        Node node4 = new Node(7);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node1.random = node3;
        node2.random = node4;
        node3.random = node2;
        node4.random = node1;
        CopyRandomList copyRandomList = new CopyRandomList();
        copyRandomList.copyRandomList(node1);

    }
}
