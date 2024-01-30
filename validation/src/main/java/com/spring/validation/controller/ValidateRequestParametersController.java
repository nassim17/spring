package com.spring.validation.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Min;


@RestController
@RequestMapping("/validation/request-parameters")
public class ValidateRequestParametersController {


    @GetMapping
    ResponseEntity<String> validateRequestParameters(@RequestParam("param") @Min(5) int param) {
        return ResponseEntity.ok("valid");
    }
}
