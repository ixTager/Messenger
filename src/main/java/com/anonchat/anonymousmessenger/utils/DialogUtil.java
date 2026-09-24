package com.anonchat.anonymousmessenger.utils;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.exceptions.UserNotFoundException;
import com.anonchat.anonymousmessenger.model.Dialog;
import com.anonchat.anonymousmessenger.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogUtil {
    private final MessageService messageService;

    public DialogDTO fromEntity(Dialog dialog) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            List<MessageDTO> messages = messageService.getMessagesByDialogId(dialog.getUniqueDialogId());
            if (!messages.isEmpty()) {
                MessageDTO lastMessage = messages.get(messages.size() - 1);
                return DialogDTO.builder()
                        .uniqueDialogId(dialog.getUniqueDialogId())
                        .lastMessageContent(lastMessage.getMessageContent())
                        .sentAtLastMessage(lastMessage.getMessageLocalSentAt().format(formatter))
                        .lastMessageStatus(lastMessage.getMessageStatus().name())
                        .firstNameMember(lastMessage.getSenderFirstName())
                        .lastNameMember(lastMessage.getSenderLastName())
                        .build();
            }
            return null;
        }
        catch (UserNotFoundException e) {
            System.err.println("User not found");
            return null;
        }
    }
}
