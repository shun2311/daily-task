package com.example.task.exception;

public class InvalidTaskStatusException extends RuntimeException {
    public InvalidTaskStatusException(String requiredStatus, String actualStatus, String action) {
        super("Invalid task status to " + action +": required " + requiredStatus + " but found " + actualStatus);
    }
}