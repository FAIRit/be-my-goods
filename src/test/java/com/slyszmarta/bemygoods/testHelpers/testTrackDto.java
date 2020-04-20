package com.slyszmarta.bemygoods.testHelpers;

import com.github.javafaker.Faker;
import com.slyszmarta.bemygoods.album.track.TrackDto;

public class testTrackDto {

    static Faker faker = new Faker();

    public static TrackDto dto(){
        TrackDto dto = TrackDto.builder()
                .name(faker.name().firstName())
                .build();
        return dto;
    }
}
