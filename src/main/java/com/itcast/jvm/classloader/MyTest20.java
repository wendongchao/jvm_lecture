package com.itcast.jvm.classloader;

import java.lang.reflect.Method;

public class MyTest20 {
    public static void main(String[] args)throws Exception {
        /**
         * 示例 1 运行结果
         * true
         */
        MyTest16 loader1 = new MyTest16("loader1");
        MyTest16 loader2 = new MyTest16("loader2");

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
