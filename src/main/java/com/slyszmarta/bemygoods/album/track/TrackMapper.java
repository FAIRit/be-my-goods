package com.slyszmarta.bemygoods.album.track;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrackMapper {
    TrackMapper INSTANCE = Mappers.getMapper(TrackMapper.class);
    TrackDto mapTrackToDto(Track track);
    Track mapDtoToTrack(TrackDto dto);
}
