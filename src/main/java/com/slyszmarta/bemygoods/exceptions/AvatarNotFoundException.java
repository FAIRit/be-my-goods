package com.slyszmarta.bemygoods.exceptions;

import java.io.IOException;


public class AvatarNotFoundException extends RuntimeException {

    public AvatarNotFoundException(String message) {
        super(message);
    }
}
