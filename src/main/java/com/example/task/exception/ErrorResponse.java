package com.example.task.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
}
