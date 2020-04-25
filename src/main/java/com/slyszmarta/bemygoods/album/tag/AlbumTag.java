package com.slyszmarta.bemygoods.album.tag;

import com.slyszmarta.bemygoods.album.Album;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity(name = "AlbumTag")
@Table(name = "tag")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Album tag details")
public class AlbumTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", updatable = false, nullable = false)
    @ApiModelProperty(notes = "Album tag ID")
    private Long id;

    @Column(name = "name", unique = true)
    @ApiModelProperty(notes = "Album tag name")
    private String name;

    @Builder.Default
    @ManyToMany(mappedBy = "albumTags")
    @ApiModelProperty(notes = "Albums with specified tag")
    private Set<Album> albums = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ApiModelProperty(notes = "Album tag user")
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
