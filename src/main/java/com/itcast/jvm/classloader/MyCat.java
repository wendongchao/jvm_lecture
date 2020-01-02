package com.itcast.jvm.classloader;

public class MyCat {

    public MyCat() {
        System.out.println("MyCat is load by: "+ this.getClass().getClassLoader());
    }
}
