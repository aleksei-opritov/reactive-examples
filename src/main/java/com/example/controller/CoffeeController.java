package com.example.controller;

import com.example.model.Coffee;
import com.example.producers.CoffeeProducer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class CoffeeController {
    private final CoffeeProducer producer;

    @GetMapping({"/coffeeFlux"})
    public Flux<Coffee> coffeeSing() {
        return this.producer.getCoffeeFlux();
    }
}
