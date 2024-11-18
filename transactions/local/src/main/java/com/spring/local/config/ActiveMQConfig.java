package com.spring.local.config;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;

@Configuration
public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    String jmsUrl;

    @Value("${spring.activemq.user}")
    String jmsUserName;

    @Value("${spring.activemq.password}")
    String jmsPassword;

    @Bean
    public ConnectionFactory connectionFactory() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(jmsUrl);
        connectionFactory.setUser(jmsUserName);
        connectionFactory.setPassword(jmsPassword);
        return connectionFactory;
    }
}
