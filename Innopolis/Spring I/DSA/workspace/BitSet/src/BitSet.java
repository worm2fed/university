/**
 * Created by worm2fed on 03.02.17.
 */
public class BitSet {
    private long[] data;
    private int size;

    // Create BitSet with default length
    BitSet() {
        size = 1;
        data = new long[1];
    }

    // Create BitSet with custom length
    BitSet(int array_size) {
        size = array_size;
        data = new long[array_size];
    }

    // Check is value exist
    public boolean exist(int num) {
        return ((data[index(num)] & 1 << pos(num)) == 0) ? false : true;
    }

    // Add value
    public void add(int num) {
        while (size < index(num))
            raise_size();

        data[index(num)] = data[index(num)] | 1 << pos(num);
    }

    // Remove value
    public void remove(int num) {
        data[index(num)] = data[index(num)] & ~(1 << pos(num));
    }

    // Get array index
    private int index(int num) {
        return num / 64;
    }

    // Get number position
    private int pos(int num) {
        return num % 64;
    }

    // Raise array size
    private void raise_size() {
        // Copy values to temp variable
        long[] tmp_data = new long[size];
        for (int i = 0; i < size; i++)
            tmp_data[i] = data[i];

        // Raise array size
        data = new long[size * 2];
        for (int i = 0; i < size; i++)
            data[i] = tmp_data[i];

        size = size * 2;
    }
}
