package com.example.mapper;

import com.example.dto.request.RegistrationRequest;
import com.example.entity.User;
import com.example.mapper.delegate.UserMapperDelegate;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(UserMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User registrationRequestToUser(RegistrationRequest registrationRequest);

}