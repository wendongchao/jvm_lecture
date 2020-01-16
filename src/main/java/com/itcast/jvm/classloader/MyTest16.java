package com.itcast.jvm.classloader;

import java.io.*;

/**
 * this.defineClass(className,data,0,data.length);
 * Converts an array of bytes into an instance of class Class. Before the Class can be used it must be resolved.
 * 字节数组转换为Class类的一个实例。class在使用之前必须被解析。
 * loadClass(String name, boolean resolve)，二进制名称，是否解析class
 * 加载的类与指定二进制名称。 对按以下顺序类这种方法搜索的默认实现
 * 1.调用{@link #findLoadedClass(String)来检查该类是否已被加载。
 * 2.调用{@link #loadClass(String) <tt>loadClass</tt>}在父类加载器方法。 如果父为空的类装载器则使用启动类加载器来代替.
 * 3.调用{@link #findClass(String)}方法找到的类
 * 如果根据这些步骤找到类，并且resolve为true，这个方法会在解析{@link #resolveClass(Class)}方法在class对象的结果中，
 * ClassLoader的子类被鼓励重写{@link #findClass(String)}方法，而不是调用它。
 * 除非被重写，否则这个方法会同步{@link #getClassLoadingLock <tt>getClassLoadingLock</tt>}方法的结果，
 * 在整个类加载的过程当中，（不重写的话类加载的过程当中会自动调用系统默认的defineClass）
 */

/**
 * 创建自定义类加载器，需要继承ClassLoader，指定名字需要实现有参构造方法
 */
public class MyTest16 extends ClassLoader{

    private String classLoaderName;

    private String path;

    private final String fileExtension = ".class";

    //传入ClassLoader参数，用于设置自定义系统类加载器。
    public MyTest16(ClassLoader classLoader){
        super(classLoader);
    }

    /**
     * 创建了一个自定义加载器的对象，同时将系统类加载器作为当前类加载器的一个父加载器
     * @param classLoaderName
     */
    public MyTest16(String classLoaderName){
        super();//将系统类加载器当做该类加载器的父加载器
        this.classLoaderName = classLoaderName;
    }

    /**
     * 调用该构造方法的前提是已经有了一个类加载器
     * @param classLoaderName
     * @param parent
     */
    public MyTest16(String classLoaderName,ClassLoader parent){
        super(parent);// 显式指定该类加载器的父加载器
        this.classLoaderName = classLoaderName;
    }
    @Override
    public String toString(){
        return "[" +this.classLoaderName + "]";
    }
    public void setPath(String path){
        this.path = path;
    }

