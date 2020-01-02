package com.itcast.jvm.classloader;

/**
 * 示例 1
 *      类只有在java程序首次主动主动使用时才初始化他们，符合主动使用第 1条
 * 示例 2
 *      引用类型数组并没有对类进行主动使用，因此并没有导致对类的初始化
 * 对于数组实例来说，其类型是由jvm在运行期动态生成的，表示为 [Lcom.itcast.jvm.classloader.MyParent4
 * 这种形式，动态生成的类型，其父类型就是Object。
 * 对于数组来说，javaDoc经常将构成数组元素视为Component，实际上就是将数组降低一个维度后的类型。
 * 助记符：
 * anewarray：表示创建一个引用类型的（如类，接口，数组）数组，并将其引用值压入栈顶。
 * newarray：表示创建一个指定的原始类型的（如int，float，char等）数组，并将其引用值压入栈顶。
 */

/**
 * 示例 2
 * 当创建一个数组类型的实例时，并不表示我对数组中元素的主动使用，仅仅表示创建了一个数组的实例
 * 这个数组的实例类型是在运行期由jvm动态创建出来的
 */
public class MyTest4 {
    public static void main(String[] args) {
        /**
         * 示例 1 运行结果
         * MyParent4 static block
         * ===============
         */
//        MyParent4 myParent4 = new MyParent4();
//        System.out.println("===============");
//        MyParent4 myParent5 = new MyParent4();

        /**
         * 示例 2 运行结果
         * 什么都没有
         */
        MyParent4[] myParent4s = new MyParent4[1];
        System.out.println(myParent4s.getClass());
        System.out.println(myParent4s.getClass().getSuperclass());
    }
}
class MyParent4 {
    /**
     * 静态代码块初始化时调用
     */
    static {
        System.out.println("MyParent4 static block");
    }
}