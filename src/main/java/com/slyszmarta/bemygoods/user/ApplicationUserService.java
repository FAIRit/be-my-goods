package com.slyszmarta.bemygoods.user;

import com.slyszmarta.bemygoods.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.xml.bind.ValidationException;
import java.util.Optional;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor
@Service
public class ApplicationUserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationUserRepository applicationUserRepository;

    @PostMapping("/user")
    public void create(ApplicationUserDto applicationUserDto) throws ValidationException {
        ApplicationUser entity = new ApplicationUser();
        entity.setUsername(applicationUserDto.getUsername());
        if (applicationUserRepository.existsByUsername(applicationUserDto.getUsername())){
            throw new ValidationException("Username already existed");
        }
        entity.setEmail(applicationUserDto.getEmail());
        entity.setCountry(applicationUserDto.getCountry());

        String encodedPassword = bCryptPasswordEncoder.encode(applicationUserDto.getPassword());
        entity.setPassword(encodedPassword);
        applicationUserRepository.save(entity);
    }

    public void delete(Long id) {
        applicationUserRepository.deleteById(id);
    }

    public boolean existsByEmail(final String email){
        return applicationUserRepository.existsByEmail(email);
    }

    public ApplicationUser getExistingUser(final Long id) {
        return applicationUserRepository.findUserById(id).orElseThrow(() ->
                new UserNotFoundException(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> applicationUser = applicationUserRepository.findUserByUsername(username);
        if (!applicationUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.get().getUsername(), applicationUser.get().getPassword(), emptyList());
    }
}
