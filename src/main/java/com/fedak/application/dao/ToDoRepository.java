package com.fedak.application.dao;

import com.fedak.application.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Integer> {
    List<ToDo> findAllByUserId(int userId);
}
