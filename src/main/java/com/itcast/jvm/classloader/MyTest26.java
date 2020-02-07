package com.itcast.jvm.classloader;

/**
 * @author: dong
 * @description:
 * @date: created in 2020/1/17 22:00
 * @modified by:
 */

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 线程上下文类加载器的一般使用模式（获取 - 使用 - 还原）
 * ClassLoader classloader = Thread.currentThread().getContextClassLoader();
 * try{
 *     Thread.currentThread().setContextClassLoader(targetTccl);
 *     myMethod();
 * }finally {
 *     Thread.currentThread().setContextClassLoader(classloader);
 * }
 * myMethod里面则调用了Thread.currentThread().getContextClassLoader()，获取当前线程的上下文类加载器做某些事情。
 * 如果一个类由类加载器A加载，那么这个类的依赖类也是由相同的类加载器加载的（如果该依赖类之前没有被加载过的话）
 * ContextClassLoader的作用就是为了破坏java的类加载委托机制。
 * 当高层提供统一的接口让底层去实现，同时又要在高层加载（或实例化）底层的类时，就必须要通过线程上下文类加载器来帮助高层的ClassLoader
 * 找到并加载该类。
 */

/**
 * ServiceLoader:
 * A simple service-provider loading facility.（facility：设施）
 * 一个简单的服务提供者装载设施
 * A service is a well-known set of interfaces and (usually abstract) classes.
 * 服务是公知的接口和（通常是抽象）的类
 * A service provider is a specific implementation of a service. （specific：具体）
 * 服务提供者是服务的具体实现
 * The classes in a provider typically implement the interfaces and subclass the classes defined in the service itself. （typically：一般，defined：定义）
 * 在提供者中的类通常实现的接口和子类中的服务本身定义的类
 * Service providers can be installed in an implementation of the Java platform in the form of extensions, （installed：安装，platform：平台，extensions：扩展）
 * 服务提供商可以安装在Java平台的扩展形式实现
 * that is, jar files placed into any of the usual extension directories.
 * 即，jar文件放入任意常用的扩展目录
 * Providers can also be made available by adding them to the application's class path or by some other platform-specific means.（available：可得到，means：手段，方式）
 * 供应商还可以通过将其添加到application的类路径或其他一些特定于平台的方式提供
 * For the purpose of loading, a service is represented by a single type, （purpose：目的，represented：代表）
 * 用于装载的目的，一个服务是由单一类型的表示
 * that is, a single interface or abstract class. (A concrete class can be used, but this is not recommended.) （concrete：具体，recommended：不推荐）
 * 即，单个接口或抽象类
 * A provider of a given service contains one or more concrete classes that extend this service type with data and code specific to the provider.(contains:包含)
 * 一个给定的服务的提供者包含一个或多个延伸与数据和代码特定于提供商这种服务类型的具体类
 * The provider class is typically not the entire provider itself but rather a proxy which contains enough information to decide whether the provider is able to satisfy a particular request together with code that can create the actual provider on demand.
 * 提供者类通常不是整个提供者本身而是一个代理包含足够的信息来决定提供者是否能满足在一起特定请求的代码，可以根据需要创建实际提供者
 * The details of provider classes tend to be highly service-specific; no single class or interface could possibly unify them, so no such type is defined here.
 * 提供者类的细节往往是高度服务特定的; 没有单一的类或接口可能可能统一它们，所以没有这样的类型在这里被定义
 * The only requirement enforced by this facility is that provider classes must have a zero-argument constructor so that they can be instantiated during loading.
 * 该工厂实施的唯一要求是提供程序类必须有一个无参数的构造函数，以便它们可以在加载中被实例化。
 * A service provider is identified by placing a provider-configuration file in the resource directory META-INF/services.
 * 服务提供商通过将供应商的配置文件中的资源目录META-INF/services
 * The file's name is the fully-qualified binary name of the service's type.
 * 该文件的名称是服务的类型的完全限定二进制名称。
 * The file contains a list of fully-qualified binary names of concrete provider classes, one per line. Space and tab characters surrounding each name, as well as blank lines, are ignored.
 * 该文件包含的具体提供者类，每行一个完全限定二进制名称列表。 周围的每一个名字，以及空行空格，制表符，将被忽略
 * The comment character is '#' ('\u0023', NUMBER SIGN); on each line all characters following the first comment character are ignored. The file must be encoded in UTF-8.
 * 注释字符为 '＃'（ '\ u0023'，NUMBER SIGN）; 在每一行的第一个注释字符的所有字符都被忽略。 该文件必须以UTF-8编码。
 * If a particular concrete provider class is named in more than one configuration file, or is named in the same configuration file more than once, then the duplicates are ignored.
 * 如果一个特定的具体提供者类是在超过一个配置文件命名，或在同一配置文件中被命名为超过一次，则副本会被忽略。
 * The configuration file naming a particular provider need not be in the same jar file or other distribution unit as the provider itself.
 * 配置文件命名特定提供者不必在相同的jar文件或其他分配单元作为提供程序本身
 * The provider must be accessible from the same class loader that was initially queried to locate the configuration file;
 * 供应商必须从最初查询找到配置文件中的类加载器访问;
 * note that this is not necessarily the class loader from which the file was actually loaded.
 * 注意，这是不一定的类加载器从该文件实际加载
 * Providers are located and instantiated lazily, that is, on demand.
 * 提供商的位置和懒地实例化，也就是需求。
 * A service loader maintains a cache of the providers that have been loaded so far.
 * 服务加载器维护到目前为止已经加载的提供者缓存。
 * Each invocation of the iterator method returns an iterator that first yields all of the elements of the cache, in instantiation order, and then lazily locates and instantiates any remaining providers, adding each one to the cache in turn.
 * 迭代方法返回的每个调用的迭代器，第一收率所有高速缓存的元素，在实例化命令，然后懒惰地定位并实例化的任何剩余提供商，加入每一个以依次缓存。
 * The cache can be cleared via the reload method.
 * 高速缓存可以通过重载方法来清除
 * Service loaders always execute in the security context of the caller. Trusted system code should typically invoke the methods in this class, and the methods of the iterators which they return, from within a privileged security context.
 * 服务装载机始终在调用者的安全上下文中执行。 受信任的系统代码通常应该在调用这个类中的方法，以及它们返回，从特权安全上下文中的迭代的方法。
 * Instances of this class are not safe for use by multiple concurrent threads.
 * 这个类的实例用于多个并发线程安全使用。
 * Unless otherwise specified, passing a null argument to any method in this class will cause a NullPointerException to be thrown.
 * 除非另有说明，否则将null参数传递给任何方法在这个类会导致空指针异常被抛出。
 * Example Suppose we have a service type com.example.CodecSet which is intended to represent sets of encoder/decoder pairs for some protocol. In this case it is an abstract class with two abstract methods:
 * 例如假设我们有一个服务类型com.example.CodecSet。 编解码器设置，其意在表示集编码器/解码器对用于一些协议。 在这种情况下，它是两个抽象方法的抽象类：
 *    public abstract Encoder getEncoder(String encodingName);
 *    public abstract Decoder getDecoder(String encodingName);
 * Each method returns an appropriate object or null if the provider does not support the given encoding. Typical providers support more than one encoding.
 * 如果提供者不支持给定的编码方法的每个返回一个适当的对象或null。 典型提供商支持多于一个的编码。
 * If com.example.impl.StandardCodecs is an implementation of the CodecSet service then its jar file also contains a file named
 * 如果com.example.impl。 标准编解码器是编解码器设置服务的实现，则其jar文件还包含一个指定的文件
 *    META-INF/services/com.example.CodecSet
 * This file contains the single line:
 *    com.example.impl.StandardCodecs    # Standard codecs
 * The CodecSet class creates and saves a single service instance at initialization:
 * 编解码器设置类创建和初始化时节省了一个服务实例
 *    private static ServiceLoader<CodecSet> codecSetLoader
 *        = ServiceLoader.load(CodecSet.class);
 * To locate an encoder for a given encoding name it defines a static factory method which iterates through the known and available providers, returning only when it has located a suitable encoder or has run out of providers.
 * 要找到对于给定的编码名称它定义它通过已知的和可用的提供迭代，返回仅当它位于一个适当的编码器或已提供者用完一个静态工厂方法的编码器。
 *   public static Encoder getEncoder(String encodingName) {
 *        for (CodecSet cp : codecSetLoader) {
 *            Encoder enc = cp.getEncoder(encodingName);
 *            if (enc != null)
 *                return enc;
 *        }
 *        return null;
 *    }
 */
