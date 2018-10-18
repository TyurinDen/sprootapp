package com.example.demo.controller;

import com.example.demo.db.models.CustomUser;
import com.example.demo.db.models.Role;
import com.example.demo.services.RoleService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "allUsers";
        //можно сделать и так return userService.getAll(); тогда имя в модели будет userList, а имя view - образуется из
        //request path. В данном случае это будет наверное list.jsp или usersList (???)
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(value = "id") long id) {
        userService.deleteUser(userService.getUserById(id));
        return "redirect:/users/list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editUser(@RequestParam(value = "id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "editUser";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editUser(CustomUser user) {
        userService.updateUser(user);
        return "redirect:/users/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser() {
        return "addUser";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@RequestParam("role") String role, CustomUser user) {
        Set<Role> rolesSet = new HashSet<>();
        String[] roles = role.split("[,]");
        for (int i = 0; i < roles.length; i++) {
            rolesSet.add(roleService.getRoleByName(roles[i]));
        }
        user.setRoles(rolesSet);
        userService.addUser(user);
        return "redirect:/users/list";
    }

}
