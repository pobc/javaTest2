package jvm;

import java.io.Serializable;

/**
 * jvm class加载机制
 * Created by jiang on 17/11/20.
 */
public class TestClass {
    public static void main(String[] args) {

        ClassLoader cl = TestClass.class.getClassLoader();
        System.out.println(cl);

        ClassLoader clParent = cl.getParent();
        System.out.println(clParent);

        ClassLoader clRoot = clParent.getParent();
        System.out.println(clRoot);
    }

}
