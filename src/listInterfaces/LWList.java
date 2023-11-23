package listInterfaces;

public interface LWList<T> extends LWCollection<T>{

    T get(int index) throws IndexOutOfBoundsException;
    T set(int index, T elem) throws IndexOutOfBoundsException;
    void add(int index, T elem) throws IndexOutOfBoundsException;
    int indexOf(T elem);
    T remove(int index) throws IndexOutOfBoundsException;

}
