package com.kata.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Réponse paginée de tâches")
public class TasksReponsePagination {
    @Schema(description = "Liste des tâches de la page courante")
    private List<TaskDTO> content;

    @Schema(description = "Numéro de la page courante (commence à 0)", example = "0")
    private int pageNo;

    @Schema(description = "Nombre d'éléments par page", example = "5")
    private int pageSize;

    @Schema(description = "Nombre total d'éléments", example = "42")
    private long totalElements;

    @Schema(description = "Nombre total de pages", example = "9")
    private int totalPages;

    @Schema(description = "Indique si c'est la dernière page", example = "false")
    private boolean last;
}
