import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by worm2fed on 20.03.17.
 */
class BinaryHeap<T extends Comparable> {
    private List<T> list;

    // Create an empty Heap
    BinaryHeap() {
        this.list = new ArrayList<>();
    }

    // Create a Heap from an array
    BinaryHeap(T[] array) {
        this.list = new ArrayList<>(Arrays.asList(array));

        // Convert Tree to Heap
        for (int i = size() / 2; i >= 0; i--)
            sort(i);
    }

    // Sort Tree with root `index` sto be a Heap
    private void sort(int index) {
        // Indexes for left, right and largest childs
        int left, right, largest;

        // Go through tree
        for (;;) {
            left = index * 2 + 1;
            right = index * 2 + 2;
            largest = index;

            // If left child is exist and bigger then current
            if (left < size() && this.list.get(left).compareTo(this.list.get(largest)) == 1)
                largest = left;

            // If right child is exist and bigger then current
            if (right < size() && this.list.get(right).compareTo(this.list.get(largest)) == 1)
                largest = left;

            // If current is the largest
            if (largest == index)
                break;

            // Change current with largest
            T tmp = this.list.get(largest);
            this.list.set(largest, this.list.get(index));
            this.list.set(index, tmp);
            index = largest;
        }
    }

    // Get Heap size
    int size() {
        return this.list.size();
    }

    // Check is Heap empty
    boolean isEmpty() {
        return this.list.isEmpty();
    }

    // Add item to Heap
    void add(T value) {
        // Add element to the end of Heap
        this.list.add(value);
        // Current index
        int index = size() - 1;
        // Parent index
        int parent = (index - 1) / 2;

        // Change with parent until parent will be greater
        while (index > 0 && this.list.get(parent).compareTo(this.list.get(index)) == -1) {
            // Save parent value
            T tmp = this.list.get(parent);
            // Change values
            this.list.set(parent, this.list.get(index));
            this.list.set(index, tmp);

            // Change indexes
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    // Get max element
    T get() {
        // Max element is a root of Heap
        T result = this.list.get(0);
        // Move last element to root
        this.list.set(0, this.list.get(size() - 1));
        this.list.remove(size() - 1);

        // Convert Tree to Heap
        sort(0);

        return result;
    }

    // View max element
    T peek() {
        return this.list.get(0);
    }
}
