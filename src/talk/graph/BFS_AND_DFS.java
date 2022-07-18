package talk.graph;

import java.util.*;

/**
 * 广度优先遍历 （Breadth-First-Search）
 *    解释：
 *       1：使用队列实现
 *       2：从源节点依次按照宽度进入队列，然后在弹出
 *       3：每一次弹出一个点，将该节点所有没有进过的队列的邻节点放入到队列中
 *       4：直到队列清空
 *  深度优先遍历 （Depth-First-Search）
 *     解释：
 *        1：使用Stack实现
 *        2：从源节点开始将界节点按照深度放入Stack中，然后弹出
 *        3：每一次弹出一个点，将源节点下一个没有进入Stack的邻接点压入Stack中
 *        4：直到Stack为空为止
 */
public class BFS_AND_DFS {

    public static void bfs(Node start){
        if (start == null){
            return;
        }
        Queue<Node> nodes = new LinkedList<>();
        Set<Node> set = new HashSet<>();
        nodes.add(start);
        set.add(start);
        while (!nodes.isEmpty()){
            Node poll = nodes.poll();
            System.out.print(poll.value + "\t");
            for (Node cur : poll.nexts){
                if (!set.contains(cur)){
                    nodes.add(cur);
                    set.add(cur);
                }
            }
        }
    }

    public static void dfs(Node start){
        if (start == null){
            return;
        }
        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();
        stack.push(start);
        set.add(start);
        while (!stack.isEmpty()){
            Node pop = stack.pop();
            System.out.print(pop.value + "\t");
            for (Node cur : pop.nexts){
                if (!set.contains(cur)){
                    stack.push(cur);
                    set.add(cur);
                }
            }
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        node1.nexts = Arrays.asList(node2,node5,node6);
        node2.nexts = Arrays.asList(node3);
        node3.nexts = Arrays.asList(node4);
        node4.nexts = Arrays.asList(node5);
        node5.nexts = Arrays.asList(node6);
        bfs(node1);
        System.out.println();
        System.out.println("======");
        dfs(node1);
    }
}
