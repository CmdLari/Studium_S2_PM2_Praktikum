package listInterfaces;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LWLinkedList<T> extends LWAbstractList<T> {
    // head and tail are sentinal nodes (Wächterknoten)

    private final Node<T> head, tail;
    // size of the list

    @Override
    public Iterator<T> iterator() {

        return new Iterator<>() {

            private Node<T> pointer = head;

            @Override
            public boolean hasNext() {
                return pointer.succ != tail;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Object does not exist");
                } else {
                    pointer = pointer.succ;
                    return pointer.content;
                }
            }
        };
    }

    private static class Node<T> {

        private Node<T> succ;
        private Node<T> pred;
        private T content;

        public Node() {
            this(null);
        }

        public Node(T content) {
            this(content, null);
        }

        public Node(T content, Node<T> succ) {
            this(content, succ, null);
        }

        public Node(T content, Node<T> succ, Node<T> pred) {
            this.content = content;
            this.succ = succ;
            this.pred = pred;
        }

    }

    /**
     * Initializes an empty list.
     */
    public LWLinkedList() {

        this.tail = new Node<T>();
        this.head = new Node<T>(null, tail);
        tail.pred = head;
        counter = 0;

    }

    public void LWLinkedList(Iterable<? extends T> target) {
        this.addAll(target);
    }

    //////////////////////////////HOMEWORK//////////////////////////////////////////////////////////////////////////////

    /**
     * Constructor of a doubly linked list out of a given collection
     *
     * @param col collection given to be transformed into doubly linked list.
     * @throws NullPointerException if collection contains null elements
     */
    public LWLinkedList(Collection<T> col) {
        this();

        for (T next : col) {
            if (notNull(next)) {
                add(next);
            }
        }
    }

    /*
     * internal method: check whether a given index is valid for the doubly linked list
     * used by all methods which receive indices as arguments, except for adding operations
     * @param index input index to be checked for validity
     * @return true, if index is valid
     * @throws IndexOutOfBoundsException
     */
    private boolean isValidIndex(int index) throws IndexOutOfBoundsException {
        String msg = String.format("index %d is not a valid index for doubly linked list", index);
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException(msg);
        return true;
    }

    /*
    Variation of index check for adding since adding operations need
    to be able to work with indices that equal size().
     */
    private boolean validIndexForAdding(int index) throws IndexOutOfBoundsException {
        String msg = String.format("index %d is not a valid index to add at in doubly linked list", index);
        if (index < 0 || index > size()) throw new IndexOutOfBoundsException(msg);
        return true;
    }

    /*
     * internal method: check for null objects
     * @param o object to be tested
     * @return true if element is not null; else exception is thrown
     * @throws NullPointerException
     */
    private boolean notNull(T o) throws NullPointerException {
        String msg = "The collection given contains null elements";
        if (o == null) throw new NullPointerException(msg);
        return true;
    }

    /*
     * internal method: get node at a given index
     * @param index position of node to be returned
     * @return the node at the given index
     */
    private Node<T> getNode(int index) {
        // check for valid index not in getNode because both adding & other methods use it & need different index checks
        Node<T> theNodeAtIndex;
        if (index < size() / 2) {                   //split at half of size() so only half of the list must be traversed
            theNodeAtIndex = head;
            for (int i = 0; i <= index; i++) {
                theNodeAtIndex = theNodeAtIndex.succ;
            }
            return theNodeAtIndex;
        } else {
            theNodeAtIndex = tail;
            for (int i = size() - 1; i >= index; i--) {
                theNodeAtIndex = theNodeAtIndex.pred;
            }
            return theNodeAtIndex;
        }
    }

    /*
     * internal method: generalized adding method
     * @param index position at which object is to be added
     * @param o object to be added
     */
    private void generalAdd(int index, T o) {
        if (notNull(o) && validIndexForAdding(index)) {
            Node<T> newNode = new Node(o);
            Node<T> theNodeAtIndex = getNode(index);
            newNode.succ = theNodeAtIndex;
            newNode.pred = theNodeAtIndex.pred;
            theNodeAtIndex.pred = newNode;
            newNode.pred.succ = newNode;
            counter++;
        }
    }

    /**
     * This method adds an Object o at the end of a doubly linked list.
     *
     * @param o Object to be added
     * @return always true
     */
    public boolean add(T o) {
        generalAdd(size(), o);
        return true;
    }

    /**
     * This method inserts Object o at the given index.
     *
     * @param index position where o is added
     * @param o     Object to be added
     */
    public void add(int index, T o) {
        generalAdd(index, o);
    }

    /**
     * fills array with the content of the nodes
     *
     * @return array filled with node content
     */
    public T[] toArray() {

        T[] listArray = (T[]) (new Object[counter]);
        int counter = 0;

        for (LWLinkedList.Node<T> i = head.succ; i != tail; i = i.succ) {
            listArray[counter] = i.content;
            counter++;
        }

        return listArray;
    }


    /**
     * prints the doubly linked list
     *
     * @return String with the list
     */
    @Override
    public String toString() {

        return Arrays.toString(this.toArray());
    }

    /**
     * This method finds the position of the first occurrence of object o in the doubly linked list.
     *
     * @param o Object to be searched for
     * @return position o was first found at; -1 if not found
     */
    public int indexOf(T o) {
        int index = 0;

        for (Node<T> currentNode = head.succ; currentNode != tail; currentNode = currentNode.succ) {
            if (currentNode.content.equals(o)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * This method checks whether object o is in the doubly linked list.
     *
     * @param o Object to be searched for
     * @return true, if o is found in the doubly linked list; false, if not found
     */
    public boolean contains(T o) {
        return indexOf(o) >= 0;
    }


    /**
     * This method deletes the object found at the given index and returns the deleted objected.
     *
     * @param index position at which an object is to be deleted
     * @return the object deleted by the method
     */
    public T removeAt(int index) {
        isValidIndex(index);

        Node<T> beGone = getNode(index);
        beGone.pred.succ = beGone.succ;
        beGone.succ.pred = beGone.pred;
        counter--;
        return beGone.content;
    }

    /**
     * This method deletes the first occurrence of object o in the doubly linked list,
     * unless o is not found in it, which is indicated by the boolean return.
     *
     * @param o Object whose first occurrence is to be removed
     * @return false, if o is not found in the doubly linked list; true otherwise
     */
    public boolean remove(T o) {
        if (!contains(o)) return false;
        removeAt(indexOf(o));
        return true;
    }


    /**
     * This method reads the object at the given index.
     *
     * @param index position of object to be returned
     * @return Object at specified position
     */
    public T get(int index) {
        isValidIndex(index);
        return getNode(index).content;
    }

    /**
     * This method replaces the content found at the specified position by the object given.
     *
     * @param index specified position where the given Object is to be put
     * @param o     given Object to be put at the specified position
     */

    public T set(int index, T o) {
        T old = getNode(index).content;
        if (isValidIndex(index) && notNull(o)) {
            Node toBeSet = getNode(index);
            toBeSet.content = o;
        }
        return old;
    }


}
