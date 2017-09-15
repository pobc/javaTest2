package Thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by jiang on 2017/9/11.
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter base directory(e.g. /usr/local/jdk5.0/src):");
        String directory = in.nextLine();
        System.out.println("Enter keyword (e.g. volatile):");
        String keyword = in.nextLine();

        ExecutorService pool = Executors.newCachedThreadPool();
        MatchCounter2 counter = new MatchCounter2(new File(directory), keyword, pool);
        Future<Integer> result = pool.submit(counter);
        try {
            System.out.println(result.get() + " files matched.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        int maxLargestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
        System.out.println("largest pool size=" + maxLargestPoolSize);

    }
}

class MatchCounter2 implements Callable<Integer> {

    private File directory;
    private String keyword;
    private ExecutorService pool;
    private int count;

    public MatchCounter2(File directory, String keyword, ExecutorService pool) {
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }

    @Override
    public Integer call() throws Exception {
        count = 0;
        try {
            File[] files = directory.listFiles();
            ArrayList<Future<Integer>> results = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounter2 counter = new MatchCounter2(file, keyword,pool);
                    Future<Integer> result = pool.submit(counter);
                    results.add(result);

                } else {
                    if (search(file)) count++;
                }

            }
            for (Future<Integer> result : results) {
                //将task返回的结果追加到count中
                count += result.get();
            }
        } catch (InterruptedException e) {
        }
        return count;
    }

    public boolean search(File file) {
        try {
            Scanner in = new Scanner(new FileInputStream(file));
            boolean found = false;
            while (!found && in.hasNext()) {
                String line = in.nextLine();
                if (line.contains(keyword)) found = true;
            }
            in.close();
            return found;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}