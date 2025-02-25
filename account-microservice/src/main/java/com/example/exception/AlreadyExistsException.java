package com.example.exception;

import com.example.enums.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException {

    private final ErrorCode errorCode;

    public AlreadyExistsException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}