package com.slyszmarta.bemygoods.lastFmApi;

import com.slyszmarta.bemygoods.album.Albums;

public interface AlbumService {
    Albums searchAlbums(String artist, String title);
}
