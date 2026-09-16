package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.request.UserRequest;
import com.anonchat.anonymousmessenger.service.chat.ChatService;
import com.anonchat.anonymousmessenger.service.message.MessageService;
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
    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<List<DialogDTO>> getDialogs() {
        List<DialogDTO> chats = chatService.getDialogsDTOCurrentUser();
        if (chats != null) return new ResponseEntity<>(chats, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{uniqueDialogId}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable("uniqueDialogId") String uniqueDialogId) {
        List<MessageDTO> messages = messageService.getMessagesByDialogId(uniqueDialogId);
        if (messages != null ) return new ResponseEntity<>(messages, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<String> createDialog(@RequestBody UserRequest userRequest) {
        String uniqueDialogId = chatService.creatingDialog(userRequest.getUniqueUserId());
        if (uniqueDialogId != null) return new ResponseEntity<>(uniqueDialogId,  HttpStatus.OK);
        return new ResponseEntity<>("Error creating the dialog", HttpStatus.BAD_REQUEST);
    }
}
