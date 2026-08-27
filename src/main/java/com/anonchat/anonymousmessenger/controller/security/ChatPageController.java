package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.dto.UserRequest;
import com.anonchat.anonymousmessenger.service.ChatService;
import com.anonchat.anonymousmessenger.service.MessageService;
import com.anonchat.anonymousmessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatPageController {
    private final UserService userService;
    private final ChatService chatService;

    @GetMapping
    public String getChatsPage(Model model) {
        UserDTO currentUser = userService.getCurrentUserDTO();
        model.addAttribute("currentUser", currentUser);
        return "pages/chats";
    }

    @GetMapping("/{uniqueDialogId}")
    public String getChatPage(@PathVariable("uniqueDialogId") String uniqueDialogId, Model model) {
        UserDTO currentUser = userService.getCurrentUserDTO();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("uniqueDialogId", uniqueDialogId);
        return "pages/chat";
    }
}

