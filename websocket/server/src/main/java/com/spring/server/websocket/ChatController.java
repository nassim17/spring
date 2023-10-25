package com.spring.server.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {

  private final SimpMessagingTemplate simpMessagingTemplate;

  public ChatController(SimpMessagingTemplate simpMessagingTemplate){
    this.simpMessagingTemplate = simpMessagingTemplate;
  }


  @MessageMapping("/chat") // L'annotation indique que cette méthode gère les messages destinés à "/app/chat".
  @SendTo("/topic/reply") // Envoie la réponse à "/topic/reply".
  public String broadcastMessage(@Payload String message) {
    // Traitement du message

    // La réponse sera envoyée à la destination "/topic/reply".
    return message;
  }

  @MessageMapping("/chat")
  public void handleChatMessage(@Payload String message) {
    // Traitement du message
  }

  @MessageMapping("/chat")
  @SendToUser("/topic/reply")
  public String handleChatMessageReply(String message) {
    // Traitement du message

    // La réponse sera envoyée à l'utilisateur qui a envoyé le message.
    return "Réponse au message : " + message;
  }

  @MessageMapping("/chat")
  public void broadcastResponse(@Payload String message) {
    // Traitement du message

    // Diffusion du message à tous les clients connectés
    simpMessagingTemplate.convertAndSend("/topic/reply",
        "Réponse au message : " + message
    );
  }

  @MessageMapping("/chat")
  public void handleChatMessageResponse(@Payload String message) {
    // Traitement du message

    // Envoi d'une réponse à l'utilisateur qui a envoyé le message
    simpMessagingTemplate.convertAndSendToUser("user", // Nom de l'utilisateur destinataire
        "/topic/reply", // Destination de réponse
        "Réponse au message : " + message
    );
  }
}
