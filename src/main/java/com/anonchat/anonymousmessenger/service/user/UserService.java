package com.anonchat.anonymousmessenger.service.user;

import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.dto.UserStatusDTO;
import com.anonchat.anonymousmessenger.enumerating.UserStatus;
import com.anonchat.anonymousmessenger.model.Dialog;
import com.anonchat.anonymousmessenger.model.User;
import com.anonchat.anonymousmessenger.exceptions.UserNotFoundException;
import com.anonchat.anonymousmessenger.repository.DialogRepository;
import com.anonchat.anonymousmessenger.repository.UserRepository;
import com.anonchat.anonymousmessenger.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final UserWebSocketService userWebSocketService;
    private final DialogRepository dialogRepository;

    @Transactional(readOnly = true)
    public boolean isPresentUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).isPresent();
    }

    // Another user
    @Transactional(readOnly = true)
    public User getUserByUniqueUserId(String uniqueUserId){
        User user =  userRepository.findByUniqueUserIdIgnoreCase(uniqueUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with uniqueUserId: " + uniqueUserId));

        log.info("User found with uniqueUserId {}", user.getUniqueUserId());
        return user;
    }

    @Transactional(readOnly = true)
    public UserDTO getUserDTOByUniqueUserId(String uniqueUserId){
        User user =  userRepository.findByUniqueUserIdIgnoreCase(uniqueUserId)
                .orElse(null);
        if (user == null) return null;

        UserDTO userDTO = userUtil.toUserDTO(user);
        log.info("User found with uniqueUserId {}", user.getUniqueUserId());
        return userDTO;
    }

    @Transactional(readOnly = true)
    public UserDTO getSecondMemberInDialog(String currentUniqueUserId, String uniqueDialogId) {
        Dialog dialog = dialogRepository.findDialogByUniqueDialogId(uniqueDialogId)
                .orElse(null);
        if (dialog == null) return null;
        User foundUser = dialog.getUsers().stream()
                .filter(user -> !user.getUniqueUserId().equals(currentUniqueUserId))
                .findFirst()
                .orElse(null);
        if (foundUser == null) return null;

        return userUtil.toUserDTO(foundUser);
    }

    @Transactional(readOnly = true)
    public UserStatusDTO getUserStatusDTOByUniqueUserId(String uniqueUserId) {
        User user =  userRepository.findByUniqueUserIdIgnoreCase(uniqueUserId)
                .orElse(null);
        if (user == null) return null;

        return UserStatusDTO.builder()
                .uniqueUserId(uniqueUserId)
                .userStatus(user.getUserStatus())
                .timeOfLastSeen(user.getTimeOfLastSeen())
                .build();
    }

    // Current User
    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public UserDTO getCurrentUserDTO() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        UserDTO userDTO = userUtil.toUserDTO(user);
        log.info("User found with email {}", user.getEmail());
        return userDTO;
    }


    @Transactional
    public void save(User user){
        userRepository.save(user);
        log.info("User saved with id {}", user.getUniqueUserId());
    }

    @Transactional
    public void notifyUserChangeStatus(UserStatusDTO userStatusDTO) {
        User user = getUserByUniqueUserId(userStatusDTO.getUniqueUserId());
        user.setUserStatus(userStatusDTO.getUserStatus());
        if (userStatusDTO.getUserStatus() == UserStatus.OFFLINE) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            user.setTimeOfLastSeen(now.format(formatter));
        }
        userRepository.save(user);

        UserStatusDTO updatedStatus = UserStatusDTO.builder()
                .uniqueUserId(user.getUniqueUserId())
                .userStatus(user.getUserStatus())
                .timeOfLastSeen(user.getTimeOfLastSeen())
                .build();

        userWebSocketService.sendUserStatus(updatedStatus);
        log.info("UserStatus updated with uniqueUserId {}", user.getUniqueUserId());
    }
}
