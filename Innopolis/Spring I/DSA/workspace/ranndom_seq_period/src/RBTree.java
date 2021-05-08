/**
 * Created by worm2fed on 22.03.17.
 */
public class RBTree {
    private static class RBNode {
        // Constructors
        RBNode(Comparable theElement) {
            this(theElement, null, null);
        }

        RBNode(Comparable element, RBNode left, RBNode right) {
            this.element  = element;
            this.left     = left;
            this.right    = right;
            this.color    = RBTree.BLACK;
        }

        // The data in the node
        Comparable  element;
        // Left child
        RBNode      left;
        // Right child
        RBNode      right;
        // Color
        int         color;
    }

    private         RBNode header;
    private static  RBNode nil;
    // Static initializer for nil
    static {
        nil = new RBNode( null );
        nil.left = nil.right = nil;
    }

    private static final int BLACK = 1;
    private static final int RED   = 0;

    // Size
    private int size;

    // Used in insert routine and its helpers
    private static RBNode current;
    private static RBNode parent;
    private static RBNode grand;
    private static RBNode great;

    public RBTree() {
        header      = new RBNode(null);
        header.left = header.right = nil;
        size = 1;
    }

    // Get size
    public int getSize() {
        return size;
    }

    // Compare elements
    private final int compare(Comparable item, RBNode t) {
        if (t == header)
            return 1;
        else
            return item.compareTo(t.element);
    }

    // Insert into tree
    public void insert(Comparable item) {
        current = parent = grand = header;
        nil.element = item;

        while (compare(item, current) != 0) {
            great = grand; grand = parent; parent = current;
            current = compare(item, current) < 0 ? current.left : current.right;

            // Check if two red children; fix if so
            if (current.left.color == RED && current.right.color == RED)
                reorient(item);
        }

        // Insertion fails if already present
        if (current != nil)
            throw new DuplicateItemException(item.toString());
        current = new RBNode( item, nil, nil );

        // Attach to parent
        if (compare(item, parent) < 0)
            parent.left = current;
        else
            parent.right = current;
        reorient(item);

        this.size++;
    }

    // Remove from the tree.
    public void remove(Comparable x) {
        throw new UnsupportedOperationException( );
    }

    // Find an item in the tree.
    public Comparable find(Comparable x) {
        nil.element = x;
        current = header.right;

        for(;;) {
            if (x.compareTo(current.element) < 0)
                current = current.left;
            else if (x.compareTo(current.element) > 0)
                current = current.right;
            else if (current != nil)
                return current.element;
            else
                return null;
        }
    }

    // Make the tree logically empty.
    public void clear() {
        header.right = nil;
    }

    // Check is the tree is logically empty.
    public boolean isEmpty() {
        return header.right == nil;
    }

    // Internal routine that is called during an insertion
    // if a node has two red children. Performs flip and rotations.
    private void reorient(Comparable item ) {
        // Do the color flip
        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;

        // Have to rotate
        if (parent.color == RED) {
            grand.color = RED;
            // Start dbl rotate
            if ((compare(item, grand) < 0 ) != (compare(item, parent) < 0))
                parent = rotate(item, grand);
            current = rotate(item, great);
            current.color = BLACK;
        }
        // Make root black
        header.right.color = BLACK;
    }

    // Internal routine that performs a single or double rotation.
    // Because the result is attached to the parent, there are four cases.
    private RBNode rotate(Comparable item, RBNode parent) {
        if (compare(item, parent) < 0)
            return parent.left = compare(item, parent.left) < 0 ?
                    rotateWithLeftChild(parent.left) :  // LL
                    rotateWithRightChild(parent.left);  // LR
        else
            return parent.right = compare(item, parent.right) < 0 ?
                    rotateWithLeftChild(parent.right) :  // RL
                    rotateWithRightChild(parent.right);  // RR
    }

    // Rotate binary tree node with left child.
    private static RBNode rotateWithLeftChild(RBNode item) {
        RBNode tmp = item.left;
        item.left = tmp.right;
        tmp.right = item;

        return tmp;
    }

    // Rotate binary tree node with right child.
    private static RBNode rotateWithRightChild(RBNode item) {
        RBNode tmp = item.right;
        item.right = tmp.left;
        tmp.left = item;

        return tmp;
    }
}
