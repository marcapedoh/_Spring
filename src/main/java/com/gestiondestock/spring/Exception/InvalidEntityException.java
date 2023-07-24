package com.gestiondestock.spring.Exception;


import lombok.Getter;

import java.util.List;

public class InvalidEntityException extends RuntimeException{

    @Getter
    private ErrorCodes errorCodes;
    @Getter
    private List<String> error;

    public InvalidEntityException(String message){
        super(message);
    }

    public InvalidEntityException(String message,Throwable cause){
        super(message,cause);
    }
    public InvalidEntityException(String message,Throwable cause,ErrorCodes errorcodes){
        super(message,cause);
        this.errorCodes=errorcodes;
    }

    public InvalidEntityException(String message,ErrorCodes errorCodes,List<String> errors){
        super(message);
        this.errorCodes=errorCodes;
        this.error=errors;
    }
}
