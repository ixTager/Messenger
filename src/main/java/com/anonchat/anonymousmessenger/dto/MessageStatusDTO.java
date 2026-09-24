package com.anonchat.anonymousmessenger.dto;

import com.anonchat.anonymousmessenger.enumerating.MessageStatus;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageStatusDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String uuidMessage;
    private String uniqueDialogId;
    private MessageStatus status;
    private String uniqueUserId;
}
