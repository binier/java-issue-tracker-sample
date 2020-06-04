package com.logger;

import com.models.IssueModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {
    private static Server instance;

    private static final String CONNECTION_STRING =  "jdbc:mysql://172.22.0.2:3306/students?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connection;
    private ServerSocket server;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Server() throws SQLException, IOException {
        server = new ServerSocket(8080);
        socket = server.accept();
        input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
        connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
    }

    public static Server getInstance() throws SQLException, IOException{
        if(instance == null){
            instance = new Server();
        }
        return instance;
    }

    public void insert(IssueModel issue) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
        "INSERT INTO issues (title, description, date, severity, status, statusChangeDate) VALUES (?, ?, ?, ?, ?, ?);"
        );
        preparedStatement.setString(1, issue.getTitle());
        preparedStatement.setString(2, issue.getDescription());
        preparedStatement.setDate(3, (Date) issue.getDate());
        preparedStatement.setInt(4, issue.getSeverity());
        preparedStatement.setString(5, issue.getStatus());
        preparedStatement.setDate(6, (Date) issue.getDate());

        preparedStatement.execute();
    }

    public void readByTitle(String issueTitle) throws  SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
        "SELECT * FROM issues WHERE title=?;"
        );
        preparedStatement.setString(1,issueTitle);
        preparedStatement.execute();
    }

    public void update(IssueModel newContent, IssueModel oldContent, String... columnNames) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
            String.format(
                    "UPDATE issues SET title=?, description=?, date=?, severity=?, status=? WHERE title={0}",
                    oldContent.getTitle()
            )
        );

        Date statusChangeDate = (Date) new java.util.Date(System.currentTimeMillis());

        preparedStatement.setString(1, newContent.getTitle());
        preparedStatement.setString(2, newContent.getDescription());
        preparedStatement.setDate(3, (Date) newContent.getDate());
        preparedStatement.setInt(4, newContent.getSeverity());
        preparedStatement.setString(5, newContent.getStatus());
        preparedStatement.setDate(5, statusChangeDate);

        preparedStatement.execute();
    }

    public void delete(String title) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE * FROM issues WHERE title=?;"
        );
        preparedStatement.setString(1,title);
        preparedStatement.execute();
    }

}
