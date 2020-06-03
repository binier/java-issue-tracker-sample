package com.logger;

public class DefaultLogger implements Logger<String> {
    public void info(String data) {
        System.out.println("LOG: " + data);
    }

    public void warn(String data) {
        System.out.println("WARN: " + data);
    }

    public void error(String data) {
        System.out.println("ERROR: " + data);
    }
}
