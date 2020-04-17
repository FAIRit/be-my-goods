package com.slyszmarta.bemygoods.user;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.ValidationException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserServiceTest {

    @Mock
    private ApplicationUserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private ApplicationUserService userService;

    Faker faker = new Faker();

    public ApplicationUserDto dto(){
        ApplicationUserDto dto = new ApplicationUserDto();
        dto.setEmail(faker.internet().emailAddress());
        dto.setUsername(faker.name().firstName());
        dto.setPassword(faker.animal().name());
        return dto;
    }

    public ApplicationUser user(){
        ApplicationUser user = ApplicationUser.builder()
                .id(faker.number().randomNumber())
                .username(faker.name().firstName())
                .password(faker.animal().name())
                .email(faker.internet().emailAddress())
                .country(faker.country().name())
                .albumList(Collections.emptyList())
                .albumTags(Collections.emptySet())
                .build();
        return user;
    }

    @Test
    public void whenSaveUserShouldReturnUser() throws ValidationException {
        ApplicationUserDto dto = dto();
        when(userRepository.save(any(ApplicationUser.class))).then(returnsFirstArg());
        ApplicationUser savedUser = userService.create(dto);
        assertThat(dto.getEmail()).isSameAs(savedUser.getEmail());
        assertThat(passwordEncoder.encode(dto.getPassword())).isSameAs(savedUser.getPassword());
        assertThat(dto.getUsername()).isSameAs(savedUser.getUsername());
    }

    @Test
    public void whenDeleteUserShouldNotReturnUser(){
        ApplicationUser user = user();
        userService.delete(user.getId());
        assertFalse(userService.existsByEmail(user.getEmail()));
    }

}
