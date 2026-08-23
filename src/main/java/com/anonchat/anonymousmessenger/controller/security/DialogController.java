package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.dto.UserRequest;
import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.service.DialogService;
import com.anonchat.anonymousmessenger.service.MessageService;
import com.anonchat.anonymousmessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class DialogController {
    private final MessageService messageService;
    private final UserService userService;
    private final DialogService dialogService;

    @GetMapping("/chats")
    public ResponseEntity<List<DialogDTO>> getChatsPage(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        List<DialogDTO> listOfDialogs = dialogService.getDialogsByUser(currentUser);


        return ResponseEntity;
    }

    @PostMapping("/api/create_dialog")
    public ResponseEntity<String> createDialog(@RequestBody UserRequest userRequest) {
        String uniqueDialogId = dialogService.createOrGetDialogByUniqueUserId(userRequest.getUniqueUserId());
        if (uniqueDialogId != null) return ResponseEntity.ok(uniqueDialogId);
        return ResponseEntity.badRequest().body("Error creating the dialog");
    }
}
