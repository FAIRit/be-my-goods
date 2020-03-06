package com.slyszmarta.bemygoods.user;

import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@Component
public interface ApplicationUserMapper {
    ApplicationUser map(ApplicationUserDto dto);
}
