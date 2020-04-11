package com.slyszmarta.bemygoods.album.track;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tracks {

    private List<TrackDto> albums;

}
