package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.request.MessageRequest;
import com.anonchat.anonymousmessenger.service.message.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@Tag(name = "Messages", description = "Messages control")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send_message")
    @Operation(
            summary = "Send message",
            description = "Sending a message to dialog by MessageRequest"
    )
    @ApiResponse(responseCode = "200", description = "Sending message is successful")
    @ApiResponse(responseCode = "400", description = "Sending message is unsuccessful")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequest messageRequest) {
        boolean sendMessageStatus = messageService.send(messageRequest);
        if (sendMessageStatus) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
