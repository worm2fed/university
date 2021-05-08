import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by worm2fed on 24.04.17.
 */
class Main {
    private static Deque<String> queue = new ArrayDeque<>();
    private static Graph graph = new Graph();

    public static void main(String[] args) throws IOException {
        String line;
        BufferedReader bf_reader = new BufferedReader(new FileReader("input.txt"));

        while ((line = bf_reader.readLine()) != null) {
            if (line.length() > 0) {
                String[] tmp = line.split("=");
                graph.put(tmp[0], tmp[1]);
            }
        }
        bf_reader.close();

        topologicalSort("R");
        calculate();
        BufferedWriter bf_writer = new BufferedWriter(new FileWriter("output.txt"));
        bf_writer.write(Integer.toString(graph.get("R").value));
        bf_writer.close();
    }

    private static boolean isInt(String s) {
        if (s.isEmpty())
            return false;

        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1)
                    return false;
                else
                    continue;
            }

            if (Character.digit(s.charAt(i), 10) < 0)
                return false;
        }

        return true;
    }

    private static void topologicalSort(String v) throws IOException {
        if (graph.get(v).color == 1) {
            BufferedWriter bf_writer = new BufferedWriter(new FileWriter("output.txt"));
            bf_writer.write("ERROR");
            bf_writer.close();
            System.exit(0);
        }

        graph.get(v).color = 1;

        if (isInt(graph.get(v).eq)) {
            queue.addFirst(v);
            graph.get(v).color = 2;

            return;
        }

        String[] t = graph.get(v).eq.split("");
        if (!isInt(t[0]))
            topologicalSort(t[0]);
        if (!isInt(t[2]))
            topologicalSort(t[2]);

        queue.addFirst(v);
        graph.get(v).color = 2;
    }

    private static int parse(String v) {
        if (isInt(v))
            return Integer.parseInt(v);
        else
            return graph.get(v).value;
    }

    private static void calculate() {
        while (!queue.isEmpty()) {
            String temp = queue.peekLast();
            queue.removeLast();
            if (Main.isInt(graph.get(temp).eq)) {
                graph.get(temp).value = Integer.parseInt(graph.get(temp).eq);
            } else {
                String t[] = graph.get(temp).eq.split("");
                if (t[1].equals("+")) {
                    graph.get(temp).value = parse(t[0]) + parse(t[2]);
                } else
                    graph.get(temp).value = parse(t[0]) * parse(t[2]);
            }
        }
    }
}
