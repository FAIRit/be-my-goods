package com.slyszmarta.bemygoods.lastFmApi;

import com.slyszmarta.bemygoods.track.Track;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumInfo {

    private String name;
    private String artist;
    private LocalDateTime releaseDate;
    private String[] topTags;
    private List<TrackInfo> tracksList;

}
