package com.example.service.impl;

import com.example.dto.request.RegistrationRequest;
import com.example.dto.response.RegistrationResponse;
import com.example.entity.User;
import com.example.enums.ErrorCode;
import com.example.exception.AlreadyExistsException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.InvalidCredentialsException;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import com.example.messaging.KafkaSender;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final KafkaSender kafkaSender;

    @Override
    @Transactional
    public RegistrationResponse createUser(RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        User savedUser = userRepository.save(userMapper.registrationRequestToUser(request));

        kafkaSender.sendNewUserEvent(request, savedUser);
        return new RegistrationResponse("User registered successfully", savedUser.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.getUsersByEmail(email).orElseThrow(
                () -> new InvalidCredentialsException(ErrorCode.INVALID_CREDENTIALS));
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.OBJECT_NOT_FOUND));
    }

}