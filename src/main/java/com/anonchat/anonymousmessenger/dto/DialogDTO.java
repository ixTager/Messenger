package com.anonchat.anonymousmessenger.dto;

import com.anonchat.anonymousmessenger.enumerating.MessageStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dialog entity to show in browser")
public class DialogDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String firstNameMember;
    private String lastNameMember;

    private String lastMessageContent;
    private String sentAtLastMessage;
    private MessageStatus lastMessageStatus;
    private long countUnreadMessages;

    private String uniqueDialogId;
}
