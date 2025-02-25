package com.example.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountFilterDto {

    private List<UUID> ids;

    private String firstName;

    private String lastName;

    private String city;

    private String country;

    private Integer ageTo;

    private Integer ageFrom;

}