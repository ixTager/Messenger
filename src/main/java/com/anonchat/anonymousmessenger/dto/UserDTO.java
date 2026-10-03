package com.anonchat.anonymousmessenger.dto;

import com.anonchat.anonymousmessenger.enumerating.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private UserStatus userStatus;
    private String uniqueUserId;
}
