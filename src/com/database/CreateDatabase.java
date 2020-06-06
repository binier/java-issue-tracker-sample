package com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateDatabase extends Database {
    public static void main(String[] args) throws SQLException, IOException {
        createDatabase();
        createTables();
        createIssueTrigger();
    }

    /**
     * Check if connection is success and database don't exist
     * Create new database and name will be Database DB_NAME parameter
     * @throws SQLException
     * @throws IOException
     */

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

    /**
     * Create statements for two table issue and comment
     * Insert into database this two table
     * @throws SQLException
     * @throws IOException
     */

    public static void createTables() throws SQLException, IOException {
        try {
            Statement stmt = getInstance().getConnection().createStatement();
            String createIssues = "CREATE TABLE IF NOT EXISTS issue (id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, title VARCHAR(255), description VARCHAR(255), severity INTEGER, status VARCHAR(255),createdDate DATETIME DEFAULT CURRENT_TIMESTAMP,statusChangeDate DATETIME )";
            String createComment = "CREATE TABLE IF NOT EXISTS comment (id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, issueId INTEGER, text VARCHAR(255), severity INTEGER, status VARCHAR(255),createdDate DATETIME DEFAULT CURRENT_TIMESTAMP)";
            //To create table
            stmt.executeUpdate(createIssues);
            stmt.executeUpdate(createComment);
            System.out.println("Tables created!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {

        }
    }

    public static void createIssueTrigger() throws SQLException, IOException {
        try {
            Statement stmt = getInstance().getConnection().createStatement();

            String createUpdateTrigger = String.join("\n"
                    , "DELIMITER $$"
                    , "CREATE TRIGGER issue_before_update"
                    , "BEFORE UPDATE ON issue"
                    , "FOR EACH ROW"
                        , "BEGIN"
                        , "IF OLD.status != NEW.status THEN BEGIN"
                            , "SET NEW.statusChangeDate = CURRENT_TIMESTAMP;"
                        , "END; END IF;"
                    , "END$$"
                    , "DELIMITER ;"
            );
            System.out.println(createUpdateTrigger);

            stmt.executeUpdate(createUpdateTrigger);
            System.out.println("issue_before_Update Trigger created!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {

        }
    }

    public CreateDatabase() throws SQLException, IOException {
    }
}