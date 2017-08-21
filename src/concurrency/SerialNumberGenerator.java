package concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jiang on 17/7/14.
 */
public class SerialNumberGenerator {
    private static volatile int serialNumber = 0;
    private static AtomicInteger i = new AtomicInteger(0);
    public  static int nextSerialNumber() {
        return i.addAndGet(1); // Not thread-safe
    }
}
