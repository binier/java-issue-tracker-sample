package com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static final String DB_NAME = "students";

    private Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        createDatabase();
        createIssuesTable();
    }

    public static void createDatabase() throws SQLException, IOException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/?useSSL=false",
                    USER, PASSWORD);
            statement = connection.createStatement();
            String createDB = "CREATE DATABASE students";
            //To create database
            statement.executeUpdate(createDB);
            System.out.println("Database created!");


        } catch (SQLException sqlException) {
            if (sqlException.getErrorCode() == 1007) {
                // Database already exists error
                System.out.println(sqlException.getMessage());
            } else {
                // Some other problems, e.g. Server down, no permission, etc
                sqlException.printStackTrace();
            }
        } catch (Exception e) {
            // No driver class found!
        }
    }

    public static void createIssuesTable() throws SQLException, IOException {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME + "?useSSL=false",
                    USER, PASSWORD);
            stmt = conn.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS issues (id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), description VARCHAR(255), severity INTEGER, status VARCHAR(255),createdDate DATETIME DEFAULT CURRENT_TIMESTAMP,statusChangeDate DATETIME )";
            //To create database
            stmt.executeUpdate(createTable);
            System.out.println("Table created!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {

        }

    }

}