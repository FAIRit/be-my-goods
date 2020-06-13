package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.last_fm_api.response.AlbumResponse;
import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;
import com.slyszmarta.bemygoods.security.user.LoggedInUser;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "Albums")
@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @ApiOperation(value = "Get an object containing a list of all your albums.", response = Albums.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Albums successfully retrieved."),
            @ApiResponse(code = 401, message = "You are not authorized to access these resources."),
            @ApiResponse(code = 403, message = "Resources you were trying to reach are forbidden."),
            @ApiResponse(code = 404, message = "Resources you were trying to reach are not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Albums getAllUsersAlbums(@ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        return albumService.getAllUserAlbums(user.getId());
    }

    @ApiOperation(value = "Get an album of specified id.", response = AlbumDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Album successfully retrieved."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/{albumId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AlbumDto getAlbumById(@ApiParam(value = "Album id", required = true) @PathVariable Long albumId, @ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        return albumService.getExistingAlbumByUserIdAndAlbumId(user.getId(), albumId);
    }

    @ApiOperation(value = "Upload an album.", response = AlbumDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Album successfully uploaded."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AlbumDto addAlbum(@ApiParam(value = "Album to add", required = true) @RequestBody AlbumResponse response, @ApiIgnore @LoggedInUser ApplicationUserDetails user){
        return albumService.saveAlbum(response, user.getId());
    }

    @ApiOperation(value = "Delete all your albums.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Albums successfully deleted."),
            @ApiResponse(code = 401, message = "You are not authorized to access these resources."),
            @ApiResponse(code = 403, message = "Resources you were trying to reach are forbidden."),
            @ApiResponse(code = 404, message = "Resources you were trying to reach are not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllUsersAlbums(@ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        albumService.deleteAllUserAlbum(user.getId());
    }

    @ApiOperation(value = "Delete an album of specified id.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Album successfully deleted."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{albumId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@ApiIgnore @LoggedInUser ApplicationUserDetails user, @ApiParam(value = "Album id") @PathVariable Long albumId) {
        albumService.deleteUserAlbum(user.getId(), albumId);
    }
}
