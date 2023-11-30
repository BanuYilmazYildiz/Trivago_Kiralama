package com.banu.manager;

import com.banu.dto.request.UserProfileSaveRequestDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Miscroservice yapısında servisleri birbirileri ile iletişime geçmeleri için kullanılan yapıdır.
 * Genellikle bir control yapısında istek atılır ve tüm end-pointleri interface içinde tanımlanır.
 * iki parametresi vardır;
 * 1- url : istek atılacak olan end point in adresi bulunur. root path burgun yazılır.(www.adres.com/userpsrofile) gibi
 * 2- name: her feignClient için benzersiz bir isimlendirme yapılır. isim yazımı işlevselliğe göre verilir.
 */
@FeignClient(url = "${my-application.user-profile-end-point}", name = "userProfileManager")
public interface UserProfileManager {

    @PostMapping("/save")
    ResponseEntity<Void> save(@RequestBody @Valid UserProfileSaveRequestDto dto);


}
