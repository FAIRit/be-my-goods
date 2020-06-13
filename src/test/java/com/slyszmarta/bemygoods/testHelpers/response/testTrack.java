package com.slyszmarta.bemygoods.testHelpers.response;

import com.slyszmarta.bemygoods.last_fm_api.response.Track;

public class testTrack {

    public static com.slyszmarta.bemygoods.last_fm_api.response.Track track(){
        String name = "Track(name=The Unforgiven)";
        Track track = com.slyszmarta.bemygoods.last_fm_api.response.Track.builder()
                .name(name)
                .build();
        return track;
    }

}
