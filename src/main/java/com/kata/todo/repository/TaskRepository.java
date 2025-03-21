package com.kata.todo.repository;

import com.kata.todo.entity.TaskEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByComplete(boolean complete, PageRequest pageRequest);
}
