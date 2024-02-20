package com.spring.validation.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Min;


@RestController
@RequestMapping("/validation/path-variables")
public class ValidatePathVariablesController {


    @GetMapping("/{id}")
    ResponseEntity<String> validatePathVariables(@PathVariable("id") @Min(5) int id) {
        return ResponseEntity.ok("valid");
    }
}
