package com.banu.mapper;

import com.banu.dto.request.UserProfileSaveRequestDto;
import com.banu.dto.response.UserProfileResponseDto;
import com.banu.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserProfileMapper {

    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    UserProfile fromDto(final UserProfileSaveRequestDto dto);

    UserProfileResponseDto toUserProfileResponseDto(final UserProfile userProfile);
}
