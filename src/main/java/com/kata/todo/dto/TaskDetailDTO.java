package com.kata.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDetailDTO {
    private Long id;
    private String label;
    private boolean complete;
    private LocalDateTime createdAt;
    private LocalDateTime modifAt;

}
