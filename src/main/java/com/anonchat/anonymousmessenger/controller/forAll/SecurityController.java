package com.anonchat.anonymousmessenger.controller.forAll;

import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.enumeration.UserRole;
import com.anonchat.anonymousmessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class SecurityController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLoginPage() { return "pages/login";}

    @GetMapping("/registration")
    public String getRegistrationPage() { return "pages/registration";}

    @PostMapping("/registration")
    public String post(@RequestParam String firstName,
                     @RequestParam(required = false) String lastName,
                     @RequestParam String email,
                     @RequestParam String password) {

        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(encodedPassword)
                .userId(UUID.randomUUID().toString())
                .role(UserRole.USER)
                .build();

        userService.save(user);
        return "redirect:/login";

    }
}
