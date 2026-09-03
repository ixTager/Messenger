package com.anonchat.anonymousmessenger.request;

import lombok.*;


@Getter
@Setter
@Builder
public class UserRequest {
    private String uniqueUserId;
}
