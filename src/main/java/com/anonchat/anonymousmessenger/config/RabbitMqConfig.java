package com.anonchat.anonymousmessenger.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.queues.first.name}")
    private String incomingMessagesQueueName;

    @Value("${rabbitmq.queues.second.name}")
    private String outgoingMessagesQueueName;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queues.first.routing-key}")
    private String incomingRoutingKey;

    @Value("${rabbitmq.queues.second.routing-key}")
    private String outgoingRoutingKey;

    @Value("${rabbitmq.queues.status.name}")
    private String statusQueueName;

    @Value("${rabbitmq.queues.status.routing-key}")
    private String statusRoutingKey;


    @Bean
    public TopicExchange exchange() { return new TopicExchange(exchangeName); }

    @Bean
    public MessageConverter jsonMessageConverter() { return new JacksonJsonMessageConverter(); }


    // Incoming Messages
    @Bean
    Queue incomingQueue() { return new Queue(incomingMessagesQueueName, true); }

    @Bean
    public Binding bindingIncoming(Queue incomingQueue, TopicExchange exchange) {
        return BindingBuilder.bind(incomingQueue).to(exchange).with(incomingRoutingKey);
    }


    // Outgoing Messages
    @Bean
    Queue outgoingQueue() { return new Queue(outgoingMessagesQueueName, true); }

    @Bean
    public Binding bindingOutGoing(Queue outgoingQueue, TopicExchange exchange) {
        return BindingBuilder.bind(outgoingQueue).to(exchange).with(outgoingRoutingKey);
    }

    // Messages Statues
    @Bean
    Queue statusMessagesQueue() { return new Queue(statusQueueName, true); }

    @Bean
    public Binding bindingStatusMessages(Queue statusMessagesQueue, TopicExchange exchange) {
        return BindingBuilder.bind(statusMessagesQueue).to(exchange).with(statusRoutingKey);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
