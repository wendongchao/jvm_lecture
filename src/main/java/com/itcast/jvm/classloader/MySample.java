package com.itcast.jvm.classloader;

public class MySample {

    public  MySample() {
        System.out.println("MySample is load by: "+ this.getClass().getClassLoader());

        new MyCat();
        System.out.println("MyCat from "+ MyCat.class);
    }
}
