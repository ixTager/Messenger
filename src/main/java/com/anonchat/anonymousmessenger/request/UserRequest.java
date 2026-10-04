package com.anonchat.anonymousmessenger.request;

import com.anonchat.anonymousmessenger.enumerating.UserStatus;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String uniqueUserId;
    private UserStatus userStatus;
}
