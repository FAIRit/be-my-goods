package com.slyszmarta.bemygoods.testHelpers;

import com.github.javafaker.Faker;
import com.slyszmarta.bemygoods.user.ApplicationUser;

public class testUser {

    static Faker faker = new Faker();

    public static ApplicationUser user(){
        ApplicationUser user = ApplicationUser.builder()
                .username(faker.name().firstName())
                .password(faker.animal().name())
                .email(faker.internet().emailAddress())
                .country(faker.country().name())
                .build();
        return user;
    }
}
