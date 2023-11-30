package com.banu.dto.request;

import com.banu.utility.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileRequestDto {

    /*
        User microservice yeni kayıt geldiğinde elastic search veritabanına da kaydet diyecek
     */

    private String id;

    private String userName;

    private String email;

    private String name;

    private String photo;

    private String phone;

    private State state;
}

