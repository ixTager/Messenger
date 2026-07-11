package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    // WebSocket
    @MessageMapping("/dialog.send")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        messageDTO.setSentAt(Instant.now());
        messageService.sendMessage(messageDTO);
    }

    @GetMapping("/messages/{dialogId}")
    @ResponseBody
    public List<MessageDTO> getMessages(@PathVariable("dialogId") String dialogId) {
        return messageService.getMessages(dialogId);
    }
}
