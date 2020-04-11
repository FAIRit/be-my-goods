package com.slyszmarta.bemygoods.security.user;

import com.slyszmarta.bemygoods.user.ApplicationUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import springfox.documentation.annotations.ApiIgnore;

@Api
@Controller
@RequestMapping
public class RegistrationDisplayController {

    @GetMapping("/auth/register")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Display register page.", response = String.class)
    @ApiResponse(code = 200, message = "Succesfully displayed.")
    public String showRegistrationForm(@ApiIgnore Model model) {
        ApplicationUserDto userDto = new ApplicationUserDto();
        model.addAttribute("user", userDto);
        return "registration.html";
    }
}
