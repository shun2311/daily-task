package com.example.task.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;

@Service
public class TaskService {
    TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
}
