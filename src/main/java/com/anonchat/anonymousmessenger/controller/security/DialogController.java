package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.request.UserRequest;
import com.anonchat.anonymousmessenger.service.chat.DialogService;
import com.anonchat.anonymousmessenger.service.message.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
@Tag(name = "chats", description = "API for working with dialogs")
public class DialogController {
    private final MessageService messageService;
    private final DialogService dialogService;

    @GetMapping
    @Operation(
            summary = "Get all dialogs for current user",
            description = "Returns a list of dialogs for the current user"
    )
    @ApiResponse(responseCode = "200", description = "Getting dialogs is successful")
    @ApiResponse(responseCode = "400", description = "Getting dialogs is unsuccessful")
    public ResponseEntity<List<DialogDTO>> getDialogs() {
        List<DialogDTO> chats = dialogService.getDialogsDTOCurrentUser();
        if (chats != null) return ResponseEntity.ok(chats);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{uniqueDialogId}")
    @Parameter(name = "Unique Dialog Id From URL", description = "Get messages by uniqueDialogId")
    @Operation(
            summary = "Get all messages in dialog",
            description = "Returns a list of messages for the current dialog"
    )
    @ApiResponse(responseCode = "200", description = "Getting messages is successful")
    @ApiResponse(responseCode = "400", description = "Getting messages is unsuccessful")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable("uniqueDialogId") String uniqueDialogId) {
        List<MessageDTO> messages = messageService.getMessagesByDialogId(uniqueDialogId);
        if (messages != null ) return ResponseEntity.ok(messages);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    @Operation(
            summary = "Creating dialog",
            description = "Creating dialog by UserRequest and return (New or Old) Unique Dialog ID"
    )
    @ApiResponse(responseCode = "200", description = "Creating dialog is successful")
    @ApiResponse(responseCode = "400", description = "Creating dialog is unsuccessful")
    public ResponseEntity<String> createDialog(@RequestBody UserRequest userRequest) {
        String uniqueDialogId = dialogService.creatingDialog(userRequest.getUniqueUserId());
        if (uniqueDialogId != null) return ResponseEntity.ok(uniqueDialogId);
        return new ResponseEntity<>("Error creating the dialog", HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/{uniqueDialogId}/read")
    @Parameter(name = "Unique Dialog Id From URL", description = "Update messages by uniqueDialogId")
    @Operation(
            summary = "Update all messages in dialog",
            description = "Set messages status - READ, when another user open the dialog"
    )
    @ApiResponse(responseCode = "200", description = "Marking messages as Read is successful")
    @ApiResponse(responseCode = "204", description = "Marking messages as Read is unsuccessful")
    public ResponseEntity<Void> markMessagesAsRead(@PathVariable("uniqueDialogId") String uniqueDialogId) {
        messageService.markMessagesAsRead(uniqueDialogId);
        return ResponseEntity.noContent().build();
    }
}
