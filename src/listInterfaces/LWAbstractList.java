package listInterfaces;

import java.util.Iterator;

public abstract class LWAbstractList<T> extends LWAbstractCollection<T> implements LWList<T>{

    protected int counter = 0;

    @Override
    public boolean equals(Object compareMe) {

        if (compareMe instanceof LWAbstractList<?> compareMeList) {

            if(this.size()!=compareMeList.size()){
                return false;
            }

            Iterator<T> iterThis = this.iterator();
            Iterator<?> iter = compareMeList.iterator();

            while (iter.hasNext()&&iterThis.hasNext()){
                T iterHere = iterThis.next();
                Object iterHereCompMe = iter.next();
                if(!iterHere.equals(iterHereCompMe)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (T o : this){
            hashCode = 31 * hashCode + (o == null ? 0 : o.hashCode());
        }
        return hashCode;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        int counter = 0;
        for (T obj : this){
            if(counter==index){
                return obj;
            }
            counter++;
        }
        throw new IndexOutOfBoundsException("Index invalid");
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public int indexOf(T elem) {
        int counter = 0;
        Iterator<T> iter = this.iterator();
        while (iter.hasNext()){
            if (iter.next().equals(elem)){
                return counter;
            }
            counter++;
        }
        return -1;
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        Iterator<T> iter = this.iterator();
        int counter = 0;
        T obj;
        while (iter.hasNext()){
            iter.next();
            if (counter==index){
                obj = get(index);
                iter.remove();
                return obj;
            }
        }
        return null;
    }

}
