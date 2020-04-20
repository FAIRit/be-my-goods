package com.slyszmarta.bemygoods.user;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static com.slyszmarta.bemygoods.testHelpers.testUser.user;
import static com.slyszmarta.bemygoods.testHelpers.testUserDetails.userDetails;
import static com.slyszmarta.bemygoods.testHelpers.testUserDto.dto;
import static org.junit.Assert.assertEquals;

public class ApplicationUserMapperTest {

    Faker faker = new Faker();

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
