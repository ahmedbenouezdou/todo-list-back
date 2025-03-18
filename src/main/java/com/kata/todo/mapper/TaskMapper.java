package com.kata.todo.mapper;


import com.kata.todo.dto.TaskDTO;
import com.kata.todo.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Mapping(source = "createdAt", target = "createdAt")
    TaskDTO taskToTaskDTO(TaskEntity task);
    TaskEntity taskDTOToTask(TaskDTO taskDTO);

    List<TaskDTO> taskListToTaskDTOList(List<TaskEntity> tasks);
}
