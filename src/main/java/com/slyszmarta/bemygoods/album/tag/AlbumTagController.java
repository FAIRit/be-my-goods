package com.slyszmarta.bemygoods.album.tag;

import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;
import com.slyszmarta.bemygoods.security.user.LoggedInUser;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.net.URISyntaxException;

@Api(value = "Album tags")
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class AlbumTagController {

    private final AlbumTagService albumTagService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get an object containing a list of all your tags.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tags successfully retrieved."),
            @ApiResponse(code = 401, message = "You are not authorized to access these resources."),
            @ApiResponse(code = 403, message = "Resources you were trying to reach are forbidden."),
            @ApiResponse(code = 404, message = "Resources you were trying to reach are not found.")
    })
    public ResponseEntity getAllUsersTags(@ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        return ResponseEntity.ok(albumTagService.getAllUserAlbumTags(user.getId()));
    }

    @GetMapping("/tagId={tagId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a tag of specified id.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Album tag successfully retrieved."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public ResponseEntity getAlbumTagById(@ApiParam(value = "Album tag id", required = true) @PathVariable Long tagId, @ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        return ResponseEntity.ok((albumTagService.getExistingAlbumTagByUserIdAndTagId(user.getId(), tagId)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new album tag", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tag successfully created."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public ResponseEntity addAlbumTag(@ApiParam(value = "Album tag to add", required = true) @RequestBody AlbumTagDto dto, @ApiIgnore @LoggedInUser ApplicationUserDetails user) throws URISyntaxException {
        return ResponseEntity.created(new URI("/tags")).body(albumTagService.saveAlbumTag(dto, user.getId()));
    }

    @PutMapping("/tagId={tagId}&albumId={albumId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add tag to an album", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tag successfully added to an album."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public ResponseEntity addAlbumTagToAlbum(@ApiParam(value = "Album tag to add", required = true) @PathVariable Long tagId, @ApiParam(value = "Album to add tag", required = true) @PathVariable Long albumId, @ApiIgnore @LoggedInUser ApplicationUserDetails user) throws URISyntaxException {
        return ResponseEntity.created(new URI("/tags")).body(albumTagService.saveAlbumTagToAlbum(tagId, albumId, user.getId()));
    }

    @DeleteMapping("/tagId={tagId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete an album of specified id.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Album successfully deleted."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public void deleteAlbumTag(@ApiIgnore @LoggedInUser ApplicationUserDetails user, @ApiParam(value = "Album tag id") @PathVariable Long tagId) {
        albumTagService.deleteUserAlbumTag(user.getId(), tagId);
    }
}
