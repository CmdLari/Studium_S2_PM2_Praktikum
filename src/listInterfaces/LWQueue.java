package listInterfaces;

import java.util.NoSuchElementException;

public interface LWQueue<T> extends LWCollection<T>{
    void enqueue(T elem);
    T dequeue() throws NoSuchElementException;
    boolean isEmpty();
    int size();
}
