package com.anonchat.anonymousmessenger.rabbitmq;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queues.first.name}")
    private String incomingQueueName;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queues.first.routing-key}")
    private String incomingRoutingKey;

    public void sendMessage(MessageDTO messageDTO) {
        rabbitTemplate.convertAndSend(exchange, incomingRoutingKey, messageDTO);
        log.info("Message sent to queue {}", incomingQueueName);
    }

}
