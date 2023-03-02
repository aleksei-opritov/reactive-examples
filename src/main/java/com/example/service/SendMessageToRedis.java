package com.example.service;

import com.example.config.RedisConfig;
import com.example.model.Coffee;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SendMessageToRedis implements SendMessage {
    private final RedisConfig redisConfig;

    public void send(Coffee item) {
        Jackson2JsonRedisSerializer<Coffee> serializer = new Jackson2JsonRedisSerializer(Coffee.class);
        Map<ByteBuffer, ByteBuffer> body = new HashMap();
        byte[] bArr = serializer.serialize(item);
        String streamKey = this.redisConfig.getStreamKey();
        body.put(Charset.forName("UTF-8").encode("*"), ByteBuffer.wrap(bArr));
        this.redisConfig.getFactory().getReactiveConnection().streamCommands()
                .xAdd(Charset.forName("UTF-8").encode(streamKey), body)
                .subscribe(id -> log.info("Added to Redis with id = {}", id));
    }
}
