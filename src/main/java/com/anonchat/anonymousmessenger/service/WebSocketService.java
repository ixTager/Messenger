package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.config.WebSocketConfig;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessage(String uniqueDialogId, MessageDTO messageDTO) {
        simpMessagingTemplate.convertAndSend(WebSocketConfig.TOPIC_DES_PREFIX + "/chat/" + uniqueDialogId, messageDTO);
        log.info("Message was send to chat with unique id: {}", uniqueDialogId);
    }
}
