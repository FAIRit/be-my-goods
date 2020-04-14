package com.slyszmarta.bemygoods.album.track;

import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;
import com.slyszmarta.bemygoods.security.user.LoggedInUser;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "Tracks")
@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @GetMapping(value = "/albumId={albumId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get an object containing a list of all tracks on specified album.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tracks successfully retrieved."),
            @ApiResponse(code = 401, message = "You are not authorized to access these resources."),
            @ApiResponse(code = 403, message = "Resources you were trying to reach are forbidden."),
            @ApiResponse(code = 404, message = "Resources you were trying to reach are not found.")
    })
    public ResponseEntity getAllAlbumTracks(@ApiParam(value = "Specified album id") @PathVariable Long albumId, @ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        return ResponseEntity.ok(trackService.getAllAlbumTracks(albumId, user.getId()));
    }
}
