package com.spring.wiremock.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;

import com.spring.wiremock.entity.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9090)
class UserControllerStubbingIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetUserExistingUser() {
        // Configurer WireMock pour simuler la réponse de l'API
        stubFor(get(urlEqualTo("/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Vinicius Junior"))); // La réponse est le nom de l'utilisateur

        // Appeler le contrôleur via TestRestTemplate
        ResponseEntity<User> response = restTemplate.getForEntity("/api/customer/1", User.class);

        // Vérifier la réponse
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Vinicius Junior", response.getBody().getName());
    }

    @Test
    void testGetUserServerError() {
        // Configurer WireMock pour simuler une erreur 500
        stubFor(get(urlEqualTo("/users/3"))
                .willReturn(aResponse().withStatus(500)));

        // Appeler le contrôleur via TestRestTemplate
        ResponseEntity<User> response = restTemplate.getForEntity("/api/customer/3", User.class);

        // Vérifier que la réponse a un code 200 et que le corps est null
        assertEquals(200, response.getStatusCode().value());
        assertNull(response.getBody()); // Aucune information utilisateur retournée
    }
}
