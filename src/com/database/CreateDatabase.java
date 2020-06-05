package com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateDatabase extends Database {
    public static void main(String[] args) throws SQLException, IOException {
        createDatabase();
        createIssuesTable();
        createIssuesTrigger();
    }

    public static void createDatabase() throws SQLException, IOException {
        try {
            Statement stmt = getInstance().getConnection().createStatement();
            String createDB = "CREATE DATABASE " + DB_NAME;
            //To create database
            stmt.executeUpdate(createDB);
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
        try {
            Statement stmt = getInstance().getConnection().createStatement();
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
        try {
            Statement stmt = getInstance().getConnection().createStatement();

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
//            System.out.println(createUpdateTrigger);

            stmt.executeUpdate(createUpdateTrigger);
            System.out.println("issues_before_Update Trigger created!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {

        }
    }

    public CreateDatabase() throws SQLException, IOException {
    }
}