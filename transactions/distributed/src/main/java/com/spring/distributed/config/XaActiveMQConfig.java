package com.spring.distributed.config;

import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atomikos.jms.AtomikosConnectionFactoryBean;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;

@Configuration
public class XaActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    String jmsUrl;

    @Value("${spring.activemq.user}")
    String jmsUserName;

    @Value("${spring.activemq.password}")
    String jmsPassword;

    @Bean("xaConnectionFactory")
    public ConnectionFactory xaConnectionFactory() throws JMSException {
        ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new ActiveMQXAConnectionFactory();
        activeMQXAConnectionFactory.setBrokerURL(jmsUrl);
        activeMQXAConnectionFactory.setUser(jmsUserName);
        activeMQXAConnectionFactory.setPassword(jmsPassword);

        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setUniqueResourceName("xamq");
        atomikosConnectionFactoryBean.setLocalTransactionMode(false);
        atomikosConnectionFactoryBean.setMaxPoolSize(10);
        atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory);
        return atomikosConnectionFactoryBean;
    }
}
