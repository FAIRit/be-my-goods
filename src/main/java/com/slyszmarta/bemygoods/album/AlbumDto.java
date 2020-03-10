package com.slyszmarta.bemygoods.album;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.slyszmarta.bemygoods.track.Track;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumDto {

    private Long id;

    private String name;

    private String artist;

    private List<Track> tracksList;
}
