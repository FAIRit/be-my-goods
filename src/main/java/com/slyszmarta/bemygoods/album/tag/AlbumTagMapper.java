package com.slyszmarta.bemygoods.album.tag;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AlbumTagMapper {
    AlbumTagMapper INSTANCE = Mappers.getMapper(AlbumTagMapper.class);
    AlbumTag mapDtoToAlbumTag (AlbumTagDto dto);
    AlbumTagDto mapAlbumTagToDto(AlbumTag entity);
}
