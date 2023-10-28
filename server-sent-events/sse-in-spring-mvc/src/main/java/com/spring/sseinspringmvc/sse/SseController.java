package com.spring.sseinspringmvc.sse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseController {


  @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter streamSse() {
    SseEmitter emitter = new SseEmitter();

    // Simulation d'envoi d'événements SSE
    new Thread(() -> {
      try {
        for (int i = 0; i < 10; i++) {
          emitter.send(SseEmitter.event()
              .data("Ceci est un événement SSE " + i)
              .id(String.valueOf(i))
              .name("custom-event"));
          Thread.sleep(1000); // Attente d'une seconde
        }
        emitter.complete();
      } catch (Exception e) {
        emitter.completeWithError(e);
      }
    }).start();

    return emitter;
  }
}
