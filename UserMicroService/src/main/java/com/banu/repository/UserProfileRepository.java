package com.banu.repository;

import com.banu.repository.entity.UserProfile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends MongoRepository<UserProfile,String> {

    Optional<UserProfile> findOptionalByAuthId(Long authId);

    List<UserProfile> findAllByUserNameContaining(String userName, Pageable pageable);
}
