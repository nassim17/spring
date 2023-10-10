package com.spring.threadpool.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application")
public class ThreadPoolConfiguration {

  private ThreadPool threadPool;

  @Bean
  public TaskExecutor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(threadPool.corePoolSize);
    executor.setMaxPoolSize(threadPool.maxPoolSize);
    executor.setQueueCapacity(threadPool.queueCapacity);
    executor.setThreadNamePrefix(threadPool.threadNamePrefix);
    executor.initialize();
    return executor;
  }

  @Setter
  private static class ThreadPool {

    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;
    private String threadNamePrefix;
  }
}
