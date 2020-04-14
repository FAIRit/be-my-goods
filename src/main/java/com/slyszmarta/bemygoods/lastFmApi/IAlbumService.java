package com.slyszmarta.bemygoods.lastFmApi;

import com.slyszmarta.bemygoods.lastFmApi.response.AlbumResponse;

import java.util.List;

public interface IAlbumService {
    List<AlbumResponse> searchAlbums(String artist, String title);
}
