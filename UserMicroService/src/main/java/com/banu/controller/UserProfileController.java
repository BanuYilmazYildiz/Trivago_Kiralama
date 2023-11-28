package com.banu.controller;

import com.banu.dto.request.GetProfileByTokenRequestDto;
import com.banu.dto.request.UpdateProfileRequestDto;
import com.banu.dto.request.UserProfileSaveRequestDto;
import com.banu.dto.response.UserProfileResponseDto;
import com.banu.repository.entity.UserProfile;
import com.banu.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.banu.constants.RestApi.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody @Valid UserProfileSaveRequestDto dto) {
        UserProfile user = userProfileService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get-profile")
    public ResponseEntity<UserProfileResponseDto> getProfileByToken(@RequestBody @Valid GetProfileByTokenRequestDto dto) {
        return ResponseEntity.ok(userProfileService.getProfileByToken(dto));
    }
    @PostMapping("/update-profile")
    public ResponseEntity<Boolean> updateProfile(@RequestBody UpdateProfileRequestDto dto){
        return ResponseEntity.ok(userProfileService.updateProfile(dto));
    }

    @GetMapping("/getmassage")
    public String getMessage(){
        return "Bu UserProfile Servistir";
    }


    @GetMapping("/upper-name")
    public String getUpperName(String userName){
      return userProfileService.getupperCase(userName);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<UserProfile>> getAllUserProfile(){
        return ResponseEntity.ok(userProfileService.getAllUserProfile());
    }

    @GetMapping("/clear-key")
    public ResponseEntity<Void> clearKey(String key){
        userProfileService.clearKey(key);
        return ResponseEntity.ok().build();
    }
}
