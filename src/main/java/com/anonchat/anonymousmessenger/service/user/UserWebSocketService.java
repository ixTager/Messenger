package com.anonchat.anonymousmessenger.service.user;

import com.anonchat.anonymousmessenger.config.WebSocketConfig;
import com.anonchat.anonymousmessenger.enumerating.UserStatus;
import com.anonchat.anonymousmessenger.enumerating.WebSocketResponseTypes;
import com.anonchat.anonymousmessenger.response.WebSocketResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserWebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendUserStatus(String uniqueUserId, UserStatus userStatus){
        String path = WebSocketConfig.TOPIC_DES_PREFIX + "/user/" + uniqueUserId + "/status";

        WebSocketResponse<UserStatus> response = WebSocketResponse.<UserStatus>builder()
                .type(WebSocketResponseTypes.USER_STATUS_UPDATE)
                .data(userStatus)
                .build();

        simpMessagingTemplate.convertAndSend(path, response);
    }

}
