package talk.graph;

import java.util.List;

/**
 * 一个图是由节点和边组成
 */
public class Graph {

    public final List<Node> nodes;
    public final List<Edge> edges;

    Graph(List<Node> _nodes,List<Edge> _edges){
        this.nodes = _nodes;
        this.edges = _edges;
    }
}
