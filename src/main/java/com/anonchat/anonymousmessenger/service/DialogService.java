package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogService {
    private final DialogRepository dialogRepository;

    public List<Dialog> getDialogsByUser(User user){
        return dialogRepository.findByUsersContains(user);
    }

}
