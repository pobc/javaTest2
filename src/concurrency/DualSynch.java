package concurrency;
/**
 *
 * Created by jiang on 17/7/14.
 */
public class DualSynch {
    private Object syncObject = new Object();
    public synchronized void f(){
        for (int i = 0; i < 5; i++) {
            System.out.println("f()");
            Thread.yield();
        }
    }
    public void g(){
        synchronized (syncObject){
            for (int i = 0; i < 5; i++) {
                System.out.println("g()");
                Thread.yield();
            }
        }
    }
}
class SyncObject{
    public static void main(String args[]){
        final DualSynch ds = new DualSynch();
        new Thread(){
            public void run(){
                ds.f();
            }
        }.start();
        ds.g();
    }
}
