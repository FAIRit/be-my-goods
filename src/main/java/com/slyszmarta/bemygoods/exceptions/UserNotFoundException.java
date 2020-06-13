package com.slyszmarta.bemygoods.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final Long id;

    public UserNotFoundException(Long id) {
        super("User with id " + id + " not found");
        this.id = id;
    }
}