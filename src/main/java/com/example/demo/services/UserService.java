package com.example.demo.services;

import com.example.demo.db.models.CustomUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void addUser(CustomUser user);

    void updateUser(CustomUser user);

    void deleteUser(CustomUser user);

    CustomUser getUserByLogin(String login);

    CustomUser getUserById(long id);

    List<CustomUser> getAllUsers();

    boolean checkIfExistByLogin(String login);

    boolean checkIfExistById(Long id);

}
