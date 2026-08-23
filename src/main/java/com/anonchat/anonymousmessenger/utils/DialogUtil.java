package com.anonchat.anonymousmessenger.utils;

import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.entity.Dialog;
import org.springframework.stereotype.Service;

@Service
public class DialogUtil {
    public DialogDTO fromEntity(Dialog dialog) {
        return DialogDTO.builder()
                .firstNameMember()
                .build();
    }
}
