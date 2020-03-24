package com.slyszmarta.bemygoods.security.registration;

import com.slyszmarta.bemygoods.security.registration.validation.ValidPassword;
import lombok.Data;

@Data
public class PasswordDto {

    private String oldPassword;

    @ValidPassword
    private String newPassword;

}
