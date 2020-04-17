package com.slyszmarta.bemygoods.user;

import com.slyszmarta.bemygoods.album.Album;
import com.slyszmarta.bemygoods.album.tag.AlbumTag;
import com.slyszmarta.bemygoods.avatar.Avatar;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "User")
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albumList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Avatar avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
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
