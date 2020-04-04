package com.slyszmarta.bemygoods.security.user;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/auth")
@Api
@RequiredArgsConstructor
public class LoginController {

    @GetMapping(value = "/login")
    public String showLoginForm(@ApiIgnore Model model) {
        return "login.html";
    }

}
