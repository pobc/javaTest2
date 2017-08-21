package concurrency;


import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by jiang on 17/7/16.
 */
public class SleepBlocked implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("interruptedException");
        }
        System.out.println("Exiting SleepBlocked.run()");
    }
}
class IOBlocked implements Runnable{
    private InputStream in;
    public IOBlocked(InputStream is){
        in = is;
    }
    @Override
    public void run() {
        System.out.println("waiting for read():");
        try {
            int a = in.read();
            System.out.println("out:"+a);
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()){
                System.out.println("Interrupted from blocked I/O");
            }else {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Exiting IOBlocked.run()");
    }
}
class SynchronizedBlocked implements Runnable{
    public synchronized void f(int i){
        System.out.println("execute from :"+i);
        while (true){
            Thread.yield();
        }
    }

    public SynchronizedBlocked(){
        new Thread(){
            public void run(){
                f(2);
            }
        }.start();
    }

    @Override
    public void run() {
        System.out.println("Trying to call f()");
        f(1);
        System.out.println("Exiting SynchronizedBlocked.run()");
    }
}
class Interrupting{
    private static ExecutorService exec = Executors.newCachedThreadPool();
    static void test(Runnable r)throws InterruptedException{
        Future<?> f = exec.submit(r);

        TimeUnit.MILLISECONDS.sleep(10000);
        System.out.println("Interrupting "+r.getClass().getName());
        f.cancel(true);
        System.out.println("exec.isShutdown():"+exec.isShutdown());
        System.out.println("Interrupt sent to "+r.getClass().getName());
    }
    public static void main(String[] args) throws InterruptedException {
        //test(new SleepBlocked());
        //test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);

    }
}