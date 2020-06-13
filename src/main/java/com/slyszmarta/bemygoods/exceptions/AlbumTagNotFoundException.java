package com.slyszmarta.bemygoods.exceptions;

import lombok.Getter;

@Getter
public class AlbumTagNotFoundException extends RuntimeException {

    private final Long id;

    public AlbumTagNotFoundException(Long id) {
        super("Album tag with id " + id + " not found");
        this.id = id;
    }

}
