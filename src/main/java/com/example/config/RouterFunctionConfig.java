package com.example.config;

import com.example.handlers.CoffeeHandler;

import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterFunctionConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(CoffeeHandler handler) {
        RouterFunctions.Builder var10000 = RouterFunctions.route();
        Objects.requireNonNull(handler);
        var10000 = var10000.POST("/coffeeToRedis", handler::coffeeToRedis);
        Objects.requireNonNull(handler);
        return var10000.GET("/coffeeRedis", handler::coffeeRedis).build();
    }
}
