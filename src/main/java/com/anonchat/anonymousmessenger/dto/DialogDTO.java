package com.anonchat.anonymousmessenger.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class DialogDTO implements Serializable {
    private String firstNameMember;
    private String lastNameMember;
    private String lastMessageContent;
    private String sentAtLastMessage;

    private String uniqueDialogId;
}
