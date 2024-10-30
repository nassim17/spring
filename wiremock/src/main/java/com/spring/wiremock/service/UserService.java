package com.spring.wiremock.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.wiremock.entity.User;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    @Value("${user.api.url}")
    private String apiUrl; // Charger l'URL de l'API depuis les propriétés

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<User> getUserById(int id) {
        // Logique métier : vérifier si l'utilisateur existe avant d'appeler le service externe
        if (id <= 0) {
            return Optional.empty(); // ID invalide
        }

        try {
            String response = restTemplate.getForObject(apiUrl + "/" + id, String.class);
            if (response != null && !response.isEmpty()) {
                // Convertir la réponse en objet User
                return Optional.of(new User(id, response)); // Supposer que le nom est la réponse
            }
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.empty(); // Utilisateur non trouvé
    }
}
