package com.slyszmarta.bemygoods.security.user;

import com.slyszmarta.bemygoods.exceptions.UserAlreadyExistsException;
import com.slyszmarta.bemygoods.security.jwt.JwtRequest;
import com.slyszmarta.bemygoods.security.jwt.JwtResponse;
import com.slyszmarta.bemygoods.security.jwt.JwtToken;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import com.slyszmarta.bemygoods.user.ApplicationUserDto;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    private final ApplicationUserService applicationUserService;

    public JwtResponse createAuthenticationToken(JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = applicationUserService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtToken.generateToken(userDetails);
        return new JwtResponse(token);
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

    public void registerUser(ApplicationUserDto accountDto) throws ValidationException {
        if (applicationUserService.existsByEmail(accountDto.getEmail())) {
            throw new UserAlreadyExistsException("Email address already in use.");
        }
        createUserAccount(accountDto);
    }

    private ApplicationUser createUserAccount(ApplicationUserDto accountDto) throws ValidationException {
        ApplicationUser registered;
        registered = applicationUserService.create(accountDto);
        return registered;
    }
}
