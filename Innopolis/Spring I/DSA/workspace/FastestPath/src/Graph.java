import java.util.ArrayList;

/**
 * Created by worm2fed on 24.04.17.
 */
public class Graph {
    private ArrayList<String> vertices = new ArrayList<>();
    private ArrayList<Vertex>[] graph;

    Graph(int n) {
        graph = new ArrayList[n];

        for (int i = 0; i < graph.length; i++)
            graph[i] = new ArrayList<>();
    }

    void addVertex(Vertex item) {
        vertices.add(item.name);
    }

    void addEdge(Vertex source, Vertex target) {
        graph[vertices.indexOf(source.name)].add(target);
        Vertex temp = new Vertex (source.name, target.dist, target.time, target.cost);
        graph[vertices.indexOf(target.name)].add(temp);
    }

    int getIndex(String name) {
        return(vertices.indexOf(name));
    }

    int getNum(int index) {
        return(graph[index].size());
    }

    ArrayList<Vertex> get(int index) {
        return graph[index];
    }
}