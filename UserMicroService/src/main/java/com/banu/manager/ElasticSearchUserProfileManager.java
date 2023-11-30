package com.banu.manager;

import com.banu.dto.request.UserProfileRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "elastic-search-user-profile-manager", url= "http://localhost:9100/elastic-user-profile")
public interface ElasticSearchUserProfileManager {

    @PostMapping("/save")
    ResponseEntity<Void> save(@RequestBody UserProfileRequestDto dto);

    @PostMapping("/update")
    ResponseEntity<Void> update(@RequestBody UserProfileRequestDto dto);




}
