package com.example.messaging;

import com.example.dto.kafka.NewUserEvent;
import com.example.dto.request.RegistrationRequest;
import com.example.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaSender {

    private final StreamBridge streamBridge;

    @Value("${spring.cloud.stream.bindings.new-user-out-0.destination}")
    private String newUserTopic;

    public void sendNewUserEvent(RegistrationRequest request, User user) {
        NewUserEvent newUserEvent = NewUserEvent.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        log.info("Sending '{}' to topic '{}'", newUserEvent, newUserTopic);

        Message<NewUserEvent> message = MessageBuilder
                .withPayload(newUserEvent)
                .setHeader("partitionKey", newUserEvent.getId())
                .build();

        streamBridge.send("new-user-out-0", message);
    }

}