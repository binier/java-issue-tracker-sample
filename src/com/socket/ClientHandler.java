package com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {
    private Socket sock;
    private PrintWriter out;
    private Thread readerThread;

    public ClientHandler(Socket sock) {
        this.sock = sock;
    }

    public synchronized void start() {
        try {
            this.out = new PrintWriter(this.sock.getOutputStream(), true);
            this.readerThread = new Thread(this::runReader);
            this.readerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stop() {
        try {
            this.sock.close();
            this.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.sock = null;
            this.out = null;
        }
    }

    public void runReader() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            String message;
            while((message = in.readLine()) != null) {
                this.send(message);
            }
            in.close();
            this.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void send(String message) {
        this.out.println(message);
    }
}
