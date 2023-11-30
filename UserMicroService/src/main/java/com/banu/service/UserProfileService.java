package com.banu.service;

import com.banu.dto.request.GetProfileByTokenRequestDto;
import com.banu.dto.request.UpdateProfileRequestDto;
import com.banu.dto.request.UserProfileSaveRequestDto;
import com.banu.dto.response.UserProfileResponseDto;
import com.banu.exception.ErrorType;
import com.banu.exception.UserException;
import com.banu.manager.ElasticSearchUserProfileManager;
import com.banu.mapper.UserProfileMapper;
import com.banu.repository.UserProfileRepository;
import com.banu.repository.entity.UserProfile;
import com.banu.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final ElasticSearchUserProfileManager manager;

    public UserProfile save(UserProfileSaveRequestDto dto) {
        UserProfile userProfile = repository.save(UserProfileMapper.INSTANCE.fromDto(dto));
        Objects.requireNonNull(cacheManager.getCache("userprofilefindall")).clear();
        manager.save(UserProfileMapper.INSTANCE.fromUserProfiletoUSerProfileRequestDto(userProfile));
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
        manager.update(UserProfileMapper.INSTANCE.fromUserProfiletoUSerProfileRequestDto(updateProfile));
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


    /**
     * @param page          -> hangi sayfayı görmek istediğiniz
     * @param size          -> Bir sayfada kac kayıt görmek istediğinizi belirtir
     * @param sortParameter -> hangi parametreye göre sıralama yapmak istediğinizi belirtir. (ad, id, userName)
     * @param sortDirection -> sıralama yönünü belirtir (ASC, DESV
     * @return
     */
    public Page<UserProfile> findAll(int page, int size, String sortParameter, String sortDirection) {
        Pageable pageable;
        if (sortParameter != null && !sortParameter.isEmpty()) {
            /*
                Sıralama için sort(sıralama) nesnesi yaratmamz gerekli.
                Sort sıralama yapabilsin diye sort directiona ihtiyacı var A-Z mi Z-A mı olacak.
                Hangi parametreye göre sırlama yapacaksın -> sortparameter
             */
            Sort sort = Sort.by(Sort.Direction.fromString(sortDirection == null ? "ASC" : sortDirection), sortParameter);
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = Pageable.ofSize(size).withPage(page);
        }
        return repository.findAll(pageable);
    }
}
