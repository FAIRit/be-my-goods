package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.user.ApplicationUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    // User
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAllUsersAlbums(@AuthenticationPrincipal ApplicationUser user) {
        return ResponseEntity.ok(albumService.getAllUserAlbums(user.getId()));
    }

    @GetMapping("/{albumId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAlbumById(@PathVariable Long albumId) {
        return ResponseEntity.ok(AlbumMapper.INSTANCE.map(albumService.getExistingAlbumById(albumId)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addAlbum(@RequestBody  AlbumDto dto, @AuthenticationPrincipal ApplicationUser user) throws URISyntaxException {
        return ResponseEntity.created(new URI("/")).body(albumService.saveAlbum(dto, user.getId()));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllUsersAlbums(@AuthenticationPrincipal ApplicationUser user) {
        albumService.deleteAllUsersAlbum(user.getId());
    }

    @DeleteMapping("/{albumId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@AuthenticationPrincipal ApplicationUser user, @PathVariable Long albumId) {
        albumService.deleteUsersAlbum(user.getId(), albumId);
    }
}
