package overload;

import java.util.*;

/**
 *
 * Created by jiang on 17/11/17.
 */
public class CollectionClassifier {
    public static String classify(Set<?> s){
        return "Set";
    }
    public static String classify(List<?> lst){
        return "List";
    }

    public static String classify(Collection<?> c){
        return "Unknown Collection";
    }

    public static void main(String[] args) {
        Collection<?>[] collections = {
                new HashSet<>(),
                new ArrayList<>(),
                new HashMap<>().values()
        };
        for (Collection<?> c : collections) {
            System.out.println("class: "+c.getClass());
            System.out.println(classify(c));
        }
    }
}
