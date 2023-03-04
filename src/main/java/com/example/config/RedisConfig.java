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
    @Value("${example.redis.stream.group.name:example-group}")
    private String groupName;
    @Value("${example.redis.stream.group.consumer.name1:consumer1}")
    private String consumer1;
    @Value("${example.redis.stream.group.consumer.name2:consumer2}")
    private String consumer2;

    public RedisConfig(final ReactiveRedisConnectionFactory factory) {
        this.factory = factory;
    }
}
