package com.slyszmarta.bemygoods.album.tag;

import com.slyszmarta.bemygoods.album.AlbumDto;
import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;
import com.slyszmarta.bemygoods.security.user.LoggedInUser;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Set;

@Api(value = "Album tags")
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class AlbumTagController {

    private final AlbumTagService albumTagService;

    @ApiOperation(value = "Get an object containing a list of all your tags.", response = AlbumTags.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tags successfully retrieved."),
            @ApiResponse(code = 401, message = "You are not authorized to access these resources."),
            @ApiResponse(code = 403, message = "Resources you were trying to reach are forbidden."),
            @ApiResponse(code = 404, message = "Resources you were trying to reach are not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AlbumTags getAllUsersTags(@ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        return albumTagService.getAllUserAlbumTags(user.getId());
    }

    @ApiOperation(value = "Get an object containing a list of all your albums under specified tag.", response = AlbumDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Albums successfully retrieved."),
            @ApiResponse(code = 401, message = "You are not authorized to access these resources."),
            @ApiResponse(code = 403, message = "Resources you were trying to reach are forbidden."),
            @ApiResponse(code = 404, message = "Resources you were trying to reach are not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/{tagId}/albums", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<AlbumDto> getAllTagAlbums(@ApiIgnore @LoggedInUser ApplicationUserDetails user, @ApiParam(value = "Specified tag id", required = true) @PathVariable Long tagId) {
        return albumTagService.getAllTagAlbums(user.getId(), tagId);
    }

    @ApiOperation(value = "Get a tag of specified id.", response = AlbumTagDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Album tag successfully retrieved."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AlbumTagDto getAlbumTagById(@ApiParam(value = "Album tag id", required = true) @PathVariable Long tagId, @ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        return (albumTagService.getAlbumTagById(user.getId(), tagId));
    }

    @ApiOperation(value = "Create new album tag", response = AlbumTagDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Album tag successfully created."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AlbumTagDto addAlbumTag(@ApiParam(value = "Album tag to add", required = true) @RequestBody AlbumTagDto dto, @ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        return albumTagService.saveAlbumTag(dto, user.getId());
    }

    @ApiOperation(value = "Add tag to an album", response = AlbumTagDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tag successfully added to an album."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{tagId}/albums/{albumId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AlbumTagDto addAlbumTagToAlbum(@ApiParam(value = "Album tag to add", required = true) @PathVariable Long tagId, @ApiParam(value = "Album to add tag", required = true) @PathVariable Long albumId, @ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        return albumTagService.saveAlbumTagToAlbum(tagId, user.getId(), albumId);
    }

    @ApiOperation(value = "Delete an album of specified id.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Album successfully deleted."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumTag(@ApiIgnore @LoggedInUser ApplicationUserDetails user, @ApiParam(value = "Album tag id") @PathVariable Long tagId) {
        albumTagService.deleteUserAlbumTag(user.getId(), tagId);
    }
}
