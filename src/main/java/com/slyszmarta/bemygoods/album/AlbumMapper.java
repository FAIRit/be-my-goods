package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.last_fm_api.response.AlbumResponse;
import com.slyszmarta.bemygoods.last_fm_api.response.Wiki;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AlbumMapper {
    AlbumMapper INSTANCE = Mappers.getMapper(AlbumMapper.class);

    AlbumDto mapAlbumToDto(com.slyszmarta.bemygoods.album.Album entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "albumTags", ignore = true)
    @Mapping(target = "tracksList", ignore = true)
    Album mapDtoToAlbum(AlbumDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "albumTags", ignore = true)
    @Mapping(target = "tracksList", ignore = true)
    Album mapResponseToAlbum(AlbumResponse response);

    String mapWikiToString(Wiki wiki);

    @Mapping(target = "summary", ignore = true)
    Wiki mapStringToWiki(String string);
}
