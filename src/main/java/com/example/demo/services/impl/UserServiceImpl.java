package com.example.demo.services.impl;

import com.example.demo.db.dao.UserRepository;
import com.example.demo.db.models.CustomUser;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(CustomUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.addUser(user);
    }

    @Override
    public void updateUser(CustomUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.updateUser(user);
    }

    @Override
    public void deleteUser(CustomUser user) {
        userRepository.deleteUser(user);
    }

    @Override
    public CustomUser getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public CustomUser getUserById(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public List<CustomUser> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public boolean checkIfExistByLogin(String login) {
        return userRepository.getUserByLogin(login) != null;
    }

    @Override
    public boolean checkIfExistById(Long id) {
        return false;
    }

}
