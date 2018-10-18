package com.example.demo.controller;

import com.example.demo.db.models.CustomUser;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/users/api")
public class RestUsersController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        CustomUser user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(String.format("User with id = '%d' not found", id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllUsers() {
        List<CustomUser> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>("There are no users in DB", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody CustomUser user, UriComponentsBuilder ucBuilder) {
        if (userService.checkIfExistByLogin(user.getLogin())) {
            return new ResponseEntity<>(String.format("User with login '%s' already exist", user.getLogin()),
                    HttpStatus.CONFLICT);
        }
        userService.addUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/api/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody CustomUser user) {
        CustomUser persistentUser = userService.getUserById(id);

        if (persistentUser == null) {
            return new ResponseEntity<>(String.format("Unable to update user. User with id '%d' not found",
                    user.getId()), HttpStatus.NOT_FOUND);
        }
        persistentUser.setPassword(user.getPassword());
        persistentUser.setRoles(user.getRoles());
        userService.updateUser(persistentUser);
        return new ResponseEntity<>(persistentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        CustomUser user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(String.format("Unable to delete user. User with id '%d' not found.", id),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getPrincipal")
    public ResponseEntity<?> getPrincipal() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return new ResponseEntity<>("No authorized users!", HttpStatus.NOT_FOUND);
        }
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
