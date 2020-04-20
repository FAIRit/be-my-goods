package com.slyszmarta.bemygoods.avatar;

import com.slyszmarta.bemygoods.user.ApplicationUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity(name = "Avatar")
@Table(name = "avatar")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Avatar details")
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id", updatable = false, nullable = false)
    @ApiModelProperty(notes = "Avatar ID")
    private String id;

    @Column(name = "filetype")
    @ApiModelProperty(notes = "Avatar filetype")
    private String fileType;

    @Lob
    @Column(name = "data")
    @ApiModelProperty(notes = "Large OBject")
    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "Avatar user")
    private ApplicationUser user;
}
