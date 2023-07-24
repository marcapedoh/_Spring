package com.gestiondestock.spring.Exception;

import lombok.Getter;

import java.util.List;

public class InvalidOperationException extends RuntimeException{
    @Getter
    private ErrorCodes errorCodes;
    @Getter
    private List<String> error;
    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String message,ErrorCodes errorCodes) {
        super(message);
        this.errorCodes=errorCodes;
    }

    public InvalidOperationException(Throwable cause) {
        super(cause);
    }

    public InvalidOperationException(String message,ErrorCodes errorCodes,List<String> error) {
        super(message);
        this.errorCodes=errorCodes;
        this.error=error;


    }
}
