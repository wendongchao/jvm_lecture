package com.itcast.jvm.classloader;

import java.util.Random;

/**
 * 初始化一个类时，并不会先初始化它所实现的接口（示例 4）
 * 当初始化一个接口时，并不会初始化它的父接口（示例 5）
 * 当一个借口在初始化时，并不要求其父接口都完成了初始化
 * 只要在真正使用父接口的时候（如引用接口中定义的常量时），才会初始化，并不代表不会被加载
 */
public class MyTest5 {
    public static void main(String[] args) {
        /**
         * 示例 1 运行结果
         * 5
         * jvm 日志
         * [Loaded java.security.UnresolvedPermission from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded java.security.BasicPermissionCollection from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded com.itcast.jvm.classloader.MyTest5 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded sun.launcher.LauncherHelper$FXHelper from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded java.lang.Class$MethodArray from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded java.lang.Void from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * 5
         * 可以看到jvm只加载了MyTest5，b常量放到了调用该常量的方法所属类的常量池中
         * 接口中常量的默认修饰 public static final
         * b常量与接口MyParent5，MyChild5没有关系了
         */
//        System.out.println(MyChild5.b);

        /**
         * 示例 3
         * 当接口MyParent5s，MyChild5s修改为类的时候，运行结果
         * 5
         * jvm 日志
         * [Loaded com.itcast.jvm.classloader.MyTest5 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded sun.launcher.LauncherHelper$FXHelper from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded java.lang.Class$MethodArray from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded java.lang.Void from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded com.itcast.jvm.classloader.MyParent5s from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded com.itcast.jvm.classloader.MyChild5s from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * 5
         * jvm 运行期间加载了MyTest5，MyParent5s，MyChild5s
         * 与示例 1形成对比，
         * 接口常量默认有final修饰，类常量没有final修饰，
         * 导致常量一个进入到调用该常量的方法所属类的常量池中，另一个符合主动使用第2条
         */
      //  System.out.println(MyChild5s.b);

        /**
         * 示例 4 运行结果
         * 5
         * 调用类的静态方法，符合主动使用第2条，并符合主动使用第3条
         * jvm 日志
         * [Loaded com.itcast.jvm.classloader.MyTest5 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded sun.launcher.LauncherHelper$FXHelper from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded java.lang.Class$MethodArray from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded java.lang.Void from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded com.itcast.jvm.classloader.MyParent5b from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded com.itcast.jvm.classloader.MyChild5b from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded com.itcast.jvm.classloader.MyParent5b$1 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * 5
         * 可以看到类都被加载了，但是接口MyParent5b没有被初始化
         * 所以：初始化一个类时，并不会先初始化它所实现的接口
         * 当把接口MyParent5b修改为类时，输出结果
         * [Loaded com.itcast.jvm.classloader.MyTest5 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded sun.launcher.LauncherHelper$FXHelper from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded java.lang.Class$MethodArray from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded java.lang.Void from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded com.itcast.jvm.classloader.MyParent5b from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded com.itcast.jvm.classloader.MyChild5b from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded com.itcast.jvm.classloader.MyParent5b$1 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * MyParent5b invoke
         * 5
         * 父类被加载了，与接口MyParent5b形成对比，
         */
//        System.out.println(MyChild5b.b);

        /**
         * 示例 5 运行结果
         * [Loaded com.itcast.jvm.classloader.MyTest5 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded sun.launcher.LauncherHelper$FXHelper from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded java.lang.Class$MethodArray from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded java.lang.Void from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
         * [Loaded com.itcast.jvm.classloader.MyParent5_1 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded com.itcast.jvm.classloader.MyChild5_1 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded com.itcast.jvm.classloader.MyParent5_1$1 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * [Loaded com.itcast.jvm.classloader.MyChild5_1$1 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
         * MyChild5_1 invoke
         * Thread[Thread-0,5,main]
         * 所以：当初始化一个接口时，并不会初始化它的父接口
         */
        System.out.println(MyChild5_1.thread);
    }
}


interface MyParent5 {
    public static int a = 3;
    /**
     * 示例 2
     */
   // public static int a = new Random().nextInt(3);
    
}

interface MyChild5 extends MyParent5 {
   // public static int b = 5;
    /**
     * 示例 2
     * 当常量b的值在运行期的时候才能确定时，jvm会加载MyTest5，MyParent5，MyChild5，
     * [Loaded com.itcast.jvm.classloader.MyTest5 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
     * [Loaded sun.launcher.LauncherHelper$FXHelper from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
     * [Loaded java.lang.Class$MethodArray from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
     * [Loaded java.lang.Void from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
     * [Loaded com.itcast.jvm.classloader.MyParent5 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
     * [Loaded com.itcast.jvm.classloader.MyChild5 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
     * [Loaded java.util.Random from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
     * 1
     * 与示例 1 形成了对比，
     */
     public static int b = new Random().nextInt(2);
}
class MyParent5s {
    public static int a = 3;
}
class MyChild5s extends MyParent5s {
    public static int b = 5;
}
class MyParent5b {
    /**
     * MyParent5b 初始化时，new Thread()这个对象一定会被创建出来，
     * 之后代码块就会执行，
     */
    public static Thread thread = new Thread() {
        {
            System.out.println("MyParent5b invoke");
        }
    };
}
class MyChild5b extends MyParent5b {
    public static int b = 5;
}
interface MyParent5_1 {
    public static Thread thread = new Thread() {
        {
            System.out.println("MyParent5_1 invoke");
        }
    };
}
interface MyChild5_1 extends MyParent5_1 {
    public static Thread thread = new Thread() {
        {
            System.out.println("MyChild5_1 invoke");
        }
    };
}