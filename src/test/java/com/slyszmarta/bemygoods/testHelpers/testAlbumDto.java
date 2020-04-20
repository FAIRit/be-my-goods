package com.slyszmarta.bemygoods.testHelpers;

import com.github.javafaker.Faker;
import com.slyszmarta.bemygoods.album.AlbumDto;

public class testAlbumDto {

    static Faker faker = new Faker();

    public static AlbumDto dto(){
        AlbumDto dto = AlbumDto.builder()
                .id(faker.number().randomNumber())
                .mbid(faker.aviation().airport())
                .name(faker.name().firstName())
                .artist(faker.artist().name())
                .wiki(faker.rickAndMorty().quote())
                .build();
        return dto;
    }

    public static AlbumDto dtowithIdSpecified(){
        AlbumDto dto = AlbumDto.builder()
                .id(1L)
                .mbid(faker.aviation().airport())
                .name(faker.name().firstName())
                .artist(faker.artist().name())
                .wiki(faker.rickAndMorty().quote())
                .build();
        return dto;
    }

}
