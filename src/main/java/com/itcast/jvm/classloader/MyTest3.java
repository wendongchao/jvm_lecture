package com.itcast.jvm.classloader;

import java.util.UUID;

/**
 * 当一个常量的值并非编译期间可以确定的，那么其值就不会被放到调用类的常量池中，
 * 这时在程序运行时，会导致主动使用这个常量所在的类，显然会导致这个类被初始化。
 */
public class MyTest3 {
    public static void main(String[] args) {
        /**
         * 示例 1
         * 输出结果：
         *       MyParent2 static block
         *       881b75b1-6f02-486b-8bda-b93ee0f7ec7b
         */
        System.out.println(MyParent3.str);
    }
}
class MyParent3 {
    public static final String str = UUID.randomUUID().toString();

    static {
        System.out.println("MyParent4 static block");
    }
}
