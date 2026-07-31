package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.service.DialogService;
import com.anonchat.anonymousmessenger.service.MessageService;
import com.anonchat.anonymousmessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class DialogController {
    private final MessageService messageService;
    private final UserService userService;
    private final DialogService dialogService;

    @GetMapping("/chats")
    public String getChatsPage(
            @RequestParam(required = false) Long dialogId,
            Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);

        List<Dialog> dialogs = dialogService.getDialogsByUser(currentUser);
        model.addAttribute("dialogs", dialogs);

        if (dialogId != null) {
            List<MessageDTO> messages = messageService.getMessagesByDialogId(dialogId);
            model.addAttribute("messages", messages);
        }

        return "pages/chats";
    }

    @PostMapping("/chats")
    public String getUserByUniqueId(@RequestParam String uniqueUserId, Model model) {
        User foundedUser = userService.getUser(uniqueUserId);
        User currentUser = userService.getCurrentUser();
        List<Dialog> dialogs = dialogService.getDialogsByUser(foundedUser);

        model.addAttribute("foundedUser", foundedUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("dialogs", dialogs);
        return "pages/chats";
    }

}
