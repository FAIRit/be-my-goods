package com.slyszmarta.bemygoods.album.track;

import com.slyszmarta.bemygoods.album.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "Track")
@Table(name = "tracks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @Column(name = "title")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @Override
    public boolean equals(Object object){
        if(this==object){
            return true;
        }
        if (!(object instanceof Track)){
            return false;
        }
        return id != null && id.equals(((Album) object).getId());
    }

    @Override
    public int hashCode(){
        return 31;
    }

}
