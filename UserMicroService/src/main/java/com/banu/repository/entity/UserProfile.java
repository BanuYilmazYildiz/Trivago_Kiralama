package com.banu.repository.entity;

import com.banu.utility.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class UserProfile implements Serializable {
    @Id
    private String id;

    private Long authId;

    private String userName;

    private String email;

    private String name;

    private String photo;

    private String phone;

    private State state;
}
