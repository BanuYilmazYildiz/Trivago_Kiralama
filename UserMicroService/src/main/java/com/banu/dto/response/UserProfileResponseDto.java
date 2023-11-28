package com.banu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileResponseDto {
    private String id;
    private String userName;
    private String email;
    private String name;
    private String photo;
    private String phone;

}
