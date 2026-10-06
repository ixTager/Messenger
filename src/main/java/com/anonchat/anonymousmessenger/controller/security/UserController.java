package com.anonchat.anonymousmessenger.controller.security;

import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.dto.UserStatusDTO;
import com.anonchat.anonymousmessenger.request.UserRequest;
import com.anonchat.anonymousmessenger.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "API for control users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{uniqueUserId}/get_status")
    public ResponseEntity<UserStatusDTO> getUserStatus(@PathVariable String uniqueUserId) {
        UserStatusDTO userStatusDTO = userService.getUserStatusDTOByUniqueUserId(uniqueUserId);
        if (userStatusDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userStatusDTO, HttpStatus.OK);
    }

    @PostMapping("/set_user_status")
    @Operation(
            summary = "Set User Status",
            description = "Update global user status"
    )
    @ApiResponse(responseCode = "200", description = "Set User Status  is successful")
    public ResponseEntity<Void> setUserActive(@RequestBody UserRequest userRequest) {
        UserStatusDTO userStatusDTO = UserStatusDTO.builder()
                .uniqueUserId(userRequest.getUniqueUserId())
                .userStatus(userRequest.getUserStatus())
                .build();
        userService.notifyUserChangeStatus(userStatusDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/find_user")
    @Operation(
            summary = "Find user",
            description = "Find user by Unique User ID"
    )
    @ApiResponse(responseCode = "200", description = "User was found")
    @ApiResponse(responseCode = "404", description = "User wasn't found")
    public ResponseEntity<UserDTO> findUserByUniqueUserId(@RequestBody UserRequest userRequest) {
        UserDTO foundedUser = userService.getUserDTOByUniqueUserId(userRequest.getUniqueUserId());
        if (foundedUser != null) return new ResponseEntity<>(foundedUser, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
