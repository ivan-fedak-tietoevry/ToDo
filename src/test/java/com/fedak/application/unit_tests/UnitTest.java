package com.fedak.application.unit_tests;

import com.fedak.application.controller.Controller;
import com.fedak.application.dao.ToDoRepository;
import com.fedak.application.dao.UserRepository;
import com.fedak.application.entity.ToDo;
import com.fedak.application.entity.User;
import com.fedak.application.service.ToDoService;
import com.fedak.application.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UnitTest {
    @InjectMocks
    Controller controller;
    @Mock
    UserService userService;
    @Mock
    ToDoService toDoService;
    @Mock
    UserRepository userRepository;
    @Mock
    ToDoRepository toDoRepository;

    private User testUser = new User("Elon", "Mask","mask@tesla@com");
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void showAllUsers() {
        List<User> allUsers = controller.showAllUsers();
        assert (allUsers.isEmpty());
    }

    @Test
    void deleteAllUsersException() {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            controller.deleteAllUsers();
        });
        assertEquals("There are no users in database", exception.getMessage());
    }
    @Test
    void deleteAllUsers() {
        User user = new User("Ivan", "Ivan", "ivan@mail.com");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.getAllUsers()).thenReturn(userList);
        assertEquals(controller.deleteAllUsers(),"All users were deleted");
    }

    @Test
    void addNewUser() {
        User user = new User("Ivan", "Ivan", "ivan@mail.com");
        when(userRepository.save(any(User.class))).thenReturn(user);
        User response = controller.addNewUser(user);
        assertEquals(response.toString(),user.toString());
    }

    @Test
    void updateUser() {
        User user = new User("Ivan", "Ivan", "ivan@mail.com");
        user.setId(1);
        when(userRepository.save(any(User.class))).thenReturn(user);
        controller.addNewUser(user);
        user.setName("Petro");
        when(userService.getUser(1)).thenReturn(user);
        User response = controller.updateUser(user);
        assertEquals(response.getName(),"Petro");
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getUserToDos() {
        User user = new User();
        user.setEmail("ivan@mail.com");
        user.setName("Ivan");
        user.setSurname("Ivan");
        user.setId(1);
        when(userService.getUser(1)).thenReturn(user);
        ToDo toDo = new ToDo();
        toDo.setUser(user);
        toDo.setTodo("Task1");
        List<ToDo> toDoList = new ArrayList<>();
        toDoList.add(toDo);
        when(toDoService.getUserToDo(user.getId())).thenReturn(toDoList);
        assertEquals(controller.getUserToDos(1),toDoList);

    }

    @Test
    void addNewToDo() {
        User user = new User("Ivan", "Ivan", "ivan@mail.com");
        user.setId(1);
        when(userService.getUser(1)).thenReturn(user);
        ToDo toDo = new ToDo("Task1");
        toDo.setUser(user);
        when(toDoRepository.save(any(ToDo.class))).thenReturn(toDo);
        ToDo response = controller.addNewToDo(toDo,1);
        assertEquals(response.toString(),toDo.toString());
    }

    @Test
    void updateUserToDo() {
        User user = new User("Ivan", "Ivan", "ivan@mail.com");
        user.setId(1);
        when(userService.getUser(1)).thenReturn(user);
        ToDo toDo = new ToDo("Task1");
        toDo.setUser(user);
        toDo.setId(2);
        controller.addNewToDo(toDo,1);
        when(toDoService.getToDo(2)).thenReturn(toDo);
        toDo.setTodo("Task2");
        ToDo response = controller.updateUserToDo(toDo,1);
        assertEquals(response.getTodo(),"Task2");
    }

    @Test
    void deleteUserToDos() {
    }

    @Test
    void updateToDo() {
    }

    @Test
    void showAllTodos() {
        List<ToDo> allToDos = controller.showAllTodos();
        assert (allToDos.isEmpty());
    }

    @Test
    void deleteAllToDos() {
    }

    @Test
    void getToDo() {
    }

    @Test
    void deleteToDo() {
    }

}