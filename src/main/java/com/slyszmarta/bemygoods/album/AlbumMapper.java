package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.lastFmApi.AlbumInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    AlbumMapper INSTANCE = Mappers.getMapper(AlbumMapper.class);

    Album map(AlbumDto dto);

    AlbumDto map(Album entity);

    AlbumDto mapAlbumInfoToBookDto(AlbumInfo albumInfo);

}
