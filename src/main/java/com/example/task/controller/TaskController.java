package com.example.task.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.task.model.Task;
import com.example.task.service.TaskService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PatchMapping(value = "/login")
    public ResponseEntity<Void> login(
        @RequestParam @NotBlank String username) {
            return new ResponseEntity<>(taskService.login(username), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Task>> viewTaskList(
        @RequestParam @NotBlank String username) {
            return new ResponseEntity<>(taskService.viewTaskList(username), HttpStatus.OK);
    }

    @PatchMapping(value = "{taskId}/complete")
    public ResponseEntity<Task> completeTask(
        @PathVariable @Valid Long taskId,
        @RequestParam @NotBlank String username) {
            return new ResponseEntity<>(taskService.completeTask(username, taskId), HttpStatus.OK);
    }

    @PatchMapping(value = "{taskId}/claim-reward")
    public ResponseEntity<Task> claimTaskReward(
        @PathVariable @Valid Long taskId,
        @RequestParam @NotBlank String username) {
            return new ResponseEntity<>(taskService.claimTaskReward(username, taskId), HttpStatus.OK);
    }
}
