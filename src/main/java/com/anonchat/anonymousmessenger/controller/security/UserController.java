package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.dto.UserRequest;
import com.anonchat.anonymousmessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/find_user")
    public ResponseEntity<UserDTO> findUserByUniqueUserId(@RequestBody UserRequest userRequest) {
        UserDTO foundedUser = userService.getUserDTOByUniqueUserId(userRequest.getUniqueUserId());
        return new ResponseEntity<>(foundedUser, HttpStatus.OK);
    }
}
