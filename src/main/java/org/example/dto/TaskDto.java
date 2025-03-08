package org.example.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.enums.TaskState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class TaskDto {
    private Long id;
    private String name;
    private TaskState state = TaskState.PENDING;
    private LocalDate dueDate;
    private int priority;
    private List<TaskDto> subtasks = new ArrayList<>();
}