package com.anonchat.anonymousmessenger.handler;

import com.anonchat.anonymousmessenger.dto.UserStatusDTO;
import com.anonchat.anonymousmessenger.enumerating.UserStatus;
import com.anonchat.anonymousmessenger.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Component
@Log4j2
@RequiredArgsConstructor
public class UserConnectHandler {
    private final UserService userService;

    @EventListener
    public void handleConnectEvent(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String uniqueUserId = (String) accessor.getSessionAttributes().get("uniqueUserId");

        if  (uniqueUserId == null) return;

        UserStatusDTO userStatusDTO = UserStatusDTO.builder()
                .uniqueUserId(uniqueUserId)
                .userStatus(UserStatus.ONLINE)
                .build();

        userService.notifyUserChangeStatus(userStatusDTO);

        log.info("User Connected: {} ",uniqueUserId);

    }
}
