# Spring WebSocket - Serveur

### Configuration:
1) Avec un projet Spring Boot, ajouter seulement la dépendance **spring-boot-starter-websocket**.
2) Créez une classe de configuration qui étend **WebSocketMessageBrokerConfigurer**.
+ Activer la prise en charge WebSocket. 
+ Définir les endpoints WebSocket, les intercepteurs et les destinations.
```
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/app");
    registry.setUserDestinationPrefix("/user");
    registry.enableSimpleBroker("/topic");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws/stomp").setAllowedOrigins("*");
  }
}
```
### MessageBrokerRegistry:
Cette classe est utilisée pour configurer le broker de messages WebSocket, qui gère la distribution de messages entre les clients WebSocket et le serveur.

Le message broker WebSocket est responsable de l'acheminement des messages entre les clients connectés et peut prendre en charge diverses fonctionnalités, telles que la diffusion (broadcast) de messages à plusieurs clients, la gestion des files d'attente (queues) de messages, etc.

Voici quelques-unes des méthodes les plus couramment utilisées de _**MessageBrokerRegistry**_:
+ _**enableSimpleBroker(String... destinations):**_ Permet d'activer un broker de messages WebSocket permettant la diffusion de messages aux clients connectés à des destinations spécifiques.
+ _**enableStompBrokerRelay (String... destinationPrefixes):**_ Permet d'active un relais de broker **STOMP** _(Simple Text Oriented Messaging Protocol)_ pour gérer la distribution de messages WebSocket, offrant des fonctionnalités avancées telles que la gestion des files d'attente et la communication avec d'autres brokers WebSocket.
+ _**setApplicationDestinationPrefixes(String destinationPrefix):**_ Permet de définir un préfixe pour les destinations au niveau de l'application.
+ _**setUserDestinationPrefix(String... prefixes):**_ Permet de définir un préfixe pour les destinations de l'utilisateur.

### StompEndpointRegistry:
Cette classe est utilisée pour enregistrer les endpoints WebSocket que les clients utiliseront pour établir des connexions WebSocket avec le serveur. Elle permet de configurer ces endpoints, y compris les options de sécurité, les transports pris en charge, et d'autres paramètres liés à la configuration des connexions WebSocket.
+ _**addEndpoint(String... paths):**_ Elle spécifie l'URL de l'endpoint WebSocket que les clients utiliseront pour établir une connexion WebSocket. C'est l'URL à laquelle les clients enverront une requête WebSocket pour démarrer une session WebSocket.
+ _**setAllowedOrigins(String... origins):**_  Elle est utilisée pour configurer la liste des origines autorisées à accéder à un endpoint WebSocket.