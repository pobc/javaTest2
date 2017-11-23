package Unbox;

/**
 * Created by jiang on 17/10/29.
 */
public class IntegerTest {
    public static void main(String[] args) {
        long start ;
        long totalStart = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            start = System.currentTimeMillis();
            calc();
            System.out.println("per times:"+(System.currentTimeMillis() - start));
        }
        System.out.printf("total:%d\n",System.currentTimeMillis() - totalStart);
    }
    //计算 105亿次
    public static void calc(){
        long sum = 0L;
        for (Long i = 0L; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
    }


}