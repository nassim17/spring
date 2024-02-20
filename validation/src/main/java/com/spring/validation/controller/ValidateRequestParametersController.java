package com.spring.validation.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Min;


@RestController
@RequestMapping("/validation/request-parameters")
@Validated
public class ValidateRequestParametersController {


    @GetMapping
    ResponseEntity<String> validateRequestParameters(@RequestParam("param") @Min(5) int param) {
        return ResponseEntity.ok("valid");
    }


    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
}
