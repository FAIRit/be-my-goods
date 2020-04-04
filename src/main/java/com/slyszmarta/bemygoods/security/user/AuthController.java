package com.slyszmarta.bemygoods.security.user;

import com.slyszmarta.bemygoods.security.jwt.JwtRequest;
import com.slyszmarta.bemygoods.security.jwt.JwtResponse;
import com.slyszmarta.bemygoods.user.ApplicationUserDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(value = "Authentication")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/auth/login", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public JwtResponse login(@Valid JwtRequest jwtRequest) throws Exception {
        return authService.createAuthenticationToken(jwtRequest);
    }

    @PostMapping(value = "/auth/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String register(@Valid ApplicationUserDto applicationUserDto) throws Exception {
        authService.registerUser(applicationUserDto);
        return "User registered!";
    }
}

