package com.spring.distributed.config;

import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class XaJmsTemplateConfig {

    @Bean("xaJmsTemplate")
    public JmsTemplate xaJmsTemplate(ConnectionFactory xaConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(xaConnectionFactory);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }
}
