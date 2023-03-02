package com.example.handlers;

import com.example.service.SendMessageToRedis;
import com.example.model.Coffee;
import com.example.producers.CoffeeRedisProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CoffeeHandler {
    private final SendMessageToRedis sendMessageToRedis;
    private final CoffeeRedisProducer redisProducer;
    public Mono<ServerResponse> coffeeRedis(ServerRequest request) {
        Flux<Coffee> coffee = this.redisProducer.consumeFromStream();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(coffee, Coffee.class);
    }

    public Mono<ServerResponse> coffeeToRedis(ServerRequest request) {
        Mono<Coffee> coffeeMono = request.body(BodyExtractors.toMono(Coffee.class));
        return coffeeMono.flatMap((v) -> {
            this.sendMessageToRedis.send(v);
            return ServerResponse.ok().bodyValue("Value: " + v);
        }).onErrorResume((error) -> {
            return ServerResponse.badRequest().bodyValue(error.getMessage());
        });
    }
}
