package talk.linkedList;

/**
 * 给定一个链表和一个数，并根据该数将链表划分为左边小，中间相等，右边大的形式
 *      解决方案：
 *          1. 使用额外三个链表，将其分别承载大中小的数，之后在进行链表的合并
 *          2. 将链表放入数组中，在数组上做partition过程（快排核心）
 */
public class SmallerEqualBigger {

    public static class Node{
        private int value;
        private Node next;
        public Node(int val){
            this.value = val;
            next = null;
        }
    }

    public Node mergeList(Node head,int pivot){
        if (head == null){
            return null;
        }
        Node smHead = null;
        Node smEnd = null;
        Node midHead = null;
        Node midEnd = null;
        Node bigHead = null;
        Node bigEnd = null;
        Node next = null;
        while (head != null){
            next = head.next;
            head.next = null;
            if (head.value < pivot){
                if (smHead == null){
                    smHead = head;
                    smEnd = smHead;
                }else {
                    smEnd.next = head;
                    smEnd = smEnd.next;
                }
            }else if (head.value  == pivot){
                if (midHead == null){
                    midHead = head;
                    midEnd = midHead;
                }else {
                    midEnd.next = head;
                    midEnd = midEnd.next;
                }
            }else {
                if (bigHead == null){
                    bigHead = head;
                    bigEnd = bigHead;
                }else {
                    bigEnd.next = head;
                    bigEnd = bigEnd.next;
                }
            }
            head = next;
        }

        if (smEnd != null){
            //有小于区域，无论等于区域有或者无，都可以连接
            smEnd.next = midHead;
            //判断等于区域的有无，决定连接下一区域的尾部节点
            midEnd = midEnd == null ? smEnd : midEnd;
        }
        if (midEnd != null){
            midEnd.next = bigHead;
        }
        return smHead == null ? (bigHead == null ? midHead : bigHead) : smHead;
//          Node res = null;
//        if (smEnd == null){
//            if (midEnd == null){
//                res = bigHead;
//            }else {
//                midEnd.next = bigEnd == null ? null : bigHead;
//                res = midHead;
//            }
//        }else {
//            if (midEnd != null){
//                midEnd.next = bigEnd == null ? null : bigHead;
//                smEnd.next = midHead;
//            }else {
//                smEnd.next = bigEnd == null ? null : bigHead;
//            }
//            res = smHead;
//        }
//        return res;
    }

    public Node partitionList(Node head,int pivot){
        if (head == null || head.next == null){
            return head;
        }
        Node[] nodes = partition(head, pivot);
        int i = 0;
        while (++i != nodes.length){
            nodes[i - 1].next = nodes[i];
        }
        nodes[i - 1].next = null;
        return nodes[0];
    }

    private Node[] partition(Node head,int pivot){
        int listLength = listLength(head);
        Node[] nodes = new Node[listLength];
        Node cur = head;
        int i = 0;
        while (cur != null){
            nodes[i++] = cur;
            cur = cur.next;
        }
        int index = 0;
        int leftPointer = index - 1;
        int rightPointer = listLength;
        while (index < rightPointer){
            if (nodes[index].value < pivot){
                swap(nodes,index++,++leftPointer);
            }else if (nodes[index].value == pivot){
                index++;
            }else {
                swap(nodes,index,--rightPointer);
            }
        }
        return nodes;
    }

    private void swap(Node[] nodes,int i,int j){
        Node temp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = temp;
    }

    private int listLength(Node head){
        Node cur = head;
        int len = 0;
        while (cur != null){
            ++len;
            cur = cur.next;
        }
        return len;
    }

    public Node generateRandomNode(int maxLen,int maxValue){
        Node head = new Node(((int)(Math.random() * maxValue) + 1) - (int)(Math.random() * maxValue));
        Node cur = head;
        int len = (int)(Math.random() * maxLen) + 1;
        while (--len > 0){
            cur.next = new Node(((int)(Math.random() * maxValue) + 1) - (int)(Math.random() * maxValue));
            cur = cur.next;
        }
        return head;
    }

    public static void main(String[] args) {
        SmallerEqualBigger smallerEqualBigger = new SmallerEqualBigger();
        int maxLen = 10;
        int maxVal = 10;
        Node head = smallerEqualBigger.generateRandomNode(maxLen, maxVal);
        printNode(head);
        Node mergeList = smallerEqualBigger.mergeList(head, 1);
        printNode(mergeList);
        System.out.println("========");

        Node head1 = smallerEqualBigger.generateRandomNode(maxLen, maxVal);
        printNode(head1);
        Node partition = smallerEqualBigger.partitionList(head1,1);
        printNode(partition);

    }

    public static void printNode(Node head){
        Node cur = head;
        int i = 0;
        while (cur != null){
            i++;
            System.out.print(cur.value + " -> ");
            cur = cur.next;
            if (i > 20){
                return;
            }
        }
        System.out.println("\n");
    }


}
