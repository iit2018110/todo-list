package org.example.service;

import org.example.dto.TaskDto;
import org.example.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto task);
    List<TaskDto> getAllTasks(TaskStatus status);
    TaskDto getTaskById(Long taskId);
    TaskDto updateTask(Long taskId, TaskDto task);
    void deleteTask(Long taskId);
    TaskDto createSubtask(Long taskId, TaskDto subtask);
    List<TaskDto> getSubtasks(Long taskId);
    double getTaskProgress(Long taskId);
    TaskDto updateSubtask(Long taskId, Long subtaskId, TaskDto subtask);
    TaskDto updateTaskStatus(Long taskId, TaskStatus status);
    TaskDto updateSubtaskStatus(Long taskId, Long subtaskId, TaskStatus status);
}