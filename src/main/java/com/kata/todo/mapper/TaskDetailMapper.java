package com.kata.todo.mapper;


import com.kata.todo.dto.TaskDTO;
import com.kata.todo.dto.TaskDetailDTO;
import com.kata.todo.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TaskDetailMapper {

    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "modifAt", target = "modifAt")
    TaskDetailDTO taskToTaskDTO(TaskEntity task);
    TaskEntity taskDTOToTask(TaskDetailDTO taskDTO);

    List<TaskDetailDTO> taskListToTaskDTOList(List<TaskEntity> tasks);
}
