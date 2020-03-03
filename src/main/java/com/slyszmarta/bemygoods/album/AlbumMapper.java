package com.slyszmarta.bemygoods.album;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AlbumMapper {

    Album map(AlbumDto dto);
    AlbumDto map (Album entity);
}
