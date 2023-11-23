package listInterfaces;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LWArrayList<T> extends LWAbstractList<T> {

    private T[] ary;

    public LWArrayList() {
        this.ary = (T[]) new Object[10];
    }

    public LWArrayList(Iterable<? extends T> toClone) {

        for (T obj : toClone) {
            this.add(obj);
        }
    }

    @Override
    public boolean add(T elem) {
        add(this.size(), elem);
        return true;
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {

        throwStuff(index);

        T old = ary[index];
        counter--;
        for (int i = index; i < counter; i++) {
            ary[i] = ary[i + 1];
        }
        ary[counter + 1] = null;

        if (counter <= ary.length / 4) {
            ary = resizeArray(ary.length / 2);
        }
        return old;
    }

    @Override
    public T set(int index, T elem) throws IndexOutOfBoundsException {

        throwStuff(index);

        T old = this.get(index);
        ary[index] = elem;
        return old;
    }

    @Override
    public void add(int index, T elem) throws IndexOutOfBoundsException {
        throwStuff(index);

        if (counter == ary.length) {
            ary = resizeArray(ary.length * 2);
        }
//        System.arraycopy(ary,index,ary,index+1,size()-1-index);

        T old;
        for (int j = index + 1; j < ary.length; j++) {
            old = ary[j - 1];
            ary[j] = old;
        }
        ary[index] = elem;

        counter++;
    }


    @Override
    public Iterator<T> iterator() {
        return new Iter<>();
    }

    private class Iter<U> implements Iterator<U> {

        private int pointer = 0;

        @Override
        public boolean hasNext() {
            return pointer < size();
        }

        @Override
        public U next() {
            if (!hasNext())
                throw new NoSuchElementException("Object does not exist");

            return (U) ary[pointer++];
        }

    }


    private void throwStuff(int number) {
        if (number > ary.length || number < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private T[] resizeArray(int size) {
        return Arrays.copyOf(ary, size);

    }
}
