package com.anonchat.anonymousmessenger.dto;

import com.anonchat.anonymousmessenger.enumerating.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User entity to show in browser")
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private UserStatus userStatus;
    private String uniqueUserId;
}
