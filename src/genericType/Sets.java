package genericType;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jiang on 17/6/6.
 */
public class Sets {
    public static <T> Set<T> union(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<>(a);
        result.addAll(b);
        return result;
    }

    public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<>(a);
        //保留与b相同的对象，其他的都删除
        result.retainAll(b);
        return result;
    }
    //subtract subset from superset:
    public static <T> Set<T> difference(Set<T> superset,Set<T> subset){
        Set<T> result = new HashSet<>(superset);
        result.removeAll(subset);
        return result;
    }
    // Reflexive -- everything not in the intersection:
    public static <T> Set<T> complement(Set<T> a,Set<T>b){
        return difference(union(a,b),intersection(a,b));
    }
}
