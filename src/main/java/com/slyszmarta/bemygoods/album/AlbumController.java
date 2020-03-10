package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.exceptions.AlbumNotFoundException;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/myprofile/albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    // User
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAllUsersAlbums(@AuthenticationPrincipal ApplicationUser user) {
        return ResponseEntity.ok(albumService.getAllUsersAlbums(user.getId()));
    }

    @GetMapping("/{albumId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAlbumById(@PathVariable(name = "albumId") final Long albumId) {
        return ResponseEntity.ok(AlbumMapper.INSTANCE.map(albumService.getExistingAlbumById(albumId)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addAlbum(UriComponentsBuilder builder, @RequestBody final AlbumDto dto, @AuthenticationPrincipal ApplicationUser user) {
        Long id = albumService.saveAlbum(dto, user.getId()).getId();
        UriComponents uriComponents = builder.path("/{id}").buildAndExpand(id);
        return (ResponseEntity) ResponseEntity.created(uriComponents.toUri());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteAllUsersAlbums(@AuthenticationPrincipal ApplicationUser user) {
        try {
            albumService.deleteAllUsersAlbum(user.getId());
            return (ResponseEntity) ResponseEntity.noContent();
        } catch (AlbumNotFoundException e) {
            return (ResponseEntity) ResponseEntity.notFound();
        }
    }

    @DeleteMapping("/{albumId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteAlbum(@AuthenticationPrincipal ApplicationUser user, @PathVariable(name = "albumId") final Long albumId) {
        try {
            albumService.deleteUsersAlbum(user.getId(), albumId);
            return (ResponseEntity) ResponseEntity.noContent();
        } catch (AlbumNotFoundException | AccessDeniedException e) {
            return (ResponseEntity) ResponseEntity.notFound();
        }
    }
}
