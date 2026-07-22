package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.config.WebSocketConfig;
import com.anonchat.anonymousmessenger.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessage(Long dialogId, MessageDTO messageDTO) {
        simpMessagingTemplate.convertAndSend(WebSocketConfig.TOPIC_DES_PREFIX + "dialog/" + dialogId, messageDTO);
    }
}
