/**
 * Created by worm2fed on 20.03.17.
 */
class Event implements Comparable<Event> {
    private String name;
    private int time;
    private int cpu;
    private int priority;

    // Create an Event
    Event(String name, int time, int cpu, int priority) {
        this.name = name;
        this.time = time;
        this.cpu = cpu;
        this.priority = priority;
    }

    // Get application name
    String getName() {
        return this.name;
    }

    // Get time when event happens
    int getTime() {
        return this.time;
    }

    // Get amount of CPU time in ms
    int getCpu() {
        return this.cpu;
    }

    // Get priority
    int getPriority() {
        return this.priority;
    }

    @Override
    // Compare priority
    public int compareTo(Event o) {
        if (this.priority > o.priority)
            return 1;
        else if (this.priority < o.priority)
            return -1;
        else
            return 0;
    }
}
