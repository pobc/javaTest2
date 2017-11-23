package String;

/**
 * Created by jiang on 17/6/30.
 */
public class StringTest {

    public static void operate(StringBuffer x,StringBuffer y){
        x.append(y);
        y = x;
        System.out.println(y);
    }
    public static void operate(String x,String y){
        x+=y;
        y=x;
        System.out.println();
    }


    public static void main(String args[]){
        /*String str1 = "string";
        String str2 = new String("string");
        String str3 = str2.intern();

        System.out.println(str1==str2);//#1
        System.out.println(str1==str3);//#2
        int c;
        System.out.println((c=1)!=2);*/

        StringBuffer a = new StringBuffer("a");
        StringBuffer b = new StringBuffer("b");
        String A="A";
        String B="B";
        operate(a,b);
        operate(A,B);
        System.out.println(a+","+b);
        System.out.println(A+","+B);

        System.out.println(System.nanoTime()/(Math.pow(1000,3)));
    }
}
