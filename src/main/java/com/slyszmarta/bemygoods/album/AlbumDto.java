package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.track.Track;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AlbumDto {

    private Long id;

    private String name;

    private String artist;

    private List<Track> tracksList;
}
