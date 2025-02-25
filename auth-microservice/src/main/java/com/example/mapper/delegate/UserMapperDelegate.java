package com.example.mapper.delegate;

import com.example.dto.request.RegistrationRequest;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

public abstract class UserMapperDelegate implements UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registrationRequestToUser(RegistrationRequest registrationRequest) {
        return User.builder()
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .isLocked(false)
                .roles(Set.of(Role.ROLE_USER))
                .build();
    }

}