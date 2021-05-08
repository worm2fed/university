import javafx.util.Pair;

import java.util.Collection;

/**
 * Created by worm2fed on 20.02.17.
 */
class YourMap<K, V> {
    private transient MyHashTable[] hashTable;
    private int size = 0;
    private int counter = 0;

    YourMap() {
        this.hashTable = new MyHashTable[256];
    }

    // Get array index
    private int getIndex(K key) {
        return Math.abs((key.hashCode() % this.hashTable.length));
    }

    // Clear map
    void clear() {
        this.hashTable = new MyHashTable[256];
        this.size = 0;
    }

    // Is empty
    boolean isEmpty() {
        return this.size == 0;
    }

    // Size of map
    int size() {
        return this.size;
    }

    // Put value to map
    void put(K key, V value) {
        if (hashTable[getIndex(key)] == null || hashTable[getIndex(key)].getKey() == null)
            hashTable[getIndex(key)] = new MyHashTable<>(key, value);
        else
            hashTable[getIndex(key)].setValue(key, value);

        this.size++;
    }

    // Get value from map
    V get(K key) {
        if (hashTable[getIndex(key)] == null || hashTable[getIndex(key)].getKey() == null)
            return null;
        else
            return (V) hashTable[getIndex(key)].getValue(key);
    }

    // Remove value from map
    void remove(K key) {
        if (hashTable[getIndex(key)] != null || hashTable[getIndex(key)].getKey() != null) {
            hashTable[getIndex(key)].remove(key);
            this.size--;
        }
    }

//    // Set counter to 0
//    void clearCounter() {
//        this.counter = 0;
//    }
//
//    // Return next not null value
//    Pair<K, V> getNext() {
//        if (this.counter == this.size) {
//            clearCounter();
//
//            return null;
//        } else {
//            while (this.hashTable[counter] == null)
//                this.counter++;
//
//            return new Pair<K, V>(this.hashTable.);
//        }
//    }
}
