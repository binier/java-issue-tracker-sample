package com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;

    protected static final String DB_NAME = "issue_tracker";
    protected static final String OPTIONS = "?useSSL=false&createDatabaseIfNotExist=true";
    protected static final String CONNECTION_STRING =  "jdbc:mysql://127.0.0.1:3306/" + DB_NAME + OPTIONS;
    private static final String USER = "root";
    private static final String PASSWORD = "";

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
