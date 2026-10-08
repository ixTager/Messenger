package com.anonchat.anonymousmessenger.utils;

import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.enumerating.MessageStatus;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageUtil {
    private final DialogRepository dialogRepository;
    private final UserRepository userRepository;

    public Message toMessageByDTO(MessageDTO messageDTO) {
        Dialog dialog = dialogRepository.findDialogByUniqueDialogId(messageDTO.getUniqueDialogId())
                .orElseThrow(() -> new DataNotFoundException("Dialog not found"));
        User user = userRepository.findByUniqueUserIdIgnoreCase(messageDTO.getUniqueUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        LocalDateTime sentAt = messageDTO.getMessageLocalSentAt();
        return Message.builder()
                .id(messageDTO.getId())
                .user(user)
                .uuidMessage(messageDTO.getMessageUUID())
                .content(messageDTO.getMessageContent())
                .instantSentAt(sentAt.atZone(ZoneId.systemDefault()).toInstant())
                .localSentAt(sentAt)
                .status(messageDTO.getMessageStatus())
                .dialog(dialog)
                .build();
    }
    public Message toMessageByMessageRequest(MessageRequest messageRequest) {
        Dialog dialog = dialogRepository.findDialogByUniqueDialogId(messageRequest.getUniqueDialogId())
                .orElseThrow(() -> new DataNotFoundException("Dialog not found"));
        return Message.builder()
                .content(messageRequest.getContent())
                .dialog(dialog)
                .build();
    }

    public MessageDTO toMessageDTOByEntity(Message message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return MessageDTO.builder()
                .id(message.getId())
                .messageUUID(message.getUuidMessage())
                .uniqueUserId(message.getUser().getUniqueUserId())
                .senderFirstName(message.getUser().getProfile().getFirstName())
                .senderLastName(message.getUser().getProfile().getLastName())
                .messageContent(message.getContent())
                .sentAt(message.getLocalSentAt().format(formatter))
                .messageLocalSentAt(message.getLocalSentAt())
                .messageStatus(message.getStatus())

                .uniqueDialogId(message.getDialog().getUniqueDialogId())
                .build();
    }

    public void setParamsToMessage(User currentUser, Message message) {
        Instant now = Instant.now();

        message.setUuidMessage(UUID.randomUUID().toString());
        message.setUser(currentUser);
        message.setInstantSentAt(now);
        message.setLocalSentAt(LocalDateTime.ofInstant(now, ZoneId.systemDefault()));
        message.setStatus(MessageStatus.SENT);
    }
}
