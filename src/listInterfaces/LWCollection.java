package listInterfaces;

public interface LWCollection<T> extends Iterable<T> {

    boolean add(T elem);
    boolean contains(T elem);
    int size();
    boolean isEmpty();
    boolean remove(T elem);
    boolean addAll(Iterable<? extends T> col);
    boolean removeAll(Iterable<? extends T> col);

}
