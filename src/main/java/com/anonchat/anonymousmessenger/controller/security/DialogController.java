package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.dto.UserRequest;
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
@RequestMapping("/api/chats")
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
    public ResponseEntity<UserDTO> findUserByUniqueUserId(@RequestBody UserRequest userRequest) {
        UserDTO foundedUser = userService.getUserDTOByUniqueUserId(userRequest.getUniqueUserId());

        return new ResponseEntity<>(foundedUser, HttpStatus.FOUND);
    }
    @PostMapping("/create_dialog")
    public ResponseEntity<String> createDialog(@RequestBody UserRequest uniqueUserId) {
        String uniqueDialogId = dialogService.createOrGetDialogByUniqueUserId(uniqueUserId.getUniqueUserId());
        if (uniqueDialogId != null) { return new ResponseEntity<>(uniqueDialogId, HttpStatus.OK); }
        else return new ResponseEntity<>("Incorrect uniqueDialogId", HttpStatus.BAD_REQUEST);
    }
}
