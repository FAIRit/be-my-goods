package com.slyszmarta.bemygoods.user;

import com.github.javafaker.Faker;
import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.ValidationException;
import java.util.Collections;

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

    public ApplicationUserDto dto(){
        var faker = new Faker();
        ApplicationUserDto dto = new ApplicationUserDto();
        dto.setEmail(faker.internet().emailAddress());
        dto.setUsername(faker.name().firstName());
        dto.setPassword(faker.animal().name());
        return dto;
    }

    ApplicationUserDto dto = dto();

    public ApplicationUser user(){
        var faker = new Faker();
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

    public ApplicationUserDetails userDetails(ApplicationUser user){
        ApplicationUserDetails userDetails = new ApplicationUserDetails(user);
        return userDetails;
    }

    @Test
    public void whenSaveUserShouldReturnUser() throws ValidationException {
        when(userRepository.save(any(ApplicationUser.class))).then(returnsFirstArg());
        ApplicationUser savedUser = userService.create(dto);
        assertThat(dto.getEmail()).isSameAs(savedUser.getEmail());
        assertThat(passwordEncoder.encode(dto.getPassword())).isSameAs(savedUser.getPassword());
        assertThat(dto.getUsername()).isSameAs(savedUser.getUsername());
        assertThat(savedUser).isNotNull();
        verify(userRepository).save(any(ApplicationUser.class));
    }

    @Test
    public void shouldBeDelete(){
        ApplicationUser user = user();
        userService.delete(user.getId());
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    public void ifExistsByEmailReturnTrue() throws ValidationException {
        ApplicationUser savedUser = userService.create(dto);
        assertThat(dto.getEmail()).isSameAs(savedUser.getEmail());
        assertThat(passwordEncoder.encode(dto.getPassword())).isSameAs(savedUser.getPassword());
        assertThat(dto.getUsername()).isSameAs(savedUser.getUsername());
        verify(userRepository).existsByEmail(savedUser.getEmail());
    }

    @Test
    public void ifUserExistsReturnUser() throws ValidationException {
        ApplicationUser savedUser = userService.create(dto);
        assertThat(dto.getEmail()).isSameAs(savedUser.getEmail());
        assertThat(passwordEncoder.encode(dto.getPassword())).isSameAs(savedUser.getPassword());
        assertThat(dto.getUsername()).isSameAs(savedUser.getUsername());
        verify(userRepository).existsByUsername(savedUser.getUsername());
    }
}
