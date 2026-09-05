package com.anonchat.anonymousmessenger.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String senderFirstName;
    private String senderLastName;
    private String content;

    private String uniqueDialogId;
    private String uniqueUserId;
    private Instant sentAt;
}
