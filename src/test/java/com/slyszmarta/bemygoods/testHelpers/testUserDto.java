package com.slyszmarta.bemygoods.testHelpers;

import com.github.javafaker.Faker;
import com.slyszmarta.bemygoods.user.ApplicationUserDto;

public class testUserDto {

    static Faker faker = new Faker();

    public static ApplicationUserDto dto(){
        ApplicationUserDto dto = new ApplicationUserDto();
        dto.setEmail(faker.internet().emailAddress());
        dto.setUsername(faker.name().firstName());
        dto.setPassword(faker.animal().name());
        return dto;
    }

}
