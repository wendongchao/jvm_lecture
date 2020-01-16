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

/**
 *     public ClassLoader run() throws Exception {
 *         String cls = System.getProperty("java.system.class.loader");
 *         if (cls == null) {
 *             return parent;
 *         }
 *
 *         Constructor<?> ctor = Class.forName(cls, true, parent)
 *             .getDeclaredConstructor(new Class<?>[] { ClassLoader.class });
 *         ClassLoader sys = (ClassLoader) ctor.newInstance(
 *             new Object[] { parent });
 *         Thread.currentThread().setContextClassLoader(sys);
 *         return sys;
 *     }
 *     代码分析：
 *     18：先判断系统设置java.system.class.loader了没（设置自定义系统类加载器），如果没有则返回系统默认的系统类加载器
 *     如果有，则把自定义类加载器设置为系统类加载器。
 *     23：根据自定义类加载器加载的类和双亲类加载器，得到被加载类的class对象。并且调用getDeclaredConstructor()方法传入一个class对象（ClassLoader.class）
 *     25：传入一个Object类对象（系统类加载器），这里可以体现，如果一个类要自定义类加载器时，需要在其类中创建一个有参构造函数，
 *     传入ClassLoader参数，
 *
 *     Class.forName(cls, true, parent)
 *     Returns the {@code Class} object associated with the class or
 *     interface with the given string name, using the given class loader.
 *     Given the fully qualified name for a class or interface (in the same
 *     format returned by {@code getName}) this method attempts to
 *     locate, load, and link the class or interface.  The specified class
 *     loader is used to load the class or interface.  If the parameter
 *     {@code loader} is null, the class is loaded through the bootstrap
 *     class loader.  The class is initialized only if the
 *     {@code initialize} parameter is {@code true} and if it has
 *     not been initialized earlier.
 *
 * 	  返回具有类或接口与给定字符串名称相关联的{@code Class}对象，使用给定的类加载器。
 * 	  鉴于全名的类或接口（通过返回的格式相同{@code getName}）这个方法尝试定位，加载和链接的类或接口。
 * 	  指定的类加载器用于加载的类或接口。 如果参数{@code loader}为空，类是通过引导类加载器加载。
 * 	  该类被初始化只有在@code {}initialize参数为{@code true}，如果它没有被更早初始化。
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
