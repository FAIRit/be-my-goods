package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.album.tag.AlbumTag;
import com.slyszmarta.bemygoods.album.track.Track;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "albums")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "musicbrainz_id")
    private String mbid;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "artist")
    private String artist;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "albums_tags", joinColumns = {@JoinColumn(name = "album_id")}, inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<AlbumTag> albumTags = new HashSet<>();

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Track> tracksList;

    @Column(name = "wiki", length = 65535, columnDefinition = "TEXT")
    @Type(type = "text")
    private String wiki;
}
