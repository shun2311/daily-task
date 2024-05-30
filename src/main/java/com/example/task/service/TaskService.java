package com.example.task.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.task.model.Task;
import com.example.task.model.TaskStatus;
import com.example.task.model.TaskType;
import com.example.task.repository.TaskRepository;

@Service
public class TaskService {
    TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    // Daily task refresh
    @Scheduled(cron = "${task.scheduled.daily.cron}")
    public void refreshDailyTasks() {
        System.out.println("Job running");
        // Write your task logic here
        List<Task> dailyTasks = taskRepository.findAllByType(TaskType.DAILY);

        // Refresh Task
        for(Task dailyTask: dailyTasks) {
            dailyTask.setStatus(TaskStatus.IC);
        }

        taskRepository.saveAll(dailyTasks);
    }

    // Weekly task refresh
    @Scheduled(cron = "${task.scheduled.weekly.cron}")
    public void refreshWeeklyTasks() {
        // Write your task logic here
        List<Task> dailyTasks = taskRepository.findAllByType(TaskType.WEEKLY);

        // Refresh Task
        for(Task dailyTask: dailyTasks) {
            dailyTask.setStatus(TaskStatus.IC);
        }

        taskRepository.saveAll(dailyTasks);
    }
}
