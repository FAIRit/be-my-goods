package com.slyszmarta.bemygoods.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.ValidationException;

import static com.slyszmarta.bemygoods.testHelpers.testUser.user;
import static com.slyszmarta.bemygoods.testHelpers.testUserDto.dto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserServiceTest {

    @Mock
    private ApplicationUserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private ApplicationUserService userService;

    @Test
    public void shouldSaveUser() throws ValidationException {
        var dto = dto();
        when(userRepository.save(any(ApplicationUser.class))).then(returnsFirstArg());
        ApplicationUser savedUser = userService.create(dto);
        assertThat(dto.getEmail()).isSameAs(savedUser.getEmail());
        assertThat(passwordEncoder.encode(dto.getPassword())).isSameAs(savedUser.getPassword());
        assertThat(dto.getUsername()).isSameAs(savedUser.getUsername());
        assertThat(savedUser).isNotNull();
        verify(userRepository).save(any(ApplicationUser.class));
    }

    @Test
    public void shouldDeleteUser(){
        var user = user();
        userService.delete(user.getId());
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    public void existsByEmailShouldReturnTrue() throws ValidationException {
        var dto = dto();
        ApplicationUser savedUser = userService.create(dto);
        assertThat(dto.getEmail()).isSameAs(savedUser.getEmail());
        assertThat(passwordEncoder.encode(dto.getPassword())).isSameAs(savedUser.getPassword());
        assertThat(dto.getUsername()).isSameAs(savedUser.getUsername());
        verify(userRepository).existsByEmail(savedUser.getEmail());
    }

    @Test
    public void existsByUsernameShouldReturnTrue() throws ValidationException {
        var dto = dto();
        ApplicationUser savedUser = userService.create(dto);
        assertThat(dto.getEmail()).isSameAs(savedUser.getEmail());
        assertThat(passwordEncoder.encode(dto.getPassword())).isSameAs(savedUser.getPassword());
        assertThat(dto.getUsername()).isSameAs(savedUser.getUsername());
        verify(userRepository).existsByUsername(savedUser.getUsername());
    }
}
