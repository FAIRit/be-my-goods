package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.lastFmApi.response.AlbumResponse;
import com.slyszmarta.bemygoods.lastFmApi.response.Wiki;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AlbumMapper {
    AlbumMapper INSTANCE = Mappers.getMapper(AlbumMapper.class);
    AlbumDto mapAlbumToDto(com.slyszmarta.bemygoods.album.Album entity);
    Album mapDtoToAlbum(AlbumDto dto);
    Album mapResponseToAlbum(AlbumResponse response);
    String mapWikiToString(Wiki wiki);
    Wiki mapStringToWiki(String string);
}
