package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.album.tag.AlbumTag;
import com.slyszmarta.bemygoods.album.track.Track;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "album")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Album details")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", updatable = false, nullable = false)
    @ApiModelProperty(notes = "Album ID")
    private Long id;

    @Column(name = "musicbrainz_id")
    @ApiModelProperty(notes = "Musicbrainz album ID")
    private String mbid;

    @NotNull
    @Column(name = "name")
    @ApiModelProperty(notes = "Album name")
    private String name;

    @NotNull
    @Column(name = "artist")
    @ApiModelProperty(notes = "Album artist")
    private String artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ApiModelProperty(notes = "Album user")
    private ApplicationUser user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "album_tag", joinColumns = {@JoinColumn(name = "album_id")}, inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @ApiModelProperty(notes = "Album tags")
    private Set<AlbumTag> albumTags = new HashSet<>();

    public void addAlbumTag(AlbumTag albumTag){
        albumTags.add(albumTag);
        albumTag.getAlbums().add(this);
    }

    public void removeAlbumTag(AlbumTag albumTag){
        albumTags.remove(albumTag);
        albumTag.getAlbums().remove(this);
    }

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @ApiModelProperty(notes = "Album tracks")
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
    @ApiModelProperty(notes = "Album wikipedia summary")
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
