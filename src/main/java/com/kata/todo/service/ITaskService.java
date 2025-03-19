package com.kata.todo.service;



import com.kata.todo.dto.TaskDTO;
import com.kata.todo.dto.TaskDetailDTO;
import com.kata.todo.dto.TaskFilterDto;
import com.kata.todo.dto.TasksReponsePagination;

import java.util.List;

public interface ITaskService {

    List<TaskDTO> getAllTasks();

    TasksReponsePagination getPaginationTasks(int pageNo,int pageSize,String sortBy,String sortDir,TaskFilterDto filtre);

    TasksReponsePagination getPendingTasks(int pageNo, int pageSize, String sortBy, String sortDir, TaskFilterDto filtre) ;

    TaskDetailDTO getTaskById(Long id);

    TaskDTO addTask(String label) ;

    TaskDTO updateTaskStatus(Long id, boolean complete);

     void deleteTask(Long id);
}
