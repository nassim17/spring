package com.spring.validation.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.spring.validation.config.WebConfigTest;


@Import(WebConfigTest.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidateRequestParametersControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @LocalServerPort
    int localServerPort;

    @Test
    void invalidRequestParameters() {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(
                    "http://localhost:" + localServerPort + "/validation/request-parameters"
                )
                .queryParam("param", 3);


        try {
            restTemplate.getForEntity(uriBuilder.toUriString() , String.class);
        } catch (RuntimeException e) {
            e.getMessage();
        }

    }

    @Test
    void validRequestParameters() {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(
                        "http://localhost:" + localServerPort + "/validation/request-parameters"
                )
                .queryParam("param", 5);

        restTemplate.getForEntity(uriBuilder.toUriString() , String.class);
    }
}
