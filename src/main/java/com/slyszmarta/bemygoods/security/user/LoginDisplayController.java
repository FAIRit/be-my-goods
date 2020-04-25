package com.slyszmarta.bemygoods.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class LoginDisplayController {

    @GetMapping(value = "/auth/login")
    public String showLoginForm(Model model) {
        return "login.html";
    }
}
