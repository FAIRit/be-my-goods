package com.slyszmarta.bemygoods.user;

import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ApplicationUserMapper {

    ApplicationUserMapper INSTANCE = Mappers.getMapper(ApplicationUserMapper.class);

    @Mapping(target = "albumList", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "albumTags", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "id", ignore = true)
    ApplicationUser map(ApplicationUserDto dto);

    @Mapping(target = "matchingPassword", ignore = true)
    ApplicationUserDto map(ApplicationUser user);

    ApplicationUserDto map(ApplicationUserDetails user);
}
