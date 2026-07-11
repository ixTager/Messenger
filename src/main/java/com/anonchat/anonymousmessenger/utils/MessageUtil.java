package com.anonchat.anonymousmessenger.utils;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import com.anonchat.anonymousmessenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageUtil {
    private final DialogRepository dialogRepository;
    private final UserRepository userRepository;

    public Message toEntity(MessageDTO messageDTO) {
        Optional<Dialog> optionalDialog = dialogRepository.findDialogByUniqueId(messageDTO.getDialogId());
        Dialog dialog = optionalDialog.orElseThrow(() -> new RuntimeException("Dialog not found"));

        User sender = userRepository.findByUserId(messageDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Message.builder()
                .sender(sender)
                .dialog(dialog)
                .content(messageDTO.getContent())
                .sentAt(Instant.now())
                .build();
    }
}
