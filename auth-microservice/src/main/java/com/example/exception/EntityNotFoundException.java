package com.example.exception;

import com.example.enums.ErrorCode;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
