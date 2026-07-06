package com.anonchat.anonymousmessenger.dto;

import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.enumeration.MessageStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Builder
public class MessageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String dialogId;
    private String sentByUserId;
    private String content;
    private Instant sentAt;
//    private MessageStatus status;

    public static MessageDTO fromEntity(Message message) {
        if (message == null) { return null; }

        return MessageDTO.builder()
                .dialogId(message.getDialog() != null ? message.getDialog().getId() : null)
                .sentByUserId(message.getSender() != null ? message.getSender().getUserId() : null)
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .build();
    }

}
