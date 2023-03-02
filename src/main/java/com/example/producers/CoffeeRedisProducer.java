package com.example.producers;

import com.example.model.Coffee;
import com.example.config.RedisConfig;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@AllArgsConstructor
public class CoffeeRedisProducer {
    private final RedisConfig redisConfig;

    public Flux<Coffee> consumeFromStream() {
        String streamKey = this.redisConfig.getStreamKey();
        StreamOffset<ByteBuffer> streamOffset = StreamOffset.fromStart(Charset.forName("UTF-8").encode(streamKey));
        Jackson2JsonRedisSerializer<Coffee> serializer = new Jackson2JsonRedisSerializer(Coffee.class);
        return this.redisConfig.getFactory().getReactiveConnection().streamCommands()
                .xRead(streamOffset)
                .mapNotNull(Record::getValue)
                .flatMap((value) -> Mono.just(
                    serializer.deserialize((value.get(Charset.forName("UTF-8").encode("*"))).array())));
    }
}
