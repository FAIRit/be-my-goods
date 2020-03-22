package com.slyszmarta.bemygoods.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ApplicationUserMapper {

    ApplicationUserMapper INSTANCE = Mappers.getMapper(ApplicationUserMapper.class);

    @Mapping(target = "albumList", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "albumTags", ignore = true)
    ApplicationUser map(ApplicationUserDto dto);

    @Mapping(target = "matchingPassword", ignore = true)
    @Mapping(target = "role", ignore = true)
    ApplicationUserDto map(ApplicationUser user);
}
