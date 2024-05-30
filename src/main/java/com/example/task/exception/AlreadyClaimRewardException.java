package com.example.task.exception;

public class AlreadyClaimRewardException extends RuntimeException {
    public AlreadyClaimRewardException(Long taskId) {
        super("Already claimed reward for task with task id " + taskId);
    }
}