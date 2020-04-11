package com.slyszmarta.bemygoods.lastFmApi.response;

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
public class Album {
    private String name;
    private String artist;
    private String mbid;
    private Tracks tracks;
    private Wiki wiki;
}
