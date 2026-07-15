package com.anonchat.anonymousmessenger.rabbitmq;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.utils.MessageUtil;
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
    private final MessageUtil messageUtil;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    //  Сообщение в очеередь
    public void sendMessage(MessageDTO messageDTO) {
        rabbitTemplate.convertAndSend(queueName, messageDTO);
        log.debug("Message sent to queue {}", queueName);
    }

}
