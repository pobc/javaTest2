package lock;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by jiang on 2017/10/13.
 */
public class ReaderWriterMap<T, K> {
    private HashMap<T, K> lockHashMap;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public ReaderWriterMap(int size, T initialValue) {
        lockHashMap = new HashMap<>(size);
    }

    public HashMap<T, K> set(T index, K element) {
        Lock wlock = lock.writeLock();
        wlock.lock();
        try {
            lockHashMap.put(index, element);
            return lockHashMap;
        } finally {
            wlock.unlock();
        }
    }

    public K get(T key) {
        Lock rlock = lock.readLock();
        rlock.lock();
        try {
            if (lock.getReadLockCount() > 1)
                System.out.println(lock.getReadLockCount());
            return lockHashMap.get(key);
        } finally {
            rlock.unlock();
        }
    }

    public static void main(String[] args){
        new ReaderWriterMapTest(30,1);
    }

}
class ReaderWriterMapTest{
    ExecutorService exec = Executors.newCachedThreadPool();
    private final static int SIZE = 100;
    private static Random rand = new Random(1000);
    private ReaderWriterMap<Integer,Integer> map = new ReaderWriterMap<>(SIZE,0);

    private class Writer implements Runnable{

        @Override
        public void run() {
            try {
                for (int i = 0; i < 20; i++) { // 2 second test
                    map.set(i, rand.nextInt());
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                // Acceptable way to exit
            }
            System.out.println("Writer finished, shutting down");
            exec.shutdownNow();
        }
    }
    private class Reader implements Runnable {
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    for (int i = 0; i < SIZE; i++) {
                        map.get(i);
                        TimeUnit.MILLISECONDS.sleep(1);
                    }
                }
            } catch (InterruptedException e) {
                // Acceptable way to exit
            }
        }
    }
    public ReaderWriterMapTest(int readers, int writers) {
        for (int i = 0; i < readers; i++)
            exec.execute(new Reader());
        for (int i = 0; i < writers; i++)
            exec.execute(new Writer());
    }
}
