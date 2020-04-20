package com.slyszmarta.bemygoods.testHelpers;

import com.github.javafaker.Faker;
import com.slyszmarta.bemygoods.album.Album;

public class testAlbum {

    static Faker faker = new Faker();

    public static Album album(){
        Album album = Album.builder()
                .id(1L)
                .name(faker.name().firstName())
                .artist(faker.artist().name())
                .mbid(faker.aviation().airport())
                .wiki(faker.rickAndMorty().quote())
                .build();
        return album;
    }

}
