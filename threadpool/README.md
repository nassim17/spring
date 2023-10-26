# ThreadPool:

## ThreadPoolTaskExecutor:
**_ThreadPoolTaskExecutor_** est une classe de Spring Framework qui fournit une abstraction autour d'une instance **_java.util.concurrent.ThreadPoolExecutor_** et l'expose en tant que Spring **_org.springframework.core.task.TaskExecutor_**. De plus, il est hautement configurable.
+ **_setCorePoolSize(int corePoolSize)_**: Définit le nombre minimum de workers à maintenir en vie sans délai d'attente.
+ **_setMaxPoolSize(int maxPoolSize)_**: Définit le nombre maximum de threads pouvant être créés, maxPoolSize dépend de queueCapacity, ThreadPoolTaskExecutor ne créera un nouveau thread que si le nombre d'éléments dans sa file d'attente dépasse queueCapacity.
+ **_setQueueCapacity(int queueCapacity)_**: Définit la capacité de la file d'attente.
+ **_setThreadNamePrefix(@Nullable String threadNamePrefix)_**: Définit un préfixe qui sera utilisé pour nommer les threads du pool.