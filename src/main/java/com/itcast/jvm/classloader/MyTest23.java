package com.itcast.jvm.classloader;

import sun.misc.Launcher;

/**
 * 类加载器加载：
 * 启动类加载器加载扩展类加载器，系统类加载器，java.lang.ClassLoader以及其他的java平台类。
 * 启动类加载器是由c++编写，扩展类加载器，系统类加载器是由java代码编写的。
 * 启动类加载器是内嵌与VM虚拟机中的，虚拟机启动时会自动加载启动类加载器。
 * 启动类加载器是特定于平台的机器指令，它负责开启整个加载过程。
 * 所有类加载器（除启动类加载器）都被实现为java类，不过，总归要有一个组件来加载第一个java类加载器，从而让整个加载过程能够顺利
 * 进行下去，加载第一个纯java类加载器就是启动类加载器的职责。
 * 启动类加载器还会负责加载供jre正常进行所需要的基本组件，这包括java.util与java.lang包中的类等等。
 */
public class MyTest23 {
    public static void main(String[] args) {
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));

        System.out.println(ClassLoader.class.getClassLoader());
        // 启动类加载器加载扩展类加载器，系统类加载器，java.lang.ClassLoader以及其他的java平台类。
        System.out.println(Launcher.class.getClassLoader());

        System.out.println("-------------------");
        //
        System.out.println(System.getProperty("java.system.class.loader"));
        System.out.println(MyTest23.class.getClassLoader());
        System.out.println(MyTest16.class.getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());

    }
}
