package com.kata.todo.controller;

import com.kata.todo.dto.TaskDTO;
import com.kata.todo.dto.TaskDetailDTO;
import com.kata.todo.service.ITaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private ITaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void getAllTasks_ShouldReturnTasks() {
        when(taskService.getAllTasks()).thenReturn(List.of(new TaskDTO(1L, "Task 1", false, null)));

        ResponseEntity<List<TaskDTO>> response = taskController.getAllTasks();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        TaskDetailDTO taskDTO = new TaskDetailDTO(1L, "Task 1", false, null,null);
        when(taskService.getTaskById(1L)).thenReturn(taskDTO);

        ResponseEntity<TaskDetailDTO> response = taskController.getTaskById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Task 1", response.getBody().getLabel());
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void addTask_ShouldReturnCreatedTask() {
        TaskDTO taskDTO = new TaskDTO(1L, "New Task", false, null);
        when(taskService.addTask("New Task")).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = taskController.addTask("New Task");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("New Task", response.getBody().getLabel());
        verify(taskService, times(1)).addTask("New Task");
    }

    @Test
    void updateTaskStatus_ShouldUpdateStatus() {
        TaskDTO taskDTO = new TaskDTO(1L, "Task 1", true, null);
        when(taskService.updateTaskStatus(1L, true)).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = taskController.updateTaskStatus(1L, true);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isComplete());
        verify(taskService, times(1)).updateTaskStatus(1L, true);
    }
}
