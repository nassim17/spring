package com.spring.distributed.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AccountBankMessageConsumer {

    /**
     * Méthode pour écouter les messages dans la queue ActiveMQ.
     * @param message Le message reçu depuis la queue.
     */
    @JmsListener(destination = "accountBankQueue")
    public void consumeMessage(String message) {
        // Log du message reçu (vous pouvez effectuer d'autres actions ici)
        log.info("Received message: {}", message);
    }
}
