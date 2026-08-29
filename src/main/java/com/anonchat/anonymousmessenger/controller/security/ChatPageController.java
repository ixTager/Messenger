package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatPageController {
    private final UserService userService;

    @GetMapping
    public String getChatsPage(Model model) {
        model.addAttribute("currentUser", userService.getCurrentUserDTO());
        return "pages/chats";
    }

    @GetMapping("/{uniqueDialogId}")
    public String getChatPage(@PathVariable("uniqueDialogId") String uniqueDialogId, Model model) {
        model.addAttribute("currentUser", userService.getCurrentUserDTO());
        model.addAttribute("uniqueDialogId", uniqueDialogId);
        return "pages/chat";
    }
}

