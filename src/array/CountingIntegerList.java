package array;

import java.util.AbstractList;

/**
 * Created by jiang on 17/6/29.
 */
public class CountingIntegerList extends AbstractList<Integer> {
    private int size;
    CountingIntegerList(int size){
        this.size = size<0?0:size;
    }
    @Override
    public Integer get(int index) {
        return index;
    }

    @Override
    public int size() {
        return size;
    }
    public static int aa(){
        return +1;
    }
    public static void main(String args[]){
        System.out.println(aa());
        System.out.println(new CountingIntegerList(30));
    }
}
