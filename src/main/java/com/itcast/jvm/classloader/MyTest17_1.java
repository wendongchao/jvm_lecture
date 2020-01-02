package com.itcast.jvm.classloader;

/**
 * 关于命名空间的重要说明
 * 1. 子加载器所加载的类能够访问到父加载器所加载的类
 * 2. 父加载器所加载的类无法访问到子加载器所加载的类
 */
public class MyTest17_1 {
    public static void main(String[] args) throws Exception{
        /**
         * 示例 1 前提条件 删除classpath路径下的MyTest1.class，并且把MyTest1.class和它的路径放到桌面，
         * 可以根据前面学的理论：得出结论
         * MySample和MyCat的类加载器是MyTest16
         */
        // 自定义类加载器，并命名为loader1
        MyTest16 loader1 = new MyTest16("loader1");
        loader1.setPath("/c/Users/dong/Desktop");
        // 加载并创建class对象，
        Class<?> clazz = loader1.loadClass("com.itcast.jvm.classloader.MySample");
        System.out.println("clazz: "+clazz.hashCode());
        // class对象实例化
        Object object = clazz.newInstance();

    }
}
