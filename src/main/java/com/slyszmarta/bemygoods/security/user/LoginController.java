package com.slyszmarta.bemygoods.security.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping
@Api
@RequiredArgsConstructor
public class LoginController {

    @GetMapping(value = "/auth/login")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Display login page.", response = String.class)
    @ApiResponse(code = 200, message = "Succesfully displayed.")
    public String showLoginForm(@ApiIgnore Model model) {
        return "login.html";
    }

}
