package com.banu.utility;

import com.banu.manager.ElasticSearchUserProfileManager;
import com.banu.mapper.UserProfileMapper;
import com.banu.repository.UserProfileRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//@Component // kendi application cıntexine doldururken bu sıunıftanda bir nesne yaratacak
@RequiredArgsConstructor
public class TestAndRun {

    private final UserProfileRepository userProfileRepository;
    private final ElasticSearchUserProfileManager manager;
    //@PostConstruct
    public void init(){
        userProfileRepository.findAll().forEach(u->{
            manager.update(UserProfileMapper.INSTANCE.fromUserProfiletoUSerProfileRequestDto(u));
            System.out.println("gönderildi..."+ u.getUserName());
        });
    }
}


