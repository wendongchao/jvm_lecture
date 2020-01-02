package com.itcast.jvm.classloader;

/**
 * final:
 *      被修饰的类：这个类不能被继承，这个类中的成员方法被隐式指定为final方法
 *      被修饰的方法：该方法不能被重写
 *      被修饰的常量：
 *            基本类型：常量只能被赋值一次，且赋值后不再改变
 *            引用类型：则在对其初始化之后便不能再让其指向其他对象了，但该引用所指向的对象的内容是可以发生变化的。
 *                     本质上是一回事，因为引用的值是一个地址，final要求值，即地址的值不发生变化。
 * final修饰的常量在编译阶段，这个常量会被存入到调用这个常量的那个方法所在类的常量池当中，
 * 本质上，调用类并没有直接引用到定义常量的类，因此并不会触发定义常量的类的初始化
 * 注意：这里指的是将常量存放到MyTest2的常量池中，之后MyTest2与MyParent2就没有任何关系了，甚至，可以把MyParent2的class文件删除
 * 示例 1 添加final修饰常量
 *       运行结果 hello world，这时str常量已经放入到调用常量的那个方法所在类（MyTest2）的常量池中，
 *       这时删除定义常量的类的class文件，之后再次运行，
 *       运行结果 hello world，说明这个str这个常量已经放入到常量池中了，
 *       [Loaded java.security.AllPermission from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
 *       [Loaded java.security.UnresolvedPermission from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
 *       [Loaded java.security.BasicPermissionCollection from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
 *       [Loaded com.itcast.jvm.classloader.MyTest2 from file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/]
 *       [Loaded sun.launcher.LauncherHelper$FXHelper from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
 *       [Loaded java.lang.Class$MethodArray from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
 *       [Loaded java.lang.Void from D:\software\Java\jdk1.8.0_191\jre\lib\rt.jar]
 *       hello world
 *       从jvm输出的日志中可以得出没有加载定义常量所在的类MyParent2，
 *       所以MyParent2没有加载和初始化，
 *
 */

/**
 * 助记符：
 *      ldc：表示将int，float，String类型的常量冲常量池中推送到栈顶、
 *      bipush：表示将单字节（-128 ~ 127）的常量值推送到栈顶
 *      sipush：表示将一个短整型常量值（-32768 ~ 32767）推送至栈顶
 *      iconst_1：表示将int类型1推送到栈顶（iconst_m1 ~ iconst_5）-1 ~ 5
 */
public class MyTest2 {
    public static void main(String[] args) {
        /**
         * 示例 1
         * 添加final 运行结果，
         * hello world
         * 不添加  运行结果，主动使用
         * MyParent2 static block
         * hello world
         */
        System.out.println(MyParent2.str);
    }
}
class MyParent2 {
    public static final String str = "hello world";

    public static final short s = 127;
    public static final int i = 7;
    public static final short m = 1;

    static {
        System.out.println("MyParent2 static block");
    }
}