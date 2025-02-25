package com.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    EMAIL_ALREADY_EXISTS("The email is already registered."),

    UNAUTHORIZED("Authentication is required or token is invalid."),

    ACCESS_DENIED("You do not have access to this resource."),

    OBJECT_NOT_FOUND("Object not found.");

    private final String message;

}