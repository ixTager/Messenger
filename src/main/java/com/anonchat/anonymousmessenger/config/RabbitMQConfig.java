package com.anonchat.anonymousmessenger.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "messages.queue";
    public static final String EXCHANGE_NAME = "messages.exchange";
    public static final String ROUTING_KEY = "messagesRoutingKey";

    @Bean
    public JacksonJsonMessageConverter messageConverter() { return new JacksonJsonMessageConverter(); }

    @Bean
    Queue queue() { return new Queue(QUEUE_NAME, true); }

    @Bean
    TopicExchange exchange() { return new TopicExchange(EXCHANGE_NAME); }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

}
