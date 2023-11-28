package com.banu.service;

import com.banu.dto.request.GetProfileByTokenRequestDto;
import com.banu.dto.request.UpdateProfileRequestDto;
import com.banu.dto.request.UserProfileSaveRequestDto;
import com.banu.dto.response.UserProfileResponseDto;
import com.banu.exception.ErrorType;
import com.banu.exception.UserException;
import com.banu.mapper.UserProfileMapper;
import com.banu.repository.UserProfileRepository;
import com.banu.repository.entity.UserProfile;
import com.banu.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository repository;
    private final JwtTokenManager jwtTokenManager;
    private final CacheManager cacheManager;

    public UserProfile save(UserProfileSaveRequestDto dto) {
        UserProfile userProfile = repository.save(UserProfileMapper.INSTANCE.fromDto(dto));
        Objects.requireNonNull(cacheManager.getCache("userprofilefindall")).clear(); //
        return userProfile;
    }

    public UserProfileResponseDto getProfileByToken(GetProfileByTokenRequestDto dto) {
        /*
            Kullanıcı token bilgisini gönderiyor. jwtManager ile token bilgisini doğruluyor ve içinden
            kişinin authId bilgisini almaya çalışıyor.
         */
        Optional<Long> authId = jwtTokenManager.getIdByToken(dto.getToken());
        if (authId.isEmpty()) {
            throw new UserException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty()){
            throw new UserException(ErrorType.USER_NOT_FOUND);
        }
        return UserProfileMapper.INSTANCE.toUserProfileResponseDto(userProfile.get());
    }

    public Boolean updateProfile(UpdateProfileRequestDto dto) {
        Optional<Long> authId = jwtTokenManager.getIdByToken(dto.getToken());
        if (authId.isEmpty()) {
            throw new UserException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty()) {
            throw new UserException(ErrorType.USER_NOT_FOUND);
        }
        UserProfile updateProfile = userProfile.get();
        updateProfile.setName(dto.getName());
        updateProfile.setEmail(dto.getEmail());
        updateProfile.setPhoto(dto.getPhoto());
        updateProfile.setPhone(dto.getPhone());
        Objects.requireNonNull(cacheManager.getCache("userprofilefindall")).clear();
        repository.save(updateProfile);
        return true;
    }

    /**
     * Burada method için bir cache oluşturup bunun redis tarafından kayıt edilmesini sağlayacağız
     * redis bu methodu şu şekilde kayıt edecek:
     *               key     value
     * getUpperName:banu ->  BANU
     * getUpperName:ali  ->  ALI
     * @param userName
     * @return
     */
    @Cacheable(value = "getuppername")
    public String getupperCase(String userName){
        String result = userName.toUpperCase();
        try {
            Thread.sleep(4000L);
        }catch (Exception e){
            System.out.println("Hata oldu");
            return "Error... : "+ e;
        }
        return result;
    }

    public void clearKey(String key){
        Objects.requireNonNull(cacheManager.getCache("getuppername")).evict(key);
    }

    @Cacheable(value = "userprofilefindall")
    public List<UserProfile> getAllUserProfile() {
        return repository.findAll();
    }
}
