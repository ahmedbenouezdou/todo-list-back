package com.kata.todo.controller;


import com.kata.todo.dto.TaskDTO;
import com.kata.todo.dto.TaskDetailDTO;
import com.kata.todo.dto.TaskFilterDto;
import com.kata.todo.dto.TasksReponsePagination;
import com.kata.todo.service.ITaskService;
import com.kata.todo.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Tasks", description = "Gestion des tâches de la To-Do List")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    public ITaskService taskService;


    @GetMapping
    @Operation(summary = "Récupérer toutes les tâches")
    @ApiResponse(responseCode = "200", description = "Liste de toutes les tâches",
            content = @Content(schema = @Schema(implementation = TaskDTO.class)))
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        logger.info("Requête API: GET /tasks");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/pagination")
    @Operation(summary = "Récupérer toutes les tâches avec pagination")
    @ApiResponse(responseCode = "200", description = "Page de tâches",
            content = @Content(schema = @Schema(implementation = TasksReponsePagination.class)))
    public ResponseEntity<TasksReponsePagination> getPaginationTasks(
            @Parameter(description = "Numéro de page (commence à 0)", example = "0")
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @Parameter(description = "Nombre d'éléments par page", example = "5")
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @Parameter(description = "Champ de tri", example = "createdAt")
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @Parameter(description = "Direction du tri : asc ou desc", example = "desc")
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestBody(required = false) TaskFilterDto filtre) {
        logger.info("Requête API: GET /pagination?page={}&size={}", pageNo, pageSize);
        return ResponseEntity.ok(taskService.getPaginationTasks(pageNo, pageSize, sortBy, sortDir, filtre));
    }

    @GetMapping("/pending")
    @Operation(summary = "Récupérer les tâches non complétées avec pagination")
    @ApiResponse(responseCode = "200", description = "Page de tâches en attente",
            content = @Content(schema = @Schema(implementation = TasksReponsePagination.class)))
    public ResponseEntity<TasksReponsePagination> getPendingTasks(
            @Parameter(description = "Numéro de page (commence à 0)", example = "0")
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @Parameter(description = "Nombre d'éléments par page", example = "5")
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @Parameter(description = "Champ de tri", example = "createdAt")
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @Parameter(description = "Direction du tri : asc ou desc", example = "desc")
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestBody(required = false) TaskFilterDto filtre) {
        logger.info("Requête API: GET /tasks/pending?page={}&size={}", pageNo, pageSize);
        return ResponseEntity.ok(taskService.getPendingTasks(pageNo, pageSize, sortBy, sortDir, filtre));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une tâche par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tâche trouvée",
                    content = @Content(schema = @Schema(implementation = TaskDetailDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tâche introuvable", content = @Content)
    })
    public ResponseEntity<TaskDetailDTO> getTaskById(
            @Parameter(description = "ID de la tâche", example = "1") @PathVariable Long id) {
        logger.info("Requête API: GET /tasks/{}", id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    @Operation(summary = "Ajouter une tâche")
    @ApiResponse(responseCode = "200", description = "Tâche créée",
            content = @Content(schema = @Schema(implementation = TaskDTO.class)))
    public ResponseEntity<TaskDTO> addTask(
            @Parameter(description = "Libellé de la tâche", required = true, example = "Acheter du pain")
            @RequestParam String label) {
        logger.info("Requête API: POST /tasks avec label={}", label);
        return ResponseEntity.ok(taskService.addTask(label));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Modifier le statut d'une tâche")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Statut mis à jour",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tâche introuvable", content = @Content)
    })
    public ResponseEntity<TaskDTO> updateTaskStatus(
            @Parameter(description = "ID de la tâche", example = "1") @PathVariable Long id,
            @Parameter(description = "Nouveau statut de complétion", example = "true") @RequestParam boolean complete) {
        logger.info("Requête API: PUT /tasks/{}/status?complete={}", id, complete);
        return ResponseEntity.ok(taskService.updateTaskStatus(id, complete));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une tâche")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tâche supprimée", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tâche introuvable", content = @Content)
    })
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "ID de la tâche", example = "1") @PathVariable Long id) {
        logger.info("Requête API: DELETE /tasks/{}", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
