package com.anonchat.anonymousmessenger.rabbitmq;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.service.chat.ChatService;
import com.anonchat.anonymousmessenger.service.message.CacheMessageService;
import com.anonchat.anonymousmessenger.service.message.MessageService;
import com.anonchat.anonymousmessenger.service.message.MessageWebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageListener {
    private final MessageService messageService;
    private final MessageWebSocketService messageWebSocketService;
    private final CacheMessageService cacheMessageService;
    private final ChatService chatService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(MessageDTO message) {
        Dialog dialog = chatService.getDialogByUniqueDialogId(message.getUniqueDialogId());
        messageService.saveMessage(message);
        messageWebSocketService.sendMessage(message.getUniqueDialogId(), message);
        chatService.notifyDialogChange(dialog);
        cacheMessageService.cacheMessageDTO(message.getUniqueDialogId(), message);
    }
}
