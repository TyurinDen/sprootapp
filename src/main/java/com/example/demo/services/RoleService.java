package com.example.demo.services;

import com.example.demo.db.models.Role;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoleService {

    void addRole(Role role);

    Role getRoleById(Long id);

    Role getRoleByName(String name);

    Set<Role> getAllRoles();

}
