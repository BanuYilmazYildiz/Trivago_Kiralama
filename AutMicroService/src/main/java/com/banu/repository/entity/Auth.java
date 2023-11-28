package com.banu.repository.entity;

import com.banu.utility.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tbl_auth")
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,length = 64, nullable = false)
    private String userName;

    @Column(nullable = false,length = 128)
    private String password;

    private Long creatAt;

    private Long updateAt;

    @Enumerated(EnumType.STRING)
    private State state;

}
