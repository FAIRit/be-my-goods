package com.slyszmarta.bemygoods.user;

import com.slyszmarta.bemygoods.exceptions.UserNotFoundException;
import com.slyszmarta.bemygoods.security.registration.PasswordResetToken;
import com.slyszmarta.bemygoods.security.registration.PasswordResetTokenRepository;
import com.slyszmarta.bemygoods.security.registration.VerificationToken;
import com.slyszmarta.bemygoods.security.registration.VerificationTokenRepository;
import com.slyszmarta.bemygoods.security.registration.roles.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.xml.bind.ValidationException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationUserRepository applicationUserRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordResetTokenRepository passwordTokenRepository;
    private final RoleRepository roleRepository;

    @PostMapping("/user")
    public ApplicationUser create(ApplicationUserDto applicationUserDto) throws ValidationException {
        ApplicationUser entity = new ApplicationUser();
        entity.setUsername(applicationUserDto.getUsername());
        if (applicationUserRepository.existsByUsername(applicationUserDto.getUsername())){
            throw new ValidationException(String.format("There is an account with this username: %s already.", applicationUserDto.getUsername()));
        }
        entity.setEmail(applicationUserDto.getEmail());
        if (applicationUserRepository.existsByEmail(applicationUserDto.getEmail())){
            throw new ValidationException(String.format("There is an account with that email adress: %s already", applicationUserDto.getEmail()));
        }
        entity.setCountry(applicationUserDto.getCountry());
        String encodedPassword = bCryptPasswordEncoder.encode(applicationUserDto.getPassword());
        entity.setPassword(encodedPassword);
        entity.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        applicationUserRepository.save(entity);
        return entity;
    }

    public void saveRegisteredUser(final ApplicationUser user) {
        applicationUserRepository.save(user);
    }

    public void delete(Long id) {
        applicationUserRepository.deleteById(id);
    }

    public boolean existsByEmail(final String email){
        return applicationUserRepository.existsByEmail(email);
    }

    public ApplicationUser getExistingUser(final Long id) {
        return applicationUserRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public ApplicationUser getExistingUserByEmail(String email) {
        return applicationUserRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> user = applicationUserRepository.findUserByEmail(username);
        if (user== null) {
            throw new UsernameNotFoundException(username);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new User(user.get().getUsername(), user.get().getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, Collections.emptyList());
    }


    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public void createVerificationToken(ApplicationUser user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "TOKEN_INVALID";
        }
        final ApplicationUser user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return "TOKEN_EXPIRED";
        }
        user.setEnabled(true);
        applicationUserRepository.save(user);
        return "TOKEN_VALID";
    }

    public String validatePasswordResetToken(long id, String token) {
        PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id)) {
            return "invalidToken";
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            return "expired";
        }

        ApplicationUser user = passToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }

    public void createPasswordResetTokenForUser(final ApplicationUser user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(user, token);
        passwordTokenRepository.save(myToken);
    }

    public ApplicationUser getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    public void changeUserPassword(final ApplicationUser user, final String password) {
        user.setPassword(bCryptPasswordEncoder.encode(password));
        applicationUserRepository.save(user);
    }

    public boolean checkIfValidOldPassword(final ApplicationUser user, final String oldPassword) {
        return bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
    }
}
