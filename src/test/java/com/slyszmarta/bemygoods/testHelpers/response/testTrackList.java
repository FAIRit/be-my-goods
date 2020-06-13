package com.slyszmarta.bemygoods.testHelpers.response;

import com.slyszmarta.bemygoods.last_fm_api.response.Track;

import java.util.ArrayList;
import java.util.List;

public class testTrackList {



    public static List<Track> trackList(){
        List<com.slyszmarta.bemygoods.last_fm_api.response.Track> trackList = new ArrayList<>();
        trackList.add(testTrack.track());
        return trackList;
    }
}
