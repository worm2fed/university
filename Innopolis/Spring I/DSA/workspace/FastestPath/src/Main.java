import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by worm2fed on 24.04.17.
 */
public class Main {
    static Graph graph;
    static int vertexNum;

    public static void main(String[] args) throws IOException {
        String line;
        StringBuilder result = new StringBuilder();

        BufferedReader bf_reader = new BufferedReader(new FileReader("russia.txt"));
        String temp[] = bf_reader.readLine().split("\\s");
        vertexNum = temp.length;
        graph = new Graph(vertexNum);
        for (String aTemp : temp) graph.addVertex(new Vertex(aTemp));

        temp = bf_reader.readLine().split("\\s|:");
        for (int i = 0; i < temp.length; i += 5)
            graph.addEdge(new Vertex(temp[i]), new Vertex(temp[i+1], Double.parseDouble(temp[i+2]),
                    Double.parseDouble(temp[i+3]), Double.parseDouble(temp[i+4])));

        bf_reader.close();

        bf_reader = new BufferedReader(new FileReader("input.txt"));
        while ((line = bf_reader.readLine()) != null) {
            temp = line.split("\\s");
            Vertex source = new Vertex(temp[0]);
            Vertex target = new Vertex(temp[1]);
            double mass = Double.parseDouble(temp[2]);
            result.append(dijkstra(source, target, mass));
            result.append("\n");
        }
        bf_reader.close();

        BufferedWriter bf_writer = new BufferedWriter(new FileWriter("output.txt"));
        result.deleteCharAt(result.length() - 1);
        bf_writer.write(result.toString());
        bf_writer.close();
    }

    static String dijkstra(Vertex source, Vertex target, double mass) throws IOException {
        boolean used[] = new boolean[vertexNum];
        Arrays.fill(used, false);
        double time[] = new double[vertexNum];
        Arrays.fill(time, Double.MAX_VALUE);
        double cost[] = new double[vertexNum];
        time[graph.getIndex(source.name)] = 0;

        for (int i = 0; i < vertexNum; i++) {
            int v = -1;
            double timeV = Double.MAX_VALUE;
            for (int j = 0; j < vertexNum; j++) {
                if (used[j])  {
                    continue;
                }
                if (timeV < time[j]) {
                    continue;
                }
                v = j;
                timeV = time[j];
            }
            for (int j = 0; j < graph.getNum(v); j++) {
                int u = graph.getIndex(graph.get(v).get(j).name);
                double timeU = graph.get(v).get(j).time;
                double costV = graph.get(v).get(j).cost;
                if (time[v] + timeU < time[u]) {
                    time[u] = time[v] + timeU;
                    cost[u] = cost[v] + costV * mass;
                }
            }
            used[v] = true;
        }
        int index = graph.getIndex(target.name);

        return source.name + " " +
                target.name + " " +
                String.format(Locale.ENGLISH, "%(.1f", mass) + " " +
                String.format(Locale.ENGLISH, "%(.1f", time[index]) + " " +
                String.format(Locale.ENGLISH, "%(.1f", cost[index]);
    }
}
