package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.service.chat.DialogService;
import com.anonchat.anonymousmessenger.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatPageController {
    private final UserService userService;
    private final DialogService dialogService;

    @GetMapping
    public String getChatsPage(Model model) {
        UserDTO currentUserDTO = userService.getCurrentUserDTO();

        if (currentUserDTO == null) return "redirect:/login";

        List<DialogDTO> dialogs = dialogService.getDialogsDTOCurrentUser();
        model.addAttribute("currentUser", currentUserDTO);
        model.addAttribute("dialogs", dialogs);
        return "pages/chats";
    }

    @GetMapping("/{uniqueDialogId}")
    public String getChatPage(@PathVariable("uniqueDialogId") String uniqueDialogId, Model model) {
        UserDTO currentUserDTO = userService.getCurrentUserDTO();

        if (currentUserDTO == null) return "redirect:/login";

        UserDTO secondUserDTO = userService.getSecondMemberInDialog(currentUserDTO.getUniqueUserId(), uniqueDialogId);
        List<DialogDTO> dialogs = dialogService.getDialogsDTOCurrentUser();

        if (secondUserDTO != null) model.addAttribute("secondUser", secondUserDTO);

        model.addAttribute("currentUser", currentUserDTO);
        model.addAttribute("dialogs", dialogs);
        model.addAttribute("uniqueDialogId", uniqueDialogId);
        return "pages/chat";

    }
}

