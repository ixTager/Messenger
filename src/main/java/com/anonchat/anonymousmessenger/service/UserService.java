package com.anonchat.anonymousmessenger.service;

import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + "not found"));
    }
}
