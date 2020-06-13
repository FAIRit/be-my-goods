package com.slyszmarta.bemygoods.user;

import com.slyszmarta.bemygoods.exceptions.UserNotFoundException;
import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationUserRepository applicationUserRepository;

    public ApplicationUser create(ApplicationUserDto applicationUserDto) throws ValidationException {
        ApplicationUser entity = new ApplicationUser();
        entity.setUsername(applicationUserDto.getUsername());
        if (applicationUserRepository.existsByUsername(applicationUserDto.getUsername())) {
            throw new ValidationException(String.format("There is an account with this username: %s already.", applicationUserDto.getUsername()));
        }
        entity.setEmail(applicationUserDto.getEmail());
        if (applicationUserRepository.existsByEmail(applicationUserDto.getEmail())) {
            throw new ValidationException(String.format("There is an account with that email adress: %s already", applicationUserDto.getEmail()));
        }
        String encodedPassword = bCryptPasswordEncoder.encode(applicationUserDto.getPassword());
        entity.setPassword(encodedPassword);
        applicationUserRepository.save(entity);
        return entity;
    }

    public void delete(Long id) {
        applicationUserRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return applicationUserRepository.existsByEmail(email);
    }

    public ApplicationUser getExistingUser(Long id) {
        return applicationUserRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        var user = applicationUserRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new ApplicationUserDetails(user);
    }

}


