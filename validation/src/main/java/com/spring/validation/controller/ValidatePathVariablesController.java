package com.spring.validation.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Min;


@RestController
@RequestMapping("/validation/path-variables")
@Validated
public class ValidatePathVariablesController {


    @GetMapping("/{id}")
    ResponseEntity<String> validatePathVariables(@PathVariable("id") @Min(5) int id) {
        return ResponseEntity.ok("valid");
    }
}
