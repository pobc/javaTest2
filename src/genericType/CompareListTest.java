package genericType;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jiang on 17/11/14.
 */
public class CompareListTest {
    public static <T extends Comparable<T>> T max(List<T> list) {
        Iterator<T> i = list.iterator();
        T result = i.next();
        while (i.hasNext()) {
            T t = i.next();
            if (t.compareTo(result) > 0)
                result = t;
        }
        return result;
    }

    public static  <T extends Annotation> T getAnnotation(Class<T> annotationType) throws IllegalAccessException, InstantiationException {
        System.out.println(annotationType);
        return annotationType.getEnumConstants()[0];
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        //Integer
        List list = Arrays.asList("a","d","c");
        System.out.println(max(list));

        getAnnotation(Anno.class);

    }
}
