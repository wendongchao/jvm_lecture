package com.itcast.jvm.classloader;

public class MyTest6 {
    public static void main(String[] args) {
        /**
         * 调用类的静态方法，符合主动使用第3条，
         * 从上到下，先准备（连接中操作）后初始化
         */
        Singleton singleton = Singleton.getInstance();
        System.out.println("counter1:"+Singleton.counter1);
        System.out.println("counter2:"+Singleton.counter2);
    }
}

class Singleton {

    /**
     * 示例 3 结果
     * 2
     * 1
     * counter1:2
     * counter2:0
     */
    public static int counter1 = 1;


   // public static int counter1;
    /**
     * 示例 1 结果
     * counter1:1
     * counter2:1
     */
    //public static int counter2 = 0;

    private static Singleton singleton = new Singleton();

    private Singleton(){
        counter1++;
        counter2++;
        System.out.println(counter1);
        System.out.println(counter2);
    }

    /**
     * 示例 2 结果
     * counter1:1
     * counter2:0
     * 调用类的静态方法，符合主动使用第3条
     * 1.把counter1初始化为0，
     * 2.new Singleton()主动使用，执行构造方法（符合主动使用第1条）
     * 3.counter1-》1，counter2-》先赋予默认值0，后++为1，
     * 4.初始化counter2为0
     */
    public static int counter2 = 0;

    public static Singleton getInstance(){
        return singleton;
    }
}
