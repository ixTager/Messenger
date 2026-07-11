package com.anonchat.anonymousmessenger.rabbitmq;

import com.anonchat.anonymousmessenger.config.RabbitMQConfig;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import com.anonchat.anonymousmessenger.repository.MessageRepository;
import com.anonchat.anonymousmessenger.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final MessageRepository messageRepository;
    private final MessageUtil messageUtil;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consume(MessageDTO messageDTO) {
        Message message = messageUtil.toEntity(messageDTO);
        messageRepository.save(message);
        simpMessagingTemplate.convertAndSend(
                "/topic/dialog/" + message.getDialog().getUniqueId(),
                messageDTO
        );
    }
}
