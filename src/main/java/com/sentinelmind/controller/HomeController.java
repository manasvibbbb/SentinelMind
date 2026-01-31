package com.sentinelmind.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Redirect root URL to dashboard after login
        return "redirect:/dashboard";
    }
}
