package com.slyszmarta.bemygoods.last_fm_api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumResponse {
    private String mbid;
    private String name;
    private String artist;
    private Tracks tracks;
    private Wiki wiki;
}
