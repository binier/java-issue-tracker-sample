package com.socket;

import com.logger.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <h2>Socket Server</h2>
 *
 * Handles incoming connection requests and passes them to {@link ClientHandler ClientHandler}.
 */
public class Server implements Runnable {
    int port;
    Logger logger;
    ServerSocket sock;
    Thread thread;

    /**
     * <h2>Initializes but does <b>NOT</b> start the <b>Server</b>.</h2>
     *
     * {@link Server#start Server.start()} needs to be called for it to actually work
     *
     * @param port
     * @param logger
     */
    public Server(int port, Logger logger) {
        this.port = Math.abs(port);
        this.logger = logger;
    }

    /**
     * Initializes socket server and runs
     * {@link Server#run Server.run()} in a new thread.
     *
     * @throws IOException
     */
    public void start() throws IOException {
        this.sock = new ServerSocket(this.port);
        this.thread = new Thread(this);
        thread.start();
    }

    /**
     * <h2>Run loop to accept connections</h2>
     *
     * For each new connection it creates new {@link ClientHandler ClientHandler}
     * and calls {@link ClientHandler#start ClientHandler.start()} on it.
     */
    @Override
    public void run() {
        while(true) {
            try {
                ClientHandler client = new ClientHandler(this.sock.accept());
                client.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
