package com.kata.todo.repository.query.impl;

import com.kata.todo.dto.TaskDTO;
import com.kata.todo.dto.TaskFilterDto;
import com.kata.todo.dto.TasksReponsePagination;
import com.kata.todo.entity.TaskEntity;
import com.kata.todo.mapper.TaskMapper;
import com.kata.todo.repository.query.ITasksQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class TasksQueryImpl implements ITasksQuery {
    private final EntityManager em;

    @Autowired
    private TaskMapper taskMapper;

    public TasksQueryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public TasksReponsePagination getPaginationTask(int pageNo, int pageSize, String sortBy, String sortDir, TaskFilterDto filter) {
        TasksReponsePagination clientResponse = new TasksReponsePagination();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TaskEntity> cq = cb.createQuery(TaskEntity.class);
        Root<TaskEntity> root = cq.from(TaskEntity.class);

        Predicate[] predicatesArray = getPredicates(filter, cb, root, cq);
        cq.distinct(true);
        cq.where(predicatesArray);
        cq.orderBy(cb.desc(root.get(sortBy)));

        long count = countTaskPagination(filter);

        TypedQuery<TaskEntity> query = em.createQuery(cq);
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);

        List<TaskEntity> taskEntityList = query.getResultList();
        List<TaskDTO> taskDTO = taskMapper.taskListToTaskDTOList(taskEntityList);


        clientResponse.setContent(taskDTO);
        clientResponse.setPageNo(pageNo);
        clientResponse.setTotalElements(count);
        clientResponse.setPageSize(pageSize);


        return clientResponse;
    }

    @Override
    public long countTaskPagination(TaskFilterDto filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<TaskEntity> root = cq.from(TaskEntity.class);
        Predicate[] predicatesArray = getPredicates(filter, cb, root, cq);
        cq.select(cb.countDistinct(root));
        cq.where(predicatesArray);
        return em.createQuery(cq).getSingleResult();
    }

    private <T> Predicate[] getPredicates(TaskFilterDto filter, CriteriaBuilder cb, Root<TaskEntity> root, CriteriaQuery<T> cq) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getLabel() != null && !Objects.equals(filter.getLabel(), "")) {
            predicates.add(cb.like(root.get("label"), "%" + filter.getLabel() + "%"));
        }
        if (!filter.isComplete()) {
            predicates.add(cb.equal(root.get("complete"),filter.isComplete()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);

    }
}
