package com.slyszmarta.bemygoods.album.track;

import com.slyszmarta.bemygoods.album.Album;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@ApiModel(description = "Track details")
@Getter
@Setter
@Entity(name = "Track")
@Table(name = "track")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Track {

    @ApiModelProperty(notes = "Track ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id", updatable = false, nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Track name")
    @NotNull
    @Column(name = "title")
    private String name;

    @ApiModelProperty(notes = "Track's album ID")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
