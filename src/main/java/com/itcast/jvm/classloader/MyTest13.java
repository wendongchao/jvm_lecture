package com.itcast.jvm.classloader;

/**
 * getSystemClassLoader:
 * Returns the system class loader for delegation.  This is the default
 * delegation parent for new <tt>ClassLoader</tt> instances, and is
 * typically the class loader used to start the application.
 * 返回系统类加载器以进行委派。这是新的类加载器实例的默认委托父对象(自定义加载器的默认双亲)，并且通常是用于启动应用程序（系统类加载器）的类加载器。
 *
 * <p> This method is first invoked early in the runtime's startup
 * sequence, at which point it creates the system class loader and sets it
 * as the context class loader of the invoking <tt>Thread</tt>.
 * 在运行时的启动序列中首先调用此方法，此刻它会创建系统类加载器并将其设置为调用<tt>Thread</tt>的上下文类加载器。（默认系统类加载器是Thred的上下文类加载器）
 *
 * <p> The default system class loader is an implementation-dependent
 * instance of this class.
 * 默认系统类加载器是此类的与实现相关的实例。
 *
 * <p> If the system property "<tt>java.system.class.loader</tt>" is defined
 * when this method is first invoked then the value of that property is
 * taken to be the name of a class that will be returned as the system
 * class loader.  The class is loaded using the default system class loader
 * and must define a public constructor that takes a single parameter of
 * type <tt>ClassLoader</tt> which is used as the delegation parent.  An
 * instance is then created using this constructor with the default system
 * class loader as the parameter.  The resulting class loader is defined
 * to be the system class loader.
 * 如果在首次调用此方法时定义了系统属性“ <tt> java.system.class.loader </ tt>”，则该属性的值将作为系统类加载器的名字并返回，返回对象为系统类加载器。
 * 该类使用默认的系统类加载器加载，并且自定义加载器必须定义一个公共构造函数，该构造函数采用类型为<tt>ClassLoader</tt>的单个参数会被作为自定义类加载器的委托父级。
 * 然后使用此构造函数以默认系统类加载器创建自定义类加载器的实例。生成的类加载器被定义为系统类加载器。（自定义的类加载器替换为系统类加载器）
 *
 */
public class MyTest13 {
    public static void main(String[] args) {
        /**
         * 示例 1 运行结果
         * sun.misc.Launcher$AppClassLoader@73d16e93
         * sun.misc.Launcher$ExtClassLoader@15db9742
         * null
         */
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);
        while (null != classLoader){
            classLoader = classLoader.getParent();
            System.out.println(classLoader);
        }
    }
}
