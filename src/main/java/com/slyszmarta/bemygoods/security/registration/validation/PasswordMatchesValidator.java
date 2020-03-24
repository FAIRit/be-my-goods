package com.slyszmarta.bemygoods.security.registration.validation;

import com.slyszmarta.bemygoods.user.ApplicationUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        var user = (ApplicationUserDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
