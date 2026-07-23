package com.anonchat.anonymousmessenger.controller.forAll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String getLoginPage() {
        return "pages/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "pages/registration";
    }
}
