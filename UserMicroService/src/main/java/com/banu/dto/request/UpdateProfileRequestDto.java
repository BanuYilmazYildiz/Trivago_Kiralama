package com.banu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProfileRequestDto {

    private String token;

    private String userName;

    private String email;

    private String name;

    private String photo;

    private String phone;


}
