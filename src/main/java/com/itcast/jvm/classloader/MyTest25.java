package com.itcast.jvm.classloader;

/**
 * @author: dong
 * @description:
 * @date: created in 2020/1/17 21:45
 * @modified by:
 */
public class MyTest25 implements Runnable {
    private Thread thread;

    public MyTest25(){
        this.thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run(){
        ClassLoader classLoader = this.thread.getContextClassLoader();
        this.thread.setContextClassLoader(classLoader);
        System.out.println("Class: "+classLoader.getClass());
        System.out.println("Parent: "+classLoader.getParent().getClass());

    }

    public static void main(String[] args) {
        /**
         * 示例 1 运行结果
         * Class: class sun.misc.Launcher$AppClassLoader
         * Parent: class sun.misc.Launcher$ExtClassLoader
         */
        new MyTest25();
    }
}
