package org.example.service.impl;

import org.example.dto.TaskDto;
import org.example.enums.TaskState;
import org.example.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
class TaskServiceImpl implements TaskService {
    private final Map<Long, TaskDto> taskStore = new HashMap<>();
    private final Map<Long, List<TaskDto>> subtaskStore = new HashMap<>();
    private long taskIdCounter = 1;
    private long subtaskIdCounter = 1;

    @Override
    public TaskDto createTask(TaskDto task) {
        task.setId(taskIdCounter++);
        taskStore.put(task.getId(), task);
        subtaskStore.put(task.getId(), new ArrayList<>());
        return task;
    }

    @Override
    public TaskDto createSubtask(Long taskId, TaskDto subtask) {
        subtask.setId(subtaskIdCounter++);
        subtask.setSubtasks(null);
        subtaskStore.computeIfAbsent(taskId, k -> new ArrayList<>()).add(subtask);
        List<TaskDto> subtasks = taskStore.get(taskId).getSubtasks();
        if (CollectionUtils.isEmpty(subtasks)) {
            subtasks = new ArrayList<>();
            subtasks.add(subtask);
        } else {
            subtasks.add(subtask);
        }
        taskStore.get(taskId).setSubtasks(subtasks);
        return subtask;
    }

    @Override
    public List<TaskDto> getAllTasks(TaskState status) {
        if (status == null) {
            return new ArrayList<>(taskStore.values());
        }
        List<TaskDto> filteredTasks = new ArrayList<>();
        for (TaskDto task : taskStore.values()) {
            if (task.getState() == status) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    @Override
    public TaskDto getTaskById(Long taskId) {
        return taskStore.getOrDefault(taskId, null);
    }

    @Override
    public List<TaskDto> getSubtasks(Long taskId) {
        return subtaskStore.getOrDefault(taskId, new ArrayList<>());
    }

    @Override
    public double getTaskProgress(Long taskId) {
        List<TaskDto> subtasks = subtaskStore.getOrDefault(taskId, new ArrayList<>());
        if (subtasks.isEmpty()) return 0.0;
        long completed = subtasks.stream().filter(s -> s.getState() == TaskState.COMPLETED).count();
        return (double) completed / subtasks.size() * 100;
    }

    @Override
    public TaskDto updateTask(Long taskId, TaskDto task) {
        if (taskStore.containsKey(taskId)) {
            task.setId(taskId);
            taskStore.put(taskId, task);
            return task;
        }
        return null;
    }

    @Override
    public TaskDto updateTaskStatus(Long taskId, TaskState state) {
        TaskDto task = taskStore.get(taskId);
        if (task != null) {
            task.setState(state);
        }
        return task;
    }

    @Override
    public TaskDto updateSubtask(Long taskId, Long subtaskId, TaskDto subtask) {
        List<TaskDto> subtasks = subtaskStore.getOrDefault(taskId, new ArrayList<>());
        for (int i = 0; i < subtasks.size(); i++) {
            if (subtasks.get(i).getId().equals(subtaskId)) {
                subtask.setId(subtaskId);
                subtasks.set(i, subtask);
                return subtask;
            }
        }
        return null;
    }

    @Override
    public TaskDto updateSubtaskStatus(Long taskId, Long subtaskId, TaskState state) {
        List<TaskDto> subtasks = subtaskStore.getOrDefault(taskId, new ArrayList<>());
        for (TaskDto subtask : subtasks) {
            if (subtask.getId().equals(subtaskId)) {
                subtask.setState(state);
                return subtask;
            }
        }
        return null;
    }

    @Override
    public void deleteTask(Long taskId) {
        taskStore.remove(taskId);
        subtaskStore.remove(taskId);
    }

}