package com.slyszmarta.bemygoods.testHelpers.response;

import com.slyszmarta.bemygoods.last_fm_api.response.Track;

import java.util.ArrayList;
import java.util.List;

public class testTracks {

    public static List<Track> track(){
        List<Track> tracks = new ArrayList<>();
        tracks.add(testTrack.track());
        return tracks;
    }

}
