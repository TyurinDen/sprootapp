package com.example.demo.db.dao;

import com.example.demo.db.models.CustomUser;

import java.util.List;

public interface UserRepository {

    void addUser(CustomUser user);

    void deleteUser(CustomUser user);

    void updateUser(CustomUser user);

    List<CustomUser> getAllUsers();

    CustomUser getUserById(Long id);

    CustomUser getUserByLogin(String login);

}
