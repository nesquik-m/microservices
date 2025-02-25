package com.example.security;

import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

@UtilityClass
public class SecurityUtils {

    public static UUID getUUIDFromSecurityContext() {
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentPrincipal instanceof User user && user.getUsername() != null) {
            return UUID.fromString(user.getUsername());
        }
        throw new EntityNotFoundException(ErrorCode.OBJECT_NOT_FOUND);
    }

}