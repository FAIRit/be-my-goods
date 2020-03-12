package com.slyszmarta.bemygoods.exceptions;

import lombok.Getter;

@Getter
public class AlbumNotFoundException extends RuntimeException {

    private Long id;

    public AlbumNotFoundException(Long id) {
        super("Album with id " + id + " not found");
        this.id = id;
    }

}
