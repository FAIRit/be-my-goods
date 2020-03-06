package com.slyszmarta.bemygoods.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ApplicationUserMapper {

    ApplicationUserMapper INSTANCE = Mappers.getMapper(ApplicationUserMapper.class);

    ApplicationUser map(ApplicationUserDto dto);
    ApplicationUserDto map(ApplicationUser user);
}
