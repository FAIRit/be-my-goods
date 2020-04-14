package com.slyszmarta.bemygoods.album;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDto {
    private Long id;
    private String mbid;
    private String name;
    private String artist;
    private String wiki;
}
