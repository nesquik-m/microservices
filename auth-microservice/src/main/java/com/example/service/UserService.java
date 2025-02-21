package com.example.service;

import com.example.dto.request.RegistrationRequest;
import com.example.dto.response.RegistrationResponse;
import com.example.entity.User;

import java.util.UUID;

public interface UserService {

    RegistrationResponse createUser(RegistrationRequest request);

    User getUserByEmail(String email);
//
//    UserResponse getUser();
//
//    UpdateUserResponse updateUser(UpdateUserRequest request);
//
    User getUserById(UUID userId);

}