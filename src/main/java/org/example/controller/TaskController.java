package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.TaskDto;
import org.example.enums.TaskStatus;
import org.example.service.TaskService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/")
    public TaskDto createTask(@RequestBody TaskDto task) {
        return taskService.createTask(task);
    }

    @PostMapping("/{taskId}/subtasks")
    public TaskDto createSubtask(@PathVariable Long taskId, @RequestBody TaskDto subtask) {
        return taskService.createSubtask(taskId, subtask);
    }

    @GetMapping("/")
    public List<TaskDto> getAllTasks(@RequestParam(required = false) TaskStatus status) {
        return taskService.getAllTasks(status);
    }

    @GetMapping("/{taskId}")
    public TaskDto getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @PutMapping("/{taskId}")
    public TaskDto updateTask(@PathVariable Long taskId, @RequestBody TaskDto task) {
        return taskService.updateTask(taskId, task);
    }


    @GetMapping("/{taskId}/subtasks")
    public List<TaskDto> getSubtasks(@PathVariable Long taskId) {
        return taskService.getSubtasks(taskId);
    }

    @GetMapping("/{taskId}/progress")
    public double getTaskProgress(@PathVariable Long taskId) {
        return taskService.getTaskProgress(taskId);
    }

    @PutMapping("/{taskId}/subtasks/{subtaskId}")
    public TaskDto updateSubtask(@PathVariable Long taskId, @PathVariable Long subtaskId, @RequestBody TaskDto subtask) {
        return taskService.updateSubtask(taskId, subtaskId, subtask);
    }

    @PatchMapping("/{taskId}/status")
    public TaskDto updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
        return taskService.updateTaskStatus(taskId, status);
    }

    @PatchMapping("/{taskId}/subtasks/{subtaskId}/status")
    public TaskDto updateSubtaskStatus(@PathVariable Long taskId, @PathVariable Long subtaskId, @RequestParam TaskStatus status) {
        return taskService.updateSubtaskStatus(taskId, subtaskId, status);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}