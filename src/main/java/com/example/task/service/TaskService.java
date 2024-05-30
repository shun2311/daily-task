package com.example.task.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.task.exception.AlreadyClaimRewardException;
import com.example.task.exception.AlreadyLoginException;
import com.example.task.exception.InvalidTaskStatusException;
import com.example.task.model.ClaimStatus;
import com.example.task.model.LoginStatus;
import com.example.task.model.Task;
import com.example.task.model.TaskStatus;
import com.example.task.model.TaskType;
import com.example.task.model.User;
import com.example.task.repository.TaskRepository;
import com.example.task.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskService {
    private static final String INVALID_CREDENTIALS = "Invalid username or password";

    TaskRepository taskRepository;
    UserRepository userRepository;
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    
    public Void login(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException(INVALID_CREDENTIALS)); 

        if (user.getLoginStatus().equals(LoginStatus.LOGIN)) {
            throw new AlreadyLoginException(user.getUsername());
        }
        user.setLoginStatus(LoginStatus.LOGIN);
        userRepository.save(user);
        
        //Complete Sign in Task
        List<Task> signInTasks = taskRepository.findAllByTypeAndStatus(TaskType.SIGNIN, TaskStatus.IC);
        for (Task sigInTask: signInTasks) {
            sigInTask.setStatus(TaskStatus.AA);
        }
        taskRepository.saveAll(signInTasks);

        return null;

    }

    public List<Task> viewTaskList(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException(INVALID_CREDENTIALS)); 

        return taskRepository.findAllByUserId(user.getUserId());

    }

    public Task completeTask(String username, Long taskId) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException(INVALID_CREDENTIALS)); 

        Task task = taskRepository.findByTaskIdAndUserId(taskId, user.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("Invalid task")); 

        // Cannot complete task more than once
        if (task.getStatus().equals(TaskStatus.AA)) {
            throw new InvalidTaskStatusException(TaskStatus.IC.toString(), task.getStatus().toString(), "complete task");
        }
        task.setStatus(TaskStatus.AA);
        taskRepository.save(task);

        return task;
    }

    public Task claimTaskReward(String username, Long taskId) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException(INVALID_CREDENTIALS)); 

        Task task = taskRepository.findByTaskIdAndUserId(taskId, user.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("Invalid task")); 
        
        // Task must be completed before claiming
        if (!task.getStatus().equals(TaskStatus.AA)) {
            throw new InvalidTaskStatusException(TaskStatus.AA.toString(), task.getStatus().toString(), "claim reward");
        }

        // Cannot claim more than once
        if (task.getClaimStatus().equals(ClaimStatus.AA)) {
            throw new AlreadyClaimRewardException(task.getTaskId());
        }

        task.setClaimStatus(ClaimStatus.AA);
        taskRepository.save(task);

        return task;
    }
    // Daily task refresh
    @Scheduled(cron = "${task.scheduled.daily.cron}")
    public void refreshDailyTasks() {
        // Write your task logic here
        List<Task> dailyTasks = taskRepository.findAllByType(TaskType.DAILY);

        // Refresh Task
        for(Task dailyTask: dailyTasks) {
            dailyTask.setStatus(TaskStatus.IC);
            dailyTask.setClaimStatus(ClaimStatus.PA);
        }

        taskRepository.saveAll(dailyTasks);
    }

    // Weekly task refresh
    @Scheduled(cron = "${task.scheduled.weekly.cron}")
    public void refreshWeeklyTasks() {
        // Write your task logic here
        List<Task> weeklyTasks = taskRepository.findAllByType(TaskType.WEEKLY);

        // Refresh Task
        for(Task weeklyTask: weeklyTasks) {
            weeklyTask.setStatus(TaskStatus.IC);
            weeklyTask.setClaimStatus(ClaimStatus.PA);
        }

        taskRepository.saveAll(weeklyTasks);
    }
}
