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

    public Connection getConnection(){
        return connection;
    }

}
