package com.example.exception;

import com.example.enums.ErrorCode;
import lombok.Getter;

@Getter
public class RefreshTokenException extends RuntimeException {

    private final ErrorCode errorCode;

    public RefreshTokenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}