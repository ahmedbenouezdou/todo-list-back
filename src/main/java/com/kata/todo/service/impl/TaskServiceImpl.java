package com.kata.todo.service.impl;


import com.kata.todo.dto.TaskDTO;
import com.kata.todo.dto.TaskDetailDTO;
import com.kata.todo.dto.TaskFilterDto;
import com.kata.todo.dto.TasksReponsePagination;
import com.kata.todo.entity.TaskEntity;
import com.kata.todo.exception.TaskNotFoundException;
import com.kata.todo.mapper.TaskDetailMapper;
import com.kata.todo.mapper.TaskMapper;
import com.kata.todo.repository.TaskRepository;
import com.kata.todo.repository.query.ITasksQuery;
import com.kata.todo.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements ITaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ITasksQuery tasksQuery;

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskDetailMapper taskDetailMapper;

    /**
     * Récupère toutes les tâches
     */
    @Override
    public List<TaskDTO> getAllTasks() {
        logger.info("Récupération de toutes les tâches");
        List<TaskEntity> tasks = taskRepository.findAll();
        return taskMapper.taskListToTaskDTOList(tasks);

    }
    /**
     * Récupère les tâches avec pagination
     */
    @Override
    public TasksReponsePagination getPaginationTasks(int pageNo,int pageSize,String sortBy,String sortDir,TaskFilterDto filtre) {
        logger.info("Récupération des tâches - Page: {}, Taille: {}", pageNo, pageSize);
        TasksReponsePagination taskPage=tasksQuery.getPaginationTask(pageNo,pageSize,sortBy,sortDir,new TaskFilterDto(null, true));
        logger.debug("Nombre de tâches retournées : {}", taskPage.getContent().size());
        return taskPage;
    }

    /**
     * Récupère les tâches en attente avec pagination
     */
    @Override
    public TasksReponsePagination getPendingTasks(int pageNo,int pageSize,String sortBy,String sortDir,TaskFilterDto filtre) {
        logger.info("Récupération des tâches en attente - Page: {}, Taille: {}", pageNo, pageSize);
        TasksReponsePagination taskPage=tasksQuery.getPaginationTask(pageNo,pageSize,sortBy,sortDir,new TaskFilterDto("",false));
        logger.debug("Nombre de tâches en attente retournées : {}", taskPage.getContent().size());

        return taskPage;
    }
    @Override
    public TaskDetailDTO getTaskById(Long id) {
        logger.info("Recherche de la tâche avec ID: {}", id);
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Tâche introuvable avec ID : " + id));
        return taskDetailMapper.taskToTaskDTO(task);
    }
    @Override
    public TaskDTO addTask(String label) {
        TaskEntity task = new TaskEntity(null, label, false, null,null);
        task = taskRepository.save(task);
        logger.info("Ajout d'une nouvelle tâche : {}", task);
        return taskMapper.taskToTaskDTO(task);
    }
    @Override
    public TaskDTO updateTaskStatus(Long id, boolean complete) {
        logger.info("Mise à jour du statut de la tâche ID: {} à {}", id, complete);
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Tâche introuvable avec ID : " + id));
        task.setComplete(complete);
        taskRepository.save(task);
        logger.debug("Statut mis à jour : {}", task);
        return taskMapper.taskToTaskDTO(task);
    }
    @Override
    public void deleteTask(Long id) {
        logger.info("Suppression de la tâche avec ID: {}", id);
        if (!taskRepository.existsById(id)) {
            logger.error("Impossible de supprimer : tâche introuvable avec ID: {}", id);
            throw new TaskNotFoundException("Tâche introuvable avec ID : " + id);
        }
        taskRepository.deleteById(id);
        logger.debug("Tâche supprimée avec succès : ID {}", id);
    }


}
