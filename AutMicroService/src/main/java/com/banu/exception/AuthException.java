package com.banu.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthException extends RuntimeException{

    /*
        sifreler doğru değil
        uername yanlıs sekilde girildi gibi uygulmaya özel exceptionlar buraya yazılır
     */

    private final ErrorType errorType;
    public AuthException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType=errorType;
    }

    public AuthException(ErrorType errorType,String message){
        super(message);
        this.errorType=errorType;
    }

}
