package com.slyszmarta.bemygoods.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    private Long id;

    @Size(min = 4, max = 255)
    @NotBlank
    private String username;

    @Size(min = 6, max = 255)
    @NotBlank
    private String password;

    @Email
    @Size(min = 6, max = 255)
    private String email;

    @Size(min = 2, max = 255)
    private String country;
}
