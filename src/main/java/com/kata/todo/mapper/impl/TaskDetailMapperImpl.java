package com.kata.todo.mapper.impl;


import com.kata.todo.dto.TaskDTO;
import com.kata.todo.dto.TaskDetailDTO;
import com.kata.todo.entity.TaskEntity;
import com.kata.todo.mapper.TaskDetailMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskDetailMapperImpl implements TaskDetailMapper {

    @Override
    public TaskDetailDTO taskToTaskDTO(TaskEntity task) {
        if (task == null) {
            return null;
        }
        TaskDetailDTO dto = new TaskDetailDTO();
        dto.setId(task.getId());
        dto.setLabel(task.getLabel());
        dto.setComplete(task.isComplete());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setModifAt(task.getMofidAt());
        return dto;
    }

    @Override
    public TaskEntity taskDTOToTask(TaskDetailDTO taskDTO) {
        if (taskDTO == null) {
            return null;
        }
        return new TaskEntity(taskDTO.getId(), taskDTO.getLabel(), taskDTO.isComplete(), taskDTO.getCreatedAt(), taskDTO.getModifAt());
    }

    @Override
    public List<TaskDetailDTO> taskListToTaskDTOList(List<TaskEntity> tasks) {
        if (tasks == null) {
            return null;
        }
        return tasks.stream().map(this::taskToTaskDTO).collect(Collectors.toList());
    }
}
