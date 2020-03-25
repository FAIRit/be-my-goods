package com.slyszmarta.bemygoods.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/user/myprofile")
@Api(value = "Users")
@RequiredArgsConstructor
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get information about currently logged user.", response = ApplicationUserDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User information successfully retrieved."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "Resource you were trying to reach is not found.")
    })
    public ApplicationUserDto getCurrentUser(@ApiIgnore @AuthenticationPrincipal ApplicationUser user){
        return ApplicationUserMapper.INSTANCE.map(user);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete currently logged user.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User successfully deleted."),
            @ApiResponse(code = 401, message = "You are not authorized to access this resource."),
            @ApiResponse(code = 403, message = "Resource you were trying to reach is forbidden."),
    })
    public void deleteUser(@ApiIgnore @AuthenticationPrincipal ApplicationUser user){
        applicationUserService.delete(user.getId());
    }

}
