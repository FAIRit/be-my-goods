package com.slyszmarta.bemygoods.security.user;

import com.slyszmarta.bemygoods.security.jwt.JwtRequest;
import com.slyszmarta.bemygoods.security.jwt.JwtResponse;
import com.slyszmarta.bemygoods.user.ApplicationUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;

@RestController
@Api(value = "Authentication")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/auth/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get authentication token.", response = JwtResponse.class)
    @ApiResponse(code = 200, message = "Succesfully logged in.")
    public JwtResponse login(@RequestBody @Valid JwtRequest jwtRequest) {
        return authService.createAuthenticationToken(jwtRequest);
    }

    @PostMapping(value = "/auth/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get authentication token.", response = JwtResponse.class)
    @ApiResponse(code = 200, message = "Succesfully logged in.")
    public JwtResponse loginUsingForm(@Valid JwtRequest jwtRequest) {
        return authService.createAuthenticationToken(jwtRequest);
    }

    @PostMapping(value = "/auth/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Register user", response = String.class)
    @ApiResponse(code = 200, message = "Succesfully registered")
    public String register(@RequestBody @Valid ApplicationUserDto applicationUserDto) throws ValidationException {
        authService.registerUser(applicationUserDto);
        return "User registered!";
    }

    @PostMapping(value = "/auth/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Register user", response = String.class)
    @ApiResponse(code = 200, message = "Succesfully registered")
    public String registerUsingForm(@Valid ApplicationUserDto applicationUserDto) throws ValidationException {
        authService.registerUser(applicationUserDto);
        return "User registered!";
    }
}

