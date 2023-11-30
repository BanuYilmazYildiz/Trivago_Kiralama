package com.banu.service;

import com.banu.dto.request.UserProfileRequestDto;
import com.banu.mapper.UserProfileMapper;
import com.banu.repository.UserProfileRepository;
import com.banu.repository.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public void save(UserProfileRequestDto dto) {
        userProfileRepository.save(UserProfileMapper.INSTANCE.userProfileFromDto(dto));
    }

    public void update(UserProfileRequestDto dto) {
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUserId(dto.getId());
        if (userProfile.isEmpty()) {
            userProfileRepository.save(UserProfileMapper.INSTANCE.userProfileFromDto(dto));
        } else {
            UserProfile profile = userProfile.get();
            profile.setPhone(dto.getPhone());
            profile.setName(dto.getName());
            profile.setPhoto(dto.getPhoto());
            profile.setEmail(dto.getEmail());
            profile.setState(dto.getState());
            userProfileRepository.save(profile);
        }
    }


    public Iterable<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }

    public UserProfile findById(String id) {
        return userProfileRepository.findById(id).orElse(null);
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
        return userProfileRepository.findAll(pageable);
    }
}
