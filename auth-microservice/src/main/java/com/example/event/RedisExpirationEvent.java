package com.example.event;

import com.example.entity.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisExpirationEvent {

    @EventListener
    public void handleRedisKeyExpired(RedisKeyExpiredEvent<RefreshToken> event) {
        RefreshToken expiredRefreshToken = (RefreshToken) event.getValue();

        if (expiredRefreshToken == null) {
            throw new RuntimeException("Refresh token is null in handleRedisKeyExpiredEvent function!");
        }
    }

}