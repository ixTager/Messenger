package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.dto.MessageRequest;
import com.anonchat.anonymousmessenger.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send_message")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequest messageRequest) {
        messageService.send(messageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/dialogs/{uniqueDialogId}/messages")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable String uniqueDialogId) {
        return ResponseEntity.ok(messageService.getMessagesByDialogId(uniqueDialogId));
    }
}
