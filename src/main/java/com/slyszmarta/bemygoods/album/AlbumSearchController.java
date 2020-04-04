package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.lastFmApi.LastFmApiService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums/search")
@Api(value = "Albums searching")
public class AlbumSearchController {

    private final LastFmApiService lastFmApiService;

    public AlbumSearchController(LastFmApiService lastFmApiService) {
        this.lastFmApiService = lastFmApiService;
    }

    @GetMapping(value = "/artist={artist}&album={title}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds music CD by artist and title", notes = "Provide artist and title to look up specific music CD from Last.fm API", response = Albums.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(code = 200, message = "Album succesfully found.")
    public Albums getAlbumsByArtistAndTitle(@ApiParam(value = "Artist value for the album you need to retrieve", required = true) @PathVariable String artist,
                                            @ApiParam(value = "Title value for the album you need to retrieve", required = true) @PathVariable  String title) {
        return lastFmApiService.searchAlbums(artist, title);
    }

    @GetMapping(value = "/mbid={musicbrainzId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds music CD by musicbrainz id", notes = "Provide musicbrainz id to look up specific music CD from Last.fm API", response = Albums.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(code = 200, message = "Album succesfully found.")
    public Albums getAlbumsByMusicbrainzId(@ApiParam(value = "Musicbrainz id value for the album you need to retrieve", required = true) @PathVariable String musicbrainzId) {
        return lastFmApiService.searchAlbums(musicbrainzId);
    }
}
