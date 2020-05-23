package com.demo.atm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCardDetailsException extends RuntimeException {

    public InvalidCardDetailsException(String message) {
        super(message);
    }
}
