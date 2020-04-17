package com.slyszmarta.bemygoods.album;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.slyszmarta.bemygoods.lastFmApi.response.AlbumResponse;
import com.slyszmarta.bemygoods.lastFmApi.response.Tracks;
import com.slyszmarta.bemygoods.lastFmApi.response.Wiki;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ApplicationUserService userService;

    @InjectMocks
    private AlbumService albumService;

    Faker faker = new Faker();

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
    ApplicationUser user = user();

    public Album album(){
        Album album = Album.builder()
                .id(faker.number().randomNumber())
                .name(faker.name().firstName())
                .artist(faker.artist().name())
                .albumTags(Collections.emptySet())
                .mbid(faker.aviation().airport())
                .tracksList(Collections.emptyList())
                .wiki(faker.rickAndMorty().quote())
                .user(user)
                .build();
        return album;
    }

    public AlbumResponse albumResponse(){
        AlbumResponse response = AlbumResponse.builder()
                .mbid(faker.aviation().airport())
                .name(faker.name().firstName())
                .artist(faker.artist().name())
                .tracks(new Tracks(Collections.emptyList()))
                .wiki(new Wiki(faker.rickAndMorty().quote()))
                .build();
        return response;
    }

    @Test
    public void shouldReturnAllAlbums(){
        List<AlbumDto> albumDtoList = new ArrayList<>();
        albumDtoList.add(AlbumMapper.INSTANCE.mapAlbumToDto(album()));
        albumDtoList.add(AlbumMapper.INSTANCE.mapAlbumToDto(album()));
        albumDtoList.add(AlbumMapper.INSTANCE.mapAlbumToDto(album()));

        Albums albums = new Albums(albumDtoList);

        given(albumRepository.findAllByUserId(user.getId())).willReturn(albumDtoList.stream().map(AlbumMapper.INSTANCE::mapDtoToAlbum).collect(Collectors.toList()));

        Albums expected = albumService.getAllUserAlbums(user.getId());

        assertEquals(expected, albums);
    }

    @Test
    public void whenSaveAlbumReturnAlbum() throws NoSuchFieldException {
        when(albumRepository.save(any(Album.class))).then(returnsFirstArg());
        AlbumResponse response = albumResponse();
        AlbumDto dto = albumService.saveAlbum(response, user.getId());
        assertThat(dto.getMbid()).isSameAs(response.getMbid());
        assertThat(dto.getArtist()).isSameAs(response.getArtist());
        assertThat(dto.getName()).isSameAs(response.getName());
        assertThat(dto.getWiki()).isSameAs(response.getWiki());
        assertThat(dto).isNotNull();
        verify(albumRepository).save(any(Album.class));
    }
}
