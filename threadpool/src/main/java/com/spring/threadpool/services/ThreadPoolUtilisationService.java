package com.spring.threadpool.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThreadPoolUtilisationService {

  private final TaskExecutor threadPoolTaskExecutor;

  public void process(){
    threadPoolTaskExecutor.execute(() -> {});
  }
}
