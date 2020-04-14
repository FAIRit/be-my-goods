package com.slyszmarta.bemygoods.exceptions;

import java.io.IOException;

public class AvatarStorageException extends RuntimeException {

    public AvatarStorageException(String message, IOException ex) {
        super(message);
    }

    public AvatarStorageException(String message) {
        super(message);
    }
}
