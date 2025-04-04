package com.branching.nmail.controller;

import com.branching.nmail.controller.models.UserReducedRequest;
import com.branching.nmail.model.User;
import com.branching.nmail.model.UserReduced;
import com.branching.nmail.service.JWTService;
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

    @Autowired
    JWTService jwtService;

    @GetMapping(value = "/users")
    @Operation(summary = "Get list of users")
    public ResponseEntity<List<User>> getUsers() {
        try {
            return new ResponseEntity<>(userService.getUsers(), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/user/{id}")
    @Operation(summary = "Get a user by ID", description = "Fetches a user’s details by their unique identifier")
    public ResponseEntity<UserReduced> getUser(@RequestHeader(value = "Authorization") String token, @PathVariable int id) {
        try {
            String reducedToken = token.substring(7);
            int userId = jwtService.extractUserId(reducedToken);
            if (id != userId) {
                return new ResponseEntity<>(HttpStatusCode.valueOf(401));
            }
            User foundUser = userService.getUserById(id);
            return new ResponseEntity<>(new UserReduced(foundUser), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/user-by-email")
    @Operation(summary = "Get a user by email")
    public ResponseEntity<UserReduced> getUserByEmail(@RequestHeader(value = "Authorization") String token, @RequestBody UserReducedRequest userReducedRequest) {
        try {
            String reducedToken = token.substring(7);
            User foundUser = userService.getUserByEmail(userReducedRequest.getEmail());
            System.out.println("user: " + foundUser.getEmail());
            System.out.println("id: " + foundUser.getId());
            int userId = jwtService.extractUserId(reducedToken);
            if (foundUser.getId() != userId) {
                return new ResponseEntity<>(HttpStatusCode.valueOf(401));
            }

            return new ResponseEntity<>(new UserReduced(foundUser), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }


    @DeleteMapping(value = "/delete-user/{id}")
    @Operation(summary = "Delete a user by ID", description = "Deletes a user by their unique identifier")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
    }

}
