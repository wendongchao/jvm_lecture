package com.itcast.jvm.classloader;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author: dong
 * @description:
 * @date: created in 2020/2/3 11:53
 * @modified by:
 */
public class MyTest27 {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mytestdb", "username", "password");
    }
}
