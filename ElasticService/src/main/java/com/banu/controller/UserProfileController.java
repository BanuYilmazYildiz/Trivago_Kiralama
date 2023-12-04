package com.banu.controller;

import com.banu.dto.request.UserProfileRequestDto;
import com.banu.repository.entity.UserProfile;
import com.banu.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elastic-user-profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody UserProfileRequestDto dto){
        userProfileService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody UserProfileRequestDto dto){
        userProfileService.update(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find-all")
    public ResponseEntity<Iterable<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @GetMapping("/find-all-page")
    public ResponseEntity<Page<UserProfile>> findAllPage(int page, int size, String sortParameter, String sortDirection){
        return ResponseEntity.ok(userProfileService.findAll(page,size,sortParameter,sortDirection));
    }
    @GetMapping("/get-message")
    public String getMessage(){
        return "Elastic Servis";
    }

    @GetMapping("/get-secret-message")
    public String getSecretMessage(){
        return "Elastic Servis: gizli bir mesaj";
    }

    @GetMapping("/get-user-message")
    public String getUserMessage(){
        return "Kullanıcının gizli mesajı";
    }
}
