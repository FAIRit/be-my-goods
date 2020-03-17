package com.slyszmarta.bemygoods.avatar;

import com.slyszmarta.bemygoods.user.ApplicationUser;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/user/myprofile/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private AvatarService avatarService;
    private ApplicationUserService userService;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get your avatar", response = ResponseEntity.class)
    public ResponseEntity<ByteArrayResource> downloadFile(@AuthenticationPrincipal ApplicationUser user) {
        var avatar = avatarService.getFile(user.getId());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(avatar.getFileType()))
                .body(new ByteArrayResource(avatar.getData()));
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Upload your avatar file")
    public ResponseEntity uploadFile(@ApiParam(value = "Avatar file to save or update") @RequestParam("file") MultipartFile file, @AuthenticationPrincipal ApplicationUser user) throws URISyntaxException {
        return ResponseEntity.created(new URI("/upload")).body(avatarService.storeFile(file, user));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete your avatar file")
    public void deleteFile(@AuthenticationPrincipal ApplicationUser user){
        avatarService.deleteUserAvatar(user.getId());
    }
}
