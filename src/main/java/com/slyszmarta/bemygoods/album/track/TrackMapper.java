package com.slyszmarta.bemygoods.album.track;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrackMapper {
    TrackMapper INSTANCE = Mappers.getMapper(TrackMapper.class);

    TrackDto mapTrackToDto(Track track);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "album", ignore = true)
    Track mapDtoToTrack(TrackDto dto);
}
