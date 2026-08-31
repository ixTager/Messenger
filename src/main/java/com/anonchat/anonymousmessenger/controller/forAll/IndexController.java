package com.anonchat.anonymousmessenger.controller.forAll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping
    public String index() {
        return "pages/index";
    }
}
