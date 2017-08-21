package ClassTest;

import java.util.HashMap;

/**
 *
 * Created by jiang on 17/7/7.
 */
public abstract class Class {
}
interface Class2{}
class Class3{
    public static void main(){
         new Class(){

        };
         new Class2(){
             HashMap mm = new HashMap();
             public void test(){
                 mm.put("aa",1);
             }
         };
    }
}
