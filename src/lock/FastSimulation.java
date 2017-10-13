package lock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by jiang on 2017/9/26.
 */
public class FastSimulation {
    static final int N_ELEMENTS = 100000;
    static final int N_GENS = 30;
    static final int N_EVOLVERS = 50;
    static final AtomicInteger[][] GRID = new AtomicInteger[N_ELEMENTS][N_GENS];
    static Random rand = new Random(47);
    static int times=0;

    static class Evolver implements Runnable {
        Lock lock = new ReentrantLock();
        @Override
        public void run() {

            System.out.println(times++);
            while (!Thread.interrupted()) {

                int element = rand.nextInt(N_ELEMENTS);
                for (int i = 0; i < N_GENS; i++) {
                    int previous = element - 1;
                    if(previous < 0) previous = N_ELEMENTS - 1;
                    int next = element + 1;
                    if(next >= N_ELEMENTS) next = 0;
                    int oldvalue = GRID[element][i].get();
                    //
                    int newvalue = oldvalue + GRID[previous][i].get() + GRID[next][i].get();
                    newvalue /= 3;
                    if (!GRID[element][i].compareAndSet(oldvalue, newvalue)) {
                        //System.out.println("Old value changed from " + oldvalue);
                    }

                }

            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < N_ELEMENTS; i++) {
            for (int j = 0; j < N_GENS; j++)
                GRID[i][j] = new AtomicInteger(rand.nextInt(1000));
        }
        for (int i = 0; i < N_EVOLVERS; i++)
            exec.execute(new Evolver());
        TimeUnit.SECONDS.sleep(5);
        System.out.println("=====================999");
        exec.shutdown();
    }

}
