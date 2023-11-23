package listInterfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ListUtil {

    public static <T> void uniter (LWCollection<? super T> target, LWCollection<T> spender){

        for (T obj : spender) {
            target.add(obj);
        }
    }
}
