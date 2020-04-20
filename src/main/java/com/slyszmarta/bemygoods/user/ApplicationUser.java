package com.slyszmarta.bemygoods.user;

import com.slyszmarta.bemygoods.album.Album;
import com.slyszmarta.bemygoods.album.tag.AlbumTag;
import com.slyszmarta.bemygoods.avatar.Avatar;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "User")
@Table(name = "application_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "User details")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    @ApiModelProperty(notes = "User ID")
    private Long id;

    @Column(name = "username", nullable = false)
    @ApiModelProperty(notes = "Username")
    private String username;

    @Column(name = "password", nullable = false)
    @ApiModelProperty(notes = "User password")
    private String password;

    @Column(name = "email", nullable = false)
    @ApiModelProperty(notes = "User email")
    private String email;

    @Column(name = "country")
    @ApiModelProperty(notes = "User country")
    private String country;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ApiModelProperty(notes = "User albums")
    private List<Album> albumList;

    public void addAlbum(Album album){
        albumList.add(album);
        album.setUser(this);
    }

    public void removeAlbum(Album album){
        albumList.remove(album);
        album.setUser(null);
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "User avatar")
    private Avatar avatar;

    public void setAvatar(Avatar avatar) {
        if (avatar == null) {
            if (this.avatar != null) {
                this.avatar.setUser(null);
            }
        }
        else {
            avatar.setUser(this);
        }
        this.avatar = avatar;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ApiModelProperty(notes = "User album tags")
    private Set<AlbumTag> albumTags = new HashSet<>();

    public void addTag(AlbumTag albumTag){
        albumTags.add(albumTag);
        albumTag.setUser(this);
    }

    public void removeTag(AlbumTag albumTag){
        albumTags.remove(albumTag);
        albumTag.setUser(null);
    }

    @Transient
    private List<SimpleGrantedAuthority> authorities;
}
