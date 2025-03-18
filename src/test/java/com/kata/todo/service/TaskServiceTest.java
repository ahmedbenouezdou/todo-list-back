package com.kata.todo.service;

import com.kata.todo.dto.TaskDTO;
import com.kata.todo.entity.TaskEntity;
import com.kata.todo.exception.TaskNotFoundException;
import com.kata.todo.mapper.TaskMapper;
import com.kata.todo.repository.TaskRepository;
import com.kata.todo.repository.query.ITasksQuery;
import com.kata.todo.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ITasksQuery tasksQuery;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskEntity task1;
    private TaskEntity task2;
    private TaskDTO taskDTO1;
    private TaskDTO taskDTO2;
    private List<TaskEntity> taskList;
    private List<TaskDTO> taskDTOList;

    @BeforeEach
    void setUp() {
        task1 = new TaskEntity(1L, "Task 1", false, null);
        task2 = new TaskEntity(2L, "Task 2", true, null);
        taskDTO1 = new TaskDTO(1L, "Task 1", false, null);
        taskDTO2 = new TaskDTO(2L, "Task 2", true, null);

        taskList = Arrays.asList(task1, task2);
        taskDTOList = Arrays.asList(taskDTO1, taskDTO2);
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        when(taskRepository.findAll()).thenReturn(taskList);
        when(taskMapper.taskListToTaskDTOList(any())).thenReturn(taskDTOList);

        List<TaskDTO> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskMapper.taskToTaskDTO(task1)).thenReturn(taskDTO1);

        TaskDTO result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_ShouldThrowException_WhenTaskNotFound() {
        when(taskRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(3L));
        verify(taskRepository, times(1)).findById(3L);
    }

    @Test
    void addTask_ShouldSaveAndReturnTask() {
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(task1);
        when(taskMapper.taskToTaskDTO(any(TaskEntity.class))).thenReturn(taskDTO1);

        TaskDTO result = taskService.addTask("Task 1");

        assertNotNull(result);
        assertEquals("Task 1", result.getLabel());
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void updateTaskStatus_ShouldUpdateStatus() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(task1);
        when(taskMapper.taskToTaskDTO(task1)).thenReturn(taskDTO1);

        TaskDTO result = taskService.updateTaskStatus(1L, true);

        assertNotNull(result);
        assertTrue(result.isComplete());
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void deleteTask_ShouldDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenTaskNotFound() {
        when(taskRepository.existsById(3L)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(3L));
        verify(taskRepository, times(1)).existsById(3L);
    }
}
