package com.slyszmarta.bemygoods.album.tag;

import com.slyszmarta.bemygoods.album.Album;
import com.slyszmarta.bemygoods.album.track.Track;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity(name = "AlbumTag")
@Table(name = "tags")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", updatable = false, nullable = false)
    private Long id;

    @NaturalId
    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "albumTags")
    private Set<Album> albums = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @Override
    public boolean equals(Object object){
        if(this==object){
            return true;
        }
        if (!(object instanceof AlbumTag)){
            return false;
        }
        AlbumTag tag = (AlbumTag) object;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name);
    }
}
