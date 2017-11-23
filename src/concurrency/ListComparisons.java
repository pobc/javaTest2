package concurrency;

import net.mindview.util.CountingIntegerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jiang on 2017/9/22.
 */
 abstract class ListTest extends Tester<List<Integer>> {

    ListTest(String testId, int nReaders, int nWriters) {
        super(testId, nReaders, nWriters);
    }

    class Reader extends TestTask {

        long result = 0;

        @Override
        void test() {
            for (int i = 0; i < testCycles; i++)
                for (int index = 0; index < containerSize; index++)
                    result += testContainer.get(index);

        }

        @Override
        void putResults() {
            readResult += result;
            readTime += duration;
        }
    }
    class Writer extends TestTask{

        @Override
        void test() {
            for (int i = 0; i < testCycles; i++)
                for (int index = 0; index < containerSize; index++)
                    testContainer.set(index,writeData[index]);
        }

        @Override
        void putResults() {
            writeTime += duration;
        }
    }
    void startReadersAndWriters(){
        for (int i = 0; i < nReaders; i++)
            exec.execute(new Reader());
        for (int i = 0; i < nWriters; i++)
            exec.execute(new Writer());

    }
}
class SynchronizedArrayListTest extends ListTest{

    SynchronizedArrayListTest(int nReaders, int nWriters) {
        super("Synched ArrayList", nReaders, nWriters);
    }

    @Override
    List<Integer> containerInitializer() {
        return Collections.synchronizedList(new ArrayList<Integer>(new CountingIntegerList(containerSize)));
    }
}

class CopyOnWriteArrayListTest extends ListTest{
    CopyOnWriteArrayListTest( int nReaders, int nWriters) {
        super("CopyOnWriterArrayList", nReaders, nWriters);
    }

    List<Integer> containerInitializer(){
        return new CopyOnWriteArrayList<Integer>(new CountingIntegerList(containerSize));
    }
}
public class ListComparisons{
     public static void main(String[] args){
         Tester.initMain(args);
         new SynchronizedArrayListTest(10,0);
         new SynchronizedArrayListTest(9,1);
         new SynchronizedArrayListTest(5,5);

         new CopyOnWriteArrayListTest(10,0);
         new CopyOnWriteArrayListTest(9,1);
         new CopyOnWriteArrayListTest(5,5);
         Tester.exec.shutdown();
         /*System.out.printf("%-14s %14s %14s\n", "Type", "Read time", "Write time");
         System.out.printf("%-27s %14s %14s\n", "Type", "Read time", "Write time");*/

     }
}





