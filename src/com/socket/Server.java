package com.socket;

import com.logger.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    int port;
    Logger logger;
    ServerSocket sock;
    Thread thread;

    public Server(int port, Logger logger) {
        this.port = Math.abs(port);
        this.logger = logger;
    }

    public void start() throws IOException {
        this.sock = new ServerSocket(this.port);
        this.thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                ClientHandler client = new ClientHandler(this.sock.accept());
                client.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
