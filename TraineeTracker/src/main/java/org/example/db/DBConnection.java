package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    // This class creates DB connection once only
    // First I tried static object getter but it didn't work because of (no) thread safety
    public Connection connection;
    public static DBConnection db = new DBConnection("jdbc:mysql://localhost:3306/ttracker", "root", "");
    public DBConnection(){

    }
    private DBConnection(String url, String username, String password){
        System.out.println("Making database connection connection...");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(Exception e){
            System.out.println("Error while accessing database");
        }
    }
    public DBConnection getDB(){
        return db;
    }
}
