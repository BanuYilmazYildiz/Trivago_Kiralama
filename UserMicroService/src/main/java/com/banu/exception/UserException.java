package com.banu.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserException extends RuntimeException{

    /*
        sifreler doğru değil
        uername yanlıs sekilde girildi gibi uygulmaya özel exceptionlar buraya yazılır
     */

    private final ErrorType errorType;
    public UserException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType=errorType;
    }

    public UserException(ErrorType errorType,String message){
        super(message);
        this.errorType=errorType;
    }

}
