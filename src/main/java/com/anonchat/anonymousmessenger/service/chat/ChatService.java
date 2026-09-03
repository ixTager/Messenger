package com.anonchat.anonymousmessenger.service.chat;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.exceptions.DialogNotFoundException;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import com.anonchat.anonymousmessenger.service.UserService;
import com.anonchat.anonymousmessenger.utils.DialogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatService {
    private final DialogRepository dialogRepository;
    private final UserService userService;
    private final DialogUtil dialogUtil;
    private final ChatWebSocketService chatWebSocketService;

    public Dialog getDialogByUniqueDialogId(String uniqueDialogId) {
        return dialogRepository.findDialogWithUsersByUniqueDialogId(uniqueDialogId)
                .orElseThrow(() -> new DialogNotFoundException("Dialog not found with uniqueDialogId: " + uniqueDialogId));
    }

    public List<DialogDTO> getDialogsDTOByUniqueUserId(String uniqueUserId) {
        return dialogRepository.findDistinctByUsers_UniqueUserId(uniqueUserId)
                .stream()
                .map(dialogUtil::fromEntity)
                .filter(Objects::nonNull)
                .toList();
    }

    public String createDialogKey(Set<User> users) {
        return users.stream()
                .map(User::getUniqueUserId)
                .sorted()
                .collect(Collectors.joining(":"));
    }

    public Dialog createDialog(Set<User> users, String key) {
        Dialog dialog =  Dialog.builder()
                .uniqueDialogId(UUID.randomUUID().toString())
                .dialogKey(key)
                .build();
        users.forEach(user -> {
            dialog.getUsers().add(user);
            user.getDialogs().add(dialog);
        });

        dialogRepository.save(dialog);
        return dialog;
    }

    public void notifyDialogChange(Dialog dialog) {
        for (User user : dialog.getUsers()) {
            String uniqueUserId = user.getUniqueUserId();
            List<DialogDTO> dialogDTOList = getDialogsDTOByUniqueUserId(uniqueUserId);
            chatWebSocketService.sendChats(uniqueUserId, dialogDTOList);
        }
    }


    public String creatingDialog(String uniqueUserId) {
        User currentUser = userService.getCurrentUser();
        User secondUser = userService.getUserByUniqueUserId(uniqueUserId);
        Set<User> members = Set.of(currentUser, secondUser);

        if (currentUser.getUniqueUserId().equals(secondUser.getUniqueUserId())) return null;
        String key = createDialogKey(members);

        Dialog foundedDialog = dialogRepository.findDialogByDialogKey(key)
                .orElse(null);
        if (foundedDialog != null) return foundedDialog.getUniqueDialogId();

        Dialog createdDialog = createDialog(members, key);
        return createdDialog.getUniqueDialogId();
    }
}
