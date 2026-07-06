package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @MessageMapping("/send")
    public void sendMessage(@Payload Message message) {
        message.setSentAt(Instant.now());
        messageService.sendMessage(message);
    }
}
