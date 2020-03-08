package com.slyszmarta.bemygoods.lastFmApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackInfo {

    private String name;
    //duration in seconds
    private int duration;
    private String artist;

}
