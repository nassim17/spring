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

## @Transactional:
L'annotation **@Transactional** supporte plusieurs paramétres:
+ **_Propagation_**: Type de la transaction.
+ **_Isolation Level_**: Niveau de la transaction.
+ **_Timeout_**: Timeout de la transaction.
+ **_readOnly flag_**: Mettre la trasaction en mode lecture seule.
+ **_Rollback_**: La gestion du Rollback.
> **N.B:** _Par défaut le Rollback se produit uniquement au moment de **l'exécution(runtime)**, avec **les exceptions non vérifiées**. L'exception vérifiée ne déclenche pas d'annulation de la transaction. Nous pouvons bien sûr configurer ce comportement avec les paramètres d'annotation **rollbackFor** et **noRollbackFor**._
