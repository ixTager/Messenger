package com.anonchat.anonymousmessenger.utils;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class MessageUtil {
    private final DialogRepository dialogRepository;

    public Message toEntity(MessageDTO message) {
        Dialog dialog = dialogRepository.findDialogById(message.getDialogId())
                .orElseThrow(() -> new RuntimeException("Dialog not found"));

        return Message.builder()
                .id(message.getId())
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .dialog(dialog)
                .build();
    }

    public MessageDTO fromEntity(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .dialogId(message.getDialog().getId())
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .build();
    }
}
