package com.slyszmarta.bemygoods.avatar;

import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;
import com.slyszmarta.bemygoods.security.user.LoggedInUser;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "Avatar")
@RestController
@RequestMapping("/user/myprofile/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;
    private final ApplicationUserService applicationUserService;

    @ApiOperation(value = "Get your avatar", response = ByteArrayResource.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Avatar successfully retrieved."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ByteArrayResource downloadFile(@ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        var avatar = avatarService.getFile(user.getId());
        return new ByteArrayResource(avatar.getData());
    }

    @ApiOperation(value = "Upload your avatar file", response = Avatar.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Avatar successfully uploaded."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Avatar uploadFile(@ApiParam(value = "Avatar file to save or update", required = true) @RequestParam("file") MultipartFile file, @ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        var uploadingUser = applicationUserService.getExistingUser(user.getId());
        return avatarService.storeFile(file, uploadingUser);
    }

    @ApiOperation(value = "Delete your avatar file")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Avatar successfully deleted."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile(@ApiIgnore @LoggedInUser ApplicationUserDetails user) {
        var deletingUser = applicationUserService.getExistingUser(user.getId());
        avatarService.deleteUserAvatar(deletingUser.getId());
    }
}
