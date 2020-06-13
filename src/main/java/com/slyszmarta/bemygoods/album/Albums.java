package com.slyszmarta.bemygoods.album;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Albums {
    private List<AlbumDto> albumDtoList;
}
