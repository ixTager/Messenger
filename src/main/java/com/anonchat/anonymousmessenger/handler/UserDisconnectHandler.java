package com.anonchat.anonymousmessenger.handler;

import com.anonchat.anonymousmessenger.enumerating.UserStatus;
import com.anonchat.anonymousmessenger.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserDisconnectHandler {
    private final UserService userService;

    @EventListener
    public void handleUserDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String uniqueUserId = (String)  headerAccessor.getSessionAttributes().get("uniqueUserId");

        if (uniqueUserId != null) {
            userService.notifyUserChangeStatus(uniqueUserId, UserStatus.OFFLINE);
            log.info("User Disconnected: {} ",uniqueUserId);
        }
    }
}
