package com.itcast.jvm.classloader;

import java.lang.reflect.Method;

/**
 * 类加载器的双亲委托模型的好处：
 * 1. 可以确保java核心库的类型安全：所有的java应用都至少会引用java.lang.Object类，也就是说在运行期，java.lang.Object这个类会被加载到
 *    java虚拟集中；如果这个加载过程是由java应用自己的类加载器所完成的，那么很可能就会在jvm中存在多个版本的java.lang.Object类，而且这些
 *    类之间还是不兼容的，相互不可见的（正是命名空间在发挥作用）。
 *    借助于双亲委托机制，java核心类库中的类的加载工作都是有启动类加载器来统一完成的，从而确保了java应用所使用的都是统一版本的java核心库，
 *    他们之间是兼容的。
 * 2. 可以确保java核心库所提供的类不会被自定义的类所替代。
 * 3. 不同的类加载器可以为相同名称(binary name)的类创建额外的命名空间。相同名称的类可以在并存在java虚拟机中，只需要用不同的类加载器来加载
 *    他们即可。不同类加载器所加载的类之间是不兼容的，这就相当于在java虚拟机内部创建了一个又一个相互隔离的java类空间，这类技术在许多框架中
 *    都得到了实际应用。
 */
public class MyTest21 {
    public static void main(String[] args)throws Exception {
        /**
         * 示例 1 运行结果
         *
         */
        MyTest16 loader1 = new MyTest16("loader1");
        MyTest16 loader2 = new MyTest16("loader2");

        loader1.setPath("/c/Users/dong/Desktop");
        loader2.setPath("/c/Users/dong/Desktop");

        Class<?> clazz1 = loader1.loadClass("com.itcast.jvm.classloader.MyPerson");
        Class<?> clazz2 = loader2.loadClass("com.itcast.jvm.classloader.MyPerson");
        System.out.println(clazz1 == clazz2);
        //创建实例对象
        Object object1 = clazz1.newInstance();
        Object object2 = clazz2.newInstance();
        //根据实例对象获取方法，指定方法名和参数类型
        Method method = clazz1.getMethod("setMyPerson", Object.class);
        //调用此Method对象表示的基本方法，使用指定的参数指定的对象上。 个人参数自动解开，以匹配原始形式参数，并且两个基元和参考参数如有方法调用转换为必要的。
        //调用object1实例对象的方法，并传入object2参数
        method.invoke(object1,object2);
    }
}
