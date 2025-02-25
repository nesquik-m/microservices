package com.example.dto.kafka;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserEvent {

    private UUID id;

    private String email;

    private String firstName;

    private String lastName;

}