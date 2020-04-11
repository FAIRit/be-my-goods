package com.slyszmarta.bemygoods.avatar;

import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;
import com.slyszmarta.bemygoods.security.user.LoggedInUser;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Api(value = "Avatar")
@RequestMapping("/user/myprofile/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;
    private final ApplicationUserService applicationUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get your avatar", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Avatar successfully retrieved."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public ResponseEntity<ByteArrayResource> downloadFile(@ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        var avatar = avatarService.getFile(user.getId());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(avatar.getFileType()))
                .body(new ByteArrayResource(avatar.getData()));
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Upload your avatar file")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Avatar successfully uploaded."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public ResponseEntity uploadFile(@ApiParam(value = "Avatar file to save or update", required = true) @RequestParam("file") MultipartFile file, @ApiIgnore @LoggedInUser ApplicationUserDetails user) throws URISyntaxException {
        var uploadingUser = applicationUserService.getExistingUser(user.getId());
        return ResponseEntity.created(new URI("/upload")).body(avatarService.storeFile(file, uploadingUser));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete your avatar file")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Avatar successfully deleted."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public void deleteFile(@ApiIgnore @LoggedInUser ApplicationUserDetails user){
        var deletingUser = applicationUserService.getExistingUser(user.getId());
        avatarService.deleteUserAvatar(deletingUser.getId());
    }
}
