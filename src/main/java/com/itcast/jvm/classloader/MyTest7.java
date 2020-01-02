package com.itcast.jvm.classloader;

/**
 * getClassLoader:
 * Returns the class loader for the class.  Some implementations may use
 * null to represent the bootstrap class loader. This method will return
 * null in such implementations if this class was loaded by the bootstrap
 * class loader.
 * 返回该类的类加载器。 有些实现可能使用null表示引导类加载器（启动类加载器）。 如果该类被引导类加载器加载，此方法将在这样的实现返回null。
 *
 * <p> If a security manager is present, and the caller's class loader is
 * not null and the caller's class loader is not the same as or an ancestor of
 * the class loader for the class whose class loader is requested, then
 * this method calls the security manager's {@code checkPermission}
 * method with a {@code RuntimePermission("getClassLoader")}
 * permission to ensure it's ok to access the class loader for the class.
 * 如果安全管理器存在，并且调用者的类加载器不为空并调用者的类装载程序是不一样的或类加载器，
 * 它的类加载器所请求的类的一个祖先，则此方法调用安全 经理{@code检查权限}方法与{@code运行权限（“获取类加载器”）}权限，
 * 以确保它的确定访问类加载器的类。
 * <p>If this object
 * represents a primitive type or void, null is returned.
 * 如果该对象表示一个基本类型或空值，则返回空。
 *
 *
 */
public class MyTest7 {
    public static void main(String[] args) throws Exception {
        /**
         * 示例 1 运行结果
         * null
         * 符合：如果该对象表示一个基本类型或空隙，则返回空。
         * 加载String的加载器是启动类加载器
         */
//        Class<?> clazz = Class.forName("java.lang.String");
//        System.out.println(clazz.getClassLoader());

        /**
         * 示例 2 运行结果
         * sun.misc.Launcher$AppClassLoader@73d16e93
         * 类C的加载器是AppClassLoader系统（应用）类加载器，
         * 应用类加载器专门加载工程文件
         */
        Class<?> clazz2 = Class.forName("com.itcast.jvm.classloader.C");
        System.out.println(clazz2.getClassLoader());
    }
}
class C {

}