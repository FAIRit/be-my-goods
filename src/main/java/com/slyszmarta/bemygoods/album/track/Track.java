package com.slyszmarta.bemygoods.album.track;

import com.slyszmarta.bemygoods.album.Album;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity(name = "Track")
@Table(name = "track")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Track details")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id", updatable = false, nullable = false)
    @ApiModelProperty(notes = "Track ID")
    private Long id;

    @NotNull
    @Column(name = "title")
    @ApiModelProperty(notes = "Track name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id")
    @ApiModelProperty(notes = "Track's album ID")
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
