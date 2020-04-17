package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.album.tag.AlbumTag;
import com.slyszmarta.bemygoods.album.track.Track;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Album")
@Table(name = "albums")
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "albums_tags", joinColumns = {@JoinColumn(name = "album_id")}, inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<AlbumTag> albumTags = new HashSet<>();

    public void addAlbumTag(AlbumTag albumTag){
        albumTags.add(albumTag);
        var albums = albumTag.getAlbums();
        albums.add(this);
    }

    public void removeAlbumTag(AlbumTag albumTag){
        albumTags.remove(albumTag);
        var albums = albumTag.getAlbums();
        albums.remove(this);
    }

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Track> tracksList = new ArrayList<>();

    public void addTrack(Track track){
        tracksList.add(track);
        track.setAlbum(this);
    }

    public void removeTrack(Track track){
        tracksList.remove(track);
        track.setAlbum(null);
    }

    @Column(name = "wiki", length = 65535, columnDefinition = "TEXT")
    @Type(type = "text")
    private String wiki;

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof Album)) return false;

        return id != null && id.equals(((Album) object).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
