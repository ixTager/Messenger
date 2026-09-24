package com.anonchat.anonymousmessenger.rabbitmq;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.dto.MessageStatusDTO;
import com.anonchat.anonymousmessenger.enumerating.MessageStatus;
import com.anonchat.anonymousmessenger.enumerating.WebSocketResponseTypes;
import com.anonchat.anonymousmessenger.model.Dialog;
import com.anonchat.anonymousmessenger.service.chat.ChatService;
import com.anonchat.anonymousmessenger.service.message.CacheMessageService;
import com.anonchat.anonymousmessenger.service.message.MessageService;
import com.anonchat.anonymousmessenger.service.message.MessageWebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class MessageListener {
    private final MessageService messageService;
    private final MessageWebSocketService messageWebSocketService;
    private final CacheMessageService cacheMessageService;
    private final ChatService chatService;

    @RabbitListener(queues = "${rabbitmq.queues.first.name}")
    @SendTo("${rabbitmq.queues.second.name}")
    public MessageDTO incomingMessages(MessageDTO message) {
        messageService.saveMessage(message);
        return message;
    }

    @RabbitListener(queues = "${rabbitmq.queues.second.name}")
    public void outgoingMessages(MessageDTO message) {
        messageWebSocketService.sendMessage(
                message.getUniqueDialogId(), WebSocketResponseTypes.MESSAGE_RECEIVED, message);

        cacheMessageService.cacheMessageDTO(message.getUniqueDialogId(), message);
        chatService.notifyDialogChange(
                chatService.getDialogByUniqueDialogId(message.getUniqueDialogId()));
    }


    @RabbitListener(queues = "${rabbitmq.queues.status.name}")
    public void onStatusUpdate(MessageStatusDTO dto) {
        messageWebSocketService.sendStatusUpdate(dto.getUniqueDialogId(), dto);
        log.info("Status update delivered for message {}", dto.getUuidMessage());
    }
}
