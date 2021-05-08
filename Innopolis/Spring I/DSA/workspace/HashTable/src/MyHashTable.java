/**
 * Created by worm2fed on 22.02.17.
 */
class MyHashTable<K, V> {
    private K key;
    private V value;
    private MyHashTable<K, V> next;

    MyHashTable(K k, V v) {
        this.key = k;
        this.value = v;
        this.next = null;
    }

    // Get internal value
    private V getValue() {
        return this.value;
    }

    // Get next
    private MyHashTable<K, V> getNext() {
        return this.next;
    }

    // Get key
    K getKey() {
        return this.key;
    }

    // Get value
    V getValue(K key) {
        if (this.key.equals(key))
            return this.value;
        else if (this.next == null)
            return null;
        else
            return this.next.getValue(key);
    }

    // Assign new value
    void setValue(K key, V value) {
        if (this.key.equals(key))
            this.value = value;
        else {
            if (this.next == null)
                this.next = new MyHashTable<>(key, value);
            else
                this.next.setValue(key, value);
        }
    }

    // Delete value
    void remove(K key) {
        if (this.key.equals(key)) {
            if (this.next == null) {
                this.key = null;
                this.value = null;
            } else {
                this.key = next.getKey();
                this.value = next.getValue();
                this.next = next.getNext();
            }
        } else
            this.next.remove(key);
    }
}