package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.lastFmApi.LastFmApiService;
import com.slyszmarta.bemygoods.lastFmApi.response.AlbumResponse;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Album succesfully found."),
            @ApiResponse(code = 401, message = "You are not authorized to access these resources."),
            @ApiResponse(code = 403, message = "Resources you were trying to reach are forbidden."),
            @ApiResponse(code = 404, message = "Resources you were trying to reach are not found.")
    })
    @ResponseBody
    public List<AlbumResponse> getAlbumsByArtistAndTitle(@ApiParam(value = "Artist value for the album you need to retrieve", required = true) @PathVariable String artist,
                                                         @ApiParam(value = "Title value for the album you need to retrieve", required = true) @PathVariable String title) {
        return lastFmApiService.searchAlbums(artist, title);
    }

    @GetMapping(value = "/mbid={musicbrainzId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds music CD by musicbrainz id", notes = "Provide musicbrainz id to look up specific music CD from Last.fm API", response = Albums.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Album succesfully found."),
            @ApiResponse(code = 401, message = "You are not authorized to access these resources."),
            @ApiResponse(code = 403, message = "Resources you were trying to reach are forbidden."),
            @ApiResponse(code = 404, message = "Resources you were trying to reach are not found.")
    })
    @ResponseBody
    public List<AlbumResponse> getAlbumsByMusicbrainzId(@ApiParam(value = "Musicbrainz id value for the album you need to retrieve", required = true) @PathVariable String musicbrainzId) {
        return lastFmApiService.searchAlbums(musicbrainzId);
    }
}
