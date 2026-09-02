package com.anonchat.anonymousmessenger.dto;

import com.anonchat.anonymousmessenger.enumeratung.WebSocketStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketResponse<T>{
    private WebSocketStatus type;
    private T data;
}
