package com.example.producers;

import com.example.config.RedisConfig;
import com.example.model.Coffee;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Component
@Slf4j
@AllArgsConstructor
public class CoffeeRedisConsumerGroup implements StreamPublisher {
    private final RedisConfig redisConfig;

    @Override
    public Flux<Coffee> getStreamPublisher() {
        String streamKey = this.redisConfig.getStreamKey();
        // Read all new arriving elements with ids greater than the last one consumed by the consumer group.
        StreamOffset<ByteBuffer> streamOffset = StreamOffset.create(
                Charset.forName("UTF-8").encode(streamKey),
                ReadOffset.lastConsumed());
        Jackson2JsonRedisSerializer<Coffee> serializer = new Jackson2JsonRedisSerializer(Coffee.class);
        // Stream consumer within a consumer group
        Consumer consumer = Consumer.from(redisConfig.getGroupName(), redisConfig.getConsumer1());
        //
        return this.redisConfig.getFactory().getReactiveConnection().streamCommands()
                .xReadGroup(consumer, StreamReadOptions.empty().autoAcknowledge(), streamOffset)
                .mapNotNull(Record::getValue)
                .flatMap((value) -> Mono.just(
                    serializer.deserialize((value.get(Charset.forName("UTF-8").encode("*"))).array())));
    }
}
