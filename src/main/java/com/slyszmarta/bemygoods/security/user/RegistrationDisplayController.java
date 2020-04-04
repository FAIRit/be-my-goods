package com.slyszmarta.bemygoods.security.user;

import com.slyszmarta.bemygoods.user.ApplicationUserDto;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Api
@Controller
@RequestMapping("/auth")
public class RegistrationDisplayController {

    @GetMapping("/register")
    public String showRegistrationForm(@ApiIgnore Model model) {
        ApplicationUserDto userDto = new ApplicationUserDto();
        model.addAttribute("user", userDto);
        return "registration.html";
    }
}
