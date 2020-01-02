package com.itcast.jvm.classloader;

/**
 * 示例 1：
 *      MyChild1使用了父类中的静态字段，符合主动使用第2条
 *      对于静态字段来说，只有定义了该字段的类才会被初始化（主动使用），
 *      因此，MyChild1 static block没有输出
 *      对父类进行了主动使用，父类进行初始化了
 * 示例 2：
 *      对MyChild1进行了主动使用，调用了该类的静态字段
 *      MyChild1继承了父类MyParent1，符合主动使用第5条，初始化一个类的子类
 *      子类初始化之前父类全部一定先初始化
 */

/**
 * 查看jvm运行详情参数：
 * -XX:+TraceClassLoading，用于追踪类的加载信息并打印
 * -XX:+TraceUnClassLoading，用于追踪类的卸载信息并打印
 * 对于示例 1 虽然没有初始化MyChild1,但是在加载阶段调用了
 * [Loaded com.itcast.jvm.classloader.MyParent1 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
 * [Loaded com.itcast.jvm.classloader.MyChild1 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
 * MyParent1 static block
 * hello world
 */
public class MyTest1 {
    public static void main(String[] args) {
        /**
         * 示例 1
         * 输出结果：
         * MyParent1 static block
         * hello world
         */
        System.out.println(MyChild1.str);
        /**
         * 示例 2
         * 输出结果：
         * MyParent1 static block
         * MyChild1 static block
         * welcome
         */
      //  System.out.print(MyChild1.str2);
    }
}
class MyParent1 {
    public static String str = "hello world";

    static {
        System.out.println("MyParent1 static block");
    }
}
class MyChild1 extends MyParent1 {
    public static String str2 = "welcome";
    static {
        System.out.println("MyChild1 static block");
    }
}
