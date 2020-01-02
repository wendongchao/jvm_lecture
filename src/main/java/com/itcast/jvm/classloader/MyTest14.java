package com.itcast.jvm.classloader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * 给定一个类路径，把类加载信息打印
 * Thread.currentThread()
 *    Returns a reference to the currently executing thread object.
 *    返回对当前正在执行的线程对象的引用。
 * getContextClassLoader()
 *    Returns the context ClassLoader for this Thread. The context
 *    ClassLoader is provided by the creator of the thread for use
 *    by code running in this thread when loading classes and resources.
 *    If not { setContextClassLoader set}, the default is the
 *    ClassLoader context of the parent Thread. The context ClassLoader of the
 *    primordial thread is typically set to the class loader used to load the
 *    application.
 *    线程的创建者提供了上下文 ClassLoader，供加载类和资源时在该线程中运行的代码使用。
 *    如果不是{setContextClassLoader set}(自定义上下文类加载器)，则默认值为父线程的ClassLoader上下文。
 *    原始线程的上下文ClassLoader通常设置为用于加载应用程序的类加载器。
 */
public class MyTest14 {
    public static void main(String[] args) throws IOException {
        /**
         * 示例 1 运行结果
         * sun.misc.Launcher$AppClassLoader@73d16e93
         */
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader);

        /**
         * 示例 2 运行结果
         * file:/D:/workspace/idea/jvm_lecture/build/classes/java/main/com/itcast/jvm/classloader/MyTest13.class
         */
        String resourceName = "com/itcast/jvm/classloader/MyTest13.class";
        Enumeration<URL> urls = classLoader.getResources(resourceName);
        while (urls.hasMoreElements()){
            URL url = urls.nextElement();
            System.out.println(url);
        }
        System.out.println("--------------");
        /**
         * 示例 3 不同类的加载器
         * 运行结果
         * sun.misc.Launcher$AppClassLoader@73d16e93
         * null
         */
        Class<?> clazz = MyTest14.class;
        System.out.println(clazz.getClassLoader());
        Class<?> clazz2 = String.class;
        System.out.println(clazz2.getClassLoader());
    }
}
