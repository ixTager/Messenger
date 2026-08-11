package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
        log.info("User saved with id {}", user.getId());
    }


    public User getUserByUniqueUserId(String uniqueUserId){
        User user =  userRepository.findByUniqueUserIdIgnoreCase(uniqueUserId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with uniqueUserId: " + uniqueUserId));

        log.info("User found with uniqueUserId {}", user.getUniqueUserId());
        return user;
    }

    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

}
