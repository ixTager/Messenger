package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.request.MessageRequest;
import com.anonchat.anonymousmessenger.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send_message")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequest messageRequest) {
        boolean sendMessageStatus = messageService.send(messageRequest);
        if (sendMessageStatus) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
