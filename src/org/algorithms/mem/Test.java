package org.algorithms.mem;

import java.util.LinkedList;
import java.util.List;

public class Test {

    public static final String str = new String();
    {
        System.out.println("blockA");
    }
    static{
        System.out.println("blockB");
        String str = "abc";
    }

    public static void main(String[] args) {
        //Test t2 = new Test();
        //List<? extends A> list = new LinkedList<>();
        //list.add(new B());
        //List<? super A> list2 = new LinkedList<>();
        //list2.add(new Object());
    }
}
