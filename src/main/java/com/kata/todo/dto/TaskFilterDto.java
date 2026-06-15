package com.kata.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Critères de filtrage des tâches")
public class TaskFilterDto {
    @Schema(description = "Filtre sur le libellé (recherche partielle)", example = "pain")
    private String label;

    @Schema(description = "Filtre sur le statut de complétion", example = "false")
    private boolean complete;
}
