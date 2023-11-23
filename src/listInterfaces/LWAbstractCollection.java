package listInterfaces;

import java.util.Iterator;

public abstract class LWAbstractCollection<T> implements LWCollection<T>{

    @Override
    public boolean contains(T elem) {
        for (T obj : this){
            if (obj.equals(elem)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean remove(T elem) {

        Iterator<T> iter = this.iterator();
        while (iter.hasNext()){
            if (iter.next().equals(elem)){
                iter.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Iterable<? extends T> col) {
        for (T obj : col){
            add(obj);
        }
        return true;
    }

    @Override
    public boolean removeAll(Iterable<? extends T> col) {
        for (T obj : col){
            remove(obj);
        }
        return true;
    }
}
