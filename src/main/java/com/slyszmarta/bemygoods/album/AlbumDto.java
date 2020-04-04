package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.user.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDto {

    private String mbid;

    private String name;

    private String artist;

    private LocalDate releaseDate;

    private ApplicationUser user;

    private List<Track> tracksList;
}
