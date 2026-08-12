package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class DialogService {
    private final DialogRepository dialogRepository;
    private final UserService userService;

    public List<Dialog> getDialogsByUser(User user){
        return dialogRepository.findByUsersContains(user);
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

    public Dialog findOrCreateDialog(Set<User> users, String key) {
        return dialogRepository.findDialogByDialogKey(key)
                .orElseGet(() -> createDialog(users, key));
    }

    public String createOrGetDialogByUniqueUserId(String uniqueUserId) {
        User currentUser = userService.getCurrentUser();
        User secondUser = userService.getUserByUniqueUserId(uniqueUserId);
        if (currentUser.getUniqueUserId().equals(secondUser.getUniqueUserId())) { return null; }
        String key = createDialogKey(Set.of(currentUser, secondUser));

        Dialog dialog = findOrCreateDialog(Set.of(currentUser, secondUser), key);
        return dialog.getUniqueDialogId();

    }

}
