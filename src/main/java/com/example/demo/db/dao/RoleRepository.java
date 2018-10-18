package com.example.demo.db.dao;

import com.example.demo.db.models.Role;

import java.util.Set;

public interface RoleRepository {

    void addRole(Role role);

    Role getRoleById(Long id);

    Role getRoleByName(String name);

    Set<Role> getAllRoles();

}
