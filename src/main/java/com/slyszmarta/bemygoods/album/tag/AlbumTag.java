package com.slyszmarta.bemygoods.album.tag;

import com.slyszmarta.bemygoods.album.Album;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@ApiModel(description = "Album tag details")
@Getter
@Setter
@Entity(name = "AlbumTag")
@Table(name = "tag")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumTag implements Serializable {

    private static final long serialVersionUID = -4110363081291729285L;

    @ApiModelProperty(notes = "Album tag ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", updatable = false, nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Album tag name")
    @Column(name = "name", unique = true)
    private String name;

    @ApiModelProperty(notes = "Albums with specified tag")
    @Builder.Default
    @ManyToMany(mappedBy = "albumTags")
    private Set<Album> albums = new HashSet<>();

    @ApiModelProperty(notes = "Album tag user")
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