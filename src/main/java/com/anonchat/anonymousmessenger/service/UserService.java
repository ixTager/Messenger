package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.exceptions.UserNotFoundException;
import com.anonchat.anonymousmessenger.repository.UserRepository;
import com.anonchat.anonymousmessenger.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    public boolean isPresentUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).isPresent();
    }

    public void save(User user){
        userRepository.save(user);
        log.info("User saved with id {}", user.getUniqueUserId());
    }

    // Another user
    public User getUserByUniqueUserId(String uniqueUserId){
        User user =  userRepository.findByUniqueUserIdIgnoreCase(uniqueUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with uniqueUserId: " + uniqueUserId));

        log.info("User found with uniqueUserId {}", user.getUniqueUserId());
        return user;
    }

    public UserDTO getUserDTOByUniqueUserId(String uniqueUserId){
        User user =  userRepository.findByUniqueUserIdIgnoreCase(uniqueUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with uniqueUserId: " + uniqueUserId));

        UserDTO userDTO = userUtil.toUserDTO(user);
        log.info("User found with uniqueUserId {}", user.getUniqueUserId());
        return userDTO;
    }



    // Current User
    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElse(null);
    }

    public UserDTO getCurrentUserDTO() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository
                .findByEmailIgnoreCase(email)
                .orElse(null);
        if (user == null) return null;
        UserDTO userDTO = userUtil.toUserDTO(user);
        log.info("User found with email {}", user.getEmail());
        return userDTO;
    }
}
