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
L'annotation **@Transactional** est la métadonnée qui spécifie la sémantique des transactions sur une méthode. Elle supporte plusieurs paramétres:
+ **_Propagation_**: Type de la transaction.
+ **_Isolation Level_**: Niveau de la transaction.
+ **_Timeout_**: Timeout de la transaction.
+ **_readOnly flag_**: Mettre la trasaction en mode lecture seule.
+ **_Rollback_**: La gestion du Rollback.

### **1) Propagation:**
### **2) Isolation Level:**
### **3) Timeout:**
### **4) readOnly flag:**
### **5) Rollback:**
Nous avons deux manières d'annuler une transaction : déclarative et programmatique.
> **N.B:** _Par défaut le Rollback se produit uniquement au moment de **l'exécution(runtime)**, avec **les exceptions non vérifiées**. L'exception vérifiée ne déclenche pas d'annulation de la transaction. Nous pouvons bien sûr configurer ce comportement avec les paramètres d'annotation **rollbackFor** et **noRollbackFor**._

L'annotation **@Transactional** nous propose les attributs ci-dessous pour configurer un **Rollback** ou un **noRollback** pour une liste d'excéptions:
**rollbackFor, rollbackForClassName, noRollbackFor, noRollbackForClassName.**

Dans la classe RollbackBookService nous avons 4 exemples de configuration du **rollback**:
- Comportement par défaut avec une excéption vérifiée.
- Un rollback pour une excéption vérifiée.
- Comportement par défaut avec une excéption non vérifiée.
- Un noRollback pour une excéption non vérifiée.

Nous avons les cas de tests dans la classe RollbackBookServiceTests.