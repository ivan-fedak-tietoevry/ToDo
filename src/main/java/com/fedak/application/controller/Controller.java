package com.fedak.application.controller;

import com.fedak.application.entity.ToDo;
import com.fedak.application.entity.User;
import com.fedak.application.service.ToDoService;
import com.fedak.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private UserService userService;

    @Autowired
    private ToDoService toDoService;

    private boolean checkUser(User user){
        if (user.getEmail().isEmpty()){
            throw new RuntimeException("Wrong email");
        } else if (user.getSurname().isEmpty()){
            throw new RuntimeException("Wrong surname");
        } else if (user.getName().isEmpty()){
            throw new RuntimeException("Wrong name");
        }
        return true;
    }
    private boolean checkToDo(ToDo toDo){
        if (toDo.getTodo().isEmpty()){
            throw new RuntimeException("Todo is empty");
        }
        return true;
    }

    @GetMapping("/users/")
    public List<User> showAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/")
    public String deleteAllUsers(){
        if (userService.getAllUsers().isEmpty()) {
            throw new RuntimeException("There are no users in database");
        }
        userService.deleteAllUser();
        return "All users were deleted";
    }

    @PostMapping("/users/")
    public User addNewUser(@RequestBody User user){
        checkUser(user);
        userService.saveUser(user);
        return user;
    }

    @PutMapping("/users/")
    public User updateUser(@RequestBody User user){
        checkUser(user);
        User updatedUser = userService.getUser(user.getId());
        if (updatedUser==null) {
            throw new RuntimeException("There is no user with ID = " + user.getId() + " in database");
        }
        userService.saveUser(user);
        return user;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id){
        User user = userService.getUser(id);
        if (user==null) {
            throw new RuntimeException("There is no user with ID = " + id + " in database");
        }
        return user;
    }

    @DeleteMapping("/users/{id}/")
    public String deleteUser(@PathVariable int id){
        if (userService.getUser(id) ==null) {
            throw new RuntimeException("There is no user with ID = " + id + " in database");
        }
        userService.deleteUser(id);
        return "User with id: " + id + " deleted";
    }

    @GetMapping("/users/{id}/todos/")
    public List<ToDo> getUserToDos(@PathVariable int id){
        User user = userService.getUser(id);
        if (user==null) {
            throw new RuntimeException("There is no user with ID = " + id + " in database");
        }
        List<ToDo> toDo = toDoService.getUserToDo(id);
        if (toDo==null) {
            throw new RuntimeException("There are no todos for user with ID = " + id + " in database");
        }
        return toDo;
    }

    @PostMapping("/users/{id}/todos/")
    public ToDo addNewToDo(@RequestBody ToDo toDo, @PathVariable Integer id ){
        User user = userService.getUser(id);
        if (user==null) {
            throw new RuntimeException("There is no user with ID = " + id + " in database");
        }
        toDo.setUser(userService.getUser(id));
        toDoService.saveToDo(toDo);
        return toDo;
    }

    @PutMapping("/users/{id}/todos/")
    public ToDo updateUserToDo(@RequestBody ToDo toDo, @PathVariable Integer id ){
        User user = userService.getUser(id);
        if (user==null) {
            throw new RuntimeException("There is no user with ID = " + id + " in database");
        }
        checkToDo(toDo);
        toDo.setUser(userService.getUser(id));
        if (toDoService.getToDo(toDo.getId())==null) {
            throw new RuntimeException("There are no todos with ID = " + toDo.getId() + " in database");
        }
        toDoService.saveToDo(toDo);
        return toDo;
    }

    @DeleteMapping("/users/{id}/todos/")
    public String deleteUserToDos(@PathVariable int id){
        List<ToDo> toDos = toDoService.getUserToDo(id);
        if (toDos==null) {
            throw new RuntimeException("There is no todos for user with ID = " + id + " in database");
        }
        for (ToDo todo: toDos){
            toDoService.deleteToDo(todo.getId());
        }
        return "All todos for user " + id + " were deleted";
    }

    @PutMapping("/todos/")
    public ToDo updateToDo(@RequestBody ToDo toDo){
        checkToDo(toDo);
        if (toDoService.getToDo(toDo.getId())==null) {
            throw new RuntimeException("There are no todos with ID = " + toDo.getId() + " in database");
        }
        toDoService.saveToDo(toDo);
        return toDo;
    }

    @GetMapping("/todos/")
    public List<ToDo> showAllTodos(){
        return toDoService.getAllToDos();
    }

    @DeleteMapping("/todos/")
    public String deleteAllToDos(){
        if (toDoService.getAllToDos().isEmpty()) {
            throw new RuntimeException("There is no todos in database");
        }
        toDoService.deleteAllToDos();
        return "All todos were deleted";
    }

    @GetMapping("/todos/{id}/")
    public ToDo getToDo(@PathVariable int id){
        ToDo toDo = toDoService.getToDo(id);
		if (toDo==null) {
			throw new RuntimeException("There is no todo with ID = " + id + " in database");
		}
        return toDo;
    }
    @DeleteMapping("/todos/{id}/")
    public String deleteToDo(@PathVariable int id) {
        if (toDoService.getToDo(id) == null) {
            throw new RuntimeException("There is no todo with ID = " + id + " in database");
        }
        toDoService.deleteToDo(id);
        return "Todo with id: " + id + " deleted";
    }
}
