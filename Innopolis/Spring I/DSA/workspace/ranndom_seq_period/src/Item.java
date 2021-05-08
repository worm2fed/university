/**
 * Created by worm2fed on 26.03.17.
 */
class Item implements Comparable<Item> {
    private int index;
    private double value;

    Item(int index, double value) {
        this.index = index;
        this.value = value;
    }

    // Get index
    public int getIndex() {
        return this.index;
    }

    // Get value
    public double getValue() {
        return this.value;
    }

    // Compare
    @Override
    public int compareTo(Item o) {
        /*
        if (abs(this.value - o.getValue()) > 1e-6)
            return 1;
        else if (abs(this.value - o.getValue()) < 1e-6)
            return -1;
        else
            return 0;
         */
        if (this.value > o.getValue())
            return 1;
        else if (this.value < o.getValue())
            return -1;
        else
            return 0;
    }
}
