package com.kata.todo.controller;


import com.kata.todo.dto.TaskDTO;
import com.kata.todo.dto.TaskDetailDTO;
import com.kata.todo.dto.TaskFilterDto;
import com.kata.todo.dto.TasksReponsePagination;
import com.kata.todo.service.ITaskService;
import com.kata.todo.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tasks")
@Tag(name = "Task Controller", description = "Gestion des tâches de la To-Do List")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    public ITaskService taskService;


    @GetMapping
    @Operation(summary = "Récupérer toutes les tâches")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        logger.info("Requête API: GET /tasks");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/pagination")
    @Operation(summary = "Récupérer toutes les tâches avec pagination",description = "Returns a greeting message")
    public ResponseEntity<TasksReponsePagination> getPaginationTasks(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)String sortDir,
            @RequestBody(required = false) TaskFilterDto filtre) {
        logger.info("Requête API: GET /pagination?page={}&size={}", pageNo, pageSize);
        return ResponseEntity.ok(taskService.getPaginationTasks(pageNo, pageSize,sortBy,sortDir,filtre));
    }

    @GetMapping("/pending")
    @Operation(summary = "Récupérer les tâches à faire avec pagination")
    public ResponseEntity<TasksReponsePagination> getPendingTasks(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)String sortDir,
            @RequestBody(required = false) TaskFilterDto filtre) {
        logger.info("Requête API: GET /tasks/pending?page={}&size={}", pageNo, pageSize);
        return ResponseEntity.ok(taskService.getPendingTasks(pageNo, pageSize,sortBy,sortDir,filtre));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une tâche par ID")
    public ResponseEntity<TaskDetailDTO> getTaskById(@PathVariable Long id) {
        logger.info("Requête API: GET /tasks/{}", id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    @Operation(summary = "Ajouter une tâche")
    public ResponseEntity<TaskDTO> addTask(@RequestParam String label) {
        logger.info("Requête API: POST /tasks avec label={}", label);
        return ResponseEntity.ok(taskService.addTask(label));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Modifier le statut d'une tâche")
    public ResponseEntity<TaskDTO> updateTaskStatus(@PathVariable Long id, @RequestParam boolean complete) {
        logger.info("Requête API: PUT /tasks/{}/status?complete={}", id, complete);
        return ResponseEntity.ok(taskService.updateTaskStatus(id, complete));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une tâche")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        logger.info("Requête API: DELETE /tasks/{}", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
