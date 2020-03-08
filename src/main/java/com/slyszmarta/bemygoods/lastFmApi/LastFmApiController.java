package com.slyszmarta.bemygoods.lastFmApi;

import com.slyszmarta.bemygoods.album.AlbumDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums/search")
public class LastFmApiController {

    private final LastFmApiService lastFmApiService;

    public LastFmApiController(LastFmApiService lastFmApiService) {
        this.lastFmApiService = lastFmApiService;
    }

    @GetMapping("/{artist}/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumDto> getAlbumsByArtistAndTitle(@PathVariable(name = "artist") final String artist, @PathVariable(name = "title") final String title){
        return lastFmApiService.searchAlbums(artist, title);
    }
}
