package com.example.producers;

import com.example.model.Coffee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
@AllArgsConstructor
@Getter
public class CoffeeUnicastSink {
    private final Sinks.Many<Coffee> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
}
