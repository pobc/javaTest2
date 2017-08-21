package concurrency;

/**
 * Created by jiang on 17/7/13.
 */
public class EvenGenerator extends IntGenerator {
    private int  currentEvenValue = 0;
    @Override
    public synchronized int next() {
        ++currentEvenValue;
        Thread.yield();
        ++currentEvenValue;
        return currentEvenValue;
    }
    public synchronized int next2(){
        ++currentEvenValue;
        Thread.yield();
        ++currentEvenValue;
        return currentEvenValue;
    }
    public static void main(String[] args){
        EvenChecker.test(new EvenGenerator());
    }
}
