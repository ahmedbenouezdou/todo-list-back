package com.kata.todo.repository.query;

import com.kata.todo.dto.TaskFilterDto;
import com.kata.todo.dto.TasksReponsePagination;

public interface ITasksQuery {
    TasksReponsePagination getPaginationTask(int pageNo, int pageSize, String sortBy, String sortDir, TaskFilterDto filter);

    long countTaskPagination(TaskFilterDto filter);
}
