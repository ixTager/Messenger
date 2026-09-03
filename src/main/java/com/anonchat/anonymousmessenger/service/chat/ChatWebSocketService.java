package com.anonchat.anonymousmessenger.service.chat;

import com.anonchat.anonymousmessenger.config.WebSocketConfig;
import com.anonchat.anonymousmessenger.dto.DialogDTO;
import com.anonchat.anonymousmessenger.enumerating.WebSocketResponseTypes;
import com.anonchat.anonymousmessenger.response.WebSocketResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ChatWebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendChats(String uniqueUserId, List<DialogDTO> dialogDTOList) {
        String path = WebSocketConfig.TOPIC_DES_PREFIX + "/user/" + uniqueUserId + "/chats";
        WebSocketResponse<List<DialogDTO>> response = WebSocketResponse.<List<DialogDTO>>builder()
                .type(WebSocketResponseTypes.DIALOGS_UPDATE)
                .data(dialogDTOList)
                .build();
        simpMessagingTemplate.convertAndSend(path, response);
        log.info("Sending chats to user: {}", uniqueUserId);
    }
}
