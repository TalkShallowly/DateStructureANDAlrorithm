package talk.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 点的描述
 */
public class Node {
//    变量类型需设置为public ，如为private 请提供set和get方法

    public int value;
    //入度： 有几条边可以进入此节点
    public int in;
    //出度： 有几条边可以从此节点出去
    public int out;
    //相邻节点（可到达的节点）
    public List<Node> nexts;
    //邻边：出度的边
    public List<Edge> edges;
    public Node(int _value){
        this.value =_value;
        this.in = 0;
        this.out = 0;
        this.nexts = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
}