    /**
     * 返回class对象
     * 并不是主动调用，是在ClassLoader的loadClass方法中调用，
     */
    @Override
    public Class<?> findClass(String className){
        /**
         * 示例 1 运行结果
         * com.itcast.jvm.classloader.MyTest1@15db9742
         * sun.misc.Launcher$AppClassLoader@73d16e93
         */
        System.out.println("findClass invoked:"+className);
        System.out.println("class loader name:"+this.classLoaderName);
        //根据类路径找到类，并读取该class的二进制数据，
        byte[] data = loadClassData(className);
        //根据class路径和class的二进制数据创建一个class对象
        return this.defineClass(className,data,0,data.length);
    }
    //返回一个class类的二进制数据
    private byte[] loadClassData(String name){
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        try {
           // this.classLoaderName = this.classLoaderName.replace(".","/");
            name = name.replace(".","/");

            is = new FileInputStream(new File(this.path + name + this.fileExtension));
            baos = new ByteArrayOutputStream();
            int ch = 0;
            while ((ch = is.read()) != -1){
                baos.write(ch);
            }
            data = baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
                baos.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        return data;
    }
    public static void test(ClassLoader classLoader) throws Exception {
        Class<?> clazz = classLoader.loadClass("com.itcast.jvm.classloader.MyTest1");
        Object object = clazz.newInstance();//返回一个对象的实例
        /**
         * 示例 1 运行结果
         * com.itcast.jvm.classloader.MyTest1@15db9742
         * sun.misc.Launcher$AppClassLoader@73d16e93
         * 由此可见MyTest1是由系统类加载器加载的，
         * 原因：MyTest16创建的类加载器加载MyTest1时，会根据双亲机制委托给父加载器加载，
         * 父加载器是从classpath路径下加载文件的，在classpath路径下可以找到MyTest1的class文件，所以系统类加载器加载了MyTest1
         * 系统类加载器就是MyTest1的定义类加载器。系统类加载器和MyTest16是MyTest1的初始类加载器
         */
        System.out.println(object);
        System.out.println(object.getClass().getClassLoader());
    }

    public static void main(String[] args) throws Exception {
        //调用构造方法创建类加载器名称是loader1，并将系统类加载器当做该类加载器的父加载器
        MyTest16 myTest16 = new MyTest16("loader1");
        /**
         * 示例 2.1 运行结果
         * clazz:366712642
         * com.itcast.jvm.classloader.MyTest1@6d06d69c
         * sun.misc.Launcher$AppClassLoader@73d16e93
         * 从运行结果可以看出，没有加载自己重写的findClass方法，并且加载器是系统类加载器，
         * 具体原因可参考示例1
         * 示例 2.2 运行结果
         * 执行之前注意删除classpath路径下的MyTest1.class，并且把MyTest1.class和它的路径放到桌面，
         * findClass invoked: com.itcast.jvm.classloader.MyTest1
         * class loader name: loader1
         * clazz:366712642
         * com.itcast.jvm.classloader.MyTest1@6d06d69c
         * sun.misc.Launcher$AppClassLoader@73d16e93
         * 由于前提条件删除classpath路径下的MyTest1.class，和类双亲委派机制可知，
         * 启动类加载器，扩展类加载器，系统类加载器在各自可以加载的路径下找不到MyTest1.class,
         * 所以由自定义加载器loader1加载，因为loader1加载器可以在给定的路径下找到MyTest1.class，
         * 所以MyTest1.class的加载器是loader1
         *
         */
        // 示例 2.1
//        myTest16.setPath("/d/workspace/idea/jvm_lecture/build/classes/java/main");
        // 示例 2.2
        myTest16.setPath("/c/Users/dong/Desktop");

        Class<?> clazz = myTest16.loadClass("com.itcast.jvm.classloader.MyTest1");
        System.out.println("clazz:"+clazz.hashCode());
        Object object = clazz.newInstance();//返回一个对象的实例
        System.out.println(object);
        System.out.println(object.getClass().getClassLoader());
        /**
         * 示例 3
         * 示例 3 和 示例 2 结合运行，
         * 运行结果 3.1
         * 当MyTest1.class没有删除时，运行结果
         * clazz:366712642
         * com.itcast.jvm.classloader.MyTest1@6d06d69c
         * sun.misc.Launcher$AppClassLoader@73d16e93
         * clazz:366712642
         * com.itcast.jvm.classloader.MyTest1@6d06d69c
         * sun.misc.Launcher$AppClassLoader@73d16e93
         * 从运行结果可以看出，两者的输出结果是一样的，加载器都是系统类加载器，当加载器加载同一个类两次时，
         * 加载器会直接从内存中拿出加载第一次的结果。
         * 运行结果 3.2 前提条件 删除classpath路径下的MyTest1.class，并且把MyTest1.class和它的路径放到桌面，
         * findClass invoked: com.itcast.jvm.classloader.MyTest1
         * class loader name: loader1
         * clazz:366712642
         * com.itcast.jvm.classloader.MyTest1@6d06d69c
         * com.itcast.jvm.classloader$loader1@73d16e93
         * findClass invoked: com.itcast.jvm.classloader.MyTest1
         * class loader name: loader2
         * clazz:366712653
         * com.itcast.jvm.classloader.MyTest1@6d06d69c
         * com.itcast.jvm.classloader$loader2@73d16e93
         * 可以看到输出了两个不同的类加载器，loader1，loader2，输出结果参考原因示例 2.2 运行结果原因
         * {@ 还有一个重要的点在这个中，那就是加载器的命名空间
         * 加载器命名空间：
         * 每个类加载器都有自己的命名空间，命名空间由该加载器及所有父加载器所加载的类组成的。
         * 1. 在同一个命名空间中，不会出现类的完整名字（包括类的包名）相同的两个类
         * 2. 在不同的命名空间中，有可能会出现类的完整名字（包括类的包名）相同的两个类 {@link
         * 示例 3.1
         * 同一个加载器命名空间内，不能有相同的被加载类，当加载器加载类的时候先检查加载器命名空间中有没有这个类，
         * 如果有，则直接使用，如果没有就创建并加载这个类
         * 示例 3.2
         * 不同的命名空间中可以有相同的被加载类。由两个不同的加载器加载的类，加载的class是一样的
         */
     //   MyTest16 myTest16_2 = new MyTest16("loader2");
        /**
         * 示例 4 运行结果 前提条件 删除classpath路径下的MyTest1.class，并且把MyTest1.class和它的路径放到桌面，
         * findClass invoked: com.itcast.jvm.classloader.MyTest1
         * class loader name: loader1
         * clazz:366712642
         * com.itcast.jvm.classloader.MyTest1@6d06d69c
         * com.itcast.jvm.classloader$loader1@73d16e93
         * clazz:366712642
         * com.itcast.jvm.classloader.MyTest1@6d06d69c
         * com.itcast.jvm.classloader$loader1@73d16e93
         * 现在的情况是，在加载器loader1命名空间中有MyTest1类，并且loader2的父类加载器是自定义加载器loader1
         * 所以第一个加载MyTest1会调用findClass，第二次加载时直接从loader1的命名空间中取出数据。
         */
        MyTest16 myTest16_2 = new MyTest16("loader2",myTest16);
        myTest16.setPath("/c/Users/dong/Desktop");
        Class<?> clazz2 = myTest16_2.loadClass("com.itcast.jvm.classloader.MyTest1");
        System.out.println("clazz:"+clazz2.hashCode());
        Object object2 = clazz2.newInstance();//返回一个对象的实例
        System.out.println(object2);
        System.out.println(object2.getClass().getClassLoader());

        /**
         * 示例 5 演示类的卸载
         * 运行结果
         * findClass invoked: com.itcast.jvm.classloader.MyTest1
         * class loader name: loader3
         * clazz:366712642
         * com.itcast.jvm.classloader.MyTest1@6d06d69c
         * com.itcast.jvm.classloader$loader3@73d16e93
         * ----------------
         * findClass invoked: com.itcast.jvm.classloader.MyTest1
         * class loader name: loader3
         * clazz:366712635
         * com.itcast.jvm.classloader.MyTest1@6d06d69c
         * com.itcast.jvm.classloader$loader3@73d16e93
         * 由于clazz的hashcode不同，所以这两loader3创建的是不同的class对象，当其中一个class对象不用时，
         * 垃圾回收器会回收class对象，这就是class卸载。
         *
         */
        MyTest16 loader3 = new MyTest16("loader3");
        loader3.setPath("/c/Users/dong/Desktop");
        Class<?> clazz3 = loader3.loadClass("com.itcast.jvm.classloader.MyTest1");
        System.out.println("clazz3:"+clazz3.hashCode());
        Object object3 = clazz3.newInstance();//返回一个对象的实例
        System.out.println(object3);
        System.out.println(object3.getClass().getClassLoader());
        System.out.println("-------------------");
        // 创建新的实例
        loader3 = new MyTest16("loader3");
        loader3.setPath("/c/Users/dong/Desktop");
        Class<?> clazz4 = loader3.loadClass("com.itcast.jvm.classloader.MyTest1");
        System.out.println("clazz4:"+clazz4.hashCode());
        Object object4 = clazz4.newInstance();//返回一个对象的实例
        System.out.println(object4);
        System.out.println(object4.getClass().getClassLoader());

        /**
         * 示例 1 运行结果
         * com.itcast.jvm.classloader.MyTest1@15db9742
         * sun.misc.Launcher$AppClassLoader@73d16e93
         */
        //test(myTest16);
    }
}
