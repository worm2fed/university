/**
 * Created by worm2fed on 03.02.17.
 */

class LinkedQueue<Item> {
    // Class to define node
    private class Node {
        Item item;
        Node next;
    }

    //begin and end nodes
    private Node front, rear;
    private int size;

    LinkedQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    // Remove item
    Item poll() {
        Item item = front.item;
        front = front.next;

        if (is_empty())
            rear = null;

        size--;

        return item;
    }

    // Add item
    void add(Item item) {
        Node oldRear = rear;
        rear = new Node();
        rear.item = item;
        rear.next = null;

        if (is_empty())
            front = rear;
        else
            oldRear.next = rear;

        size++;
    }

    // Get first element without removing
    Item peek() {
        return front.item;
    }

    // Check is empty
    boolean is_empty() {
        return (size == 0);
    }

    // Get queue size
    int size() {
        return size;
    }
}
