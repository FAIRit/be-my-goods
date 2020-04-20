package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.user.ApplicationUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.slyszmarta.bemygoods.testHelpers.testAlbum.album;
import static com.slyszmarta.bemygoods.testHelpers.testUser.user;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ApplicationUserService userService;

    @InjectMocks
    private AlbumService albumService;

    @Test
    public void shouldReturnAllAlbums(){
        List<AlbumDto> albumDtoList = new ArrayList<>();
        albumDtoList.add(AlbumMapper.INSTANCE.mapAlbumToDto(album()));
        albumDtoList.add(AlbumMapper.INSTANCE.mapAlbumToDto(album()));
        albumDtoList.add(AlbumMapper.INSTANCE.mapAlbumToDto(album()));
        var user = user();
        Albums albums = new Albums(albumDtoList);
        given(albumRepository.findAllByUserId(user.getId())).willReturn(albumDtoList.stream().map(AlbumMapper.INSTANCE::mapDtoToAlbum).collect(Collectors.toList()));
        Albums expected = albumService.getAllUserAlbums(user.getId());
        assertEquals(expected, albums);
    }

//    @Test
//    public void shouldReturnAddedAlbum(){
//        AlbumResponse response = albumResponse();
//        Album album = AlbumMapper.INSTANCE.mapResponseToAlbum(response);
//        AlbumDto dto = AlbumMapper.INSTANCE.mapAlbumToDto(album);
//        var user = user();
//        AlbumDto expected = albumService.saveAlbum(response, user.getId());
//        assertEquals(expected, dto);
//        verify(albumRepository).save(album);
//    }

    @Test
    public void shouldDeleteAlbum(){
        Album album = album();
        var user = user();
        albumService.deleteUserAlbum(user.getId(), album.getId());
        verify(albumRepository).delete(albumRepository.findByIdAndUserId(album.getId(), user.getId()));
    }

    @Test
    public void shouldDeleteAllAlbums(){
        var user = user();
        albumService.deleteAllUserAlbum(user.getId());
        verify(albumRepository).deleteAllByUserId(user.getId());
    }
}
