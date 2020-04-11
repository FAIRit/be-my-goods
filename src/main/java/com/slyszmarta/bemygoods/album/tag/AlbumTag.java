package com.slyszmarta.bemygoods.album.tag;

import com.slyszmarta.bemygoods.album.Album;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity(name = "tags")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "albumTags")
    private Set<Album> albums = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
}
