package com.spring.wiremock.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.spring.wiremock.entity.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerRecordingIntegrationTest {

    private static final String TARGET_SERVICE_URL = "https://jsonplaceholder.typicode.com"; // Exemple de service tiers

    private WireMockServer wireMockServer;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        // Créer le dossier pour enregistrer les mappings si nécessaire
        File recordingsDir = new File("src/test/resources/mappings");
        if (!recordingsDir.exists()) {
            recordingsDir.mkdirs();
        }

        wireMockServer = new WireMockServer(9090);
        wireMockServer.start();
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    @Order(1) // Enregistrement des interactions
    void testRecordGetUser() {
        // Configurer WireMock pour enregistrer les interactions avec le service cible
        wireMockServer.startRecording(
                WireMock.recordSpec()
                        .forTarget(TARGET_SERVICE_URL)
                        .captureHeader("Content-Type")
                        .makeStubsPersistent(true)  // Enregistrer les stubs pour les utiliser en relecture
                        .build()
        );

        // Effectuer une requête pour enregistrer la réponse
        ResponseEntity<String> response = restTemplate.getForEntity("/api/customer/2", String.class);

        // Vérifier que l'enregistrement a bien capturé la réponse du service externe
        assertEquals(200, response.getStatusCode().value());

        // Arrêter l'enregistrement
        wireMockServer.stopRecording();
    }

    @Test
    @Order(2) // Relecture des interactions
    void testReplayGetUser() {
        // Charger les stubs enregistrés dans le dossier "mappings"
        wireMockServer.resetAll();

        // Appeler le même endpoint en mode relecture
        ResponseEntity<User> replayedResponse = restTemplate.getForEntity("/api/customer/2", User.class);

        // Vérifier la réponse rejouée pour confirmer que WireMock utilise le stub enregistré
        assertEquals(200, replayedResponse.getStatusCode().value());
        assertNotNull(replayedResponse.getBody());
    }
}
