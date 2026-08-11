package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.dto.UserRequest;
import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.service.DialogService;
import com.anonchat.anonymousmessenger.service.MessageService;
import com.anonchat.anonymousmessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/chats")
@RequiredArgsConstructor
public class DialogController {
    private final MessageService messageService;
    private final UserService userService;
    private final DialogService dialogService;

    @GetMapping
    public String getChatsPage(
            @RequestParam(required = false) String uniqueDialogId,
            Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);

        List<Dialog> dialogs = dialogService.getDialogsByUser(currentUser);
        model.addAttribute("dialogs", dialogs);

        if (uniqueDialogId != null) {
            List<MessageDTO> messages = messageService.getMessagesByDialogId(uniqueDialogId);
            model.addAttribute("messages", messages);
        }

        return "pages/chats";
    }

    @PostMapping("/find_user")
    public String findUserByUniqueUserId(@RequestParam String uniqueUserId, Model model) {
        User currentUser = userService.getCurrentUser();
        User foundedUser = userService.getUserByUniqueUserId(uniqueUserId);
        List<Dialog> dialogs = dialogService.getDialogsByUser(currentUser);

        model.addAttribute("foundedUser", foundedUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("dialogs", dialogs);

        return "pages/chats";
    }
    @PostMapping("/create_dialog")
    public String createDialog(@RequestBody UserRequest userRequest) {
        String uniqueDialogId = dialogService.createOrGetDialog(userRequest);
        if (uniqueDialogId == null) { return "pages/chats"; }

        return "redirect:/pages/chats?uniqueDialogId=" + uniqueDialogId;
    }

}
