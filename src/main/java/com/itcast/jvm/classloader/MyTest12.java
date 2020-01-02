package com.itcast.jvm.classloader;

/**
 * 调用ClassLoader类的loadClass方法加载一个类，并不是对类的主动使用，不会导致类的初始化。
 * Class.forName();符合主动使用第4条反射
 */
public class MyTest12 {
    public static void main(String[] args) throws Exception {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Class<?> clazz = loader.loadClass("com.itcast.jvm.classloader.CL");
        System.out.println(clazz);
        System.out.println("----------");
        clazz = Class.forName("com.itcast.jvm.classloader.CL");
        System.out.println(clazz);
    }
}
class CL {
    static {
        System.out.println("Class CL");
    }
}