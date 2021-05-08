/**
 * Created by worm2fed on 24.04.17.
 */
class Vertex {
    final String name;
    final double time;
    final double dist;
    final double cost;

    public Vertex(String name, double dist, double time, double cost) {
        this.name = name;
        this.dist = dist;
        this.time = time;
        this.cost = cost;
    }

    public Vertex(String name) {
        this(name, -1, -1, -1);
    }
}
