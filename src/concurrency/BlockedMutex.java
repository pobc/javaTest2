package concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jiang on 17/7/16.
 */
public class BlockedMutex {
    private Lock lock = new ReentrantLock();
    public BlockedMutex() {
        // Acquire it right away, to demonstrate interruption
        // of a task blocked on a ReentrantLock:
        lock.lock();
    }
    public void f() {
        try {
            // This will never be available to a second task
            lock.lockInterruptibly(); // Special call
            System.out.println("lock acquired in f()");
        } catch(InterruptedException e) {
            System.out.println("Interrupted from lock acquisition in f()");
        }
    }
}
class Blocked2 implements Runnable {
    //private Lock lock = new ReentrantLock();
    BlockedMutex blocked = new BlockedMutex();
    public void run() {
        System.out.println("Waiting for f() in BlockedMutex");
        /*try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        blocked.f();
        System.out.println("Broken out of blocked call");
    } }
 class Interrupting2 {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Blocked2());
        t.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Issuing t.interrupt()");
        System.out.println("first:"+ t.isInterrupted());
        t.interrupt();
        System.out.println("second:"+t.isInterrupted());
    }
}
