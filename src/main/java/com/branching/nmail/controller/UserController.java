package com.branching.nmail.controller;

import com.branching.nmail.model.User;
import com.branching.nmail.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    @Operation(summary = "Get list of users")
    public ResponseEntity<List<User>> getUsers() {
        try {
            return new ResponseEntity<>(userService.getUsers(), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "user/{id}")
    @Operation(summary = "Get a user by ID", description = "Fetches a user’s details by their unique identifier")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping(value = "delete-user/{id}")
    @Operation(summary = "Delete a user by ID", description = "Deletes a user by their unique identifier")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
    }

}
