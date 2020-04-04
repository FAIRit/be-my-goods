package com.slyszmarta.bemygoods.user;

import com.slyszmarta.bemygoods.security.user.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class ApplicationUserDto {

    @Size(min = 4, max = 255)
    @NotBlank
    private String username;

    @Size(min = 6, max = 255)
    @NotBlank
    private String password;

    @NotBlank
    private String matchingPassword;

    @NotBlank
    @Size(min = 6, max = 255)
    private String email;

}
