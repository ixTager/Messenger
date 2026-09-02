package com.anonchat.anonymousmessenger.utils;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.entity.Dialog;
import com.anonchat.anonymousmessenger.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogUtil {
    private final MessageService messageService;

    public DialogDTO fromEntity(Dialog dialog) {
        List<MessageDTO> messages = messageService.getMessagesByDialogId(dialog.getUniqueDialogId());
        if (!messages.isEmpty()) {
            MessageDTO lastMessage = messages.get(messages.size() - 1);
            return DialogDTO.builder()
                    .uniqueDialogId(dialog.getUniqueDialogId())
                    .lastMessageContent(lastMessage.getContent())
                    .sentAtLastMessage(String.valueOf(lastMessage.getSentAt()))
                    .firstNameMember(lastMessage.getSenderName())
                    .lastNameMember(lastMessage.getSenderName())
                    .build();
        }
        return null;
    }
}
