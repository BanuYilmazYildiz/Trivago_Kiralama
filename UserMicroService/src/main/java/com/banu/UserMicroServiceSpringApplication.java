package com.banu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //Feigb Client'ı aktif hale getiriyoruz
public class UserMicroServiceSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserMicroServiceSpringApplication.class, args);
    }
}
