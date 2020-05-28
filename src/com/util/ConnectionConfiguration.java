package com.util;

import java.sql.DriverManager;

import java.sql.Connection;

public class ConnectionConfiguration {
    public static Connection getConnection(){
        Connection connection = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://dolgi.informatik.rwth-aachen.de:3306/ldavlab_idil?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey","ldavlab_idil","Osn6CT6M2dD5VmK3");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
    
    public static Connection getConnection2(){
        Connection connection = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://dolgi.informatik.rwth-aachen.de:3306/ldavlab?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey","ldavlab_idil","Osn6CT6M2dD5VmK3");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
