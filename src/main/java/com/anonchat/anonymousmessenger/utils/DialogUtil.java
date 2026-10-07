package com.anonchat.anonymousmessenger.utils;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import com.anonchat.anonymousmessenger.enumerating.MessageStatus;
import com.anonchat.anonymousmessenger.exceptions.UserNotFoundException;
import com.anonchat.anonymousmessenger.model.Dialog;
import com.anonchat.anonymousmessenger.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DialogUtil {
    private final MessageUtil messageUtil;

    public DialogDTO toDialogDTOFromDialog(String uniqueUserId, Dialog dialog) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            List<MessageDTO> messages = dialog.getMessages().stream()
                    .map(messageUtil::toMessageDTOByEntity)
                    .toList();

            if (!messages.isEmpty()) {
                long countUnreadMessages = messages.stream()
                        .filter(msg -> msg.getMessageStatus() != MessageStatus.READ)
                        .filter(msg -> !msg.getUniqueUserId().equals(uniqueUserId))
                        .count();
                MessageDTO lastMessage = messages.get(messages.size() - 1);

                return DialogDTO.builder()
                        .uniqueDialogId(dialog.getUniqueDialogId())
                        .lastMessageContent(lastMessage.getMessageContent())
                        .sentAtLastMessage(lastMessage.getMessageLocalSentAt().format(formatter))
                        .lastMessageStatus(lastMessage.getMessageStatus())
                        .countUnreadMessages(countUnreadMessages)
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

    public String createDialogKey(Set<User> users) {
        return users.stream()
                .map(User::getUniqueUserId)
                .sorted()
                .collect(Collectors.joining(":"));
    }
}
