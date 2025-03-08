package com.branching.nmail.controller;

import com.branching.nmail.model.User;
import com.branching.nmail.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    // Csrf is currently disabled in the customized filter chain in SecurityConfig class
//    @GetMapping("/csrf-token")
//    public CsrfToken getCsrfToken(HttpServletRequest request) {
//        return (CsrfToken) request.getAttribute("_csrf");
//
//    }

    @PostMapping(value = "/register")
    @Operation(summary = "Register a user")
    public User registerUser(@RequestBody User user) {
        BCryptPasswordEncoder arg2SpringSecurity = new BCryptPasswordEncoder(12);
        String hash = arg2SpringSecurity.encode(user.getPassword());

        user.setPassword(hash);
        return userService.saveUser(user);
    }

    @PostMapping(value = "/login")
    @Operation(summary = "Log In")
    public ResponseEntity<String> logInUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.verify(user), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
}
