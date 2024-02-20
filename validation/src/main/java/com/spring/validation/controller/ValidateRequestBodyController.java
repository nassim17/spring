package com.spring.validation.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.validation.dto.PayLoadDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/validation/validateBody")
@Validated
public class ValidateRequestBodyController {

    @PostMapping("/valid")
    ResponseEntity<String> validateBody(@Valid @RequestBody PayLoadDto payLoadDto) {
        return ResponseEntity.ok("valid");
    }
}
