package com.anonchat.anonymousmessenger.controller.forAll;

import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.entity.UserProfile;
import com.anonchat.anonymousmessenger.entity.UserRole;
import com.anonchat.anonymousmessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("isAuthenticationFailed", true);
        }
        return "pages/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "pages/registration";
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam String firstName,
                                             @RequestParam(required = false) String lastName,
                                             @RequestParam String email,
                                             @RequestParam String password) {
        String encodedPassword = passwordEncoder.encode(password);
        UserProfile userProfile = UserProfile.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        User user = User.builder()
                .uniqueUserId(UUID.randomUUID().toString())
                .profile(userProfile)
                .email(email)
                .password(encodedPassword)
                .role(UserRole.USER)
                .build();
        userService.save(user);
        return "redirect:pages/login";
    }

}
