package com.command;

public class UnknownActionException extends Exception {
    String action;
    public UnknownActionException(String action) {
        super("unknown action");
        this.action = action;
    }
}
