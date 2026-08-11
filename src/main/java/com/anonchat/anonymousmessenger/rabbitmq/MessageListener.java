package com.anonchat.anonymousmessenger.rabbitmq;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.service.MessageService;
import com.anonchat.anonymousmessenger.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageListener {
    private final MessageService messageService;
    private final WebSocketService webSocketService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(MessageDTO message) {
        messageService.saveMessage(message);
        webSocketService.sendMessage(message.getUniqueDialogId(), message);
    }
}
