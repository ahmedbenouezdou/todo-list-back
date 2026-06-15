package com.kata.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Représentation d'une tâche")
public class TaskDTO {
    @Schema(description = "Identifiant unique de la tâche", example = "1")
    private Long id;

    @Schema(description = "Libellé de la tâche", example = "Acheter du pain")
    private String label;

    @Schema(description = "Indique si la tâche est complétée", example = "false")
    private boolean complete;

    @Schema(description = "Date de création de la tâche")
    private LocalDateTime createdAt;
}
