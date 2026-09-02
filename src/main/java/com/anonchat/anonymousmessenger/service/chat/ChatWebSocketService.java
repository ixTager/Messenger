package com.anonchat.anonymousmessenger.service.chat;


import com.anonchat.anonymousmessenger.config.WebSocketConfig;
import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.dto.WebSocketResponse;
import com.anonchat.anonymousmessenger.enumeratung.WebSocketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatWebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendChats(String uniqueUserId, List<DialogDTO> dialogDTOList) {
        WebSocketResponse<List<DialogDTO>> res = WebSocketResponse.<List<DialogDTO>>builder()
                .type(WebSocketStatus.DIALOGS_UPDATED)
                .data(dialogDTOList)
                .build();
        simpMessagingTemplate.convertAndSend(WebSocketConfig.TOPIC_DES_PREFIX + "/user/" + uniqueUserId, res);
    }
}
