package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @GetMapping("/{id}")
    public String getChats(@PathVariable String id,
                           Model model,
                           @RequestParam(required = false) String dialogId) {
        try{
            User user = userService.getCurrentUser();
            if (user == null) { return "redirect:/"; }
        }
        catch (UsernameNotFoundException e){
            return "redirect:/";
        }
        return "pages/chats";
    }
}
