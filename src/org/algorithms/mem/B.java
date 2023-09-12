package org.algorithms.mem;

public class B extends A{

    public Object str;
    public int a;
    public static void call(String B){

    }

    @Override
    public void say() {
        System.out.println("hello");
    }

    public static void main(String[] args) {
        A b  = new B();
        b.say();
    }
}
