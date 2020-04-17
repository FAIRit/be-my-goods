package com.slyszmarta.bemygoods.user;

import com.github.javafaker.Faker;
import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ApplicationUserMapperTest {

    Faker faker = new Faker();

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

    public ApplicationUserDetails userDetails(){
        var user = user();
        ApplicationUserDetails userDetails = new ApplicationUserDetails(user);
        return userDetails;
    }

    public ApplicationUserDto dto(){
        ApplicationUserDto dto = new ApplicationUserDto();
        dto.setEmail(faker.internet().emailAddress());
        dto.setUsername(faker.name().firstName());
        dto.setPassword(faker.animal().name());
        return dto;
    }

    @Test
    public void shouldmapDtoToApplicationUser(){
        var dto = dto();
        ApplicationUser user = ApplicationUserMapper.INSTANCE.mapDtoToApplicationUser(dto);

        assertEquals(dto.getUsername(), user.getUsername());
        assertEquals(dto.getPassword(), user.getPassword());
        assertEquals(dto.getEmail(), user.getEmail());
    }

    @Test
    public void shouldMapApplicationUserToDto(){
        var user = user();
        ApplicationUserDto dto = ApplicationUserMapper.INSTANCE.mapApplicationUserToDto(user);

        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getPassword(), dto.getPassword());
        assertEquals(user.getEmail(), dto.getEmail());
    }

    @Test
    public void shouldMapApplicationUserDetailsToDto(){
        var userDetails = userDetails();
        ApplicationUserDto dto = ApplicationUserMapper.INSTANCE.map(userDetails);

        assertEquals(userDetails.getUsername(), dto.getUsername());
        assertEquals(userDetails.getPassword(), dto.getPassword());
        assertEquals(userDetails.getEmail(), dto.getEmail());
    }

}
