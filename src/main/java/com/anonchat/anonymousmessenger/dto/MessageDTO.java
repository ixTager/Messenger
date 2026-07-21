package com.anonchat.anonymousmessenger.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Builder
@Getter
@Setter
public class MessageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long dialogId;
    private String content;
    private String uniqueUserId;
    private String senderName;
    private Instant sentAt;
}
