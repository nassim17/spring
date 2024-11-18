package com.spring.distributed.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.atomikos.jdbc.AtomikosDataSourceBean;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "xaEntityManagerFactoryBankA",
        transactionManagerRef = "transactionManagerJta",
        basePackages = {"com.spring.distributed.banka"}
)
public class XaAccountBankAConfig {

    @Value("${spring.datasource.bank-a.url}")
    String dbUrl;

    @Value("${spring.datasource.bank-a.username}")
    String username;

    @Value("${spring.datasource.bank-a.password}")
    String password;

    public Map<String, String> jpaProperties() {
        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.ddl-auto", "update");
        jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        jpaProperties.put("javax.persistence.transactionType", "JTA");
        return jpaProperties;
    }

    @Bean(name = "buildXaEntityManagerFactoryBankA")
    public EntityManagerFactoryBuilder buildXaEntityManagerFactoryBankA(JpaVendorAdapter jpaVendorAdapter) {
        return new EntityManagerFactoryBuilder(
                jpaVendorAdapter, jpaProperties(), null
        );
    }


    @Bean(name = "xaEntityManagerFactoryBankA")
    public LocalContainerEntityManagerFactoryBean xaEntityManagerFactoryBankA(@Qualifier("buildXaEntityManagerFactoryBankA") EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("xaDataSourceBankA") DataSource postgresDataSource) {

        return entityManagerFactoryBuilder
                .dataSource(postgresDataSource)
                .packages("com.spring.distributed.banka")
                .persistenceUnit("banka")
                .properties(jpaProperties())
                .jta(true)
                .build();
    }

    @Bean("xaDataSourceBankA")
    public DataSource xaDataSourceBankA() {
        PGXADataSource pgxaDataSource = new PGXADataSource();
        pgxaDataSource.setUrl(dbUrl);
        pgxaDataSource.setUser(username);
        pgxaDataSource.setPassword(password);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(pgxaDataSource);
        xaDataSource.setUniqueResourceName("BankA");
        xaDataSource.setMaxPoolSize(10);
        return xaDataSource;
    }
}
