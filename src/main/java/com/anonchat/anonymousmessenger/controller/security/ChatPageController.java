package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.exceptions.UserNotFoundException;
import com.anonchat.anonymousmessenger.service.user.UserService;
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
        try {
            UserDTO currentUserDTO = userService.getCurrentUserDTO();
            model.addAttribute("currentUser", currentUserDTO);
            return "pages/chats";
        }
        catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/{uniqueDialogId}")
    public String getChatPage(@PathVariable("uniqueDialogId") String uniqueDialogId, Model model) {
        try {
            UserDTO currentUserDTO = userService.getCurrentUserDTO();
            UserDTO secondUserDTO = userService.getSecondMemberInDialog(currentUserDTO.getUniqueUserId(), uniqueDialogId);

            if (secondUserDTO != null) model.addAttribute("secondUser", secondUserDTO);

            model.addAttribute("currentUser", currentUserDTO);
            model.addAttribute("uniqueDialogId", uniqueDialogId);
            return "pages/chat";
        }
        catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
            return "redirect:/login";
        }
    }
}

