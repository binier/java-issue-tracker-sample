package com.logger;

public interface Logger<T> {
    void info(T data);
    void warn(T data);
    void error(T data);
}
