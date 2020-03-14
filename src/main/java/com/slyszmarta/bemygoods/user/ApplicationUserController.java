package com.slyszmarta.bemygoods.user;

import com.github.javafaker.App;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/myprofile")
@RequiredArgsConstructor
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUserDto getCurrentUser(@AuthenticationPrincipal ApplicationUser user){
        return ApplicationUserMapper.INSTANCE.map(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@AuthenticationPrincipal ApplicationUser user){
        applicationUserService.delete(user.getId());
    }

}
