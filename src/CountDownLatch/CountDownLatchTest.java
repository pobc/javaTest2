package CountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiang on 17/11/23.
 */
public class CountDownLatchTest {
    public static long time(Executor executor,int concurrency, final Runnable action)
            throws InterruptedException{
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final int b = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {

                    ready.countDown();//开始执行
                    System.out.println("here:"+start.getCount());
                    try {
                        start.await();//等待
                        action.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }finally {
                        System.out.println("done:"+done.getCount());
                        done.countDown();
                    }
                }
            });
        }
        System.out.println("ready await");
        ready.await();
        long startNanos = System.nanoTime();
        start.countDown();
        done.await();
        return System.nanoTime() - startNanos;
    }

    public static void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newFixedThreadPool(10);
       long time = time(executor, 10, new Runnable() {
            @Override
            public void run() {
                System.out.println("haha");
            }
        });
        System.out.println("spent:"+time);
    }
}
