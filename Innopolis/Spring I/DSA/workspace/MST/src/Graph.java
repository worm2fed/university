import java.util.ArrayList;

/**
 * Created by worm2fed on 24.04.17.
 */
public class Graph {
    private ArrayList<String> vertices = new ArrayList<>();
    private ArrayList<Vertex>[] graph;

    public Graph(int n) {
        graph = new ArrayList[n];

        for (int i = 0; i < graph.length; i++)
            graph[i] = new ArrayList<>();
    }

    public void addVertex(Vertex item) {
        vertices.add(item.name);
    }

    public void addEdge(Vertex source, Vertex target) {
        graph[vertices.indexOf(source.name)].add(target);
        Vertex temp = new Vertex (source.name, target.dist, target.time, target.cost);
        graph[vertices.indexOf(target.name)].add(temp);
    }

    public int getIndex(String name) {
        return(vertices.indexOf(name));
    }

    public int getNum(int index) {
        return(graph[index].size());
    }

    public ArrayList<Vertex> get(int index) {
        return graph[index];
    }

    public void makeMST() {
        int[] span = new int[vertices.size()];
        for (int i = 0; i < span.length; i++) {
            span[i] = i;
        }
        boolean isReady = false;
        ArrayList[] newGraph = new ArrayList[vertices.size()];

        for (int i = 0; i < newGraph.length; i++)
            newGraph[i] = new ArrayList<>();

        while (!isReady) {
            double min = Double.MAX_VALUE;
            int m = 0, n = 0;

            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph[i].size(); j++) {
                    if (graph[i].get(j).dist < min && span[i] != span[vertices.indexOf(graph[i].get(j).name)]) {
                        m = i;
                        n = j;
                        min = graph[i].get(j).dist;
                    }
                }
            }
            Vertex temp = graph[m].get(n);
            Vertex toAdd = new Vertex(vertices.get(m), temp.dist, temp.time, temp.cost);
            newGraph[m].add(temp);
            newGraph[vertices.indexOf(graph[m].get(n).name)].add(toAdd);
            graph[m].remove(temp);
            for (int i = 0; i < graph[vertices.indexOf(temp.name)].size(); i++) {
                if (graph[vertices.indexOf(temp.name)].get(i).name.equals(toAdd.name)) {
                    graph[vertices.indexOf(temp.name)].remove(i);
                }
            }
            int j = span[vertices.indexOf(temp.name)];
            for (int i = 0; i < span.length; i++) {
                if (span[i] == j) {
                    span[i] = span[m];
                }
            }
            isReady = true;
            for (int i = 0; i < span.length-1; i++) {
                if (span[i+1] != span[i]) {
                    isReady = false;
                    break;
                }
            }
        }
        graph = newGraph;
    }
}
