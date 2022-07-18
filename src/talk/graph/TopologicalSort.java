package talk.graph;

import java.util.*;

/**
 * 题目描述：
 *      给定一个有向图，图节点的拓扑排序定义如下:
 *         对于图中的每一条有向边 A -> B , 在拓扑排序中A一定在B之前.   
 *         拓扑排序中的第一个节点可以是图中的任何一个没有其他节点指向它的节点.
 *         针对给定的有向图找到任意一种拓扑排序的顺序
 *  解释：
 *      图中的节点顺序先后顺序的依赖， A -> B ，其中 B 节点在必须在 A 节点创建之后才可以出现
 *          （注： 不能循环依赖）
 *   解决方案：
 *      1. 点位排序：
 *           1： 找出图中所有入度为 0 的点位输出
 *           2： 将所有入度为 0 的点在图中删除，继续找入度为 0 的点为输出， 周而复始
 *           3： 图的所有点被删除后依次输出的顺序即为拓扑排序
 *          要求： 有向图：其中没有环
 *          应用： 事件安排，编译顺序
 *      2: BFS排序：
 *      3: DFS排序：
 */
public class TopologicalSort {

    public class DirectedGraphNode{
        int label;
        List<DirectedGraphNode> neighbors;
        DirectedGraphNode(int x){
            this.label = x;
            neighbors = new ArrayList<>();
        }
    }

    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph){
        //定义一个入度索表 （先将其 list 最外层的数据全部放入其中，并假设为开始节点，既入度为 0 ）
        HashMap<DirectedGraphNode,Integer> indegreeMap = new HashMap<>();
        for (DirectedGraphNode cur : graph){
            indegreeMap.put(cur,0);
        }
        //在依次遍历去开始节点下的每一条数据 ， 同时更新索引表 （得到所有的点位信息索引表）
        for (DirectedGraphNode cur : graph){
            for (DirectedGraphNode subCur : cur.neighbors){
                indegreeMap.put(subCur,indegreeMap.get(cur) + 1);
            }
        }
        //准备一个队列，用于对做入度为 0 的节点存储
        Queue<DirectedGraphNode> zeroQueue = new LinkedList<>();
        for (DirectedGraphNode cur : indegreeMap.keySet()){
            if (indegreeMap.get(cur) == 0){
                zeroQueue.add(cur);
            }
        }
        //返回排序数据
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        while (!zeroQueue.isEmpty()){
            DirectedGraphNode poll = zeroQueue.poll();
            ans.add(poll);
            for (DirectedGraphNode subCur : poll.neighbors){
                //依次遍历： 将弹出点的符合条件的邻接点的点位的入度 设置为 0
                indegreeMap.put(subCur,indegreeMap.get(subCur) - 1);
                if (indegreeMap.get(subCur) == 0){
                    zeroQueue.add(subCur);
                }
            }
        }
        return ans;
    }
}
