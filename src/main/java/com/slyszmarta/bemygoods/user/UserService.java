package com.slyszmarta.bemygoods.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void create(UserDto userDto) {
        User entity = new User();
        entity.setUsername(userDto.getUsername());
        entity.setEmail(userDto.getEmail());
        entity.setCountry(userDto.getCountry());

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        entity.setPassword(encodedPassword);
        userRepository.save(entity);
    }
}
