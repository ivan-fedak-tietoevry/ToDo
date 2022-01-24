package com.fedak.application.service;

import com.fedak.application.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void deleteUser(int id);

    public void deleteAllUser();
}
