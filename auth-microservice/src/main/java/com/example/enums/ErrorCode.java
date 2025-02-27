package com.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    EMAIL_ALREADY_EXISTS("The email is already registered."),

    INVALID_CREDENTIALS("Invalid email or password."),

    UNAUTHORIZED("Authentication is required or token is invalid."),

    ACCESS_DENIED("You do not have access to this resource."),

    OBJECT_NOT_FOUND("Object not found."),

    TOKEN_EXPIRED("Invalid refresh token.");

    private final String message;

}