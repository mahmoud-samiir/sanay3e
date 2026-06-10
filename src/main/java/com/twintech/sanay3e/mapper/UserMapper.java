package com.twintech.sanay3e.mapper;

import com.twintech.sanay3e.dto.user.UserProfileResponse;
import com.twintech.sanay3e.dto.user.UpdateProfileRequest;
import com.twintech.sanay3e.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    UserProfileResponse toUserProfileResponse(User user);

    void updateUserFromDto(UpdateProfileRequest dto, @MappingTarget User user);
}