package com.slyszmarta.bemygoods.avatar;

import com.slyszmarta.bemygoods.user.ApplicationUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity(name = "avatars")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Avatar details")
public class Avatar {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
