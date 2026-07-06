package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.config.RabbitMQConfig;
import com.anonchat.anonymousmessenger.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final RabbitTemplate rabbitTemplate;
    private final DialogCacheService dialogCacheService;

    public void sendMessage(Message message) {
        dialogCacheService.cacheMessages(message);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                message
        );
    }

}
