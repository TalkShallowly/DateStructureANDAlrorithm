package talk.graph;

public class Edge {
    //边的权重
    public final int weight;
    //起始节点
    public final Node from;
    //到达节点
    public final Node to;
    Edge(int _weight,Node _from,Node _to){
        this.weight = _weight;
        this.from = _from;
        this.to = _to;
    }
}
