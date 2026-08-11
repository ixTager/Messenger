package com.anonchat.anonymousmessenger.utils;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.dto.MessageRequest;
import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import com.anonchat.anonymousmessenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageUtil {
    private final DialogRepository dialogRepository;
    private final UserRepository userRepository;

    public Message toEntity(MessageDTO messageDTO) {
        Dialog dialog = dialogRepository.findDialogByUniqueDialogId(messageDTO.getUniqueDialogId())
                .orElseThrow(() -> new RuntimeException("Dialog not found"));
        User user = userRepository.findByUniqueUserIdIgnoreCase(messageDTO.getUniqueUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Message.builder()
                .id(messageDTO.getId())
                .user(user)
                .content(messageDTO.getContent())
                .sentAt(messageDTO.getSentAt())
                .dialog(dialog)
                .build();
    }
    public Message toEntity(MessageRequest messageRequest) {
        Dialog dialog = dialogRepository.findDialogByUniqueDialogId(messageRequest.getUniqueDialogId())
                .orElseThrow(() -> new RuntimeException("Dialog not found"));
        return Message.builder()
                .content(messageRequest.getContent())
                .dialog(dialog)
                .build();
    }

    public MessageDTO fromEntity(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .uniqueUserId(message.getUser().getUniqueUserId())
                .senderName(message.getUser().getProfile().getFirstName())
                .uniqueDialogId(message.getDialog().getUniqueDialogId())
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .build();
    }

}
