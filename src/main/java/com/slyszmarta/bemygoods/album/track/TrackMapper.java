package com.slyszmarta.bemygoods.album.track;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrackMapper {

    TrackMapper INSTANCE = Mappers.getMapper(TrackMapper.class);

    Track map(TrackDto dto);

    TrackDto map(Track entity);

    TrackDto map(com.slyszmarta.bemygoods.lastFmApi.response.Track entity);

}
