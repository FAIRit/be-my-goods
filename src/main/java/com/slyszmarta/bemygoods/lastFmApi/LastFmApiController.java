package com.slyszmarta.bemygoods.lastFmApi;

import com.slyszmarta.bemygoods.album.Albums;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums/search")
public class LastFmApiController {

    private final LastFmApiService lastFmApiService;

    public LastFmApiController(LastFmApiService lastFmApiService) {
        this.lastFmApiService = lastFmApiService;
    }

    @GetMapping(value = "/{artist}/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds music CD by artist and title", notes = "Provide artist and title to look up specific music CD from Last.fm API", response = Albums.class)
    @ResponseStatus(HttpStatus.OK)
    public Albums getAlbumsByArtistAndTitle(@ApiParam(value = "Artist value for the album you need to retrieve", required = true) @PathVariable(name = "artist") final String artist,
                                            @ApiParam(value = "Title value for the album you need to retrieve", required = true) @PathVariable(name = "title") final String title) {
        return lastFmApiService.searchAlbums(artist, title);
    }
}
