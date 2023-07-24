package com.gestiondestock.spring.Exception;


import lombok.Getter;

public class EntityNotFoundException extends  RuntimeException {

    @Getter
    private ErrorCodes errorcodes;

    public EntityNotFoundException(String message){
        super(message);
    }

    public EntityNotFoundException(String message,ErrorCodes errorcodes){
        super(message);
        this.errorcodes=errorcodes;
    }

    public EntityNotFoundException(String message,Throwable cause,ErrorCodes errorcodes){
        super(message,cause);
        this.errorcodes=errorcodes;
    }
}
