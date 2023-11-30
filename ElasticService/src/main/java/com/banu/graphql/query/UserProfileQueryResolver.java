package com.banu.graphql.query;

import com.banu.dto.request.UserProfileRequestDto;
import com.banu.graphql.model.UserProfileInput;
import com.banu.repository.entity.UserProfile;
import com.banu.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class UserProfileQueryResolver {
    private final UserProfileService userProfileService;

    @QueryMapping
    public Iterable<UserProfile> findAll() {
        return userProfileService.findAll();
    }

    @QueryMapping
    public UserProfile findById(@Arguments String id) {
        return userProfileService.findById(id);
    }

    @MutationMapping
    public UserProfile saveUser(@Arguments UserProfileInput input) {
        userProfileService.save(UserProfileRequestDto.builder()
                .userName(input.getUserName())
                .authId(input.getAuthId())
                .email(input.getEmail())
                .phone(input.getPhone())
                .name(input.getName())
                .photo(input.getPhoto())
                .build());
        return new UserProfile();
    }

}

