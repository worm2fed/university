import java.util.Arrays;

/**
 * Created by worm2fed on 05.02.17.
 */
class ArrayList<Item> {
    private int size;
    private Object[] data;

    // Default create with size = 1
    ArrayList() {
        this(1);
    }

    // Create with custom size
    ArrayList(int capacity) {
        data = new Object[capacity];
        size = 0;
    }

    // Check capacity and extend if need
    private void check_capacity() {
        if (size >= data.length) {
            // New capacity
            int new_capacity = (data.length * 3) / 2 + 1;
            // Extend array
            data = Arrays.copyOf(data, new_capacity);
        }
    }

    // Add element
    void add(Object item) {
        check_capacity();
        data[size++] = item;
    }

    // Get element
    Object get(int index) {
        return data[index];
    }

    // Get size
    int size() {
        return size;
    }
}
