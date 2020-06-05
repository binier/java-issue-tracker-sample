package com.logger;

public class DefaultLogger implements Logger<String> {
    @Override
    public void info(String data) {
        System.out.println("LOG: " + data);
    }

    @Override
    public void warn(String data) {
        System.out.println("WARN: " + data);
    }

    @Override
    public void error(String data) {
        System.out.println("ERROR: " + data);
    }
}
