package com.slyszmarta.bemygoods.lastFmApi;

import com.slyszmarta.bemygoods.album.Albums;

public interface IAlbumService {
    Albums searchAlbums(String artist, String title);
}
