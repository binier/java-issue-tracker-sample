package com.socket;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.net.Socket;

public class ClientHandler {
    private Socket sock;
    public ClientHandler(Socket sock) {
        this.sock = sock;
    }

    public synchronized void start() {
        throw new NotImplementedException();
    }
}
