package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.user.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "albumTags")
public class AlbumTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "albumTags")
    private Set<Album> albums = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_tags", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
    private Set<ApplicationUser> users = new HashSet<>();
}
