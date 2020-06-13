package com.slyszmarta.bemygoods.avatar;

import com.slyszmarta.bemygoods.user.ApplicationUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@ApiModel(description = "Avatar details")
@Getter
@Setter
@Entity(name = "Avatar")
@Table(name = "avatar")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Avatar implements Serializable {

    private static final long serialVersionUID = -4182183336520827243L;

    @ApiModelProperty(notes = "Avatar ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id", updatable = false, nullable = false)
    private String id;

    @ApiModelProperty(notes = "Avatar filetype")
    @Column(name = "filetype")
    private String fileType;

    @ApiModelProperty(notes = "Large OBject")
    @Lob
    @Column(name = "data")
    private byte[] data;

    @ApiModelProperty(notes = "Avatar user")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
}
