package concurrency;

/**
 * Created by jiang on 17/7/13.
 */
public abstract class IntGenerator {
    private volatile  boolean canceled =false;
    public abstract int next();
    public abstract  int next2();
    // allow this to be canceled;
    public void cancel(){canceled = true;}
    public boolean isCanceled(){return canceled;}
}
