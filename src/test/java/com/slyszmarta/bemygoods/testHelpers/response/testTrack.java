package com.slyszmarta.bemygoods.testHelpers.response;

import com.slyszmarta.bemygoods.lastFmApi.response.Track;

public class testTrack {

    public static com.slyszmarta.bemygoods.lastFmApi.response.Track track(){
        String name = "Track(name=The Unforgiven)";
        Track track = com.slyszmarta.bemygoods.lastFmApi.response.Track.builder()
                .name(name)
                .build();
        return track;
    }

}
