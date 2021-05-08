/**
 * Created by worm2fed on 03.02.17.
 */

class LinkedStack <Item> {
    // Class to define node
    private class Node {
        Item item;
        Node next;
    }

    // The first node
    private Node head;
    // Stack size
    private int size;

    LinkedStack() {
        head = null;
        size = 0;
    }

    // Push data to stack
    void push(Item item) {
        Node oldHead = head;
        head = new Node();
        head.item = item;
        head.next = oldHead;
        size++;
    }

    // Get top stack value
    Item pop() {
        Item item = head.item;
        head = head.next;
        size--;

        return item;
    }

    // Get top stack value without pop
    Item peek() {
        return head.item;
    }

    // Check is stack empty
    boolean is_empty() {
        return (size == 0);
    }

    // Get stack size
    int size() {
        return size;
    }
}
