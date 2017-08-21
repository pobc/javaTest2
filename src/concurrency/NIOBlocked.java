package concurrency;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by jiang on 17/7/16.
 */
public class NIOBlocked implements Runnable {
    private final SocketChannel sc;
    public NIOBlocked(SocketChannel sc) { this.sc = sc; }
    public void run() {
        try {
            System.out.println("Waiting for read() in " + this);
            sc.read(ByteBuffer.allocate(1));
        } catch(ClosedByInterruptException e) {
            System.out.println("ClosedByInterruptException");
        } catch(AsynchronousCloseException e) {
            System.out.println("AsynchronousCloseException");
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Exiting NIOBlocked.run() " + this);
    }
}

 class NIOInterruption {
    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        ServerSocket server = new ServerSocket(8080);
        InetSocketAddress isa =
                new InetSocketAddress("localhost", 8080);
        SocketChannel sc1 = SocketChannel.open(isa);
        SocketChannel sc2 = SocketChannel.open(isa);
        Future<?> f = exec.submit(new NIOBlocked(sc1));

        f =exec.submit(new NIOBlocked(sc2));
        /*exec.execute(new NIOBlocked(sc1));
        exec.execute(new NIOBlocked(sc2));
        exec.shutdownNow();*/
        TimeUnit.SECONDS.sleep(1);
        // Produce an interrupt via cancel:
        f.cancel(true);
       /* f2.cancel(true);*/
        TimeUnit.SECONDS.sleep(1);
        // Release the block by closing the channel:
        sc1.close();
        /*sc2.close();*/
    }

    // 使用Future执行线程任务时  f.cancel 可以关闭InetSocketAddress 线程
     // 使用ExecutorService执行线程任务时 ，需要通过关闭资源的形式或者 用shutdownNow() 中断阻塞的线程
}