package com.itcast.jvm.classloader;

/**
 * A class loader is an object that is responsible for loading classes. The class ClassLoader is an abstract class.
 * Given the binary name of a class, a class loader should attempt to locate or generate data that constitutes a definition for the class.
 * A typical strategy is to transform the name into a file name and then read a "class file" of that name from a file system.
 * Every Class object contains a reference to the ClassLoader that defined it.
 * 一个类加载器是一个对象，是负责加载类。 类装载器是一个抽象类。
 * 根据类的二进制名称，一个类加载器应该试图找到或生成构成类定义的数据（找到.class并把二进制数据加载到内存中）。
 * 一个典型的策略是将名称从文件系统转换成文件名，然后读取该名称的“class file”。
 * 每一个类对象包含到所述类装载器定义它的引用。(每一个类对象包含加载它的类加载器的引用)
 * Class objects for array classes are not created by class loaders,but are created automatically as required by the Java runtime.
 * The class loader for an array class, as returned by Class.getClassLoader() is the same as the class loader for its element type;
 * if the element type is a primitive type, then the array class has no class loader.
 * Applications implement subclasses of ClassLoader in order to extend the manner in which the Java virtual machine dynamically loads classes.
 *  * 对于数组类类对象不是由类加载器创建的，但所要求的Java运行时自动创建。
 *  * 用于阵列的类，由Class.getClassloader（）所返回的类加载器是相同的类加载器以其元素类型;（数组的实例的类加载器和元素实例的类加载器相同）
 *  * 如果元素类型是基本类型，则该数组类没有类加载器。
 *  * 应用程序实现类装载器的子类，以延长的方式，其中Java虚拟机动态地加载类
 * Class loaders may typically be used by security managers to indicate security domains.
 * The ClassLoader class uses a delegation model to search for classes and resources.
 * Each instance of ClassLoader has an associated parent class loader.
 * 类装载器类使用委托模型来搜索类和资源。 类装载器的每个实例具有相关联的父类加载器。
 * When requested to find a class or resource, a ClassLoader instance will delegate the search for the class or resource to its parent class loader before attempting to find the class or resource itself.
 * 当要求找到一个类或资源，类加载器实例将在自己搜索类或资源之前先委派父类加载器搜索类或资源。
 * The virtual machine's built-in class loader, called the "bootstrap class loader", does not itself have a parent but may serve as the parent of a ClassLoader instance.
 * 虚拟机的内置类加载器，称为“引导类加载器”，本身并不具有父，但可以作为一个类加载器实例的父。
 * Class loaders that support concurrent loading of classes are known as parallel capable class loaders and are required to register themselves at their class initialization time by invoking the ClassLoader.registerAsParallelCapable method.
 * Note that the ClassLoader class is registered as parallel capable by default.
 * However, its subclasses still need to register themselves if they are parallel capable.
 * 支持的类装载并发类加载器被称为并行能的类加载器，并且需要通过调用类Loader.register作为并行方法有能力在其类初始化的时间来注册自己。 请注意，类装载器类被注册为能够并行默认。 然而，它的子类还需要注册自己，
 * In environments in which the delegation model is not strictly hierarchical, class loaders need to be parallel capable, otherwise class loading can lead to deadlocks because the loader lock is held for the duration of the class loading process (see loadClass methods).
 * 如果他们是平行的能力。 在其中委派模型是不是严格的等级的环境中，类加载器需要是能够平行，否则因为加载锁被保持为类加载过程的持续时间类加载可导致死锁（见负载类方法）。
 * Normally, the Java virtual machine loads classes from the local file system in a platform-dependent manner.
 * 正常情况下，在依赖于平台的方式在本地文件系统中的Java虚拟机加载类。
 * For example, on UNIX systems, the virtual machine loads classes from the directory defined by the CLASSPATH environment variable.
 * 例如，在UNIX系统上，从目录中虚拟机加载类由CLASSPATH环境变量定义。
 * However, some classes may not originate from a file; they may originate from other sources, such as the network, or they could be constructed by an application. The method defineClass converts an array of bytes into an instance of class Class.
 * Instances of this newly defined class can be created using Class.newInstance.
 * 然而，有些类可能不会来自文件; 它们可从其他来源，例如网络发起，或者它们可以由一个应用程序来构造。 该方法定义了类字节数组转换成类类的一个实例。 这种新定义的类的实例可以使用Class.new实例被创建。
 * The methods and constructors of objects created by a class loader may reference other classes.
 * 由类加载器创建的对象的方法和构造可引用其他类。
 * To determine the class(es) referred to, the Java virtual machine invokes the loadClass method of the class loader that originally created the class.
 * For example, an application could create a network class loader to download class files from a server. Sample code might look like:
 *  为了确定类（ES）的简称，在Java虚拟机调用最初创建该类的类加载器的负载类方法。 例如，一个应用程序可以创建一个网络类加载器从服务器上下载类文件。 示例代码可能看起来像：
 *      ClassLoader loader = new NetworkClassLoader(host, port);
 *      Object main = loader.loadClass("Main", true).newInstance();
 *           . . .
 *
 * The network class loader subclass must define the methods findClass and loadClassData to load a class from the network.
 * Once it has downloaded the bytes that make up the class,
 * it should use the method defineClass to create a class instance.
 * 网络类加载器的子类必须定义方法找到类和负载类的数据从网络加载类。
 * 一旦已下载组成该类的字节，它应该使用方法定义类来创建类的实例
 * A sample implementation is:
 *        class NetworkClassLoader extends ClassLoader {
 *            String host;
 *            int port;
 *
 *            public Class findClass(String name) {
 *                byte[] b = loadClassData(name);
 *                return defineClass(name, b, 0, b.length);
 *            }
 *
 *            private byte[] loadClassData(String name) {
 *                // load the class data from the connection
 *                 . . .
 *            }
 *        }
 */
public class MyTest15 {
    public static void main(String[] args) {
        /**
         * 示例 1 运行结果
         * null,启动类加载器
         * 用于阵列的类，由Class.getClassLoader()所返回的类加载器与其元素类型的类加载器是相同的类加载器。
         *
         */
        String[] strings = new String[2];
        System.out.println(strings.getClass().getClassLoader());
        System.out.println("-----------------");
        /**
         * 示例 2 运行结果
         * sun.misc.Launcher$AppClassLoader@73d16e93
         * 用于阵列的类，由Class.getClassLoader()所返回的类加载器与其元素类型的类加载器是相同的类加载器。
         */
        MyTest15[] myTest15 = new MyTest15[2];
        System.out.println(myTest15.getClass().getClassLoader());
        System.out.println("-----------------");

        /**
         * 示例 3 运行结果
         * null
         * 如果元素类型是基本类型，则该数组类没有类加载器。
         */
        int[] a = new int[2];
        System.out.println(a.getClass().getClassLoader());
    }
}
