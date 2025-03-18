package com.kata.todo.mapper.impl;


import com.kata.todo.dto.TaskDTO;
import com.kata.todo.entity.TaskEntity;
import com.kata.todo.mapper.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDTO taskToTaskDTO(TaskEntity task) {
        if (task == null) {
            return null;
        }
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setLabel(task.getLabel());
        dto.setComplete(task.isComplete());
        dto.setCreatedAt(task.getCreatedAt());
        return dto;
    }

    @Override
    public TaskEntity taskDTOToTask(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return null;
        }
        return new TaskEntity(taskDTO.getId(), taskDTO.getLabel(), taskDTO.isComplete(), taskDTO.getCreatedAt());
    }

    @Override
    public List<TaskDTO> taskListToTaskDTOList(List<TaskEntity> tasks) {
        if (tasks == null) {
            return null;
        }
        return tasks.stream().map(this::taskToTaskDTO).collect(Collectors.toList());
    }
}
