/**
 * Created by worm2fed on 24.04.17.
 */
class Graph {
    Node[] graph;

    Graph() {
        graph = new Node[26];
        for (int i = 0; i < graph.length; i++)
            graph[i] = new Node();
    }

    void put(String key, String value) {
        for (Node aGraph : graph) {
            if (aGraph.name.isEmpty()) {
                aGraph.name = key;
                aGraph.eq = value;
                break;
            }
        }
    }

    Node get(String key) {
        for (Node aGraph : graph) {
            if (aGraph.name.equals(key))
                return aGraph;
        }
        return null;
    }
}
