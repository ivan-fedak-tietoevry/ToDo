package com.fedak.application.service;

import com.fedak.application.entity.ToDo;

import java.util.List;

public interface ToDoService {
    List<ToDo> getAllToDos();

    void saveToDo(ToDo toDo);

    ToDo getToDo(int id);

    void deleteToDo(int id);

    void deleteAllToDos();

    List<ToDo> getUserToDo(int id);
}
