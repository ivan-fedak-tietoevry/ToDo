package com.fedak.application.service;

import com.fedak.application.dao.ToDoRepository;
import com.fedak.application.entity.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService{
    @Autowired
    private ToDoRepository toDoRepository;

    @Override
    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }

    @Override
    public void saveToDo(ToDo ToDo) {
        toDoRepository.save(ToDo);
    }

    @Override
    public ToDo getToDo(int id) {
        ToDo toDo = toDoRepository.getById(id);
        return toDo;
    }

    @Override
    public void deleteToDo(int id) {
        toDoRepository.deleteById(id);
    }

    @Override
    public void deleteAllToDos() {
        toDoRepository.deleteAll();
    }

    @Override
    public List<ToDo> getUserToDo(int id) {
        return toDoRepository.findAllByUserId(id);
    }

}
