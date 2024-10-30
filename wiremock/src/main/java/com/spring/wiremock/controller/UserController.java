package com.spring.wiremock.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.wiremock.entity.User;
import com.spring.wiremock.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/customer/{id}")
    public User getUser(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        return user.orElse(null); // Retourne null si l'utilisateur n'existe pas
    }
}
