package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.config.RabbitMQConfig;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final RabbitTemplate rabbitTemplate;
    private final DialogCacheService dialogCacheService;

    public void sendMessage(MessageDTO message) {
        dialogCacheService.cacheMessages(message.getDialogId(), message);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                message
        );
    }

    public List<MessageDTO> getMessages(String dialogId) {
        List<MessageDTO> messages = dialogCacheService.getLatestMessages(dialogId);
        if (messages != null) {
            return messages;
        }
        List<MessageDTO> messagesToCache = dialogCacheService.getLatestMessages(dialogId);
        messagesToCache.forEach(message -> {
            dialogCacheService.cacheMessages(message.getDialogId(), message);
        });
        return messagesToCache;
    }

}
