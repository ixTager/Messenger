package com.anonchat.anonymousmessenger.utils;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.exceptions.UserNotFoundException;
import com.anonchat.anonymousmessenger.request.MessageRequest;
import com.anonchat.anonymousmessenger.model.Dialog;
import com.anonchat.anonymousmessenger.model.Message;
import com.anonchat.anonymousmessenger.model.User;
import com.anonchat.anonymousmessenger.exceptions.DataNotFoundException;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import com.anonchat.anonymousmessenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MessageUtil {
    private final DialogRepository dialogRepository;
    private final UserRepository userRepository;

    public Message toEntity(MessageDTO messageDTO) {
        Dialog dialog = dialogRepository.findDialogByUniqueDialogId(messageDTO.getUniqueDialogId())
                .orElseThrow(() -> new DataNotFoundException("Dialog not found"));
        User user = userRepository.findByUniqueUserIdIgnoreCase(messageDTO.getUniqueUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        LocalDateTime sentAt = messageDTO.getLocalSentAt();
        return Message.builder()
                .id(messageDTO.getId())
                .user(user)
                .content(messageDTO.getContent())
                .instantSentAt(sentAt.atZone(ZoneId.systemDefault()).toInstant())
                .localSentAt(sentAt)
                .status(messageDTO.getStatus())
                .dialog(dialog)
                .build();
    }
    public Message toEntity(MessageRequest messageRequest) {
        Dialog dialog = dialogRepository.findDialogByUniqueDialogId(messageRequest.getUniqueDialogId())
                .orElseThrow(() -> new DataNotFoundException("Dialog not found"));
        return Message.builder()
                .content(messageRequest.getContent())
                .dialog(dialog)
                .build();
    }

    public MessageDTO fromEntity(Message message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return MessageDTO.builder()
                .id(message.getId())
                .uniqueUserId(message.getUser().getUniqueUserId())
                .senderFirstName(message.getUser().getProfile().getFirstName())
                .senderLastName(message.getUser().getProfile().getLastName())
                .content(message.getContent())
                .sentAt(message.getLocalSentAt().format(formatter))
                .localSentAt(message.getLocalSentAt())
                .status(message.getStatus())

                .uniqueDialogId(message.getDialog().getUniqueDialogId())
                .build();
    }

}
