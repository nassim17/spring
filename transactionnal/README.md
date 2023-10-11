# Les transactions avec Spring et JPA

### Configuration:
1) Sur un projet Spring Boot avec Spring-Data- les transactions sont actives par défaut.
2) Sur un projet qui n'utilise pas Spring-Data- il faut activer les transactions avec une class @Configuration + @EnableTransactionManagement.
```
@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig{

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
       //...
   }

   @Bean
   public PlatformTransactionManager transactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
      return transactionManager;
   }
}
```