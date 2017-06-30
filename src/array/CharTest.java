package array;

import java.util.Arrays;

/**
 * Created by jiang on 17/6/28.
 */
public class CharTest {
    interface Generator<T>{
        T next();
    }
    public static void main(String args[]){
        Character c[] = new Character[2];
        System.out.println((char) c[0]);
    }
    public static <T> T[] array(T[] a, Generator<T> gen){
        return null;
    }
}
