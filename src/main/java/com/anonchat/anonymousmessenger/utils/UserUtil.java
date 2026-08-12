package com.anonchat.anonymousmessenger.utils;

import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserUtil {
    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .firstName(user.getProfile().getFirstName())
                .lastName(user.getProfile().getLastName())
                .uniqueUserId(user.getUniqueUserId())
                .build();
    }
}
