package com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DB_NAME = "students";

    private Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        createDatabase();
        createIssuesTable();
        createIssuesTrigger();
    }

    public static void createDatabase() throws SQLException, IOException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
            statement = connection.createStatement();
            String createDB = "CREATE DATABASE issues";
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
            conn = DriverManager.getConnection(CONNECTION_STRING + "/issues", USER, PASSWORD);
            stmt = conn.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS issues (id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, title VARCHAR(255), description VARCHAR(255), severity INTEGER, status VARCHAR(255),createdDate DATETIME DEFAULT CURRENT_TIMESTAMP,statusChangeDate DATETIME )";
            //To create database
            stmt.executeUpdate(createTable);
            System.out.println("Table created!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {

        }
    }

    public static void createIssuesTrigger() throws SQLException, IOException {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING + "/issues", USER, PASSWORD);
            stmt = conn.createStatement();

            String createUpdateTrigger = String.join("\n"
                    , "DELIMITER $$"
                    , "CREATE TRIGGER issues_before_update"
                    , "BEFORE UPDATE ON issues"
                    , "FOR EACH ROW"
                        , "BEGIN"
                        , "IF OLD.status != NEW.status THEN BEGIN"
                            , "SET NEW.statusChangeDate = CURRENT_TIMESTAMP;"
                        , "END; END IF;"
                    , "END$$"
                    , "DELIMITER ;"
            );
            System.out.println(createUpdateTrigger);

            //To create database
            stmt.executeUpdate(createUpdateTrigger);
            System.out.println("Issues_Update Trigger created!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {

        }
    }

}