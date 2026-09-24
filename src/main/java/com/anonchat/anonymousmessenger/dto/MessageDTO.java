package com.anonchat.anonymousmessenger.dto;

import com.anonchat.anonymousmessenger.enumerating.MessageStatus;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String messageUUID;
    private String senderFirstName;
    private String senderLastName;
    private String messageContent;
    private MessageStatus messageStatus;

    private String uniqueDialogId;
    private String uniqueUserId;
    private String sentAt;
    private LocalDateTime messageLocalSentAt;

}
