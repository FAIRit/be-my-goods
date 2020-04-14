package com.slyszmarta.bemygoods.album.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumTags {
    private List<AlbumTagDto> albums;
}
