package com.itcast.jvm.classloader;

/**
 * 只有当程序访问的静态变量或静态方法确实在当前类或当前接口中定义时，
 * 才可以认为对类或接口的主动使用
 */
public class MyTest11 {
    public static void main(String[] args) {
        System.out.println(Child3.a);
        System.out.println("--------");
        Child3.doSomething();
    }
}
class Parent3 {
    static int a = 3;
    static {
        System.out.println("Parent3 static block");
    }
    static void doSomething(){
        System.out.println("do something");
    }
}
class Child3 extends Parent3 {
    static {
        System.out.println("Child3 static block");
    }
}