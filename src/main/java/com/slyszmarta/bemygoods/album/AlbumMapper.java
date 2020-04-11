package com.slyszmarta.bemygoods.album;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    AlbumMapper INSTANCE = Mappers.getMapper(AlbumMapper.class);

    com.slyszmarta.bemygoods.album.Album map(AlbumDto dto);

    AlbumDto map(com.slyszmarta.bemygoods.album.Album entity);

    AlbumDto map(com.slyszmarta.bemygoods.lastFmApi.response.Album entity);

}