public class MyTest26 {
    public static void main(String[] args) {
        /**
         * 示例 2 输出结果
         * 当前线程上下文类加载器：sun.misc.Launcher$ExtClassLoader@15db9742
         * ServiceLoader的类加载器：null
         * MyTest26的类加载器是AppClassLoader，它的父类加载器是ExtClassLoader，扩展类加载器加载不了classpath路径下的类
         * 所以驱动配置文件没有加载
         */
        Thread.currentThread().setContextClassLoader(MyTest26.class.getClassLoader().getParent());

        /**
         * 示例 1 运行结果
         * dirver: class com.mysql.jdbc.Driver, loader: sun.misc.Launcher$AppClassLoader@73d16e93
         * dirver: class com.mysql.fabric.jdbc.FabricMySQLDriver, loader: sun.misc.Launcher$AppClassLoader@73d16e93
         * 当前线程上下文类加载器：sun.misc.Launcher$AppClassLoader@73d16e93
         * ServiceLoader的类加载器：null
         * 从运行结果可以看出：启动类可以加载数据库驱动，上下文类加载器是系统类加载器，
         * ServiceLoader的类加载器是启动类加载器，有系统类加载器加载数据库驱动的具体实现。
         * 驱动类在classpath下，所以类加载器是AppClassLoader
         */
        /**
         * 为什么ServiceLoader可以加载供应商提供的数据库驱动的具体实现？
         * ServiceLoader.load(Driver.class):
         * ServiceLoader加载Driver驱动类（java.sql.Driver），这个类时jdk提供给驱动供应商的接口，驱动供应商实现接口，
         * 来实现自己具体的驱动实现类，
         * ServiceLoader：一个简单的服务提供者装载设施，用于加载服务提供者提供的具体实现驱动类，
         * 这个类有一定的限制：
         * A service provider is identified by placing a provider-configuration file in the resource directory META-INF/services.
         * The file's name is the fully-qualified binary name of the service's type.
         * The file contains a list of fully-qualified binary names of concrete provider classes, one per line. Space and tab characters surrounding each name, as well as blank lines, are ignored.
         * The comment character is '#' ('\u0023', NUMBER SIGN); on each line all characters following the first comment character are ignored. The file must be encoded in UTF-8.
         * 大致意思：需要加载配置文件：META-INF/services下名称与服务名称相同（java.sql.Driver）的配置文件，并加载配置文件中的数据（数据就是服务提供商提供的具体驱动的二进制路径），
         * 加载之后可以具体实例化驱动类，
         * 当然对于服务提供者也有具体的要求：
         * The only requirement enforced by this facility is that provider classes must have a zero-argument constructor so that they can be instantiated during loading.
         * 该工厂实施的唯一要求是提供程序类必须有一个无参数的构造函数，以便它们可以在加载中被实例化。
         */
        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = loader.iterator();
        while (iterator.hasNext()){
            Driver driver = iterator.next();
            System.out.println("dirver: "+driver.getClass()+", loader: "+driver.getClass().getClassLoader());
        }
        System.out.println("当前线程上下文类加载器："+Thread.currentThread().getContextClassLoader());
        System.out.println("ServiceLoader的类加载器："+ServiceLoader.class.getClassLoader());
    }
}
