package com.test.socket;

import com.logger.DefaultLogger;
import com.socket.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void start() {
        DefaultLogger loger = new DefaultLogger();
        Server server = new Server(2000, loger);

    }

    @Test
    void run() {
        DefaultLogger loger = new DefaultLogger();
        Server server = new Server(2000,loger);
    }
}