package com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;

    private static final String CONNECTION_STRING =  "jdbc:mysql://172.22.0.2:3306/students?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public static Database getInstance() throws SQLException, IOException{
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Database() throws SQLException, IOException {
        connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
    }

    public Connection getConnection(){
        return connection;
    }
}
