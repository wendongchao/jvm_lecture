package com.itcast.jvm.classloader;

import com.sun.crypto.provider.AESKeyGenerator;

/**
 * 扩展类加载器
 * sun.misc.Launcher$ExtClassLoader@232204a1
 * sun.misc.Launcher$AppClassLoader@73d16e93
 */
public class MyTest19 {
    public static void main(String[] args) {
        AESKeyGenerator aesKeyGenerator = new AESKeyGenerator();
        System.out.println(aesKeyGenerator.getClass().getClassLoader());
        System.out.println(MyTest19.class.getClassLoader());

    }
}
