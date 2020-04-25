package com.slyszmarta.bemygoods.security.user;

import com.slyszmarta.bemygoods.user.ApplicationUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class RegistrationDisplayController {

    @GetMapping("/auth/register")
    public String showRegistrationForm(Model model) {
        ApplicationUserDto userDto = new ApplicationUserDto();
        model.addAttribute("user", userDto);
        return "registration.html";
    }
}
