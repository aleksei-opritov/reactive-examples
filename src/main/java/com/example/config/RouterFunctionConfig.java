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
        RouterFunctions.Builder route = RouterFunctions.route();
        Objects.requireNonNull(handler);
        route = route.POST("/coffeeToRedis", handler::coffeeToRedis);
        return route.GET("/coffeeRedis", handler::coffeeRedis).build();
    }
}
