package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.track.Track;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name="albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @Column(name = "title")
    private String name;

    @Column(name = "artist")
    private String artist;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Track> tracksList;

}
