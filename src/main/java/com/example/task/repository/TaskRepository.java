package com.example.task.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task.model.Task;
import com.example.task.model.TaskStatus;
import com.example.task.model.TaskType;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserId(Long userId);

    List<Task> findAllByType(TaskType type);

    List<Task> findAllByTypeAndStatus(TaskType type, TaskStatus status);

    Optional<Task> findByTaskIdAndUserId(Long taskId, Long userId);
}