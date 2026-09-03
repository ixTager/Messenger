package com.anonchat.anonymousmessenger.response;

import com.anonchat.anonymousmessenger.enumerating.WebSocketResponseTypes;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketResponse <T>{
    private WebSocketResponseTypes type;
    private T data;
}
