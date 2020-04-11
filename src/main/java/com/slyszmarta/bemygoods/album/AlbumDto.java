package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.lastFmApi.response.Tracks;
import com.slyszmarta.bemygoods.lastFmApi.response.Wiki;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDto {
    private String mbid;
    private String name;
    private String artist;
    private Tracks tracks;
    private Wiki wiki;
}
