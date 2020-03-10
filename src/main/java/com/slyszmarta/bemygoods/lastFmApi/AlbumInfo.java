package com.slyszmarta.bemygoods.lastFmApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumInfo {

    private String name;
    private String artist;
    private LocalDateTime releaseDate;
    private String[] topTags;
    private List<TrackInfo> tracksList;
    private List<Wiki> wikiInformation;
}
