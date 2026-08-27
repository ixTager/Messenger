package com.anonchat.anonymousmessenger.dto;

import com.anonchat.anonymousmessenger.entity.Message;
import com.anonchat.anonymousmessenger.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class DialogDTO implements Serializable {
    private String uniqueDialogId;
    private String dialogKey;
    private String lastMessage;
    private String sentAtLastMessage;
    private String firstNameMember;
    private String lastNameMember;
}
