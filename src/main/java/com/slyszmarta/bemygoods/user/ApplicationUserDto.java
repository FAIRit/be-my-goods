package com.slyszmarta.bemygoods.user;

import com.slyszmarta.bemygoods.security.registration.validation.PasswordMatches;
import com.slyszmarta.bemygoods.security.registration.validation.ValidEmail;
import com.slyszmarta.bemygoods.security.registration.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class ApplicationUserDto {

    private Long id;

    @Size(min = 4, max = 255)
    @NotBlank
    private String username;

    @Size(min = 6, max = 255)
    @NotBlank
    @ValidPassword
    private String password;

    @NotBlank
    private String matchingPassword;

    @ValidEmail
    @NotBlank
    @Size(min = 6, max = 255)
    private String email;

    @Size(min = 2, max = 255)
    private String country;

    private Integer role;
}
