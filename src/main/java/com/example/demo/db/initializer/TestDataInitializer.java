package com.example.demo.db.initializer;

import com.example.demo.db.models.CustomUser;
import com.example.demo.db.models.Role;
import com.example.demo.services.RoleService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

public class TestDataInitializer {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private void init() {
        Role adminRole = new Role("ADMIN");
        roleService.addRole(adminRole);
        Role userRole = new Role("USER");
        roleService.addRole(userRole);
        CustomUser adminUser = new CustomUser("root");
        adminUser.setPassword("root");
        //adminUser.setRoles(roleService.getRoleByNameAsSet(adminRole.getName()));
        adminUser.setRoles(Collections.singleton(adminRole)); // красивее чем предыдущий способ

        CustomUser userUser = new CustomUser("user");
        userUser.setPassword("123");
        //userUser.setRoles(roleService.getRoleByNameAsSet(userRole.getName()));
        userUser.setRoles(Collections.singleton(userRole)); // красивее чем предыдущий способ

        userService.addUser(adminUser);
        userService.addUser(userUser);
    }
}
