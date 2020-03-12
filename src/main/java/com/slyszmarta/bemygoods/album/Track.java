package com.slyszmarta.bemygoods.album;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name="tracks")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title")
    private String name;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

}
