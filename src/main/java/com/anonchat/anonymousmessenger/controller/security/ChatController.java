package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.dto.UserRequest;
import com.anonchat.anonymousmessenger.service.ChatService;
import com.anonchat.anonymousmessenger.service.MessageService;
import com.anonchat.anonymousmessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {
    private final MessageService messageService;
    private final UserService userService;
    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<List<DialogDTO>> getDialogs() {
        UserDTO currentUser = userService.getCurrentUserDTO();
        List<DialogDTO> chats = chatService.getDialogsDTOByUniqueUserId(currentUser.getUniqueUserId());
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @GetMapping("/{uniqueDialogId}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable("uniqueDialogId") String uniqueDialogId) {
        List<MessageDTO> messages = messageService.getMessagesByDialogId(uniqueDialogId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createDialog(@RequestBody UserRequest userRequest) {
        String uniqueDialogId = chatService.createOrGetDialogByUniqueUserId(userRequest.getUniqueUserId());
        if (uniqueDialogId != null) return ResponseEntity.ok(uniqueDialogId);
        return ResponseEntity.badRequest().body("Error creating the dialog");
    }
}
