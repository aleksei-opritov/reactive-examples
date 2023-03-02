package com.example.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RedisConfig {
    private final ReactiveRedisConnectionFactory factory;
    @Value("${example.redis.stream.key:example-message-stream}")
    private String streamKey;

    public RedisConfig(final ReactiveRedisConnectionFactory factory) {
        this.factory = factory;
    }
}
