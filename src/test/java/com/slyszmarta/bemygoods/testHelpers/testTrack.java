package com.slyszmarta.bemygoods.testHelpers;

import com.github.javafaker.Faker;
import com.slyszmarta.bemygoods.album.track.Track;

public class testTrack {

    static Faker faker = new Faker();

    public static Track track(){
        Track track = Track.builder()
                .id(faker.number().randomNumber())
                .name(faker.name().firstName())
                .build();
        return track;
    }

}
