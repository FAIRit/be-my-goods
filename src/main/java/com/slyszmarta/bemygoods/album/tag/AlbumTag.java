package com.slyszmarta.bemygoods.album.tag;

import com.slyszmarta.bemygoods.album.Album;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "tags")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "albumTags")
    private Set<Album> albums = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
}
