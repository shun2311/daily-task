package com.example.task.exception;

public class AlreadyLoginException extends RuntimeException {
    public AlreadyLoginException(String username) {
        super("User " + username  + " already login.");
    }
}