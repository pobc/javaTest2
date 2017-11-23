package enum2;

/**
 * Created by jiang on 17/11/15.
 */
public enum Operation {
    PLUS {double apply(double x, double y) {return x + y;}},
    MINUS {double apply(double x, double y) {return x - y;}},
    TIMES {double apply(double x, double y) {return x * y;}},
    DIVIDE {double apply(double x, double y) {return x / y;}};

    abstract double apply(double x, double y);

    public static void main(String[] args) {
        System.out.println(Text.Style.BOLD);
        System.out.println(Operation.PLUS.apply(2, 3));
    }
}
