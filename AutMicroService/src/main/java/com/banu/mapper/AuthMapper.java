package com.banu.mapper;

import com.banu.dto.request.RegisterRequestDto;
import com.banu.dto.request.UserProfileSaveRequestDto;
import com.banu.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    Auth fromDto(RegisterRequestDto dto);

    @Mapping(source = "id", target = "authId")
    UserProfileSaveRequestDto fromAuth(final Auth auth);

}
