package com.slyszmarta.bemygoods.album.tag;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AlbumTagMapper {
    AlbumTagMapper INSTANCE = Mappers.getMapper(AlbumTagMapper.class);

    @Mapping(target = "albums", ignore = true)
    @Mapping(target = "user", ignore = true)
    AlbumTag mapDtoToAlbumTag(AlbumTagDto dto);

    AlbumTagDto mapAlbumTagToDto(AlbumTag entity);
}
