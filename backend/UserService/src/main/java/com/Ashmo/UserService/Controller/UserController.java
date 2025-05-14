package com.Ashmo.UserService.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.Ashmo.UserService.Model.Users;
import com.Ashmo.UserService.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;

    // Public endpoints - no authorization needed
    @PostMapping("/register")
    public Users addUser(@RequestBody Users user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Users user) {
        return userService.verify(user);
    }
    
    // Protected endpoints - require authentication
    @GetMapping("/refreshtoken")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER', 'TESTER')")
    public Map<String, String> getNewToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing Authorization header");
        }
        String token = authorizationHeader.substring(7);
        return userService.newAccessToken(token);
    }
    
    @GetMapping("/username/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER', 'TESTER')")
    public String getUsername(@PathVariable int id) {
        return userService.getUsername(id);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER', 'TESTER')")
    public Users getUser(@PathVariable String username) {
        return userService.getUser(username);
    }
}
