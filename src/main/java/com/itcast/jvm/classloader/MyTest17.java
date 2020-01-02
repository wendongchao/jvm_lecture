package com.itcast.jvm.classloader;

public class MyTest17 {
    public static void main(String[] args) throws Exception{
        /**
         * 示例 1.1 运行结果
         * clazz: 1829164700
         * MySample is load by: sun.misc.Launcher$AppClassLoader@73d16e93
         * MyCat is load by: sun.misc.Launcher$AppClassLoader@73d16e93
         * 示例 1.2
         * 当注释class对象实例化代码 运行结果
         * clazz: 1829164700
         * 结果表明：没有实例化MySample对象，没有调用MySample的构造方法，
         * 也没有实例化MyCat对象，没有调用MyCat的构造方法，
         */
        // 自定义类加载器，并命名为loader1
        MyTest16 loader1 = new MyTest16("loader1");
        // 加载并创建class对象，
        Class<?> clazz = loader1.loadClass("com.itcast.jvm.classloader.MySample");
        System.out.println("clazz: "+clazz.hashCode());
        // class对象实例化
//        Object object = clazz.newInstance();

    }
}
