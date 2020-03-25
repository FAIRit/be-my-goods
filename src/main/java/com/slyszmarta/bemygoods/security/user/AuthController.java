package com.slyszmarta.bemygoods.security.user;

import com.slyszmarta.bemygoods.security.jwt.JwtRequest;
import com.slyszmarta.bemygoods.security.jwt.JwtResponse;
import com.slyszmarta.bemygoods.security.jwt.JwtToken;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "Authentication")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    private final ApplicationUserService applicationUserService;


    @PostMapping(value = "/authenticate")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Authenticate", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully authenticated."),
            @ApiResponse(code = 201, message = ""),
            @ApiResponse(code = 401, message = ""),
            @ApiResponse(code = 403, message = ""),
            @ApiResponse(code = 404, message = "")
    })
    public ResponseEntity createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = applicationUserService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtToken.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

