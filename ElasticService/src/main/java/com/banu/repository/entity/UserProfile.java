package com.banu.repository.entity;

import com.banu.utility.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * DİKKAT!!!!!!!!
 * camelCase notasyonunda yazılacak.
 * userProfile yazılmaz!
 */
@Document(indexName = "user-profile")
public class UserProfile {

    @Id
    private String id;

    private String userId;

    private Long authId;

    private String userName;

    private String email;

    private String name;

    private String photo;

    private String phone;

    private State state;

}
