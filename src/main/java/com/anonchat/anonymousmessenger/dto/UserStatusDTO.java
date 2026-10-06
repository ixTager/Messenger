package com.anonchat.anonymousmessenger.dto;

import com.anonchat.anonymousmessenger.enumerating.UserStatus;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String uniqueUserId;
    private UserStatus userStatus;
    private String timeOfLastSeen;
}
