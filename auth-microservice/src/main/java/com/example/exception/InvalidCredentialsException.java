package com.example.exception;

import com.example.enums.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidCredentialsException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidCredentialsException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}