package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestBody Message message) {
        messageService.send(message);
    }

}
