package com.anonchat.anonymousmessenger.rabbitmq;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.service.MessageService;
import com.anonchat.anonymousmessenger.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageListener {
    private final MessageService messageService;

    @RabbitListener(queues = "#{@environment.getProperty('rabbitmq.queue.name')}")
    public void receiveMessage(MessageDTO message) {
        messageService.saveMessage(message);
    }
}
