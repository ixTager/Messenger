package com.anonchat.anonymousmessenger.dto;

import lombok.Builder;
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

    private Long id;
    private String dialogId;
    private String senderName;
    private String senderId;
    private String content;
    private Instant sentAt;
//    private MessageStatus status;

}
