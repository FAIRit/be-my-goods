package com.slyszmarta.bemygoods.exceptions;

import lombok.Getter;

@Getter
public class AlbumNotFoundException extends RuntimeException {

    private String title;
    private Long id;

    public AlbumNotFoundException(Long id) {
        super("Album with title " + id + " not found");
        this.id = id;
    }

}
