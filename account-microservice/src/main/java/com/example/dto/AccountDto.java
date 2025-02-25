package com.example.dto;

import com.example.mapper.LocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private UUID id;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private String city;

    private String country;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime birthDate;

    private String about;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

}