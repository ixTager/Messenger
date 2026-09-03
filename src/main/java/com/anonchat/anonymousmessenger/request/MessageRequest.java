package com.anonchat.anonymousmessenger.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String uniqueDialogId;
    private String content;
}
