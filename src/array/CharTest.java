package array;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by jiang on 17/6/28.
 */
public class CharTest {
    interface Generator<T>{
        T next();
    }
    public void say(String s){
        System.out.println("456");
    }
    public void say(Object s){
        System.out.println("123");
    }

    public static void main(String args[]){
        StringBuilder sb =new StringBuilder("234");
        Integer saa =null;
        sb.append(saa);
        System.out.println(sb);
        /*Character c[] = new Character[2];
        System.out.println((char) c[0]);
        HashMap<String,String> mm = new HashMap<>();
        mm.put("32423","342");
        System.out.println("end");*/
        System.out.println(null instanceof String);
        new CharTest().say(null);


    }
    public static <T> T[] array(T[] a, Generator<T> gen){
        return null;
    }
}
