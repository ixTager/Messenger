package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public String getChats(@PathVariable String userId,
                           @RequestParam(required = false, name = "dialog") String dialogId,
                           Model model) {
        User currentUser = userService.getCurrentUser();

        if (!currentUser.getUserId().equals(userId)) { return "redirect:/";}

        model.addAttribute("userId", userId);
        model.addAttribute("dialogId", dialogId);
        model.addAttribute("senderName", currentUser.getFirstName());
        return "pages/chats";
    }
}
