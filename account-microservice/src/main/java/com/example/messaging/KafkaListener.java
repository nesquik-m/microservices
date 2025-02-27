package com.example.messaging;

import com.example.dto.kafka.ActivityEvent;
import com.example.dto.kafka.NewUserEvent;
import com.example.mapper.AccountMapper;
import com.example.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.kafka.support.KafkaHeaders;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaListener {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @Bean
    Consumer<Message<NewUserEvent>> user() {
        return message -> {
            NewUserEvent newUser = message.getPayload();
            MessageHeaders messageHeaders = message.getHeaders();
            log.info("Received a message, topic: {}, payload: {}",
                    messageHeaders.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
                    newUser);
            accountService.createAccount(accountMapper.newUserEventToAccount(newUser));
        };
    }

    @Bean
    Consumer<Message<ActivityEvent>> activity() {
        return message -> {
            ActivityEvent activityEvent = message.getPayload();
            MessageHeaders messageHeaders = message.getHeaders();
            log.info("Received a message, topic: {}, payload: {}",
                    messageHeaders.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
                    activityEvent);
            accountService.markAccountAsOnline(activityEvent.getId());
        };
    }

}