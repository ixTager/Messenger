package com.anonchat.anonymousmessenger.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest {
    private Long dialogId;
    private String content;
}
