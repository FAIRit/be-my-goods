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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApiModel(description = "Album details")
@Getter
@Setter
@Entity(name = "Album")
@Table(name = "album")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable {

    private static final long serialVersionUID = -4748102815645843899L;

    @ApiModelProperty(notes = "Album ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", updatable = false, nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Musicbrainz album ID")
    @Column(name = "musicbrainz_id")
    private String mbid;

    @ApiModelProperty(notes = "Album name")
    @NotNull
    @Column(name = "name")
    private String name;

    @ApiModelProperty(notes = "Album artist")
    @NotNull
    @Column(name = "artist")
    private String artist;

    @ApiModelProperty(notes = "Album user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @ApiModelProperty(notes = "Album tags")
    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "album_tag", joinColumns = {@JoinColumn(name = "album_id")}, inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<AlbumTag> albumTags = new HashSet<>();

    public void addAlbumTag(AlbumTag albumTag){
        albumTags.add(albumTag);
        albumTag.getAlbums().add(this);
    }

    public void removeAlbumTag(AlbumTag albumTag){
        albumTags.remove(albumTag);
        albumTag.getAlbums().remove(this);
    }

    @ApiModelProperty(notes = "Album tracks")
    @Builder.Default
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

    @ApiModelProperty(notes = "Album wikipedia summary")
    @Type(type = "text")
    @Column(name = "wiki", length = 65535, columnDefinition = "TEXT")
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
