package com.banu.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {

    SIFRE_UYUSMUYOR(1001,"Girilen şifreler uyuşmuyıor",HttpStatus.BAD_REQUEST),
    USERNAME_PASSWORD_ERROR(1001,"Kullanıcı adı ya da şifre hatalı",HttpStatus.BAD_REQUEST),
    KAYITLI_KULLANICI_ADI(1003,"Bu kullanıcı adı zaten kayıtlıdır",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1004,"Geçersiz token",HttpStatus.BAD_REQUEST), // kullanıcını gmnderdiği token hatalı
    USER_NOT_FOUND(1005,"Böyle bir kullanıcı kayıtlı değildir",HttpStatus.BAD_REQUEST),
    BAD_REQUEST_ERROR(3001,"Girilen bilgiler hatalı", HttpStatus.BAD_REQUEST);


    private int code;
    private  String message;
    private HttpStatus httpStatus;

}
