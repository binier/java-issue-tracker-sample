package com.main;

import com.logger.DefaultLogger;
import com.logger.Logger;
import com.socket.Server;

public class Main {
    public static void main(String[] args) {
        Logger logger = new DefaultLogger();
        Server server = new Server(3002, logger);
        try {
            server.start();
        } catch (Exception ex) {
            System.out.println("Failed to start Socket Server");
            ex.printStackTrace();
        }
    }
}
